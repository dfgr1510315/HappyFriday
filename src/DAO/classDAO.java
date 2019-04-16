package DAO;

import Model.Lesson;
import Model.Class;
import java.util.List;

public interface classDAO {
    //查询教师开设的所有课程
    List get_class(String teacher);

    //创建课程
    int add_class(String teacher,String class_title,int class_type);

    //查询学生学习的所有课程
    List get_student_class(String student);

    //讲课程添加或取消收藏
    boolean set_collection(String class_id,String student,String collection);

    //删除学生的学习课程
    boolean delete_student_class(String class_id,String student);

    //教师页修改课程内容
    boolean set_class_content(int class_id,List<Lesson> lessons);

    //教师页获取课程内容
    List get_class_content(int class_id);

    //获取章节目录
    List get_chapter(int class_id);

    //获取相同类型推荐课程
    List get_recommend(int class_type);

    //学习页获取课程信息
    Class get_class_infor(int class_id);

    //获取课程名
    String get_class_name(int class_id);

    //加入课程学习
    boolean join_class(int class_id, String username, String time, int classification);
}
