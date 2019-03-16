package Server;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;


@WebServlet(name = "PostNote")
public class PostNote extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        int No;
        String class_No = request.getParameter("class_No");
        String author = request.getParameter("author");
        String time;
        switch (action) {
            case "post":
                No = Integer.parseInt(request.getParameter("No"));
                String note_editor = request.getParameter("note_editor");
               /* SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式*/
                time = request.getParameter("time");
                post_note(response, No, class_No, author, note_editor, time);
                break;
            case "get":
                No = Integer.parseInt(request.getParameter("No"));
                get_note(response, No, class_No, author);
                break;
            case "delete":
                int note_no = Integer.parseInt(request.getParameter("note_no"));
                delete_note(response, note_no);
                break;
            case "change":
                note_no = Integer.parseInt(request.getParameter("note_no"));
                note_editor = request.getParameter("note_editor");
                time = request.getParameter("time");
                change_note(response, note_no,note_editor,time);
                break;
            case "get_my_all_note":
                get_my_all_note(response, author);
                break;
            case "get_this_class_note":
                No = Integer.parseInt(request.getParameter("No"));
                int page = Integer.parseInt(request.getParameter("page"));
                get_this_class_note(response, No,page);
                break;

        }
    }
    private void get_this_class_note(HttpServletResponse response,int No,int page){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select text,note_time,note.unit_no,author,head,lesson_title from note,personal_table,class where belong_class_id="+No+" and author=username and belong_class_id=class_id and note.unit_no=class.unit_no order by note_time desc limit "+(6*(page-1))+","+6);
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> text_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> head = new ArrayList<>();
            ArrayList<String> class_no = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> class_title = new ArrayList<>();
            while (rs.next()){
                text_list.add(rs.getString("text"));
                time_list.add(rs.getString("note_time"));
                head.add(rs.getString("head"));
                class_no.add(rs.getString("note.unit_no"));
                name.add(rs.getString("author"));
                class_title.add(rs.getString("lesson_title"));
            }
            rs = statement.executeQuery("SELECT COUNT(*) count FROM note where belong_class_id="+No);
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
            jsonObj.put("text",text_list);
            jsonObj.put("time",time_list);
            jsonObj.put("head",head);
            jsonObj.put("class_no",class_no);
            jsonObj.put("name",name);
            jsonObj.put("class_title",class_title);
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_my_all_note(HttpServletResponse response,String author){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select note_id,text,note_time,note.unit_no,belong_class_id,class_title,cover_address from note,class_teacher_table where class_id=belong_class_id and author= '"+author+"'");
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> note_no = new ArrayList<>();
            ArrayList<String> title_list = new ArrayList<>();
            ArrayList<String> text_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> image_address = new ArrayList<>();
            ArrayList<String> class_no = new ArrayList<>();
            ArrayList<String> title_no = new ArrayList<>();
            ArrayList<String> class_title = new ArrayList<>();
            while (rs.next()){
                note_no.add(rs.getString("note_id"));
                title_list.add(rs.getString("class_title"));
                text_list.add(rs.getString("text"));
                time_list.add(rs.getString("note_time"));
                image_address.add(rs.getString("cover_address"));
                class_no.add(rs.getString("note.unit_no"));
                title_no.add(rs.getString("belong_class_id"));
            }
            rs = statement.executeQuery("select lesson_title from note,class where note.unit_no=class.unit_no and author= '"+author+"'");
            while (rs.next()){
                class_title.add(rs.getString("课时标题"));
            }
            jsonObj.put("note_no",note_no);
            jsonObj.put("text",text_list);
            jsonObj.put("time",time_list);
            jsonObj.put("title",title_list);
            jsonObj.put("image_address",image_address);
            jsonObj.put("class_no",class_no);
            jsonObj.put("title_no",title_no);
            jsonObj.put("class_title",class_title);
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void change_note(HttpServletResponse response,int note_no,String note_editor,String time){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("update note set text=?,note_time=? where note_id=?");
            qsql.setString(1,note_editor);
            qsql.setString(2,time);
            qsql.setInt(3,note_no);
            qsql.executeUpdate();
            qsql.close();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delete_note(HttpServletResponse response,int note_no){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql = con.prepareStatement("delete FROM note WHERE note_id=?");
            qsql.setInt(1, note_no);
            qsql.executeUpdate();
            qsql.close();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            //System.out.println(jsonObj+"????");
            out.flush();
            out.print(jsonObj);
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_note(HttpServletResponse response,int No,String class_No,String author){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select text,note_time,note_id from note where belong_class_id="+No+" and unit_no='"+class_No+"' and author= '"+author+"'");
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> text_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> note_no = new ArrayList<>();
            while (rs.next()){
                text_list.add(rs.getString("text"));
                time_list.add(rs.getString("note_time"));
                note_no.add(rs.getString("note_id"));
            }
            jsonObj.put("text",text_list);
            jsonObj.put("time",time_list);
            jsonObj.put("note_no",note_no);
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            //System.out.println(jsonObj+"????");
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void post_note(HttpServletResponse response,int No,String class_No,String author,String note_editor,String time){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("insert into note(belong_class_id,unit_no,author,text,note_time) values(?,?,?,?,?)");
            qsql.setInt(1,No);
            qsql.setString(2,class_No);
            qsql.setString(3,author);
            qsql.setString(4,note_editor);
            qsql.setString(5,time);
            qsql.executeUpdate();
            qsql.close();
            JSONObject jsonObj = new JSONObject();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select LAST_INSERT_ID() note_id");
            while (rs.next()){
                jsonObj.put("note_id",rs.getString("note_id"));
            }
            PrintWriter out = response.getWriter();
            //System.out.println(jsonObj+"????");
            out.flush();
            out.print(jsonObj);
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
