package com.LJZ.DAO;


import com.LJZ.Model.SubModel.SubAddHW;

import java.util.HashMap;
import java.util.List;

public interface HwDAO {
    //获取该课时的作业题目
    List<HashMap<String,Object>> get_class(int id);

    //学生提交作业
    //int post_work(int work_id,String student,String time,String question,String option,String select,String sel_standard,String calculation,String cal_standard,String cal_answer);
    int post_work(String id_stu,int work_id,String student,String time,int flag);

    //学生提交作业内容
    int post_text(String id_stu,String question,String option,String select,String sel_standard,String calculation,String cal_standard,String cal_answer);

    //获取所有班级作业概况
    List get_homework(int course_id);

    //获取此班级作业情况
    List<HashMap<String,Object>> get_stu(int class_id,int course_id);

    List<HashMap<String,Object>> get_stu_hw(int belong_work);

    //获取学生作业内容
    List<HashMap<String,Object>> get_text(String stu_id);

    int delete_work(int id);

    //添加作业
    void add_work(SubAddHW subAddHW);

    //课程主页获取作业信息
    List get_work_list(String student,int class_id);

    //作业界面权限判别
    String power_work(String student,int work_id);

    //获取题库数量及设置过的抽题
    List getRandom(int id);

    int postRandom(int sel,int cal,int id);
}
