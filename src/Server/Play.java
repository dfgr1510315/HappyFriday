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
            String sql = "select video_address,Image_text,file_address,file_name from class where class_id=" + No + " and unit_no='"+class_No+"'";
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObj = new JSONObject();
            while (rs.next()){
                jsonObj.put("Video_address",rs.getString("video_address"));
                jsonObj.put("text",rs.getString("Image_text"));
                jsonObj.put("file_address",rs.getString("file_address"));
                jsonObj.put("file_name",rs.getString("file_name"));
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
