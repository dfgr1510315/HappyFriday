package com.LJZ.DAO;


import com.LJZ.Model.UserBase;

import java.util.HashMap;
import java.util.List;

public interface LoginDAO {
    int change_email(String code,String username);

    List<HashMap<String,Object>> forgetPW(String username);

    int register(String username,String password,String email,int active,String code);

    UserBase login(String name);

    //若是教师登录则获取所教所有课程的ID
    List get_teacher_class(String username);

    //获取历史学习数据
    List get_history(String username);

    //查询是否有未读信息
    List unread(String username);
}
