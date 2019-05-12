package DAO;


import java.util.List;

public interface homeWorkDAO {
    //获取该课时的作业题目
    String get_class(int id);

    //学生提交作业
    boolean post_work(int work_id,String student,String time,String select,String subjective);

    //获取所有班级作业概况
    List get_homework(int course_id);

    //获取此班级作业情况
    List get_stu(int class_id,int course_id,int belong_work);
}
