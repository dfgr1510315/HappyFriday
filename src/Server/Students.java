package Server;

import com.alibaba.druid.pool.DruidPooledConnection;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
                int page = Integer.parseInt(request.getParameter("id"));
                get_class_students(response,No,page);
                break;
            case "save_schedule" :
                No = Integer.parseInt(request.getParameter("No"));
                String user = request.getParameter("user");
                String last_time = request.getParameter("last_time");
                int schedule = Integer.parseInt(request.getParameter("schedule"));
                save_schedule(request,No,schedule,user,last_time);
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
            case "get_class":
                No = Integer.parseInt(request.getParameter("No"));
                get_class(response,No);
                break;
            case "create_class":
                No = Integer.parseInt(request.getParameter("No"));
                user = request.getParameter("class_name");
                create_class(response,No,user);
                break;
            case "delete_class":
                No = Integer.parseInt(request.getParameter("id"));
                delete_class(response,No);
                break;
        }
    }

    private void delete_class(HttpServletResponse response,int id){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("delete from classification where id="+id);
            qsql.executeUpdate();
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(1);
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
    }

    private void create_class(HttpServletResponse response,int No,String class_name){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into classification(class_id,name) value (?,?)");
            qsql.setInt(1,No);
            qsql.setString(2,class_name);
            qsql.executeUpdate();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select LAST_INSERT_ID() id");
            int id = -1;
            while (rs.next()){
                id = rs.getInt("id");
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(id);
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
    }

    private void get_class(HttpServletResponse response,int No){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select id,name from classification where class_id="+No);
            JSONObject jsonObj = new JSONObject();
            ArrayList<Integer> id = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            while (rs.next()){
                id.add(rs.getInt("id"));
                name.add(rs.getString("name"));
            }
            jsonObj.put("id",id);
            jsonObj.put("name",name);
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
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
    }

    private void remove_student(HttpServletResponse response,int No,String user){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("delete from sc where user=? and class=?");
            qsql.setString(1,user);
            qsql.setInt(2,No);
            qsql.executeUpdate();
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(1);
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
    }

    private void save_schedule(HttpServletRequest request,int No,int percentage,String user,String last_time){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("update sc set schedule=?,last_time=? where user=? and class=?");
            qsql.setInt(1,percentage);
            qsql.setString(2,last_time);
            qsql.setString(3,user);
            qsql.setInt(4,No);
            qsql.executeUpdate();
            HttpSession session = request.getSession();
            int[] history_class_id = (int[])session.getAttribute("history_class_id");
            int[] schedule = (int[]) session.getAttribute("schedule");
            String[] history_last_time = (String[]) session.getAttribute("last_time");
            for (int i=0;i<6;i++){
                if (No==history_class_id[i]){
                    schedule[i] = percentage;
                    history_last_time[i] = last_time;
                    break;
                }
            }
            session.setAttribute("schedule",schedule);
            session.setAttribute("last_time",history_last_time);
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
    }

    private void get_schedule(HttpServletResponse response,int No,String user){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select schedule,last_time from sc where user='"+user+"' and class="+No);
            JSONObject jsonObj = new JSONObject();
            while (rs.next()){
                jsonObj.put("schedule",rs.getInt("schedule"));
                jsonObj.put("last_time",rs.getString("last_time"));
            }
      /*      JSONObject jsonObj = new JSONObject();
            HttpSession session = request.getSession();
            int[] history_class_id = (int[])session.getAttribute("history_class_id");
            int[] schedule = (int[]) session.getAttribute("schedule");
            String[] last_time = (String[]) session.getAttribute("last_time");
            for (int i=0;i<6;i++){
                if (No==history_class_id[i]){
                    jsonObj.put("schedule",schedule[i]);
                    jsonObj.put("last_time",last_time[i]);
                    break;
                }
            }*/
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
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
    }

    private void get_class_students(HttpServletResponse response,int No,int classification){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            /*classification
             * order by time desc limit "+(6*(page-1))+","+6);
              * */
            ResultSet rs = statement.executeQuery("select user,head,schedule,time from sc,personal_table where class="+No+" and username=user and classification="+classification);
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
       /*     rs = statement.executeQuery("SELECT COUNT(*) count FROM sc where class="+No);
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }*/
            jsonObj.put("user",user_list);
            jsonObj.put("time",time_list);
            jsonObj.put("head",head);
            jsonObj.put("schedule",schedule_list);
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
