package com.dingtaifu.dingdingbang.mvp.main.view.fragment;

/**
 * Created by zengjichun on 2018/4/30.
 */

public class Loans {
        private boolean isOverdue;
        private int loanAmount;
        private String loanBank;
        private String loanTime;
        private String loanType;
        private boolean nowIsOverdue;
        private int overMonth;
        private int overMonth90days;

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanBank() {
        return loanBank;
    }

    public void setLoanBank(String loanBank) {
        this.loanBank = loanBank;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public boolean isNowIsOverdue() {
        return nowIsOverdue;
    }

    public void setNowIsOverdue(boolean nowIsOverdue) {
        this.nowIsOverdue = nowIsOverdue;
    }

    public int getOverMonth() {
        return overMonth;
    }

    public void setOverMonth(int overMonth) {
        this.overMonth = overMonth;
    }

    public int getOverMonth90days() {
        return overMonth90days;
    }

    public void setOverMonth90days(int overMonth90days) {
        this.overMonth90days = overMonth90days;
    }

}
