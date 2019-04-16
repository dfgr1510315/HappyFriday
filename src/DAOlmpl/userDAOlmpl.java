package DAOlmpl;

import DAO.userDAO;
import Model.User;
import Server.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class userDAOlmpl implements userDAO {

    @Override
    public User get_user(String username) {
        User user = null;
        String SQL = "select * from personal_table where username='" + username + "'";
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            while (rs.next()) {
                user = new User();
                user.setNike(rs.getString("nike"));
                user.setSex(rs.getString("sex"));
                user.setBirth(rs.getString("birth"));
                user.setInformation(rs.getString("information"));
                user.setTeacher(rs.getString("teacher"));
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
        return user;
    }

    @Override
    public boolean add_user(String username) {
        String SQL = "insert into personal_table (username,nike)values(?,?)";
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con = null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement(SQL);
            qsql.setString(1, username);
            qsql.setString(2, username);
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
    public List search_user(String username,int page) {
        User user;
        List<User> list = new ArrayList<>();
        String SQL = "select nike,usertype,sex,head,information from personal_table where nike like '%"+username+"%' limit "+(6*(page-1))+","+6;
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            while (rs.next()) {
                user = new User();
                user.setNike(rs.getString("nike"));
                user.setSex(rs.getString("sex"));
                user.setUsertype(rs.getString("usertype"));
                user.setHead(rs.getString("head"));
                user.setInformation(rs.getString("information"));
                list.add(user);
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
    public boolean delete_user(String username) {
        return false;
    }

    @Override
    public boolean change_user(String username, String nike, String sex, String birth, String information, String teacher) {
        String SQL = "update personal_table set nike=?,sex=?,birth=?,information=?,teacher=? where username=?";
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con = null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement(SQL);
            qsql.setString(1, nike);
            qsql.setString(2, sex);
            qsql.setString(3, birth);
            qsql.setString(4, information);
            qsql.setString(5, teacher);
            qsql.setString(6, username);
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
    public boolean change_head(String username, String head) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("update personal_table set head=? where username=?");
            qsql.setString(1, head);
            qsql.setString(2, username);
            state = qsql.executeUpdate();
        }
        catch(Exception e){
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
    public int count_user(String username) {
        int count = 0;
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select count(*) user_count from personal_table where nike like '%"+username+"%'");
            while (rs.next()) {
                count = rs.getInt("user_count");
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
}
