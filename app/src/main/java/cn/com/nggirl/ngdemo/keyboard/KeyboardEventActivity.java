package cn.com.nggirl.ngdemo.keyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.nggirl.ngdemo.R;
import cn.com.nggirl.ngdemo.business.DataServer;

public class KeyboardEventActivity extends AppCompatActivity {

    @BindView(R.id.rl_list)
    RecyclerView mRlList;

    public static void start(Context context) {
        context.startActivity(new Intent(context, KeyboardEventActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_event);
        ButterKnife.bind(this);

        mRlList.setLayoutManager(new LinearLayoutManager(this));
        mRlList.setAdapter(new QuickAdapter());
    }

    private class QuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
        QuickAdapter() {
            super(DataServer.getMultipleItemData());
            addItemType(MultipleItem.TEXT, R.layout.item_text_view);
            addItemType(MultipleItem.IMG, R.layout.item_image_view);
            addItemType(MultipleItem.IMG_TEXT, R.layout.item_img_text_view);
        }

        @Override
        protected void convert(BaseViewHolder helper, MultipleItem item) {
            switch (helper.getItemViewType()) {
                case MultipleItem.TEXT:
                    helper.setText(R.id.tv, item.getContent());
                    EditText editText = helper.getView(R.id.et1);
                    break;
                case MultipleItem.IMG_TEXT:
                    switch (helper.getLayoutPosition() %
                            2) {
                        case 0:
                            helper.setImageResource(R.id.iv, R.mipmap.animation_img1);
                            break;
                        case 1:
                            helper.setImageResource(R.id.iv, R.mipmap.animation_img1);
                            break;

                    }
                    break;
            }
        }
    }
}
