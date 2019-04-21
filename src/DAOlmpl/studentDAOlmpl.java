package DAOlmpl;

import DAO.studentDAO;
import Model.Class;
import Model.Student;
import Server.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class studentDAOlmpl implements studentDAO {
    @Override
    public boolean move_student(int class_id, String student) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("update sc set classification="+class_id+" where user='"+student+"'");
            state = qsql.executeUpdate();
        } catch (Exception e) {
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
    public boolean delete_class(int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("delete from classification where id="+class_id);
            state = qsql.executeUpdate();
        } catch (Exception e) {
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
    public int create_class(int course_id, String class_name) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int id = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into classification(class_id,name) value (?,?)");
            qsql.setInt(1,course_id);
            qsql.setString(2,class_name);
            qsql.executeUpdate();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select LAST_INSERT_ID() id");
            while (rs.next()){
                id = rs.getInt("id");
            }
            rs.close();
        } catch (Exception e) {
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
    public List get_class(int course_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Class> list = new ArrayList<>();
        Class cl;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select id,name from classification where class_id="+course_id);
            while (rs.next()){
                cl = new Class();
                cl.setId(rs.getInt("id"));
                cl.setName(rs.getString("name"));
                list.add(cl);
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
        return list;
    }

    @Override
    public boolean remove_student(int course_id, String student) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("delete from sc where user=? and class=?");
            qsql.setString(1,student);
            qsql.setInt(2,course_id);
            state = qsql.executeUpdate();
        } catch (Exception e) {
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
    public void save_schedule(int course_id, int percentage, String student, String last_time) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("update sc set schedule=?,last_time=? where user=? and class=?");
            qsql.setInt(1,percentage);
            qsql.setString(2,last_time);
            qsql.setString(3,student);
            qsql.setInt(4,course_id);
            qsql.executeUpdate();
        } catch (Exception e) {
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
    }

    @Override
    public String[] get_schedule(int course_id, String student) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        String[] string = new String[2];
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select schedule,last_time from sc where user='"+student+"' and class="+course_id);
            while (rs.next()){
                string[0] = rs.getString("schedule");
                string[1] = rs.getString("last_time");
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
        return string;
    }

    @Override
    public List get_class_students(int course_id, int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Student> list = new ArrayList<>();
        Student student;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select user,head,schedule,time from sc,personal_table where class="+course_id+" and username=user and classification="+class_id);
            while (rs.next()){
                student = new Student();
                student.setName(rs.getString("user"));
                student.setTime(rs.getString("time"));
                student.setHead(rs.getString("head"));
                student.setSchedule(rs.getString("schedule"));
                list.add(student);
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
        return list;
    }
}
