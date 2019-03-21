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

@WebServlet(name = "Learn_list")
public class Learn_list extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        if (action.equals("get_class")){
            int No = Integer.parseInt(request.getParameter("No"));
            PrintWriter out = response.getWriter();
            JSONObject jsonObject = get_class(No);
            out.print(jsonObject);
            out.flush();
            out.close();
        }else if (action.equals("join_class")){
            int No = Integer.parseInt(request.getParameter("No"));
            HttpSession session=request.getSession();
            String username=(String) session.getAttribute("user_id");
            String time = request.getParameter("time");
            join_class(response,No,username,time);
        }

    }

    private void join_class(HttpServletResponse response,int No,String username,String time){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("insert into sc(user,class,time) value (?,?,?)");
            qsql.setString(1, username);
            qsql.setInt(2, No);
            qsql.setString(3, time);
            qsql.executeUpdate();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
        }
        catch(Exception e){
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

    private JSONObject get_class(int No){
        JSONObject jsonObject = new JSONObject();
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            String sql = "select unit_no,unit_title,lesson_title,release_status from class where class_id=" + No ;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            int class_type = 0;
            ArrayList<String> Serial_No = new ArrayList<>();
            ArrayList<String> Unit_Name = new ArrayList<>();
            ArrayList<String> Class_Name = new ArrayList<>();
            ArrayList<String> State = new ArrayList<>();
            ArrayList<String> other_class_title = new ArrayList<>();
            ArrayList<Integer> other_class_no = new ArrayList<>();
            ArrayList<String> other_class_image = new ArrayList<>();
            while (rs.next()) {
                Serial_No.add(rs.getString("unit_no"));
                Unit_Name.add(rs.getString("unit_title"));
                Class_Name.add(rs.getString("lesson_title"));
                State.add(rs.getString("release_status"));
            }
            sql = "select class_teacher_table.teacher,class_title,head,student_count,class_type,outline from class_teacher_table,personal_table where class_teacher_table.teacher=username and class_id=" + No;
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                jsonObject.put("head",rs.getString("head"));
                jsonObject.put("teacher",rs.getString("teacher"));
                jsonObject.put("title",rs.getString("class_title"));
                jsonObject.put("student_number",rs.getInt("student_count"));
                jsonObject.put("class_type",rs.getInt("class_type"));
                jsonObject.put("outline",rs.getString("outline"));
                class_type = rs.getInt("class_type");
            }

            sql = "select class_id,class_title,cover_address from class_teacher_table where release_status=1 and class_type="+class_type+" limit 5";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                other_class_title.add(rs.getString("class_title"));
                other_class_no.add(rs.getInt("class_id"));
                other_class_image.add(rs.getString("cover_address"));
            }
            sql = "SELECT COUNT(note_id) note_count,(SELECT COUNT(ask_id) FROM ask WHERE belong_class_id="+No+") ask_count FROM note where belong_class_id="+No;
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                jsonObject.put("note_count",rs.getInt("note_count"));
                jsonObject.put("ask_count",rs.getInt("ask_count"));
            }
            jsonObject.put("other_class_title",other_class_title);
            jsonObject.put("other_class_no",other_class_no);
            jsonObject.put("other_class_iamge",other_class_image);
            jsonObject.put("Serial_No",Serial_No);
            jsonObject.put("Unit_Name",Unit_Name);
            jsonObject.put("Class_Name",Class_Name);
            jsonObject.put("State",State);
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
        return jsonObject;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
