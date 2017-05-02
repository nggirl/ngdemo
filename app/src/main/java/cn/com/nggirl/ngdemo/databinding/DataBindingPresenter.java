package cn.com.nggirl.ngdemo.databinding;

public class DataBindingPresenter implements DataBindingContract.Presenter {
    private DataBindingContract.View mView;

    public DataBindingPresenter(DataBindingContract.View view) {
        mView = view;
    }

    @Override
    public void onShowData(TemperatureData temperatureData) {
        mView.showData(temperatureData);
    }

    @Override
    public void showList() {
        mView.startSecondActivity();
    }
}
