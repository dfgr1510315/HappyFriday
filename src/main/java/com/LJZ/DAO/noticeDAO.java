package com.LJZ.DAO;

import java.util.List;

public interface noticeDAO {
    //获取用户的通知
    List get_notice(int page,String user);

    //删除通知
    boolean delete_notice(int notice_id);

    //获取此用户的通知总数
    int get_notice_count(String user);
}
