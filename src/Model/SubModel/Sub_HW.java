package Model.SubModel;

import Model.homework;

public class Sub_HW extends homework {
    private int class_id;
    private String class_name;//班级名
    private String file_add;

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

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
