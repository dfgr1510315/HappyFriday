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

@WebServlet(name = "Students")
public class Students extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        switch (action) {
            case "get_class_students":
                int No = Integer.parseInt(request.getParameter("No"));
                int page = Integer.parseInt(request.getParameter("page"));
                get_class_students(response,No,page);
                break;
            case "save_schedule" :
                No = Integer.parseInt(request.getParameter("No"));
                String user = request.getParameter("user");
                String last_time = request.getParameter("last_time");
                int schedule = Integer.parseInt(request.getParameter("schedule"));
                save_schedule(No,schedule,user,last_time);
                break;
            case "remove_student" :
                No = Integer.parseInt(request.getParameter("No"));
                user = request.getParameter("student");
                remove_student(response,No,user);
                break;
            case "get_schedule" :
                No = Integer.parseInt(request.getParameter("No"));
                user = request.getParameter("student");
                get_schedule(response,No,user);
                break;
        }
    }

    private void remove_student(HttpServletResponse response,int No,String user){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("delete from sc where user=? and class=?");
            qsql.setString(1,user);
            qsql.setInt(2,No);
            qsql.executeUpdate();
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(1);
            qsql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save_schedule(int No,int percentage,String user,String last_time){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("update sc set schedule=?,last_time=? where user=? and class=?");
            qsql.setInt(1,percentage);
            qsql.setString(2,last_time);
            qsql.setString(3,user);
            qsql.setInt(4,No);
            qsql.executeUpdate();
            qsql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_schedule(HttpServletResponse response,int No,String user){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select schedule,last_time from sc where user='"+user+"' and class="+No);
            JSONObject jsonObj = new JSONObject();
            while (rs.next()){
                jsonObj.put("schedule",rs.getInt("schedule"));
                jsonObj.put("last_time",rs.getString("last_time"));
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

    private void get_class_students(HttpServletResponse response,int No,int page){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select user,head,schedule,time from sc,personal_table where class="+No+" and username=user order by time desc limit "+(6*(page-1))+","+6);
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> user_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> head = new ArrayList<>();
            ArrayList<String> schedule_list = new ArrayList<>();
            while (rs.next()){
                user_list.add(rs.getString("user"));
                time_list.add(rs.getString("time"));
                head.add(rs.getString("head"));
                schedule_list.add(rs.getString("schedule"));
            }
            rs = statement.executeQuery("SELECT COUNT(*) count FROM sc where class="+No);
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
            jsonObj.put("user",user_list);
            jsonObj.put("time",time_list);
            jsonObj.put("head",head);
            jsonObj.put("schedule",schedule_list);
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
