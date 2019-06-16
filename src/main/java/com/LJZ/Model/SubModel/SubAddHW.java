package com.LJZ.Model.SubModel;

import com.LJZ.Model.homework;

public class SubAddHW extends homework {
    private int class_id;//班级id
    private int course_id;
    private String file_name;
    private int selLine;
    private int calLine;

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

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
}
