package com.LJZ.Model.SubModel;

import com.LJZ.Model.Ask;

public class Ask_infor extends Ask {
    private String head;
    private String unit_no;
    private int class_type;
    private String nike;
    private String ask_text;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public String getNike() {
        return nike;
    }

    public void setNike(String nike) {
        this.nike = nike;
    }

    public String getAsk_text() {
        return ask_text;
    }

    public void setAsk_text(String ask_text) {
        this.ask_text = ask_text;
    }



    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public int getClass_type() {
        return class_type;
    }

    public void setClass_type(int class_type) {
        this.class_type = class_type;
    }
}
