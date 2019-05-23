package DAOlmpl;

import DAO.homeWorkDAO;
import Model.SubModel.Sub_HW;
import Model.homework;
import Server.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class hwDAOlmpl implements homeWorkDAO {
    @Override
    public String get_class(int id) {
        String file_add = null;
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select file_add from homework where id="+id);
            while (rs.next()){
                file_add = rs.getString("file_add");
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
        return file_add;
    }

    @Override
    public boolean post_work(int work_id,String student,String time,String question,String option,String select,String sel_standard,String calculation,String cal_standard,String cal_answer) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into stu_work values(?,?,?,?,?)");
            qsql.setString(1,work_id+student);
            qsql.setInt(2,work_id);
            qsql.setString(3,student);
            qsql.setString(4,time);
            qsql.setInt(5,0);
            qsql.executeUpdate();
            qsql  = con.prepareStatement("insert into stu_text values(?,?,?,?,?,?,?,?)");
            qsql.setString(1,work_id+student);
            qsql.setString(2,question);//选择题问题
            qsql.setString(3,option);//选项
            qsql.setString(4,select);//学生选择的答案
            qsql.setString(5,sel_standard);//选择题标准答案
            qsql.setString(6,calculation);//计算题
            qsql.setString(7,cal_standard);//计算题标准答案
            qsql.setString(8,cal_answer);//计算题学生答案
            state = qsql.executeUpdate();
            if (state==0){
                qsql  = con.prepareStatement("delete from stu_work where id="+work_id+student);
                qsql.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return state!=0;
    }

    @Override
    public List get_homework(int course_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Sub_HW> lists = new ArrayList<>();
        Sub_HW hw;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select homework.id,time,title,name,homework.class_id,file_add from homework,classification where classification.id=homework.class_id and course_id="+course_id);
            while (rs.next()){
                hw = new Sub_HW();
                hw.setId(rs.getInt("id"));
                hw.setTime(rs.getInt("time"));
                hw.setTitle(rs.getString("title"));
                hw.setClass_name(rs.getString("name"));
                hw.setClass_id(rs.getInt("class_id"));
                hw.setFile_add(rs.getString("file_add"));
                lists.add(hw);
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
        return lists;
    }

    @Override
    public List get_stu(int class_id, int course_id,int belong_work) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<String> lists = new ArrayList<>();
        StringBuilder user = new StringBuilder();
        StringBuilder post_user = new StringBuilder();
        StringBuilder time = new StringBuilder();
        StringBuilder flag = new StringBuilder();
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select user from sc where class="+course_id+" and classification="+class_id);
            while (rs.next()) {
                user.append(rs.getString("user")).append("|");
            }
            rs = statement.executeQuery("select student,time,flag from stu_work where belong_work="+belong_work);
            while (rs.next()){
                post_user.append(rs.getString("student")).append("|");
                time.append(rs.getString("time")).append("|");
                flag.append(rs.getString("flag")).append("|");
            }
            lists.add(user.toString());
            lists.add(post_user.toString());
            lists.add(time.toString());
            lists.add(flag.toString());
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
        return lists;
    }

    @Override
    public List get_text(String stu_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<String> lists = new ArrayList<>();
        String question = null;
        String _option = null;
        String _select = null;
        String sel_standard = null;
        String calculation = null;
        String cal_standard = null;
        String cal_answer = null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select question,_option,_select,sel_standard,calculation,cal_standard,cal_answer from stu_text where belong_stu='"+stu_id+"'");
            while (rs.next()) {
                question = rs.getString("question");
                _option = rs.getString("_option");
                _select = rs.getString("_select");
                sel_standard = rs.getString("sel_standard");
                calculation = rs.getString("calculation");
                cal_standard = rs.getString("cal_standard");
                cal_answer = rs.getString("cal_answer");
            }
            lists.add(question);
            lists.add(_option);
            lists.add(_select);
            lists.add(sel_standard);
            lists.add(calculation);
            lists.add(cal_standard);
            lists.add(cal_answer);
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
        return lists;
    }

    @Override
    public boolean delete_work(int id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("delete from homework where id="+id);
            state = qsql.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return state!=0;
    }

    @Override
    public int add_work(int class_id, int course_id, String file_add, int time, String title) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int id = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into homework(class_id,course_id,file_add,time,title) values(?,?,?,?,?)");
            qsql.setInt(1,class_id);
            qsql.setInt(2,course_id);
            qsql.setString(3,file_add);
            qsql.setInt(4,time);
            qsql.setString(5,title);
            qsql.executeUpdate();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select LAST_INSERT_ID() id");
            while (rs.next()){
                id = rs.getInt("id");
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return id;
    }

    @Override
    public List get_work_list(String student, int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<homework> lists = new ArrayList<>();
        homework hw;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select id,homework.time,title from homework,sc where user='"+student+"' and class="+class_id+" and classification=class_id and course_id="+class_id);
            while (rs.next()) {
                hw = new homework();
                hw.setId(rs.getInt("id"));
                hw.setTitle(rs.getString("title"));
                hw.setTime(rs.getInt("time"));
                lists.add(hw);
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
        return lists;
    }

    @Override
    public String power_work(int work_id, String student) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        String title = null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select title from homework,sc where user='"+student+"' and class_id=classification and course_id=class and id="+work_id);
            //System.out.println(student);
            while (rs.next()) {
                //System.out.println(rs.getString("sc.time"));
                title = rs.getString("title");
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
        return title;
    }
}
