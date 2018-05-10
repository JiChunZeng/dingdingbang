package com.dingtaifu.dingdingbang.mvp.share.presenter;

import android.app.Activity;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import com.dingtaifu.dingdingbang.R;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;

public class CustomHelper {
    private View rootView;

    public static CustomHelper of(View rootView) {
        return new CustomHelper(rootView);
    }

    private CustomHelper(View rootView) {
        this.rootView = rootView;
    }

    public void onClick(View view, TakePhoto takePhoto, Activity activity) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto, activity);
        configTakePhotoOption(takePhoto);
        switch (view.getId()) {
            case R.id.btn_photo://选择照片
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                break;
            case R.id.btn_camera://拍照
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                break;
            default:
                break;
        }
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        takePhoto.setTakePhotoOptions(builder.create());

    }

    private void configCompress(TakePhoto takePhoto, Activity activity) {

        Display display = activity.getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        int mWidth = dm.widthPixels;
        int mHeight = dm.heightPixels;

        int maxSize = Integer.parseInt("102400");
        int width = /*Integer.parseInt("900")*/mWidth;
        int height = /*Integer.parseInt("1000")*/mHeight / 3*2;
        boolean showProgressBar = true;
        boolean enableRawFile = false;
        CompressConfig config;

        config = new CompressConfig.Builder().setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();

//            LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
//            config = CompressConfig.ofLuban(option);
//            config.enableReserveRaw(enableRawFile);

        takePhoto.onEnableCompress(config, showProgressBar);


    }

    private CropOptions getCropOptions() {
        boolean withWonCrop = true;
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

}
