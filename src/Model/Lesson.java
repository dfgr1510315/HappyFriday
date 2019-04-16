package Model;

public class Lesson {
    private int class_id;
    private String unit_no;
    private String unit_title;
    private String lesson_title;
    private int release_status;
    private String source_video_address;
    private String source_video_title;
    private String video_address;
    private String Image_text;
    private String file_address;
    private String file_name;

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getUnit_title() {
        return unit_title;
    }

    public void setUnit_title(String unit_title) {
        this.unit_title = unit_title;
    }

    public String getLesson_title() {
        return lesson_title;
    }

    public void setLesson_title(String lesson_title) {
        this.lesson_title = lesson_title;
    }

    public int getRelease_status() {
        return release_status;
    }

    public void setRelease_status(int release_status) {
        this.release_status = release_status;
    }

    public String getSource_video_address() {
        return source_video_address;
    }

    public void setSource_video_address(String source_video_address) {
        this.source_video_address = source_video_address;
    }

    public String getSource_video_title() {
        return source_video_title;
    }

    public void setSource_video_title(String source_video_title) {
        this.source_video_title = source_video_title;
    }

    public String getVideo_address() {
        return video_address;
    }

    public void setVideo_address(String video_address) {
        this.video_address = video_address;
    }

    public String getImage_text() {
        return Image_text;
    }

    public void setImage_text(String image_text) {
        Image_text = image_text;
    }

    public String getFile_address() {
        return file_address;
    }

    public void setFile_address(String file_address) {
        this.file_address = file_address;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}
