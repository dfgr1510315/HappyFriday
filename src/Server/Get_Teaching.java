package Server;

import net.sf.json.JSONArray;
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

@WebServlet(name = "Get_Teaching")
public class Get_Teaching extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String username = request.getParameter("username");
        JSONArray json= new JSONArray();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 标题,学员数,状态,封面地址 from class_teacher_table where 教师用户名='"+username+"'");
            while (rs.next()){
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("Title",rs.getString("标题"));
                jsonObj.put("学员数",rs.getInt("学员数"));
                jsonObj.put("状态",rs.getString("状态"));
                jsonObj.put("封面地址",rs.getString("封面地址"));
                json.add(jsonObj);
            }
            rs.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        System.out.println(json);
        out.flush();
        out.print(json);
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
