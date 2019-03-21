package Server;

import com.alibaba.druid.pool.DruidPooledConnection;
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
        HttpSession session=request.getSession();
        String type = request.getParameter("type");
        String username=(String) session.getAttribute("user_id");
        switch (type) {
            case "found_class":
                String Class_Name = request.getParameter("Class_Name");
                int Class_Type = Integer.parseInt(request.getParameter("Class_Type"));
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
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("delete FROM sc WHERE user=? and class=?");
            qsql.setString(1, username);
            qsql.setString(2, class_No);
            qsql.executeUpdate();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
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

    private void set_collection(HttpServletResponse response,String class_No,String username,String type){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("update sc set collection=? where user=? and class=?");
            qsql.setString(1,type);
            qsql.setString(2,username);
            qsql.setString(3,class_No);
            qsql.executeUpdate();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg",type);
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
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

    private void get_my_class(HttpServletResponse response,String username){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select class_type,collection,class,class_title,cover_address,schedule,last_time from sc,class_teacher_table where user='"+username+"' and class=class_id" );
            ArrayList<String> class_no = new ArrayList<>();
            ArrayList<String> title = new ArrayList<>();
            ArrayList<String> img_address = new ArrayList<>();
            ArrayList<String> collection = new ArrayList<>();
            ArrayList<Integer> class_type = new ArrayList<>();
            ArrayList<String> schedule = new ArrayList<>();
            ArrayList<String> last_time = new ArrayList<>();
            while (rs.next()){
                class_no.add(rs.getString("class"));
                title.add(rs.getString("class_title"));
                img_address.add(rs.getString("cover_address"));
                collection.add(rs.getString("collection"));
                class_type.add(rs.getInt("class_type"));
                schedule.add(rs.getString("schedule"));
                last_time.add(rs.getString("last_time"));
            }

            rs = statement.executeQuery("select belong_class_id,count(*) sum from sc,note where user='"+username+"' and class=belong_class_id and user=author group by belong_class_id" );
            ArrayList<String> note_count = new ArrayList<>();
            ArrayList<String> note_count_no = new ArrayList<>();
            while (rs.next()){
                note_count.add(rs.getString("sum"));
                note_count_no.add(rs.getString("belong_class_id"));
            }
            rs = statement.executeQuery("select belong_class_id,count(*) sum from sc,ask where user='"+username+"' and class=belong_class_id and user=asker group by belong_class_id");
            ArrayList<String> ask_count = new ArrayList<>();
            ArrayList<String> ask_count_no = new ArrayList<>();
            while (rs.next()){
                ask_count.add(rs.getString("sum"));
                ask_count_no.add(rs.getString("belong_class_id"));
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
    }

    private void add_class(HttpServletResponse response,String username,String Class_Name,int type){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("insert into  class_teacher_table(teacher,class_title,release_status,class_type,cover_address)"+"values(?,?,?,?,?)");
            qsql.setString(1,username);
            qsql.setString(2,Class_Name);
            qsql.setInt(3,0);
            qsql.setInt(4,type);
            qsql.setString(5,"/image/efb37fee400582742424a4ce08951213.png");
            qsql.executeUpdate();
            PrintWriter out = response.getWriter();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("state","1");
            out.flush();
            out.print(jsonObj);
            out.close();
        }catch (Exception e){
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

    private void get_Infor(HttpServletResponse response,String username){
        JSONArray json= new JSONArray();
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select class_id,class_title,student_count,release_status,cover_address from class_teacher_table where teacher='"+username+"'");
            while (rs.next()){
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("Title",rs.getString("class_title"));
                jsonObj.put("学员数",rs.getInt("student_count"));
                jsonObj.put("状态",rs.getInt("release_status"));
                jsonObj.put("封面地址",rs.getString("cover_address"));
                jsonObj.put("课程编号",rs.getInt("class_id"));
                json.add(jsonObj);
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(json);
            out.close();
            rs.close();
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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
