package com.dingtaifu.dingdingbang.mvp.main.model;

import java.util.ArrayList;
import java.util.List;

public class Classes {

    // 名m
    public String name;

    public String value;
    // 班级中的学生列表
    public List<Students> students = new ArrayList<>();

    @Override
    public String toString() {
        return "Classes{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", students=" + students +
                '}';
    }
}
