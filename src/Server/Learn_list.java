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
            join_class(response,No,username);
        }

    }

    private void join_class(HttpServletResponse response,int No,String username){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql = con.prepareStatement("insert into sc(user,class) value (?,?)");
            qsql.setString(1, username);
            qsql.setInt(2, No);
            qsql.executeUpdate();
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
            String sql = "select 章节序号,章节标题,课时标题,发布状态 from class where 课程编号=" + No ;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String teacher = null;
            String title = null;
            String head = null;
            String student_number = null;
            String class_type = null;
            ArrayList<String> Serial_No = new ArrayList<>();
            ArrayList<String> Unit_Name = new ArrayList<>();
            ArrayList<String> Class_Name = new ArrayList<>();
            ArrayList<String> State = new ArrayList<>();
            while (rs.next()) {
                Serial_No.add(rs.getString("章节序号"));
                Unit_Name.add(rs.getString("章节标题"));
                Class_Name.add(rs.getString("课时标题"));
                State.add(rs.getString("发布状态"));
            }
            sql = "select 教师用户名,标题,head,学员数,课程类型 from class_teacher_table,personal_table where 教师用户名=username and 课程编号=" + No;
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                head = rs.getString("head");
                teacher = rs.getString("教师用户名");
                title = rs.getString("标题");
                student_number = rs.getString("学员数");
                class_type = rs.getInt("课程类型")+"";
            }
            jsonObject.put("teacher",teacher);
            jsonObject.put("head",head);
            jsonObject.put("title",title);
            jsonObject.put("student_number",student_number);
            jsonObject.put("class_type",class_type);
            jsonObject.put("Serial_No",Serial_No);
            jsonObject.put("Unit_Name",Unit_Name);
            jsonObject.put("Class_Name",Class_Name);
            jsonObject.put("State",State);
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
