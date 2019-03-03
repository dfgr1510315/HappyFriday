package Server;

import net.sf.json.JSONArray;
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

@WebServlet(name = "Get_Teaching")
public class Get_Teaching extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        //String username = request.getParameter("username");
        HttpSession session=request.getSession();
        String type = request.getParameter("type");
        String username=(String) session.getAttribute("user_id");
        switch (type) {
            case "found_class":
                String Class_Name = request.getParameter("Class_Name");
                String Class_Type = request.getParameter("Class_Type");
                add_class(response, username, Class_Name, Class_Type);
                break;
            case "get_Class":
                get_Infor(response, username);
                break;
            case "get_my_class":
                get_my_class(response, username);
                break;
            case "add_collection":
                String class_no = request.getParameter("class_no");
                set_collection(response,class_no, username,"1");
                break;
            case "remove_collection":
                class_no = request.getParameter("class_no");
                set_collection(response,class_no, username,"0");
                break;
            case "delete_class":
                class_no = request.getParameter("class_no");
                delete_class(response,class_no, username);
                break;
        }
    }

    private void delete_class(HttpServletResponse response,String class_No,String username){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql = con.prepareStatement("delete FROM sc WHERE user=? and class=?");
            qsql.setString(1, username);
            qsql.setString(2, class_No);
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

    private void set_collection(HttpServletResponse response,String class_No,String username,String type){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("update sc set collection=? where user=? and class=?");
            qsql.setString(1,type);
            qsql.setString(2,username);
            qsql.setString(3,class_No);
            qsql.executeUpdate();
            qsql.close();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg",type);
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_my_class(HttpServletResponse response,String username){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 课程类型,collection,class,标题,封面地址,schedule,last_time from SC,class_teacher_table where user='"+username+"' and class=课程编号" );
            ArrayList<String> class_no = new ArrayList<>();
            ArrayList<String> title = new ArrayList<>();
            ArrayList<String> img_address = new ArrayList<>();
            ArrayList<String> collection = new ArrayList<>();
            ArrayList<String> class_type = new ArrayList<>();
            ArrayList<String> schedule = new ArrayList<>();
            ArrayList<String> last_time = new ArrayList<>();
            while (rs.next()){
                class_no.add(rs.getString("class"));
                title.add(rs.getString("标题"));
                img_address.add(rs.getString("封面地址"));
                collection.add(rs.getString("collection"));
                class_type.add(rs.getString("课程类型"));
                schedule.add(rs.getString("schedule"));
                last_time.add(rs.getString("last_time"));
            }

            rs = statement.executeQuery("select 所属课程编号,count(*) sum from SC,note where user='"+username+"' and class=所属课程编号 and user=署名 group by 所属课程编号" );
            ArrayList<String> note_count = new ArrayList<>();
            ArrayList<String> note_count_no = new ArrayList<>();
            while (rs.next()){
                note_count.add(rs.getString("sum"));
                note_count_no.add(rs.getString("所属课程编号"));
            }
            rs = statement.executeQuery("select 所属课程编号,count(*) sum from SC,ask where user='"+username+"' and class=所属课程编号 and user=提问者 group by 所属课程编号");
            ArrayList<String> ask_count = new ArrayList<>();
            ArrayList<String> ask_count_no = new ArrayList<>();
            while (rs.next()){
                ask_count.add(rs.getString("sum"));
                ask_count_no.add(rs.getString("所属课程编号"));
            }
            PrintWriter out = response.getWriter();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("class_no",class_no);
            jsonObj.put("title",title);
            jsonObj.put("img_address",img_address);
            jsonObj.put("collection",collection);
            jsonObj.put("class_type",class_type);
            jsonObj.put("note_count",note_count);
            jsonObj.put("note_count_no",note_count_no);
            jsonObj.put("ask_count",ask_count);
            jsonObj.put("ask_count_no",ask_count_no);
            jsonObj.put("schedule",schedule);
            jsonObj.put("last_time",last_time);
            out.flush();
            out.print(jsonObj);
            out.close();
            rs.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void add_class(HttpServletResponse response,String username,String Class_Name,String type){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement psql = con.prepareStatement("insert into  class_teacher_table(教师用户名,标题,状态,课程类型,封面地址)"+"values(?,?,?,?,?)");
            psql.setString(1,username);
            psql.setString(2,Class_Name);
            psql.setString(3,"未发布");
            psql.setString(4,type);
            psql.setString(5,"/image/efb37fee400582742424a4ce08951213.png");
            psql.executeUpdate();
            PrintWriter out = response.getWriter();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("state","1");
            out.flush();
            out.print(jsonObj);
            out.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void get_Infor(HttpServletResponse response,String username){
        JSONArray json= new JSONArray();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 课程编号,标题,学员数,状态,封面地址 from class_teacher_table where 教师用户名='"+username+"'");
            while (rs.next()){
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("Title",rs.getString("标题"));
                jsonObj.put("学员数",rs.getInt("学员数"));
                jsonObj.put("状态",rs.getString("状态"));
                jsonObj.put("封面地址",rs.getString("封面地址"));
                jsonObj.put("课程编号",rs.getString("课程编号"));
                json.add(jsonObj);
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(json);
            out.close();
            rs.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
