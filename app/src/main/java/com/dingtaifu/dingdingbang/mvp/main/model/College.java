package com.dingtaifu.dingdingbang.mvp.main.model;

import java.util.List;

public class College {
    // 大学名
    public String name;
    public int icon;

    // 班级列表
    public List<Classes> classList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public List<Classes> getClassList() {
        return classList;
    }

    public void setClassList(List<Classes> classList) {
        this.classList = classList;
    }

    @Override
    public String toString() {
        return "College{" +
                "name='" + name + '\'' +
                ", icon=" + icon +
                ", classList=" + classList +
                '}';
    }
}
