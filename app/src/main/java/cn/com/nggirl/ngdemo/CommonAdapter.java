package cn.com.nggirl.ngdemo;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.com.nggirl.ngdemo.anim.Status;
import cn.com.nggirl.ngdemo.business.DataServer;

public class CommonAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
    public CommonAdapter() {
        super(R.layout.layout_animation, DataServer.getSampleData(100));
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetText).addOnClickListener(R.id.tweetName);
        switch (helper.getLayoutPosition() %
                3) {
            case 0:
            case 1:
            case 2:
                SimpleDraweeView draweeView = helper.getView(R.id.iv_fresco_big_pic);
                draweeView.setAspectRatio(0.1812063163f);
                draweeView.setImageURI("file://" + "/storage/emulated/0/DCIM/big_pic/超长图3.jpg");
                break;
        }
        helper.setText(R.id.tweetName, "Hoteis in Rio de Janeiro");
        String msg = "\"He was one of Australia's most of distinguished artistes, renowned for his portraits\"";

    }
}