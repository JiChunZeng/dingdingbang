package com.dingtaifu.dingdingbang.mvp.main.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.base.BaseFragment;
import com.dingtaifu.dingdingbang.bean.Account;
import com.dingtaifu.dingdingbang.bean.UserLogin;
import com.dingtaifu.dingdingbang.data.APP;
import com.dingtaifu.dingdingbang.data.ViewShowHelper;
import com.dingtaifu.dingdingbang.data.XutilsHttp;
import com.dingtaifu.dingdingbang.databinding.FragmentEvaluateBinding;
import com.dingtaifu.dingdingbang.mvp.login.view.LoginActivity;
import com.dingtaifu.dingdingbang.mvp.main.model.Classes;
import com.dingtaifu.dingdingbang.mvp.main.model.College;
import com.dingtaifu.dingdingbang.mvp.main.model.Students;
import com.dingtaifu.dingdingbang.mvp.main.presenter.SimpleExpandableListViewAdapter;
import com.dingtaifu.dingdingbang.mvp.main.view.activity.QuestionActivity;
import com.dingtaifu.dingdingbang.utils.Logs;
import com.dingtaifu.dingdingbang.utils.ToastUtil;
import com.example.radarviewlib.RadarData;
import com.example.radarviewlib.RadarView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




public class EvaluateFragment extends BaseFragment implements View.OnClickListener {
    private RadarView mRadarView;
    private FragmentEvaluateBinding mBinding;
    private ExpandableListView listview;
    private List<College> colleges = new ArrayList<>();
    private List<College> historys = new ArrayList<>();
        private List<College> collegesliabilitiesa = new ArrayList<>();
    private List<College> collegesinvestigation = new ArrayList<>();
    RadarData data;
    SimpleExpandableListViewAdapter adapter ,historysadapter,liabilitiesadapter,investigationadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluate, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        return mBinding.getRoot();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void loadData() {
        mBinding.radarView.setEmptyHint("");
        mBinding.radarView.animeValue(3000);

        List<Float> max = new ArrayList<>();
        Collections.addAll(max, 5f, 5f, 5f, 5f);
        mBinding.radarView.setMaxValues(max);

//        List<Integer> layerColor = new ArrayList<>();
//        Collections.addAll(layerColor, 0x3300bcd4, 0x3303a9f4, 0x335677fc, 0x333f51b5, 0x33673ab7);
//        mBinding.radarView.setLayerColor(layerColor);

        List<String> test = new ArrayList<>();
        Collections.addAll(test, "", "", "", "");
        mBinding.radarView.setVertexText(test);


//        List<Integer> res = new ArrayList<>();
//        Collections.addAll(res, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
//                R.mipmap.ic_launcher);
//        mBinding.radarView.setVertexIconResid(res);

        List<Float> values = new ArrayList<>();
        Collections.addAll(values, 2f, 4f, 4f, 3f);

        data = new RadarData(values,R.color.text_color);
        mBinding.radarView.addData(data);

        initData();

        initadapter();
        inithistorysadapter();
        initliabilitiesadapter();
        initinvestigationadapetr();
        adapter = new SimpleExpandableListViewAdapter(colleges, mActivity);
        mBinding.expandablelistviewFerformance.setAdapter(adapter);
        //历史
        historysadapter = new SimpleExpandableListViewAdapter(historys, mActivity);
        mBinding.expandableListViewHistory.setAdapter(historysadapter);

        liabilitiesadapter = new SimpleExpandableListViewAdapter(collegesliabilitiesa, mActivity);
        mBinding.expandableListViewLiabilities.setAdapter(liabilitiesadapter);

        investigationadapter = new SimpleExpandableListViewAdapter(collegesinvestigation, mActivity);
        mBinding.expandableListViewInvestigation.setAdapter(investigationadapter);

        mBinding.expandablelistviewFerformance.setVisibility(View.VISIBLE);
        mBinding.expandableListViewHistory.setVisibility(View.GONE);
        mBinding.expandableListViewInvestigation.setVisibility(View.GONE);
        mBinding.expandableListViewLiabilities.setVisibility(View.GONE);

        mBinding.expandListviewFerformanceTv.setOnClickListener(this);
        mBinding.expandListviewHistoryTv.setOnClickListener(this);
        mBinding.expandListviewInvestigationTv.setOnClickListener(this);
        mBinding.expandListviewLiabilitiesTv.setOnClickListener(this);
        mBinding.expandListviewFerformanceTvs.setOnClickListener(this);
        mBinding.expandListviewHistoryTvs.setOnClickListener(this);
        mBinding.expandListviewInvestigationTvs.setOnClickListener(this);
        mBinding.expandListviewLiabilitiesTvs.setOnClickListener(this);
        mBinding.wenjuan.setOnClickListener(this);
    }

