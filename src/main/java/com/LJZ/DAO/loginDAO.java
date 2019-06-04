package com.LJZ.DAO;


import com.LJZ.Model.UserBase;

import java.util.List;

public interface loginDAO {
    boolean change_email(String username,String email,String code);

    String[] forgetPW(String username);

    boolean register(String username,String password,String email,int active,String code);

    UserBase login(String name);

    //若是教师登录则获取所教所有课程的ID
    String get_teacher_class(String username);

    //获取历史学习数据
    List get_history(String username);

    //查询是否有未读信息
    int unread(String username);
}
