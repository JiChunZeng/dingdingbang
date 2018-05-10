package com.dingtaifu.dingdingbang.mvp.credit.view;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.bean.CreditLoginName;
import com.dingtaifu.dingdingbang.data.APP;
import com.dingtaifu.dingdingbang.data.ViewShowHelper;
import com.dingtaifu.dingdingbang.data.XutilsHttp;
import com.dingtaifu.dingdingbang.databinding.ActivityRecoverLoginNameBinding;
import com.dingtaifu.dingdingbang.utils.Logs;
import com.dingtaifu.dingdingbang.utils.ToastUtil;

import org.xutils.http.RequestParams;

public class RecoverLoginNameActivity extends BaseActivity implements View.OnClickListener {
    private ActivityRecoverLoginNameBinding mBinding;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recover_login_name);
        mBinding.layoutCommonTitle.ivBack.setOnClickListener(this);
        mBinding.layoutCommonTitle.tvTitle.setText("找回登录名");

        mBinding.layoutRecoverNameId.btnRecoverNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_recover_next:
                if (mBinding.layoutRecoverNameId.etRecoverId.getText().toString().equals("") |
                        mBinding.layoutRecoverNameId.etRecoverName.getText().toString().equals("")) {
                    ToastUtil.show(this, "请填写完整信息");
                    return;
                }

                sendData();

                break;

        }
    }

    private void sendData() {
        RequestParams params = new RequestParams(APP.credit_findName);
        params.addBodyParameter("name", mBinding.layoutRecoverNameId.etRecoverName.getText().toString());
        params.addBodyParameter("idCard", mBinding.layoutRecoverNameId.etRecoverId.getText().toString());

        XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<CreditLoginName>() {
            @Override
            public void onXUtilsSuccess(CreditLoginName creditLoginName) {
                if (creditLoginName.getCode().equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecoverLoginNameActivity.this);
                    @SuppressLint("ResourceType") View view = View
                            .inflate(RecoverLoginNameActivity.this, R.layout.dialog_recover_login_name, null);
                    builder.setView(view);
                    builder.setCancelable(true);
                    Button btn_comfirm = (Button) view
                            .findViewById(R.id.btn_recover_login_name);//确定按钮
                    //取消或确定按钮监听事件处理
                    btn_comfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                } else {
                    ToastUtil.show(RecoverLoginNameActivity.this, creditLoginName.getMessage());
                }
            }

            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
                Logs.e("", ex.getMessage());

            }
        });
    }
}
