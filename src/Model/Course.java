package Model;

public class Course {
    private int class_id;
    private String teacher;
    private String class_title;
    private int student_count;
    private String cover_address;
    private int class_type;
    private String outline;

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClass_title() {
        return class_title;
    }

    public void setClass_title(String class_title) {
        this.class_title = class_title;
    }

    public int getStudent_count() {
        return student_count;
    }

    public void setStudent_count(int student_count) {
        this.student_count = student_count;
    }

    public String getCover_address() {
        return cover_address;
    }

    public void setCover_address(String cover_address) {
        this.cover_address = cover_address;
    }

    public int getClass_type() {
        return class_type;
    }

    public void setClass_type(int class_type) {
        this.class_type = class_type;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

}
