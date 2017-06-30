package cn.com.nggirl.ngdemo.gallery;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import cn.com.nggirl.ngdemo.R;
import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

public class PhotoFragment extends Fragment implements OnPhotoTapListener {
    public static final String TAG = PhotoFragment.class.getSimpleName();

    private PhotoDraweeView easyImageView;
    private RelativeLayout mLoadingView;

    private EasyGalleryActivity mActivity;

    public static final String EXTRA_URL = "url";

    public static PhotoFragment newInstance(String url) {
        final PhotoFragment photoFragment = new PhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_URL, url);
        photoFragment.setArguments(bundle);
        return photoFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (EasyGalleryActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingView = (RelativeLayout) view.findViewById(R.id.loading_view);
        easyImageView = (PhotoDraweeView) view.findViewById(R.id.iv_gallery_photo);
        easyImageView.setOnPhotoTapListener(this);
        easyImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "long click ");
                mActivity.showFileOperationDialog();
                return true;
            }
        });
    }

    public void showLoadingView(boolean show) {
        mLoadingView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String url = getArguments().getString(EXTRA_URL);

        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(Uri.parse(url));
        controller.setOldController(easyImageView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {

            @Override
            public void onSubmit(String id, Object callerContext) {
                super.onSubmit(id, callerContext);
                showLoadingView(true);
            }

            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                showLoadingView(false);

                if (imageInfo == null || easyImageView == null) {
                    return;
                }
                easyImageView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                Toast.makeText(getActivity().getApplication(), getString(R.string.gallery_load_error), Toast.LENGTH_SHORT).show();
                showLoadingView(false);
            }
        });
        easyImageView.setController(controller.build());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        getActivity().finish();
    }
}
