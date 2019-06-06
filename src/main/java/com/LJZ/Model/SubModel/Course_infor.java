package com.LJZ.Model.SubModel;

import com.LJZ.Model.Course;

public class Course_infor extends Course {
    private String head;
    private String nike;
    private int unit_count;
    private int release_status;

    public String getNike() {
        return nike;
    }

    public void setNike(String nike) {
        this.nike = nike;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getUnit_count() {
        return unit_count;
    }

    public void setUnit_count(int unit_count) {
        this.unit_count = unit_count;
    }

    public int getRelease_status() {
        return release_status;
    }

    public void setRelease_status(int release_status) {
        this.release_status = release_status;
    }
}
