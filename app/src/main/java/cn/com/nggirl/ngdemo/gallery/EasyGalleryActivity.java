package cn.com.nggirl.ngdemo.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.nggirl.ngdemo.FileOperationUtils;
import cn.com.nggirl.ngdemo.R;

public class EasyGalleryActivity extends AppCompatActivity {
    public static final String TAG = EasyGalleryActivity.class.getSimpleName();

    public static final String EXTRA_INDEX = "index";
    public static final String EXTRA_LIST = "list";

    private View rootView;
    private ViewPager mViewPager;
    private CirclePageIndicator mPageIndicator;

    private int index;
    private List<String> photoList;

    public static void start(Activity context, int index, @NonNull List<String> list) {
        Intent intent = new Intent(context, EasyGalleryActivity.class);
        intent.putExtra(EXTRA_INDEX, index);
        intent.putStringArrayListExtra(EXTRA_LIST, (ArrayList<String>) list);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = getLayoutInflater().inflate(R.layout.activity_gallery, null);
        setContentView(rootView);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

        index = getIntent().getIntExtra(EXTRA_INDEX, 0);
        photoList = getIntent().getStringArrayListExtra(EXTRA_LIST);

        if (photoList != null && !photoList.isEmpty()) {

            showGallery(index, checkUrls(photoList));
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.gallery_photo_list_error), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private List<String> checkUrls(List<String> list) {
        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String picUrl = deleteSuffix(s);
            imageUrls.add(picUrl);
        }
        return imageUrls;
    }

    private String deleteSuffix(String string) {
        if (string.indexOf("@") == -1) {
            return string;
        }
        String[] split = string.split("\\@");
        return split[0];
    }

    private void savePhoto() {
        if (photoList == null || photoList.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.gallery_save_failed), Toast.LENGTH_SHORT).show();
            return;
        }

        if (index < 0 || index >= photoList.size()) {
            Toast.makeText(getApplicationContext(), getString(R.string.gallery_save_failed), Toast.LENGTH_SHORT).show();
            return;
        }

        final String photoUrl = photoList.get(index);

        if (TextUtils.isEmpty(photoUrl)) {
            Toast.makeText(getApplicationContext(), getString(R.string.gallery_save_failed), Toast.LENGTH_SHORT).show();
            return;
        }

        final Uri uri = Uri.parse(photoUrl);
        if (isLocalCached(uri)) {
            final File localCache = getLocalCache(uri);
            Log.e(TAG, "by cache saveImageToGallery");
            saveImageToGallery(localCache);
        } else {
            Log.e(TAG, "fetch ");
            /*FrescoUtils.loadBitmap(getApplicationContext(), photoUrl, new FrescoUtils.BitmapListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    Log.e(TAG, "onSuccess saveImageToGallery ");
                    saveImageToGallery(getApplicationContext(), bitmap);
                }

                @Override
                public void onFail() {
                    Toast.makeText(getApplicationContext(), getString(R.string.gallery_save_failed), Toast.LENGTH_SHORT).show();
                }
            });*/
        }
    }

    public void showImageToast() {
        LayoutInflater inflater = getLayoutInflater();
        //View layout = inflater.inflate(R.layout.toast_save_success, null);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        //toast.setView(layout);
        toast.show();
    }

    public File getLocalCache(Uri uri) {
        ImageRequest imageRequest = ImageRequest.fromUri(uri);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest, getApplicationContext());
        BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
        return ((FileBinaryResource) resource).getFile();
    }

    public boolean isLocalCached(Uri uri) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<Boolean> dataSource = imagePipeline.isInDiskCache(uri);
        if (dataSource == null) {
            return false;
        }

        ImageRequest imageRequest = ImageRequest.fromUri(uri);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest, getApplicationContext());
        BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
        return resource != null && dataSource.getResult() != null && dataSource.getResult();
    }

    public void saveImageToGallery(File srcFile) {
        // 首先保存图片
        File reviews = new File(FileOperationUtils.getExternalCacheDirectory(), "reviews");

        final boolean existsDir = FileOperationUtils.createOrExistsDir(reviews);
        if (!existsDir) {
            Log.e(TAG, "failed to create folder");
            Toast.makeText(getApplicationContext(), getString(R.string.gallery_save_failed), Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File destFile = new File(reviews, fileName);
        FileOperationUtils.copyFile(srcFile, destFile);

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), destFile.getAbsolutePath(), fileName, null);
           showImageToast();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.gallery_save_failed), Toast.LENGTH_SHORT).show();
            return;
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + destFile)));
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File reviews = new File(FileOperationUtils.getExternalCacheDirectory(), "reviews");

        final boolean existsDir = FileOperationUtils.createOrExistsDir(reviews);
        if (!existsDir) {
            Log.e(TAG, "failed to create folder");
            Toast.makeText(getApplicationContext(), getString(R.string.gallery_save_failed), Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File destFile = new File(reviews, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(destFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showImageToast();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.gallery_save_failed), Toast.LENGTH_SHORT).show();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),destFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.gallery_save_failed), Toast.LENGTH_SHORT).show();
            return;
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + destFile.getPath())));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        //showLoadingView(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    private void showGallery(int index, @NonNull List<String> list) {
        if (index >= list.size()) {
            Log.e(TAG, getString(R.string.gallery_index_out_of_bound));
            //showLoadingView(false);
            finish();
            return;
        }

        List<PhotoFragment> photoFragments = new ArrayList<>();
        for (String url : list) {
            final PhotoFragment fragment = PhotoFragment.newInstance(url);
            photoFragments.add(fragment);
        }

        GalleryPagerAdapter adapter = new GalleryPagerAdapter(getSupportFragmentManager(), photoFragments);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                EasyGalleryActivity.this.index = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setOffscreenPageLimit(2);

        mViewPager.setCurrentItem(index);

        mPageIndicator.setViewPager(mViewPager);

    }

    public void showFileOperationDialog() {
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View view = getLayoutInflater().inflate(R.layout.ppw_file, null);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        View empty = view.findViewById(R.id.view_empty);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        TextView savePhoto = (TextView) view.findViewById(R.id.tv_save_photo);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);

        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Log.d(TAG, "savePhoto");
                savePhoto();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private class GalleryPagerAdapter extends FragmentStatePagerAdapter {

        private List<PhotoFragment> list;

        GalleryPagerAdapter(FragmentManager fm, List<PhotoFragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

}
