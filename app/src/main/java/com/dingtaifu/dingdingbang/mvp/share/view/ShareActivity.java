package com.dingtaifu.dingdingbang.mvp.share.view;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.bean.UserLogin;
import com.dingtaifu.dingdingbang.data.APP;
import com.dingtaifu.dingdingbang.data.ViewShowHelper;
import com.dingtaifu.dingdingbang.data.XutilsHttp;
import com.dingtaifu.dingdingbang.databinding.ActivityShareBinding;
import com.dingtaifu.dingdingbang.mvp.credit.view.RecoverPasswordActivity;
import com.dingtaifu.dingdingbang.mvp.share.presenter.CustomHelper;
import com.dingtaifu.dingdingbang.utils.Logs;
import com.dingtaifu.dingdingbang.utils.ToastUtil;

import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.xutils.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.R.attr.path;

public class ShareActivity extends TakePhotoActivity implements View.OnClickListener {
    private ActivityShareBinding mBinding;
    private CustomHelper customHelper;
    private ImageView iv_share_picture;
    View view;
    ImageView shareVtv;
    PopupWindow popupWindow;
    public static String[] WEEK = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public static final int WEEKDAYS = 7;
    private int[] image = {R.mipmap.image1,R.mipmap.image2,R.mipmap.image3,R.mipmap.image4,R.mipmap.image5};



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("");
        findViewById(R.id.iv_back).setOnClickListener(this);
        iv_share_picture = findViewById(R.id.iv_share_picture);

        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        view.findViewById(R.id.btn_camera).setOnClickListener(this);
        view.findViewById(R.id.btn_photo).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);

        findViewById(R.id.iv_share_camera).setOnClickListener(this);
        findViewById(R.id.iv_share_share).setOnClickListener(this);
        customHelper = CustomHelper.of(contentView);
        shareVtv = findViewById(R.id.share_vtv);
        TextView tvShareDateMonth = findViewById(R.id.tv_share_date_month);
        TextView tvShareDateDay = findViewById(R.id.tv_share_date_day);
        TextView tvShareDateWeek = findViewById(R.id.tv_share_date_week);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        tvShareDateMonth.setText((calendar.get(Calendar.MONTH) + 1) + ". ");
        if (calendar.get(Calendar.DAY_OF_MONTH) >= 10){
            tvShareDateDay.setText(calendar.get(Calendar.DAY_OF_MONTH) + "");
         }else{
            tvShareDateDay.setText("0"+calendar.get(Calendar.DAY_OF_MONTH));
        }
        tvShareDateWeek.setText(DateToWeek(date));
//        RequestParams params = new RequestParams(APP.dictum);
        Random rand = new Random();
        int i = rand.nextInt(4);
        shareVtv.setImageDrawable(this.getResources().getDrawable(image[i]));
//        XutilsHttp.getInstance().get(params, new ViewShowHelper.ClickListenr<UserLogin>() {
//
//                    @Override
//                    public void onXUtilsSuccess(UserLogin userLogin) {
//                       if (userLogin.getCode().equals("1")){
//                           String  strText =userLogin.getMessage() ;
//                           strText = getTextHtoV(strText);
//                           shareVtv.setText(strText);
//                       }
//                    }
//
//                    @Override
//                    public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
//
//                    }
//                });

        queryClock();
