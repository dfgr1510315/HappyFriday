package Model;

public class Material {
    private boolean permit;
    private String video_address;
    private String Image_text;
    private String file_address;
    private String file_name;

    public boolean isPermit() {
        return permit;
    }

    public void setPermit(boolean permit) {
        this.permit = permit;
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
