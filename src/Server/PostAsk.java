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

@WebServlet(name = "PostAsk")
public class PostAsk extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        String class_No = request.getParameter("class_No");
        String author = request.getParameter("author");
        String time;
        switch (action) {
            case "post":
                int No = Integer.parseInt(request.getParameter("No"));
                String note_editor = request.getParameter("note_editor");
                /* SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式*/
                time = request.getParameter("time");
                String ask_title = request.getParameter("ask_title");
                post_ask(response, No, class_No, author, note_editor, time,ask_title);
                break;
            case "get":
                No = Integer.parseInt(request.getParameter("No"));
                get_ask(response,No,class_No,author);
                break;
            case "get_All_ask" :
                int page = Integer.parseInt(request.getParameter("page"));
                String user = request.getParameter("user");
                get_All_ask(response,user,page);
                break;
        }
    }

    private void get_All_ask(HttpServletResponse response,String user,int page){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 问题描述,时间,标题 from class_teacher_table,ask where 教师用户名='"+user+"' and 课程编号=所属课程编号 order by 时间 desc limit "+(6*(page-1))+","+6);
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> title_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> describe_list = new ArrayList<>();
            while (rs.next()){
                title_list.add(rs.getString("标题"));
                time_list.add(rs.getString("时间"));
                describe_list.add(rs.getString("问题描述"));
            }
            jsonObj.put("title",title_list);
            jsonObj.put("time",time_list);
            jsonObj.put("describe",describe_list);
            rs = statement.executeQuery("SELECT COUNT(*) count FROM ask,class_teacher_table where 教师用户名='"+user+"' and 课程编号=所属课程编号");
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
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

    private void get_ask(HttpServletResponse response,int No,String class_No,String author){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 问题描述,时间 from ask where 所属课程编号="+No+" and 课时序号='"+class_No+"' and 提问者= '"+author+"'");
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> title_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            while (rs.next()){
                title_list.add(rs.getString("问题描述"));
                time_list.add(rs.getString("时间"));
            }
            jsonObj.put("title",title_list);
            jsonObj.put("time",time_list);
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

    private void post_ask(HttpServletResponse response,int No,String class_No,String author,String note_editor,String time,String ask_title){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("insert into ask values(?,?,?,?,?,?)");
            qsql.setInt(1,No);
            qsql.setString(2,class_No);
            qsql.setString(3,ask_title);
            qsql.setString(4,author);
            qsql.setString(5,note_editor);
            qsql.setString(6,time);
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
