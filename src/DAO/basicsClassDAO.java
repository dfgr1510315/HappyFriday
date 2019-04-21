package DAO;


import java.util.List;

public interface basicsClassDAO {
    //根据不同的条件获取课程列
    List get_class(String where,String order,String limit);

    int get_class_count(String where);

    List<String> search_tips(String keyword);

    boolean delete_class(int class_id);

    boolean set_infor(int class_id,String title,int class_type,String outline);

    String[] get_infor(int class_id);

    //保存课程内容基本信息
    boolean save_class(int class_id, String UnitCount, String ClassCount);

    String[] read_class(int class_id);

    //改变课程发布状态
    boolean change_class_state(int No,int state);
}
