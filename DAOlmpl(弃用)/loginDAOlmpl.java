/*
package com.LJZ.DAOlmpl;

import com.LJZ.DAO.LoginDAO;
import com.LJZ.Model.UserBase;
import com.LJZ.Model.History;
import com.LJZ.DB.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class loginDAOlmpl implements LoginDAO {

    @Override
    public int unread(String username) {
            DBPoolConnection dbp = DBPoolConnection.getInstance();
            DruidPooledConnection con =null;
            int read = -1;
            try {
                con = dbp.getConnection();
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("SELECT readed from notice where to_user='"+username+"' order by time desc limit 1;");
                while (rs.next()) read = rs.getInt("readed");
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
            return read;
    }

    @Override
    public boolean change_email(String username, String email,String code) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("update login_table set code=? where username=?");
            qsql.setString(1,code);
            qsql.setString(2,username);
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
    public String[] forgetPW(String username) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        //ArrayList list = new ArrayList();
        String [] list = new String [2];
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select password,email from login_table where username='"+username+"'");
            while (rs.next()){
                list[0] = rs.getString("password");
                list[1] = rs.getString("email");
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
        }
        return list;
    }

    @Override
    public boolean register(String username, String password, String email, int active, String code) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("insert into login_table values(?,?,?,?,?)");
            qsql.setString(1,username);
            qsql.setString(2,password);
            qsql.setString(3,email);
            qsql.setInt(4,active);
            qsql.setString(5,code);
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
    public UserBase login(String name) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        UserBase userBase = new UserBase();
        try {
            con = dbp.getConnection();
            String sql = "select login_table.username,password,email,nike,head,usertype from login_table,personal_table where login_table.username=personal_table.username and active=1 and login_table.username='" + name + "'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                userBase.setUsername(rs.getString("login_table.username"));
                userBase.setPaw(rs.getString("password"));
                userBase.setNike(rs.getString("nike"));
                userBase.setHead_image(rs.getString("head"));
                userBase.setUsertype(rs.getString("usertype"));
                userBase.setEmail(rs.getString("email"));
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
        }
        return userBase;
    }

    @Override
    public String get_teacher_class(String username) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        String class_id = "";
        try {
            con = dbp.getConnection();
            String sql = "select class_id from class_teacher_table where teacher='"+username+"'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                class_id = class_id +","+rs.getString("class_id");
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
        }
        return class_id;
    }

    @Override
    public List get_history(String username) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<History> list = new ArrayList<>();
        History history;
        try {
            con = dbp.getConnection();
            String sql = "SELECT classId,schedule,last_time,class_title from sc,class_teacher_table where user='"+username+"' and classId=class_id order by time desc limit 6";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                history = new History();
                history.setClass_id(rs.getInt("classId"));
                history.setSchedule(rs.getInt("schedule"));
                history.setLast_time(rs.getString("last_time"));
                history.setClass_title(rs.getString("class_title"));
                list.add(history);
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
        }
        return list;
    }

}
*/
