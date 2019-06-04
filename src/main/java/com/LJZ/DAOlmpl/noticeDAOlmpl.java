package com.LJZ.DAOlmpl;

import com.LJZ.DAO.noticeDAO;
import com.LJZ.Model.Notice;
import com.LJZ.DB.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class noticeDAOlmpl implements noticeDAO {

    @Override
    public List get_notice(int page, String user) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        Notice notice;
        List<Notice> notices = new ArrayList<>();
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            String SQL_class_title = "(SELECT class_title from class_teacher_table where class_teacher_table.class_id=notice.class_id) class_title,";
            String SQL_ask_title = "(SELECT ask_title from ask where ask.ask_id=notice.ask_id) ask_title";
            ResultSet rs = statement.executeQuery("select notice_id,nike,class_id,ask_id,notice_type,time,"+SQL_class_title+SQL_ask_title+" from notice,personal_table where to_user='"+user+"'and username=from_user order by time desc limit "+(6*(page-1))+","+6);
            while (rs.next()){
                notice = new Notice();
                notice.setNotice_id(rs.getInt("notice_id"));
                notice.setFrom_user(rs.getString("nike"));
                notice.setClass_id(rs.getInt("class_id"));
                notice.setAsk_id(rs.getInt("ask_id"));
                notice.setNotice_type(rs.getInt("notice_type"));
                notice.setTime(rs.getString("time"));
                notice.setAsk_title(rs.getString("ask_title"));
                notice.setClass_title(rs.getString("class_title"));
                notices.add(notice);
            }
            qsql = con.prepareStatement("update notice set readed=? where readed=? and to_user=? order by time desc limit "+6*page);
            qsql.setInt(1,1);
            qsql.setInt(2,0);
            qsql.setString(3,user);
            qsql.executeUpdate();
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
        return notices;
    }

    @Override
    public boolean delete_notice(int notice_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("delete from notice where notice_id=?");
            qsql.setInt(1,notice_id);
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
    public int get_notice_count(String user) {
        int count = 0;
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) count FROM notice where to_user='"+user+"'");
            while (rs.next()) {
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
}
