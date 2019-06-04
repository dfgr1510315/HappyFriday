package com.LJZ.Model.SubModel;

import com.LJZ.Model.Ask;

public class Ask_class extends Ask {
    private int ask_id;
    private String unit_no;
    private String lesson_title;
    private String asker;
    private String head;
    private String new_answerer;
    private String new_answer;

    public int getAsk_id() {
        return ask_id;
    }

    public void setAsk_id(int ask_id) {
        this.ask_id = ask_id;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getLesson_title() {
        return lesson_title;
    }

    public void setLesson_title(String lesson_title) {
        this.lesson_title = lesson_title;
    }

    public String getAsker() {
        return asker;
    }

    public void setAsker(String asker) {
        this.asker = asker;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNew_answerer() {
        return new_answerer;
    }

    public void setNew_answerer(String new_answerer) {
        this.new_answerer = new_answerer;
    }

    public String getNew_answer() {
        return new_answer;
    }

    public void setNew_answer(String new_answer) {
        this.new_answer = new_answer;
    }
}
