package cn.com.nggirl.ngdemo.mvvm;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.com.nggirl.ngdemo.R;
import cn.com.nggirl.ngdemo.business.source.TasksRepository;
import cn.com.nggirl.ngdemo.business.source.local.TasksLocalDataSource;
import cn.com.nggirl.ngdemo.databinding.DataBindingActivity;
import cn.com.nggirl.ngdemo.databinding.TaskFragBinding;
import cn.com.nggirl.ngdemo.databinding.TaskItemBinding;
import cn.com.nggirl.ngdemo.databinding.TaskItemViewModel;

public class TasksActivity extends AppCompatActivity {
    private TasksViewModel mTasksViewModel;

    private TaskFragBinding mTasksFragBinding;
    private TasksAdapter mListAdapter;

    public static void start(Context context) {
        context.startActivity(new Intent(context, TasksActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTasksViewModel = new TasksViewModel(
                TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
                        TasksLocalDataSource.getInstance(this)),
                this);

        mTasksFragBinding = DataBindingUtil.setContentView(this, R.layout.task_frag);
        mTasksFragBinding.setViewmodel(mTasksViewModel);

        setupListAdapter();
    }

    private void setupListAdapter() {
        ListView listView =  mTasksFragBinding.tasksList;

        mListAdapter = new TasksAdapter(
                new ArrayList<Task>(0),
                this,
                TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
                        TasksLocalDataSource.getInstance(this)),
                mTasksViewModel);
        listView.setAdapter(mListAdapter);
    }

    public static class TasksAdapter extends BaseAdapter {

        private final TasksViewModel mTasksViewModel;

        private List<Task> mTasks;

        private TasksRepository mTasksRepository;

        public TasksAdapter(List<Task> tasks, TasksActivity taskItemNavigator,
                            TasksRepository tasksRepository,
                            TasksViewModel tasksViewModel) {
            mTasksRepository = tasksRepository;
            mTasksViewModel = tasksViewModel;
            setList(tasks);

        }

        public void replaceData(List<Task> tasks) {
            setList(tasks);
        }

        @Override
        public int getCount() {
            return mTasks != null ? mTasks.size() : 0;
        }

        @Override
        public Task getItem(int i) {
            return mTasks.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Task task = getItem(i);
            TaskItemBinding binding;
            if (view == null) {
                // Inflate
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

                // Create the binding
                binding = TaskItemBinding.inflate(inflater, viewGroup, false);
            } else {
                // Recycling view
                binding = DataBindingUtil.getBinding(view);
            }

            final TaskItemViewModel viewmodel = new TaskItemViewModel(
                    viewGroup.getContext().getApplicationContext(),
                    mTasksRepository
            );

            //viewmodel.setNavigator(mTaskItemNavigator);

            binding.setViewmodel(viewmodel);
            // To save on PropertyChangedCallbacks, wire the item's snackbar text observable to the
            // fragment's.
            viewmodel.snackbarText.addOnPropertyChangedCallback(
                    new Observable.OnPropertyChangedCallback() {
                        @Override
                        public void onPropertyChanged(Observable observable, int i) {
                            mTasksViewModel.snackbarText.set(viewmodel.getSnackbarText());
                        }
                    });
            viewmodel.setTask(task);

            return binding.getRoot();
        }


        private void setList(List<Task> tasks) {
            mTasks = tasks;
            notifyDataSetChanged();
        }
    }
}
