package com.LJZ.DAO;

import java.util.List;

public interface noteDAO {
    //获取该课程笔记数量
    int get_note_count(String SQL);

    //获取笔记
    List get_note(String SQL);

    //得到此课程下所有笔记
    List get_this_class_note(int class_id,int page);

    boolean change_note(int note_id,String note_editor,String time);

    boolean delete_note(int note_id);

    //获取当前播放课时下我的笔记
    List get_lesson_note(int class_id,String lesson_no,String author);

    int post_note(int class_id,String lesson_no,String author,String note_editor,String time);
}
