package com.LJZ.Model.SubModel;

import com.LJZ.Model.Note;

public class NoteInsert extends Note {
    private int belong_class_id;
    private String unit_no;
    private String author;

    public int getBelong_class_id() {
        return belong_class_id;
    }

    public void setBelong_class_id(int belong_class_id) {
        this.belong_class_id = belong_class_id;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
