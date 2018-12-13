package Server;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@WebServlet(name = "Learn_list")
public class Learn_list extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String Title = request.getParameter("title");
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = ConnectMysql(Title);
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    private JSONObject ConnectMysql(String title){
        JSONObject jsonObject = new JSONObject();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            String sql = "select * from class,class_teacher_table where 课程标题='" + title + "' and 课程标题=标题";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String teacher = null;
            ArrayList<String> Serial_No = new ArrayList<>();
            ArrayList<String> Unit_Name = new ArrayList<>();
            ArrayList<String> Class_Name = new ArrayList<>();
            ArrayList<String> Video_Src = new ArrayList<>();
            ArrayList<String> Editor = new ArrayList<>();
            ArrayList<String> File_Href = new ArrayList<>();
            ArrayList<String> State = new ArrayList<>();
            while (rs.next()) {
                teacher = rs.getString("教师用户名");
                Serial_No.add(rs.getString("章节序号"));
                Unit_Name.add(rs.getString("章节标题"));
                Class_Name.add(rs.getString("课时标题"));
                Video_Src.add(rs.getString("视频地址"));
                Editor.add(rs.getString("图文信息"));
                File_Href.add(rs.getString("文件地址"));
                State.add(rs.getString("发布状态"));
            }
            jsonObject.put("teacher",teacher);
            jsonObject.put("Serial_No",Serial_No);
            jsonObject.put("Unit_Name",Unit_Name);
            jsonObject.put("Class_Name",Class_Name);
            jsonObject.put("Video_Src",Video_Src);
            jsonObject.put("Editor",Editor);
            jsonObject.put("File_Href",File_Href);
            jsonObject.put("State",State);
            //System.out.println(jsonObject);
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
