package com.LJZ.DAO;
import java.util.List;

public interface BasicsClassDAO {
    //根据不同的条件获取课程列
    List get_class(String where,String order,String limit);

    int get_class_count(String where);

    List search_tips(String keyword);

    int delete_class(int class_id);

    int set_infor(int class_id,String title,int class_type,String outline);

    List get_infor(int class_id);

    //保存课程内容基本信息
    int save_class( String UnitCount, String ClassCount,int class_id);

    List read_class(int class_id);

    //改变课程发布状态
    int change_class_state(int No,int state);

    //获取课程素材
    List get_files(String class_id);

    int delete_file(String address);
}
