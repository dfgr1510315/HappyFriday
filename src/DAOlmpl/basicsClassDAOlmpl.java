package DAOlmpl;

import DAO.basicsClassDAO;
import Model.Course;
import Model.File;
import Server.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class basicsClassDAOlmpl  implements basicsClassDAO {

    @Override
    public List get_class(String where,String order,String limit) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Course> list = new ArrayList<>();
        Course cl;
        String select = "class_id,class_title,teacher,student_count,cover_address,class_type,outline";
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT "+select+" FROM class_teacher_table where "+where+" order by "+order+" limit "+limit);
            while (rs.next()){
                cl = new Course();
                cl.setClass_id(rs.getInt("class_id"));
                cl.setClass_title(rs.getString("class_title"));
                cl.setTeacher(rs.getString("teacher"));
                cl.setStudent_count(rs.getInt("student_count"));
                cl.setCover_address(rs.getString("cover_address"));
                cl.setClass_type(rs.getInt("class_type"));
                cl.setOutline(rs.getString("outline"));
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
    public int get_class_count(String where) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        int count = 0;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select count(class_id) count from class_teacher_table where "+where);
            while (rs.next()){
                count = rs.getInt("count");
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
        return count;
    }

    @Override
    public List<String> search_tips(String keyword) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<String> tips=new ArrayList<>();
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select class_title from class_teacher_table where class_title like '%"+keyword+"%'" );
            while (rs.next()){
                tips.add(rs.getString("class_title"));
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
        return tips;
    }

    @Override
    public boolean delete_class(int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("delete from class_teacher_table where class_id=?");
            qsql.setInt(1,class_id);
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
    public boolean set_infor(int class_id, String title, int class_type, String outline) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("update class_teacher_table set class_title=?, class_type=?, outline=? where class_id=? ");
            qsql.setString(1,title );
            qsql.setInt(2,class_type );
            qsql.setString(3,outline );
            qsql.setInt(4,class_id );
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
    public String[] get_infor(int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        String[] infor = new String[3];
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select class_title,outline,class_type from class_teacher_table where class_id="+class_id );
            while (rs.next()){
                infor[0] = rs.getString("class_title");
                infor[1] = rs.getString("outline");
                infor[2] = rs.getString("class_type");
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
        return infor;
    }

    @Override
    public boolean save_class(int class_id, String UnitCount, String ClassCount) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("update class_teacher_table set  UnitCount=?, ClassCount=? where class_id=? ");
            qsql.setString(1,UnitCount );
            qsql.setString(2,ClassCount );
            qsql.setInt(3,class_id );
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
    public String[] read_class(int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        String[] cl = new String[3];
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select class_title,teacher,release_status from class_teacher_table where class_id="+class_id);
            while (rs.next()){
                cl[0] = rs.getString("class_title");
                cl[1] = rs.getString("teacher");
                cl[2] = rs.getString("release_status");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return cl;
    }

    @Override
    public boolean change_class_state(int No, int state) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int Rstate = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("update class_teacher_table set release_status=? where class_id=?");
            qsql.setInt(1,state );
            qsql.setInt(2,No );
            Rstate = qsql.executeUpdate();
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
        return Rstate!=0;
    }

    @Override
    public List get_files(String class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<File> list = new ArrayList<>();
        File file;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT file_name,file_path FROM file where class_id="+class_id);
            while (rs.next()){
                file = new File();
                file.setFile_add(rs.getString("file_path"));
                file.setFile_name(rs.getString("file_name"));
                list.add(file);
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
    public boolean delete_file(String address) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("delete from file where file_path=?");
            qsql.setString(1,address);
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
}
