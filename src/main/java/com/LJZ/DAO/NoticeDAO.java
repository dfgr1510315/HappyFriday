package com.LJZ.DAO;

import java.util.List;

public interface NoticeDAO {
    //获取用户的通知
    List get_notice(String user,int page);

    //修改通知未读状态
    void upRead(String user,int page);

    //删除通知
    int delete_notice(int notice_id);

    //获取此用户的通知总数
    int get_notice_count(String user);
}
