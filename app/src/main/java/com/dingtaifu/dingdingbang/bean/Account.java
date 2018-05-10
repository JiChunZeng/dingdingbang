package com.dingtaifu.dingdingbang.bean;

/**
 * Created by zengjichun on 2018/4/30.
 */

import java.util.Date;
public class Account {
    private String accountType;
    private int creditAmount;
    private boolean isOverdue;
    private String issueBank;
    private String issueTime;
    private boolean nowIsOverdue;
    private int overMonth;
    private int overMonth90days;
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public String getAccountType() {
        return accountType;
    }

    public void setCreditAmount(int creditAmount) {
        this.creditAmount = creditAmount;
    }
    public int getCreditAmount() {
        return creditAmount;
    }

    public void setIsOverdue(boolean isOverdue) {
        this.isOverdue = isOverdue;
    }
    public boolean getIsOverdue() {
        return isOverdue;
    }

    public void setIssueBank(String issueBank) {
        this.issueBank = issueBank;
    }
    public String getIssueBank() {
        return issueBank;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }
    public String getIssueTime() {
        return issueTime;
    }

    public void setNowIsOverdue(boolean nowIsOverdue) {
        this.nowIsOverdue = nowIsOverdue;
    }
    public boolean getNowIsOverdue() {
        return nowIsOverdue;
    }

    public void setOverMonth(int overMonth) {
        this.overMonth = overMonth;
    }
    public int getOverMonth() {
        return overMonth;
    }

    public void setOverMonth90days(int overMonth90days) {
        this.overMonth90days = overMonth90days;
    }
    public int getOverMonth90days() {
        return overMonth90days;
    }

}
