package com.LJZ.Model.SubModel;

import com.LJZ.Model.Note;

public class Note_list extends Note {
    private String class_title;
    private String cover_address;
    private String unit_no;
    private int belong_class_id;
    private String lesson_title;

    public String getClass_title() {
        return class_title;
    }

    public void setClass_title(String class_title) {
        this.class_title = class_title;
    }

    public String getCover_address() {
        return cover_address;
    }

    public void setCover_address(String cover_address) {
        this.cover_address = cover_address;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public int getBelong_class_id() {
        return belong_class_id;
    }

    public void setBelong_class_id(int belong_class_id) {
        this.belong_class_id = belong_class_id;
    }

    public String getLesson_title() {
        return lesson_title;
    }

    public void setLesson_title(String lesson_title) {
        this.lesson_title = lesson_title;
    }
}