    private void initinvestigationadapetr() {
        collegesinvestigation.clear();
        College college = new College();
        college.name = "查询次数";
        college.icon = R.drawable.icon;
        List<Classes> classesList = new ArrayList<>();
        Classes classes = new Classes();
        classes.name = "个人查询";
        classes.value = "0";
        List<Students> list = new ArrayList<>();
        classes.students = list;
        classesList.add(classes);
        //
        Classes classes1 = new Classes();
        classes1.name = "机构查询";
        classes1.value = "0";
        List<Students> list1 = new ArrayList<>();
        classes1.students =list1;
        classesList.add(classes1);
        college.classList = classesList;
        collegesinvestigation.add(college);
    }

    private void initliabilitiesadapter() {
        collegesliabilitiesa.clear();
        College college = new College();
        college.name = "信用卡使用率";
        college.icon = R.drawable.icon;
        List<Classes> classesList = new ArrayList<>();
        Classes classes = new Classes();
        classes.name = "使用额度";
        classes.value = "0";
        List<Students> list = new ArrayList<>();
        classes.students = list;
        classesList.add(classes);
        //
        Classes classes1 = new Classes();
        classes1.name = "授信额度";
        classes1.value = "0";
        List<Students> list1 = new ArrayList<>();
        classes1.students =list1;
        classesList.add(classes1);
        college.classList = classesList;
        collegesliabilitiesa.add(college);


        College college1 = new College();
        college1.name = "贷款总额";
        college1.icon = R.drawable.icon;
        List<Classes> cl = new ArrayList<>();
        Classes cs = new Classes();
        cs.name = "贷款总额";
        cs.value = "0";
        List<Students> list3 = new ArrayList<>();
        cs.students = list3;
        cl.add(cs);
        //
        Classes cs1 = new Classes();
        cs1.name = "未还金额";
        cs1.value = "0";
        List<Students> list4 = new ArrayList<>();
        cs1.students =list4;
        cl.add(classes1);
        college1.classList = cl;
        collegesliabilitiesa.add(college1);
    }

    private void inithistorysadapter() {
        historys.clear();
        College college = new College();
        college.name = "信用长度";
        college.icon = R.drawable.icon;
        List<Classes> classesList = new ArrayList<>();
        Classes classes = new Classes();
        classes.name = "首次借贷详情";
        List<Students> list = new ArrayList<>();
        Logs.e("history",classesList.toString()+"~~~~~~~~~~~~~~~");
        //

        classes.students = list;
        classesList.add(classes);
        Logs.e("history",classesList.toString()+"~~~~~~~~~~~~~~~");
        //
        Classes classes1 = new Classes();
        classes1.name = "信用时常";
        List<Students> list1 = new ArrayList<>();
        list1.add(new Students("时长（月）", ""));
        classes1.students =list1;
        classesList.add(classes1);
        Logs.e("history",classesList.toString()+"~~~~~~~~~~~~~~~");
        college.classList = classesList;
        historys.add(college);

    }

