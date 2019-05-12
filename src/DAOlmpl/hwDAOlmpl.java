package DAOlmpl;

import DAO.homeWorkDAO;
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
    public boolean post_work(int work_id,String student,String time,String select,String subjective) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into stu_work values(?,?,?,?,?)");
            qsql.setInt(1,work_id);
            qsql.setString(2,student);
            qsql.setString(3,time);
            qsql.setString(4,select);
            qsql.setString(5,subjective);
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
    public List get_homework(int course_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<homework> lists = new ArrayList<>();
        homework hw;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select homework.id,time,title,name,homework.class_id from homework,classification where classification.id=homework.class_id and course_id="+course_id);
            while (rs.next()){
                hw = new homework();
                hw.setId(rs.getInt("id"));
                hw.setTime(rs.getInt("time"));
                hw.setTitle(rs.getString("title"));
                hw.setClass_name(rs.getString("name"));
                hw.setClass_id(rs.getInt("class_id"));
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
}
