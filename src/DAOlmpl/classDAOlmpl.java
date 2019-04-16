package DAOlmpl;

import DAO.classDAO;
import Model.Chapter;
import Model.Class;
import Model.Lesson;
import Model.Sc;
import Server.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class classDAOlmpl implements classDAO {

    @Override
    public List get_class(String teacher) {
        Class c;
        List<Class> list = new ArrayList<>();
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select class_id,class_title,student_count,release_status,cover_address from class_teacher_table where teacher='"+teacher+"'");
            while (rs.next()){
                c = new Class();
                c.setClass_title(rs.getString("class_title"));
                c.setStudent_count(rs.getInt("student_count"));
                c.setRelease_status(rs.getInt("release_status"));
                c.setCover_address(rs.getString("cover_address"));
                c.setClass_id(rs.getInt("class_id"));
                list.add(c);
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return list;
    }

    @Override
    public int add_class(String teacher, String class_title, int class_type) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int id = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("insert into class_teacher_table(teacher,class_title,release_status,class_type,cover_address)"+"values(?,?,?,?,?)");
            qsql.setString(1,teacher);
            qsql.setString(2,class_title);
            qsql.setInt(3,0);
            qsql.setInt(4,class_type);
            qsql.setString(5,"/image/efb37fee400582742424a4ce08951213.png");
            qsql.executeUpdate();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID() class_ID");
            while (rs.next()){
                id = rs.getInt("class_ID");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return id;
    }

    @Override
    public List get_student_class(String student) {
        Sc sc;
        List<Sc> list = new ArrayList<>();
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        String SQL_note = "(SELECT COUNT(note_id) from note where belong_class_id=class and author=user) note_count";
        String SQL_ask = ",(SELECT COUNT(ask_id) from ask where belong_class_id=class and asker=user) ask_count";
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select class_type,collection,class,class_title,cover_address,schedule,last_time,"+SQL_note+SQL_ask+" from sc,class_teacher_table where user='"+student+"' and class=class_id");
            while (rs.next()){
                sc = new Sc();
                sc.setClass_id(rs.getInt("class"));
                sc.setClass_title(rs.getString("class_title"));
                sc.setCover_address(rs.getString("cover_address"));
                sc.setCollection(rs.getString("collection"));
                sc.setClass_type(rs.getString("class_type"));
                sc.setSchedule(rs.getInt("schedule"));
                sc.setLast_time(rs.getString("last_time"));
                sc.setNote_count(rs.getInt("note_count"));
                sc.setAsk_count(rs.getInt("ask_count"));
                list.add(sc);
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return list;
    }

    @Override
    public boolean set_collection(String class_id, String student, String collection) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("update sc set collection=? where user=? and class=?");
            qsql.setString(1,collection);
            qsql.setString(2,student);
            qsql.setString(3,class_id);
            state = qsql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return state!=0;
    }

    @Override
    public boolean delete_student_class(String class_id,String student) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("delete FROM sc WHERE user=? and class=?");
            qsql.setString(1, student);
            qsql.setString(2, class_id);
            state = qsql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return state!=0;
    }

    @Override
    public boolean set_class_content(int class_id,List<Lesson> lessons) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select unit_no from class where class_id='"+class_id+"'");
            ArrayList<String> Unit = new ArrayList<>();
            ArrayList<String> Serial_No = new ArrayList<>();
            while (rs.next()){
                Unit.add(rs.getString("unit_no"));
            }
            for (Lesson lesson : lessons) {
                if (Unit.contains(lesson.getUnit_no())) {
                    qsql = con.prepareStatement("update class set unit_title=?, lesson_title=?, release_status=?,video_address=?,Image_text=?,file_address=?,file_name=?,source_video_title=? ,source_video_address=? where unit_no=? and class_id=?");
                    qsql.setString(1, lesson.getUnit_title());
                    qsql.setString(2, lesson.getLesson_title());
                    qsql.setInt(3, lesson.getRelease_status());
                    qsql.setString(4, lesson.getVideo_address());
                    qsql.setString(5, lesson.getImage_text());
                    qsql.setString(6, lesson.getFile_address());
                    qsql.setString(7, lesson.getFile_name());
                    qsql.setString(8, lesson.getSource_video_title());
                    qsql.setString(9, lesson.getSource_video_address());
                    qsql.setString(10, lesson.getUnit_no());
                    qsql.setInt(11, class_id);
                    state = qsql.executeUpdate();
                } else {
                    qsql = con.prepareStatement("insert into class values(?,?,?,?,?,?,?,?,?,?,?)");
                    qsql.setInt(1, class_id);
                    qsql.setString(2, lesson.getUnit_no());
                    qsql.setString(3, lesson.getUnit_title());
                    qsql.setString(4, lesson.getLesson_title());
                    qsql.setInt(5, lesson.getRelease_status());
                    qsql.setString(6, lesson.getSource_video_address());
                    qsql.setString(7, lesson.getSource_video_title());
                    qsql.setString(8, lesson.getVideo_address());
                    qsql.setString(9, lesson.getImage_text());
                    qsql.setString(10, lesson.getFile_address());
                    qsql.setString(11, lesson.getFile_name());
                    state = qsql.executeUpdate();
                }
                Serial_No.add(lesson.getUnit_no());
            }
            for (String aUnit : Unit) {
                if (!Serial_No.contains(aUnit)) {
                    qsql = con.prepareStatement("delete FROM class WHERE unit_no=?and class_id=?");
                    qsql.setString(1, aUnit);
                    qsql.setInt(2, class_id);
                    state = qsql.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return state!=0;
    }

    @Override
    public List get_class_content(int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from class where class_id="+class_id+" order by unit_no");
            while (rs.next()){
                lesson = new Lesson();
                lesson.setUnit_no(rs.getString("unit_no"));
                lesson.setUnit_title(rs.getString("unit_title"));
                lesson.setLesson_title(rs.getString("lesson_title"));
                lesson.setRelease_status(rs.getInt("release_status"));
                lesson.setSource_video_address(rs.getString("source_video_address"));
                lesson.setSource_video_title(rs.getString("source_video_title"));
                lesson.setVideo_address(rs.getString("video_address"));
                lesson.setImage_text(rs.getString("Image_text"));
                lesson.setFile_address(rs.getString("file_address"));
                lesson.setFile_name(rs.getString("file_name"));
                lessons.add(lesson);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return lessons;
    }

    @Override
    public List get_chapter(int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Chapter> Chapters = new ArrayList<>();
        Chapter chapter;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select unit_no,unit_title,lesson_title from class where class_id=" + class_id + " and release_status=1 order by unit_no");
            while (rs.next()){
                chapter = new Chapter();
                chapter.setUnit_no(rs.getString("unit_no"));
                chapter.setUnit_title(rs.getString("unit_title"));
                chapter.setLesson_title(rs.getString("lesson_title"));
                Chapters.add(chapter);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return Chapters;
    }

    @Override
    public List get_recommend(int class_type) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Class> classes = new ArrayList<>();
        Class my_class;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select class_id,class_title,cover_address from class_teacher_table where release_status=1 and class_type=" + class_type + " limit 5");
            while (rs.next()){
                my_class = new Class();
                my_class.setClass_id(rs.getInt("class_id"));
                my_class.setClass_title(rs.getString("class_title"));
                my_class.setCover_address(rs.getString("cover_address"));
                classes.add(my_class);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return classes;
    }

    @Override
    public Class get_class_infor(int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        Class my_class = new Class();
        String SQL = "select release_status,class_teacher_table.teacher,class_title,head,student_count,class_type,outline from class_teacher_table,personal_table where class_teacher_table.teacher=username and class_id=" + class_id;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            while (rs.next()){
                my_class.setRelease_status(rs.getInt("release_status"));
                my_class.setTeacher(rs.getString("class_teacher_table.teacher"));
                my_class.setClass_title(rs.getString("class_title"));
                my_class.setTeacher_head(rs.getString("head"));
                my_class.setStudent_count(rs.getInt("student_count"));
                my_class.setClass_type(rs.getInt("class_type"));
                my_class.setOutline(rs.getString("outline"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return my_class;
    }

    @Override
    public String get_class_name(int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        String SQL = "select class_title from class_teacher_table where class_id=" + class_id;
        String class_name = null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            while (rs.next()){
                class_name = rs.getString("class_title");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return class_name;
    }

    @Override
    public boolean join_class(int class_id, String username, String time, int classification) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con = null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("insert into sc(user,class,time,classification) value (?,?,?,?)");
            qsql.setString(1, username);
            qsql.setInt(2, class_id);
            qsql.setString(3, time);
            qsql.setInt(4, classification);
            state = qsql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (qsql != null)
                try {
                    qsql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return state!=0;
    }
}
