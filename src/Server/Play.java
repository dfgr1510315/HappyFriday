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

@WebServlet(name = "Play")
public class Play extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        int No = Integer.parseInt(request.getParameter("No"));
        String class_No = request.getParameter("class_No");
        get_address(response,No,class_No);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private void get_address(HttpServletResponse response,int No,String class_No){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            String sql = "select * from class,class_teacher_table where class.课程编号=" + No + " and class.课程编号=class_teacher_table.课程编号  and 章节序号='"+class_No+"'";
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObj = new JSONObject();
            while (rs.next()){
                jsonObj.put("Video_address",rs.getString("视频地址"));
                jsonObj.put("text",rs.getString("图文信息"));
                jsonObj.put("file_address",rs.getString("文件地址"));
                jsonObj.put("file_name",rs.getString("文件名称"));
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