    private void initadapter() {
        colleges.clear();
        College college = new College();
        college.name = "信用卡";
        college.icon = R.drawable.icon;
        college.classList = initClass(new ArrayList<Account>());
        College college1 = new College();
        college1.name = "贷款";
        college1.icon = R.drawable.icon;
        college1.classList = initLoans(new ArrayList<Loans>());
        colleges = new ArrayList<>();
        Logs.e("@@@@@"+colleges.toString());
        colleges.add(college);
        colleges.add(college1);
        Logs.e("history",colleges.toString()+"~~~~~~~~~~~~~~~");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expand_listview_ferformance_tv:
                ferformanceonClick(view);
                break;
            case R.id.expand_listview_history_tv:
                historysonClick(view);
                break;
            case R.id.expand_listview_investigation_tv:
                investigationonClick(view);
                break;
            case R.id.expand_listview_liabilities_tv:
                liabilitiesaonClick(view);
                break;
            case R.id.expand_listview_ferformance_tvs:
                ferformanceonClick(view);
                break;
            case R.id.expand_listview_history_tvs:
                historysonClick(view);
                break;
            case R.id.expand_listview_investigation_tvs:
                investigationonClick(view);
                break;
            case R.id.expand_listview_liabilities_tvs:
                liabilitiesaonClick(view);
                break;
            case R.id.wenjuan:
                startActivity(new Intent(this.getActivity(), QuestionActivity.class));
                break;

        }
    }

    private void initData() {
        mBinding.expandablelistviewFerformance.setVisibility(View.VISIBLE);
        mBinding.expandableListViewHistory.setVisibility(View.GONE);
        mBinding.expandableListViewInvestigation.setVisibility(View.GONE);
        mBinding.expandableListViewLiabilities.setVisibility(View.GONE);
        RequestParams performace = new RequestParams(APP.performace);
        Logs.e(this.getActivity().getSharedPreferences("loginUser", Context.MODE_PRIVATE).getString("user_id",""));
        performace.addBodyParameter("phone",  this.getActivity().getSharedPreferences("loginUser", Context.MODE_PRIVATE).getString("user_id",""));
        XutilsHttp.getInstance().get(performace, new ViewShowHelper.ClickListenr<UserLogin>() {

            @Override
            public void onXUtilsSuccess(UserLogin userLogin) {
                if (userLogin.getCode().equals("1")) {
                    try {
                        JSONObject jsonObject = new JSONObject(userLogin.getData());
                        Logs.i(jsonObject.getString("credits"));
                        JSONArray credits = new JSONArray((String) jsonObject.get("credits"));
                        JSONArray loans = new JSONArray((String) jsonObject.get("loans"));
                        JSONObject risk = new JSONObject((String) jsonObject.get("risk"));
                        riskinit(risk);
                        List<Account> list = new ArrayList<Account>();
                        List<Loans> lists = new ArrayList<Loans>();
                        Gson gson = new Gson();
                        for (int i = 0; i < credits.length(); i++) {
                            Account account = gson.fromJson(credits.getString(i), Account.class);
                            list.add(account);
                        }
                        for (int i = 0; i < loans.length(); i++) {
                            Loans loan = gson.fromJson(loans.getString(i), Loans.class);
                            lists.add(loan);
                        }
                        initColleges(list, lists);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.show(EvaluateFragment.this.getContext(), userLogin.getMessage());
                }
                post();
            }


            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {

            }
        });

    }
     private void post(){
         final RequestParams history = new RequestParams(APP.history);
         XutilsHttp.getInstance().get(history, new ViewShowHelper.ClickListenr<UserLogin>() {

             @Override
             public void onXUtilsSuccess(UserLogin userLogin) {
                 if (userLogin.getCode().equals("1")) {
                     try {
                         JSONObject jsonObject = new JSONObject(userLogin.getData());
                         historys.clear();
                         Logs.e("history",userLogin.getData());
                         College college = new College();
                         college.name = "信用长度";
                         college.icon = R.drawable.icon;
                         List<Classes> classesList = new ArrayList<>();
                         Classes classes = new Classes();
                         classes.name = "首次借贷详情";
                         List<Students> list = new ArrayList<>();
                         Logs.e("history",classesList.toString()+"~~~~~~~~~~~~~~~");
                         //
                         list.add(new Students("发卡银行", jsonObject.getString("issueBank").toString()));
                         list.add(new Students("货币种类", jsonObject.getString("accountType").toString()));
                         list.add(new Students("授信额度", jsonObject.getString("creditAmount").toString()));
                         list.add(new Students("发卡日期", jsonObject.getString("issueTime").toString()));
                         classes.students = list;
                         classesList.add(classes);
                         Logs.e("history",classesList.toString()+"~~~~~~~~~~~~~~~");
                         //
                         Classes classes1 = new Classes();
                         classes1.name = "信用时常";
                         List<Students> list1 = new ArrayList<>();
                         list1.add(new Students("时长（月）", jsonObject.getString("duration").toString()));
                         classes1.students =list1;
                         classesList.add(classes1);
                         Logs.e("history",classesList.toString()+"~~~~~~~~~~~~~~~");
                         college.classList = classesList;
                         historys.add(college);
                         Logs.e("history",historys.toString());
                         historysadapter.notifyDataSetChanged();
                     } catch (JSONException e) {
                         e.printStackTrace();
                         Logs.e("history",e.getMessage()+"~~~~~~~~~~~~~~~");
                     }

                 } else {
                     ToastUtil.show(EvaluateFragment.this.getContext(), userLogin.getMessage());
                 }
             }

             @Override
             public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {

             }
         });
         RequestParams liabilities = new RequestParams(APP.liabilities);
         XutilsHttp.getInstance().get(liabilities, new ViewShowHelper.ClickListenr<UserLogin>() {

             @Override
             public void onXUtilsSuccess(UserLogin userLogin) {
                 if (userLogin.getCode().equals("1")) {
                     try {
                         JSONObject jsonObject = new JSONObject(userLogin.getData());
                         collegesliabilitiesa.clear();
                         College college = new College();
                         college.name = "信用卡使用率";
                         college.icon = R.drawable.icon;
                         List<Classes> classesList = new ArrayList<>();
                         Classes classes = new Classes();
                         classes.name = "使用额度";
                         classes.value = jsonObject.getString("creditUsed").toString();
                         List<Students> list = new ArrayList<>();
                         classes.students = list;
                         classesList.add(classes);
                         //
                         Classes classes1 = new Classes();
                         classes1.name = "授信额度";
                         classes1.value = jsonObject.getString("creditAmount").toString();
                         List<Students> list1 = new ArrayList<>();
                         classes1.students =list1;
                         classesList.add(classes1);
                         college.classList = classesList;
                         collegesliabilitiesa.add(college);


                         College college1 = new College();
                         college1.name = "贷款总额";
                         college1.icon = R.drawable.icon;
                         List<Classes> cl = new ArrayList<>();
                         Classes cs = new Classes();
                         cs.name = "贷款总额";
                         cs.value = jsonObject.getString("loanAmount").toString();
                         List<Students> list3 = new ArrayList<>();
                         cs.students = list3;
                         cl.add(cs);
                         //
                         Classes cs1 = new Classes();
                         cs1.name = "未还金额";
                         cs1.value = jsonObject.getString("loanBalance").toString();
                         List<Students> list4 = new ArrayList<>();
                         cs1.students =list4;
                         cl.add(classes1);
                         college1.classList = cl;
                         collegesliabilitiesa.add(college1);
                         liabilitiesadapter.notifyDataSetChanged();
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 } else {
                     ToastUtil.show(EvaluateFragment.this.getContext(), userLogin.getMessage());
                 }
             }

             @Override
             public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {

             }
         });
         RequestParams survey = new RequestParams(APP.survey);
         XutilsHttp.getInstance().get(survey, new ViewShowHelper.ClickListenr<UserLogin>() {

             @Override
             public void onXUtilsSuccess(UserLogin userLogin) {
                 if (userLogin.getCode().equals("1")) {
                     try {
                         JSONObject jsonObject = new JSONObject(userLogin.getData());
                         collegesinvestigation.clear();
                         College college = new College();
                         college.name = "查询次数";
                         college.icon = R.drawable.icon;
                         List<Classes> classesList = new ArrayList<>();
                         Classes classes = new Classes();
                         classes.name = "个人查询";
                         classes.value = jsonObject.getString("perCount").toString();
                         List<Students> list = new ArrayList<>();
                         classes.students = list;
                         classesList.add(classes);
                         //
                         Classes classes1 = new Classes();
                         classes1.name = "机构查询";
                         classes1.value = jsonObject.getString("orgCount").toString();
                         List<Students> list1 = new ArrayList<>();
                         classes1.students =list1;
                         classesList.add(classes1);
                         college.classList = classesList;
                         collegesinvestigation.add(college);
                         investigationadapter.notifyDataSetChanged();
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 } else {
                     ToastUtil.show(EvaluateFragment.this.getContext(), userLogin.getMessage());
                 }
             }

             @Override
             public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {

             }
         });
     }

    private void riskinit(JSONObject risk) throws JSONException {
        mBinding.radarView.removeRadarData(data);
        List<Float> values = new ArrayList<>();
        Collections.addAll(values, switchScore(risk.getString("creditSize")), switchScore(risk.getString("refundCase")), switchScore(risk.getString("creditQuery")), switchScore(risk.getString("creditUsage")));
        mBinding.zonghexinyong.setText("信用等级:"+risk.getString("scoreSum"));
        data = new RadarData(values);
        mBinding.radarView.addData(data);

    }
 private float switchScore(String score){
     switch (score){
         case "AAA":
             return 5f;

         case "AA":
             return 4f;

         case "A":
             return 3f;

         case "B":
             return 2f;

         case "C":
             return 1f;

         default:
             return 0f;

     }
 }

    private void initColleges(List<Account> credits, List<Loans> loans) {
        College college = new College();
        college.name = "信用卡";
        college.icon = R.drawable.icon;
        college.classList = initClass(credits);
        College college1 = new College();
        college1.name = "贷款";
        college1.icon = R.drawable.icon;
        college1.classList = initLoans(loans);
        colleges = new ArrayList<>();
        colleges.add(college);
        colleges.add(college1);
        adapter.notifyDataSetChanged();
    }

    private List<Classes> initLoans(List<Loans> loans) {
        List<Classes> classesList = new ArrayList<>();
        Classes classes = new Classes();
        classes.name = "未逾期贷款";
        List<Students> list = new ArrayList<>();
        Classes classes1 = new Classes();
        classes1.name = "已逾期贷款";
        List<Students> list1 = new ArrayList<>();
        for (int i = 0; i < loans.size(); i++) {
            if (!loans.get(i).isNowIsOverdue()) {
                list.add(new Students("贷款银行", loans.get(i).getLoanBank()));
                list.add(new Students("贷款种类", loans.get(i).getLoanType()));
                list.add(new Students("贷款金额", loans.get(i).getLoanAmount() + ""));
                list.add(new Students("贷款日期", loans.get(i).getLoanTime() + ""));
                list.add(new Students("逾期次数", loans.get(i).getOverMonth() + ""));
                classes.students = list;
            } else {
                list1.add(new Students("贷款银行", loans.get(i).getLoanBank()));
                list1.add(new Students("贷款种类", loans.get(i).getLoanType()));
                list1.add(new Students("贷款金额", loans.get(i).getLoanAmount() + ""));
                list1.add(new Students("贷款日期", loans.get(i).getLoanTime() + ""));
                list1.add(new Students("逾期次数", loans.get(i).getOverMonth() + ""));
                classes1.students = list1;
            }
        }
        Logs.i(classes.students.size() + "  classes");
        Logs.i(classes1.students.size() + "  classes1");
        classes.value = classes.students.size()+"";
        classes1.value = classes1.students.size()+"";
//        if (classes.students.size() > 0) {
            classesList.add(classes);
//        }
//        if (classes1.students.size() > 0) {
            classesList.add(classes1);
//        }
        return classesList;
    }

    private List<Classes> initClass(List<Account> credits) {
        List<Classes> classesList = new ArrayList<>();
        Classes classes = new Classes();
        classes.name = "未逾期信用卡";
        List<Students> list = new ArrayList<>();
        Classes classes1 = new Classes();
        classes1.name = "已逾期信用卡";
        List<Students> list1 = new ArrayList<>();
        int size1 = 0;
        int size2 = 0;
        for (int i = 0; i < credits.size(); i++) {
            if (!credits.get(i).getNowIsOverdue()) {
                size1++;
                list.add(new Students("发卡银行", credits.get(i).getIssueBank()));
                list.add(new Students("币种", credits.get(i).getAccountType()));
                list.add(new Students("授信额度", credits.get(i).getCreditAmount() + ""));
                list.add(new Students("发卡时间", credits.get(i).getIssueTime() + ""));
                list.add(new Students("逾期次数", credits.get(i).getOverMonth() + ""));
                classes.students = list;
            } else {
                size2++;
                list1.add(new Students("发卡银行", credits.get(i).getIssueBank()));
                list1.add(new Students("币种", credits.get(i).getAccountType()));
                list1.add(new Students("授信额度", credits.get(i).getCreditAmount() + ""));
                list1.add(new Students("发卡时间", credits.get(i).getIssueTime() + ""));
                list1.add(new Students("逾期次数", credits.get(i).getOverMonth() + ""));
                classes1.students = list1;
            }
        }
        classes.value = classes.students.size()+"";
        classes1.value = classes1.students.size()+"";
        Logs.i(classes.students.size() + "   classes");
        Logs.i(classes1.students.size() + "     classes1");
//        if (classes.students.size() > 0) {
            classesList.add(classes);
//        }
//        if (classes1.students.size() > 0) {
            classesList.add(classes1);
//        }
        return classesList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        initData();
    }

    public void ferformanceonClick(View v){
        Logs.e("ferformanceonClick");
        mBinding.expandablelistviewFerformance.setVisibility(View.VISIBLE);
        mBinding.expandableListViewHistory.setVisibility(View.GONE);
        mBinding.expandableListViewInvestigation.setVisibility(View.GONE);
        mBinding.expandableListViewLiabilities.setVisibility(View.GONE);
    }

    public void historysonClick(View v){
        Logs.e("historysonClick");
        mBinding.expandablelistviewFerformance.setVisibility(View.GONE);
        mBinding.expandableListViewHistory.setVisibility(View.VISIBLE);
        mBinding.expandableListViewInvestigation.setVisibility(View.GONE);
        mBinding.expandableListViewLiabilities.setVisibility(View.GONE);
    }
    public void liabilitiesaonClick(View v){
        Logs.e("liabilitiesaonClick");
        mBinding.expandablelistviewFerformance.setVisibility(View.GONE);
        mBinding.expandableListViewHistory.setVisibility(View.GONE);
        mBinding.expandableListViewInvestigation.setVisibility(View.GONE);
        mBinding.expandableListViewLiabilities.setVisibility(View.VISIBLE);
    }
    public void investigationonClick(View v){
        Logs.e("investigationonClick");
        mBinding.expandablelistviewFerformance.setVisibility(View.GONE);
        mBinding.expandableListViewHistory.setVisibility(View.GONE);
        mBinding.expandableListViewInvestigation.setVisibility(View.VISIBLE);
        mBinding.expandableListViewLiabilities.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        // 发布事件
        Logs.e("onResume");
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        Logs.e("onDestroyView");
        super.onDestroyView();
//        initData();
    }

}
