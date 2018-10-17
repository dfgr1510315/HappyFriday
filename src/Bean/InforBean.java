package Bean;

public class InforBean {
    private String nike;
    private String sex;
    private String name;
    private String birth;
    private String information;
    private String teacher;

    public void setNike(String nike){
        this.nike=nike;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

    public void setBirth(String birth){
        this.birth = birth;
    }

    public void setInformation(String information){
        this.information = information;
    }

    public void setTeacher(String teacher){
        this.teacher = teacher;
    }

    public String getNike(){
        return nike;
    }

    public String getSex(){
        return sex;
    }

    public String getName(){
        return name;
    }

    public String getBirth(){
        return birth;
    }

    public String getInformation(){
        return information;
    }

    public String getTeacher(){
        return teacher;
    }
}
