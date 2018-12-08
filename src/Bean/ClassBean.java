package Bean;

public class ClassBean {
    private String Class_name;
    private int Release_statu;
    private String Vedio_Src;
    private String Editor;
    private String File_Src;

    public String getClass_name() {
        return Class_name;
    }

    public int getRelease_statu() {
        return Release_statu;
    }

    public String getEditor() {
        return Editor;
    }

    public String getFile_Src() {
        return File_Src;
    }

    public String getVedio_Src() {
        return Vedio_Src;
    }

    public void setClass_name(String class_name) {
        Class_name = class_name;
    }

    public void setEditor(String editor) {
        Editor = editor;
    }

    public void setFile_Src(String file_Src) {
        File_Src = file_Src;
    }

    public void setRelease_statu(int release_statu) {
        Release_statu = release_statu;
    }

    public void setVedio_Src(String vedio_Src) {
        Vedio_Src = vedio_Src;
    }

}
