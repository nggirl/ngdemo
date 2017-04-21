package cn.com.nggirl.ngdemo.retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.nggirl.ngdemo.R;
import cn.com.nggirl.ngdemo.business.bean.Contributor;

public class RetrofitDemoActivity extends AppCompatActivity implements RetrofitDemoContract.View {

    @BindView(R.id.tv_retrofit_content)
    TextView tvRetrofitContent;
    @BindView(R.id.et_retrofit_input_owner)
    EditText etRetrofitInputUser;
    @BindView(R.id.et_retrofit_input_repo)
    EditText etRetrofitInputRepo;
    @BindView(R.id.btn_query)
    Button btnQuery;
    private RetrofitDemoContract.Presenter mPresenter;

    public static void start(Context context) {
        context.startActivity(new Intent(context, RetrofitDemoActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        new RetrofitDemoPresenter(this);
    }

    @OnClick(R.id.btn_query)
    public void onViewClicked() {
        final String userInput = etRetrofitInputUser.getText().toString().trim();
        final String repoInput = etRetrofitInputRepo.getText().toString().trim();
        if (TextUtils.isEmpty(userInput)) {
            Toast.makeText(RetrofitDemoActivity.this, "请输入查询仓库拥有者", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(repoInput)) {
            Toast.makeText(RetrofitDemoActivity.this, "请输入查询仓库", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mPresenter != null) {
            mPresenter.contributors(userInput, repoInput);
        }
    }

    @Override
    public void setPresenter(RetrofitDemoContract.Presenter presenter) {
        if (presenter == null) {
            throw new NullPointerException();
        }
        mPresenter = presenter;

        mPresenter.contributors("nggirl", "ngdemo");
    }

    @Override
    public void showContributors(List<Contributor> contributors) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Contributor contributor : contributors) {
            stringBuilder.append(contributor.toString());
            stringBuilder.append("\n");
        }

        tvRetrofitContent.setText(stringBuilder.toString());
    }

    @Override
    public void showContributorsError(String message) {
        tvRetrofitContent.setText(message);
    }

    @Override
    public void showContributorsEmpty() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }
}
