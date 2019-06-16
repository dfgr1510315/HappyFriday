package com.LJZ.DAO;

import com.LJZ.Model.SubModel.NoteInsert;

import java.util.List;

public interface NoteDAO {
    //获取该课程笔记数量
    List get_note_count(String SQL);

    //获取笔记
    List get_note(String SQL);

    //得到此课程下所有笔记
    List get_this_class_note(int class_id,int page);

    int change_note(String note_editor,String time,int note_id);

    int delete_note(int note_id);

    //获取当前播放课时下我的笔记
    List get_lesson_note(int class_id,String lesson_no,String author);

    void post_note(NoteInsert noteInsert);
}
