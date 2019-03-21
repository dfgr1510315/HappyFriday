package Server;

import com.alibaba.druid.pool.DruidPooledConnection;
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

@WebServlet(name = "Notification")
public class Notification extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        //String time = request.getParameter("time");
        switch (action) {
            case "get_notice":
                String user = request.getParameter("user");
                int page = Integer.parseInt(request.getParameter("page"));
                get_notice(response,page,user);
                break;
            case "delete_notice":
                int notice_id = Integer.parseInt(request.getParameter("notice_id"));
                delete_notice(response,notice_id);
        }

    }

    private void delete_notice(HttpServletResponse response,int notice_id){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("delete from notice where notice_id=?");
            qsql.setInt(1,notice_id);
            qsql.executeUpdate();
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(1);
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
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    private void get_notice(HttpServletResponse response,int page,String user){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select notice.*,(SELECT class_title from class_teacher_table where class_teacher_table.class_id=notice.class_id) class_title,(SELECT ask_title from ask where ask.ask_id=notice.ask_id) ask_title from notice where to_user='"+user+"' order by time desc limit "+(6*(page-1))+","+6);
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> user_list;
            user_list = new ArrayList<>();
            ArrayList<Integer> notice_id = new ArrayList<>();
            ArrayList<Integer> class_id = new ArrayList<>();
            ArrayList<Integer> ask_id = new ArrayList<>();
            ArrayList<Integer> notice_type = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> describe_list = new ArrayList<>();
            ArrayList<String> class_title = new ArrayList<>();
            while (rs.next()){
                notice_id.add(rs.getInt("notice_id"));
                user_list.add(rs.getString("from_user"));
                class_id.add(rs.getInt("class_id"));
                ask_id.add(rs.getInt("ask_id"));
                notice_type.add(rs.getInt("notice_type"));
                time_list.add(rs.getString("time"));
                describe_list.add(rs.getString("ask_title"));
                class_title.add(rs.getString("class_title"));
            }
            jsonObj.put("notice_id",notice_id);
            jsonObj.put("user",user_list);
            jsonObj.put("class_id",class_id);
            jsonObj.put("ask_id",ask_id);
            jsonObj.put("notice_type",notice_type);
            jsonObj.put("time",time_list);
            jsonObj.put("describe",describe_list);
            jsonObj.put("class_title",class_title);
            rs = statement.executeQuery("SELECT COUNT(*) count FROM notice where to_user='"+user+"'");
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
            qsql = con.prepareStatement("update notice set readed=? where readed=? and to_user=? order by time desc limit "+6*page);
            qsql.setInt(1,1);
            qsql.setInt(2,0);
            qsql.setString(3,user);
            qsql.executeUpdate();
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