//        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share_camera:
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
                popupWindow.setOutsideTouchable(true);
                View parent = LayoutInflater.from(this).inflate(R.layout.activity_framelayout, null);
                popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                //popupWindow在弹窗的时候背景半透明
                final WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 0.5f;
                getWindow().setAttributes(params);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        params.alpha = 1.0f;
                        getWindow().setAttributes(params);
                    }
                });
                break;
            case R.id.btn_camera:
                customHelper.onClick(v, getTakePhoto(), this);
                popupWindow.dismiss();
                break;
            case R.id.btn_photo:
                customHelper.onClick(v, getTakePhoto(), this);
                popupWindow.dismiss();
                break;
            case R.id.btn_cancel:
                popupWindow.dismiss();
                break;
            case R.id.iv_share_share:
                /**
                 * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
                 */
                if (Build.VERSION.SDK_INT >= 23) {
                    int REQUEST_CODE_CONTACT = 101;
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    //验证是否许可权限
                    for (String str : permissions) {
                        if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                            //申请权限
                            this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                            return;
                        }
                    }
                }
                Logs.e(saveImageToGallery(this,shotActivityNoBar(this)).getPath());

                shareImg("分享","主题"," 内容",saveImageToGallery(this,shotActivityNoBar(this)));
                break;

            default:

                break;
        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {

        Glide.with(this).load(new File(images.get(images.size() - 1).getCompressPath())).into(iv_share_picture);

    }

    public static String DateToWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > WEEKDAYS) {
            return null;
        }

        return WEEK[dayIndex - 1];
    }


    private void queryClock() {
        RequestParams params = new RequestParams(APP.share_query);
        XutilsHttp.getInstance().get(params, new ViewShowHelper.ClickListenr<UserLogin>() {
            @Override
            public void onXUtilsSuccess(UserLogin user) {
                if (user.getCode().equals("1")) {
                    ToastUtil.show(ShareActivity.this, user.getMessage());
                } else {
                    new SweetAlertDialog(ShareActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("提示")
                            .setContentText("您还未签到，是否签到？")
                            .setConfirmText("确认")
                            .setCancelText("不了")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    getData();
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
                Logs.e("", ex.getMessage());
            }
        });
    }

    private void getData() {
        RequestParams params = new RequestParams(APP.share_Punch);
        XutilsHttp.getInstance().get(params, new ViewShowHelper.ClickListenr<UserLogin>() {
            @Override
            public void onXUtilsSuccess(UserLogin user) {
                if (user.getCode().equals("1")) {
                    ToastUtil.show(ShareActivity.this, user.getMessage());
                } else {
                    //登录失败
                    ToastUtil.show(ShareActivity.this, user.getMessage());
                }
            }

            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
                Logs.e("", ex.getMessage());
            }
        });
    }
    /*
        * 功能：多行横排文本转换为多列直排（以换行符 \n 作为断行标记）
        * 说明：可支持1～N行，但要求有换行标记符，或稍作修改以标点符号换行
        * @param: strText
        * @return: 返回值为行列转置后的多行文本
        */
    public static String getTextHtoV(String strText){
        String strResult = "";
        String br = "\n";      //断行标记，这里可改用逗号或分号等字符
        String brs = "，";      //断行标记，这里可改用逗号或分号等字符
        String strArr[] = strText.split(brs);
        int nMaxLen = 0;      //各行最多字符数
        int nLines = strArr.length;    //总共的行数
        char charArr[][] = new char[nLines][];    //字符陈列（即二维数组）
        for (int i = 0; i < nLines; i++) {
            String str = strArr[i];
            int n = str.length();

            //以最长的行的字符数（即原列数）作为目标陈列的行数
            if (n > nMaxLen) nMaxLen = n;
            charArr[i] = strArr[i].toCharArray();
        }

        //行列转换
        for (int i = 0; i < nMaxLen; i++) {
            for (int j = 0; j < nLines; j++) {
                //若短句字符已“用完”则以空格代替
                char c = i < charArr[j].length ? charArr[j][i] : '　';
                strResult += String.valueOf(c);

                //两列文字之间加空格，不需要的话请注释掉下面一行
                if (j < nLines - 1) strResult += " ";  //★
            }
            strResult += br;   //添加换行符
        }

        return strResult;
    }
    public Bitmap shotActivityNoBar(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();

        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();

        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);

        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;
    }

    /**
     * 分享图片和文字内容
     *
     * @param dlgTitle
     *            分享对话框标题
     * @param subject
     *            主题
     * @param content
     *            分享内容（文字）
     * @param uri
     *            图片资源URI
     */
    private void shareImg(String dlgTitle, String subject, String content,
                          Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (content != null && !"".equals(content)) {
            intent.putExtra(Intent.EXTRA_TEXT, content);
        }

        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            startActivity(intent);
        }
    }


    public Uri  saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 最后通知图库更新
       Uri uri = Uri.parse("file://" + file.getPath());
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
        return uri;
    }


}
