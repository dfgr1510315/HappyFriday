package com.LJZ.Model.SubModel;

import com.LJZ.Model.homework;

public class Sub_HW extends homework {
    private int class_id;
    private String name;//班级名
    private String file_add;
    private int selLine; //选择题数
    private int calLine; //计算题or简答题数

    public int getSelLine() {
        return selLine;
    }

    public void setSelLine(int selLine) {
        this.selLine = selLine;
    }

    public int getCalLine() {
        return calLine;
    }

    public void setCalLine(int calLine) {
        this.calLine = calLine;
    }

    public String getFile_add() {
        return file_add;
    }

    public void setFile_add(String file_add) {
        this.file_add = file_add;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
