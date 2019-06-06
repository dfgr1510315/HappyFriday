package com.LJZ.DAO;

import com.LJZ.Model.SubModel.Ask_infor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AskDAO {
    //获取该课程问答数量
    int get_ask_count(String SQL);

    //获取问答
    List get_ask(String SQL);

    //添加回答
    int post_answer(int class_id,String answer,String answer_time,String answer_text);

    //添加回复(楼中楼)
    int post_reply(int class_id,String replyer,String reply_time,String reply_text,String reply_to);

    //访问数+1
    void visits_count(int ask_id);

    //得到此问答的基本信息
    Ask_infor get_ask_infor(int ask_id);

    //得到回答
    List get_answer(int ask_id);

    // 得到回答下的所有回复
    List get_reply(int answer_id);

    //相关问题
    List get_other_ask(int class_id,String unit_no,int ask_no);

    //得到此课程下所有问答
    List get_this_class_ask(int class_id,int page);

    int post_ask(int class_id,String unit_no,String asker,String ask_text,String time,String ask_title);

    //插入通知
    void inNotice(String fromUser,int askId,int type,String toUser,String time);
}
