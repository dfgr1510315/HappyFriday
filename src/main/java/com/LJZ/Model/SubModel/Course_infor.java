package com.LJZ.Model.SubModel;

import com.LJZ.Model.Course;

public class Course_infor extends Course {
    private String teacher_head;

    private int unit_count;
    private int release_status;

    public String getTeacher_head() {
        return teacher_head;
    }

    public void setTeacher_head(String teacher_head) {
        this.teacher_head = teacher_head;
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
