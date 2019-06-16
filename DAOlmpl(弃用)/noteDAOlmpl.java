/*
package com.LJZ.DAOlmpl;

import com.LJZ.DAO.NoteDAO;
import com.LJZ.Model.Note;
import com.LJZ.Model.SubModel.Note_class;
import com.LJZ.Model.SubModel.Note_list;
import com.LJZ.DB.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class noteDAOlmpl implements NoteDAO {
    @Override
    public int get_note_count(String SQL) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        //String SQL = "select count(note_id) note_count from note where belong_class_id=" + class_id;
        int count = 0;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs  = statement.executeQuery(SQL);
            while (rs.next()) {
                count =  rs.getInt("count");
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
        return count;
    }

    @Override
    public List get_note(String SQL) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        String SQL_select = "note_id,text,note_time,note.unit_no,belong_class_id,class_title,cover_address,lesson_title";
        String SQL_from = "note,class_teacher_table,class";
        List<Note_list> lists = new ArrayList<>();
        Note_list note_list;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select "+SQL_select+" from "+SQL_from+" where "+SQL);
            while (rs.next()){
                note_list = new Note_list();
                note_list.setNote_id(rs.getInt("note_id"));
                note_list.setText(rs.getString("text"));
                note_list.setNote_time(rs.getString("note_time"));
                note_list.setUnit_no(rs.getString("note.unit_no"));
                note_list.setBelong_class_id(rs.getInt("belong_class_id"));
                note_list.setClass_title(rs.getString("class_title"));
                note_list.setCover_address(rs.getString("cover_address"));
                note_list.setLesson_title(rs.getString("lesson_title"));
                lists.add(note_list);
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
    public List get_this_class_note(int class_id, int page) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        String SQL_select = "note_id,text,note_time,note.unit_no,nike,head,lesson_title";
        String SQL_from = "note,personal_table,class";
        String SQL_where = "belong_class_id="+class_id+" and author=username and belong_class_id=class_id and note.unit_no=class.unit_no order by note_time desc";
        List<Note_class> lists = new ArrayList<>();
        Note_class note_class;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select "+SQL_select+" from "+SQL_from+" where "+SQL_where+" limit "+(6*(page-1))+","+6);
            while (rs.next()){
                note_class  =new Note_class();
                note_class.setNote_id(rs.getInt("note_id"));
                note_class.setText(rs.getString("text"));
                note_class.setNote_time(rs.getString("note_time"));
                note_class.setHead(rs.getString("head"));
                note_class.setUnit_no(rs.getString("note.unit_no"));
                note_class.setAuthor(rs.getString("nike"));
                note_class.setLesson_title(rs.getString("lesson_title"));
                lists.add(note_class);
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
    public boolean change_note(int note_id, String note_editor, String time) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("update note set text=?,note_time=? where note_id=?");
            qsql.setString(1,note_editor);
            qsql.setString(2,time);
            qsql.setInt(3,note_id);
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
    public boolean delete_note(int note_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int state = 0;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("delete FROM note WHERE note_id=?");
            qsql.setInt(1, note_id);
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
    public List get_lesson_note(int class_id, String lesson_no, String author) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Note> list = new ArrayList<>();
        Note note;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select text,note_time,note_id from note where belong_class_id="+class_id+" and unit_no='"+lesson_no+"' and author= '"+author+"'");
            while (rs.next()){
                note = new Note();
                note.setText(rs.getString("text"));
                note.setNote_time(rs.getString("note_time"));
                note.setNote_id(rs.getInt("note_id"));
                list.add(note);
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
    public int post_note(int class_id, String lesson_no, String author, String note_editor, String time) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int note_id = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into note(belong_class_id,unit_no,author,text,note_time) values(?,?,?,?,?)");
            qsql.setInt(1,class_id);
            qsql.setString(2,lesson_no);
            qsql.setString(3,author);
            qsql.setString(4,note_editor);
            qsql.setString(5,time);
            qsql.executeUpdate();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select LAST_INSERT_ID() note_id");
            while (rs.next()){
                note_id = rs.getInt("note_id");
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
        return note_id;
    }
}
*/
