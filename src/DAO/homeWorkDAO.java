package DAO;


import java.util.List;

public interface homeWorkDAO {
    //获取该课时的作业题目
    String[] get_class(int id);

    //学生提交作业
    boolean post_work(int work_id,String student,String time,String question,String option,String select,String sel_standard,String calculation,String cal_standard,String cal_answer);

    //获取所有班级作业概况
    List get_homework(int course_id);

    //获取此班级作业情况
    List get_stu(int class_id,int course_id,int belong_work);

    //获取学生作业内容
    List get_text(String stu_id);

    boolean delete_work(int id);

    //添加作业
    int add_work(int class_id,int course_id,String file_add,String file_name,int time,String title);

    //课程主页获取作业信息
    List get_work_list(String student,int class_id);

    //作业界面权限判别
    String power_work(int work_id,String student);

    //获取题库数量及设置过的抽题
    int[] getRandom(int id);

    boolean postRandom(int id,int sel,int cal);
}
