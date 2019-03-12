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

@WebServlet(name = "Notification")
public class Notification extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        String time = request.getParameter("time");
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
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("delete from notice where id=?");
            qsql.setInt(1,notice_id);
            qsql.executeUpdate();
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(1);
            qsql.close();
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_notice(HttpServletResponse response,int page,String user){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select notice.*,(SELECT 标题 from class_teacher_table where 课程编号=课程) 标题,(SELECT 问题描述 from ask where 问题编号=讨论) 问题描述 from notice where username='"+user+"' order by time desc limit "+(6*(page-1))+","+6);
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
                notice_id.add(rs.getInt("id"));
                user_list.add(rs.getString("用户"));
                class_id.add(rs.getInt("课程"));
                ask_id.add(rs.getInt("讨论"));
                notice_type.add(rs.getInt("通知类型"));
                time_list.add(rs.getString("time"));
                describe_list.add(rs.getString("问题描述"));
                class_title.add(rs.getString("标题"));
            }
            jsonObj.put("notice_id",notice_id);
            jsonObj.put("user",user_list);
            jsonObj.put("class_id",class_id);
            jsonObj.put("ask_id",ask_id);
            jsonObj.put("notice_type",notice_type);
            jsonObj.put("time",time_list);
            jsonObj.put("describe",describe_list);
            jsonObj.put("class_title",class_title);
            rs = statement.executeQuery("SELECT COUNT(*) count FROM notice where username='"+user+"'");
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
            PreparedStatement psql = con.prepareStatement("update notice set readed=? where readed=? and username=? order by time desc limit "+6*page);
            psql.setInt(1,1);
            psql.setInt(2,0);
            psql.setString(3,user);
            psql.executeUpdate();
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
