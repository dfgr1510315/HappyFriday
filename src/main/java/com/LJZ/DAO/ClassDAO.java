package com.LJZ.DAO;

import com.LJZ.Model.Lesson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassDAO {
    //查询教师开设的所有课程
    List get_class(String teacher);

    //创建课程
    int add_class(String teacher,String class_title,int class_type);

    //查询学生学习的所有课程
    List get_student_class(String student);

    //讲课程添加或取消收藏
    int set_collection(String class_id,String student,String collection);

    //获取课程封面
    List get_cover(int class_id);

    //删除学生的学习课程
    int delete_student_class(String class_id,String student);

    //教师页修改课程内容
    //boolean set_class_content(int class_id,List<Lesson> lessons);
    List getUnitNo(int class_id);
    void UpClassContent(@Param("l")Lesson lesson, @Param("class_id")int class_id);
    void InClassContent(@Param("l")Lesson lesson, @Param("class_id")int class_id);
    void DeClassContent(int class_id, String unit_no);

    //教师页获取课程内容
    List get_class_content(int class_id);

    //获取章节目录
    List get_chapter(int class_id);

    //获取相同类型推荐课程
    List get_recommend(int class_type);

    //学习页获取课程信息
    List get_class_infor(int class_id);

    //获取课程名
    List get_class_name(int class_id);

    //加入课程学习
    int join_class(int class_id, String username, String time, int classification);

    //获取课时视频和文件
    List get_material(int class_id,String lesson_no);

    //播放权限判断
    List PlayPower(String username,int class_id);

    //设置课程封面
    int set_cover(int class_id,String cover);

    //增加课程播放次数
    int save_viewed(int class_id, int time);

    void insertViewed(int class_id,int time);

    //获取播放次数统计
    List get_viewed(int class_id,int start,int end);
}
