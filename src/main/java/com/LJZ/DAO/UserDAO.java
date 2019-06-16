package com.LJZ.DAO;

import com.LJZ.Model.User;

import java.util.List;

public interface UserDAO {
    //获取用户信息
    User get_user(String username);

    //新用户注册添加默认个人信息
    int add_user(String username);

    //搜索用户
    List search_user(String username,int page);

    int delete_user(String username);

    //修改用户信息
    int change_user(String nike,String sex,String birth,String information,String teacher,String username);

    //修改用户头像
    int change_head(String head, String username);

    //查询匹配的用户数
    int count_user(String username);
}
