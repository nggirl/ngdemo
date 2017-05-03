package cn.com.nggirl.ngdemo.databinding;

import android.content.Context;

import cn.com.nggirl.ngdemo.business.source.TasksRepository;

/**
 * Listens to user actions from the list item in ({@link TasksFragment}) and redirects them to the
 * Fragment's actions listener.
 */
public class TaskItemViewModel extends TaskViewModel {

    public TaskItemViewModel(Context context, TasksRepository tasksRepository) {
        super(context, tasksRepository);
    }

    /**
     * Called by the Data Binding library when the row is clicked.
     */
    public void taskClicked() {
        String taskId = getTaskId();
        if (taskId == null) {
            // Click happened before task was loaded, no-op.
            return;
        }
        //openTaskDetails(taskId);
    }
}