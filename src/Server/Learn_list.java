package Server;

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
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql = con.prepareStatement("insert into sc(user,class,time) value (?,?,?)");
            qsql.setString(1, username);
            qsql.setInt(2, No);
            qsql.setString(3, time);
            qsql.executeUpdate();
            //notice.join_class_notice(username,No);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
            qsql.close();
            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private JSONObject get_class(int No){
        JSONObject jsonObject = new JSONObject();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            String sql = "select unit_no,unit_title,lesson_title,release_status from class where class_no=" + No ;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String teacher = null;
            String title = null;
            String head = null;
            String student_number = null;
            String class_type = null;
            String outline = null;
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
            sql = "select teacher,class_title,head,student_count,class_type,outline from class_teacher_table,personal_table where teacher=username and class_id=" + No;
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                head = rs.getString("head");
                teacher = rs.getString("teacher");
                title = rs.getString("class_title");
                student_number = rs.getString("student_count");
                class_type = rs.getInt("class_type")+"";
                outline = rs.getString("outline");
            }

            sql = "select class_id,class_title,cover_address from class_teacher_table where release_status='已发布' and class_type="+class_type+" limit 5";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                other_class_title.add(rs.getString("class_title"));
                other_class_no.add(rs.getInt("class_id"));
                other_class_image.add(rs.getString("cover_address"));
            }
            jsonObject.put("other_class_title",other_class_title);
            jsonObject.put("other_class_no",other_class_no);
            jsonObject.put("other_class_iamge",other_class_image);
            jsonObject.put("teacher",teacher);
            jsonObject.put("head",head);
            jsonObject.put("title",title);
            jsonObject.put("student_number",student_number);
            jsonObject.put("class_type",class_type);
            jsonObject.put("Serial_No",Serial_No);
            jsonObject.put("Unit_Name",Unit_Name);
            jsonObject.put("Class_Name",Class_Name);
            jsonObject.put("State",State);
            jsonObject.put("outline",outline);
            //System.out.println(jsonObject);
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
