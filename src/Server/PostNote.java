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
            ResultSet rs = statement.executeQuery("select note.*,head,课时标题 from note,personal_table,class where 所属课程编号="+No+" and 署名=username and 所属课程编号=课程编号 and 课时序号=章节序号 order by 时间 desc limit "+(6*(page-1))+","+6);
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> text_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> head = new ArrayList<>();
            ArrayList<String> class_no = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> class_title = new ArrayList<>();
            while (rs.next()){
                text_list.add(rs.getString("内容"));
                time_list.add(rs.getString("时间"));
                head.add(rs.getString("head"));
                class_no.add(rs.getString("课时序号"));
                name.add(rs.getString("署名"));
                class_title.add(rs.getString("课时标题"));
            }
            rs = statement.executeQuery("SELECT COUNT(*) count FROM note where 所属课程编号="+No);
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
            ResultSet rs = statement.executeQuery("select note.*,标题,封面地址 from note,class_teacher_table where 课程编号=所属课程编号 and 署名= '"+author+"'");
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
                note_no.add(rs.getString("笔记编号"));
                title_list.add(rs.getString("标题"));
                text_list.add(rs.getString("内容"));
                time_list.add(rs.getString("时间"));
                image_address.add(rs.getString("封面地址"));
                class_no.add(rs.getString("课时序号"));
                title_no.add(rs.getString("所属课程编号"));
            }
            rs = statement.executeQuery("select 课时标题 from note,class where 课时序号=章节序号 and 署名= '"+author+"'");
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
            PreparedStatement qsql  = con.prepareStatement("update note set 内容=?,时间=? where 笔记编号=?");
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
            PreparedStatement qsql = con.prepareStatement("delete FROM note WHERE 笔记编号=?");
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
            ResultSet rs = statement.executeQuery("select 内容,时间,笔记编号 from note where 所属课程编号="+No+" and 课时序号='"+class_No+"' and 署名= '"+author+"'");
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> text_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> note_no = new ArrayList<>();
            while (rs.next()){
                text_list.add(rs.getString("内容"));
                time_list.add(rs.getString("时间"));
                note_no.add(rs.getString("笔记编号"));
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
            PreparedStatement qsql  = con.prepareStatement("insert into note(所属课程编号,课时序号,署名,内容,时间) values(?,?,?,?,?)");
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
