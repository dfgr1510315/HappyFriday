package DAO;


import Model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface userDAO {
    //获取用户信息
    User get_user(String username);

    //新用户注册添加默认个人信息
    boolean add_user(String username);

    //搜索用户
    List search_user(String username,int page);

    boolean delete_user(String username);

    //修改用户信息
    boolean change_user(String username,String nike,String sex,String birth,String information,String teacher);

    //修改用户头像
    boolean change_head(String username, String head);
}
