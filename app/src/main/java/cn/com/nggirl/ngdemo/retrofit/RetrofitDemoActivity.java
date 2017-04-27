package cn.com.nggirl.ngdemo.retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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
    @BindView(R.id.tv_download_progress)
    TextView mTvDownloadProgress;
    @BindView(R.id.btn_download)
    Button mBtnDownload;
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
    public void showDownloadProgress(String progress) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @OnClick({R.id.btn_query, R.id.btn_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
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
                break;
            case R.id.btn_download:
                if (mPresenter != null) {
                    mPresenter.downloadFile("https://photosd.nggirl.com.cn/work/7bca1abde2fc451cb6535e1a3ddfdfbb.jpg");
                }
                break;
        }
    }
}
