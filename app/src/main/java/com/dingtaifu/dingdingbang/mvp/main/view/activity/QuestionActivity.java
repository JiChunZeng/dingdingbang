package com.dingtaifu.dingdingbang.mvp.main.view.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.bean.CreditLogin;
import com.dingtaifu.dingdingbang.data.APP;
import com.dingtaifu.dingdingbang.data.ViewShowHelper;
import com.dingtaifu.dingdingbang.data.XutilsHttp;
import com.dingtaifu.dingdingbang.databinding.ActivityQuestionBinding;
import com.dingtaifu.dingdingbang.mvp.credit.view.CreditLoginActivity;
import com.dingtaifu.dingdingbang.utils.Logs;
import com.dingtaifu.dingdingbang.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;


public class QuestionActivity extends BaseActivity implements View.OnClickListener{

    private ActivityQuestionBinding mBinding;
    int check1 = 0;
    int check2 = 0;
    int check3 = 0;
    int check4 = 0;
    int check5 = 0;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_question);
        mBinding.commonTitle.tvTitle.setText("问卷调查");
        mBinding.commonTitle.ivBack.setOnClickListener(this);
        question();
        mBinding.btnCreditLoginCommit.setOnClickListener(this);
    }

    private void question() {
        try {
            List<Question> certify = new ArrayList();
            certify.add(new Question("从事职业","国企/事业单位","民营","合资/外企","个体工商户","自由职业者"));
            certify.add(new Question("年收入情况","1-5万","5-10万","10-30万","30-50万","50万以上"));
            certify.add(new Question("相关资产情况","有房有车","有房无车","无房无车","无房有车","即将有房有车"));
            certify.add(new Question("金融服务额度","一万以下","1-3万","3-10万","10-30万","30万以上"));
            certify.add(new Question("资金用途","日常消费","结婚/装修","公司经营","资金周转","购车/购房"));
            for (int i = 0; i < certify.size(); i++) {

                TextView tv = new TextView(this);
                LinearLayout.LayoutParams layoutpargms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutpargms.setMargins(20, 30, 20, 30);
                tv.setTextSize(20f);
                tv.setLayoutParams(layoutpargms);
                tv.setText((i + 1) + ". " + certify.get(i).getQuestion());
                tv.setTextColor(this.getResources().getColor(R.color.color_font_grey));
                mBinding.rvCreditLoginTipQuestion.addView(tv);

                RadioGroup rg = new RadioGroup(this);
                rg.setOrientation(LinearLayout.VERTICAL);

                for (int j = 0; j < 5; j++) {
                    String option1 = "";
                    switch (j) {
                        case 0:
                        option1 =  certify.get(i).getOptions1();
                            break;
                        case 1:
                            option1 =  certify.get(i).getOptions2();
                            break;
                        case 2:
                            option1 =  certify.get(i).getOptions3();
                            break;
                        case 3:
                            option1 =  certify.get(i).getOptions4();
                            break;
                        case 4:
                            option1 =  certify.get(i).getOptions5();
                            break;
                    }
                    RadioButton rb = new RadioButton(this);
                    layoutpargms.setMargins(20, 30, 20, 30);
                    rb.setLayoutParams(layoutpargms);
                    rb.setTextSize(20f);
                    rb.setText(option1);
                    rb.setId(j + 1);
                    rb.setTextColor(this.getResources().getColor(R.color.color_font_grey));
                    rg.addView(rb);
                    Logs.i(option1);
                }
                final int finalI = i;
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (finalI) {
                            case 0:
                                check1 = checkedId;
                                return;
                            case 1:
                                check2 = checkedId;
                                return;
                            case 2:
                                check3 = checkedId;
                                return;
                            case 3:
                                check4 = checkedId;
                                return;
                            case 4:
                                check5 = checkedId;
                                return;
                        }
                    }
                });

                mBinding.rvCreditLoginTipQuestion.addView(rg);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_credit_login_commit:
                if (check1!=0&check2!=0&check3!=0&check4!=0&check5!=0) {
                    RequestParams params = new RequestParams(APP.questionnaire);
                    params.addBodyParameter("str", check1+","+check2+","+check3+","+check4+","+check5);
                    XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<CreditLogin>(){
                        @Override
                        public void onXUtilsSuccess(CreditLogin creditLogin) {
                            if (creditLogin.getCode().equals("1")) {
                                ToastUtil.show(QuestionActivity.this, creditLogin.getMessage());
                                QuestionActivity.this.finish();
                            } else {
                                ToastUtil.show(QuestionActivity.this, creditLogin.getMessage());
                            }
                        }

                        @Override
                        public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {

                        }
                    });

                }else{
                    ToastUtil.show(this, "还有问题没回答");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private class Question{
        String question ;
        String options1;
        String options2;
        String options3;
        String options4;
        String options5;

        public Question(String question, String options1, String options2, String options3, String options4, String options5) {
            this.question = question;
            this.options1 = options1;
            this.options2 = options2;
            this.options3 = options3;
            this.options4 = options4;
            this.options5 = options5;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getOptions1() {
            return options1;
        }

        public void setOptions1(String options1) {
            this.options1 = options1;
        }

        public String getOptions2() {
            return options2;
        }

        public void setOptions2(String options2) {
            this.options2 = options2;
        }

        public String getOptions3() {
            return options3;
        }

        public void setOptions3(String options3) {
            this.options3 = options3;
        }

        public String getOptions4() {
            return options4;
        }

        public void setOptions4(String options4) {
            this.options4 = options4;
        }

        public String getOptions5() {
            return options5;
        }

        public void setOptions5(String options5) {
            this.options5 = options5;
        }
    }
}
