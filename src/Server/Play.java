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

@WebServlet(name = "Play")
public class Play extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        int No = Integer.parseInt(request.getParameter("No"));
        String class_No = request.getParameter("class_No");
        get_address(response,request,No,class_No);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private void get_address(HttpServletResponse response,HttpServletRequest request,int No,String class_No){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            HttpSession session = request.getSession();
            String sql ="select user from sc where user='"+session.getAttribute("user_id")+"' and class="+No;
            ResultSet rs = statement.executeQuery(sql);
            String sc_user = null;
            JSONObject jsonObj = new JSONObject();
            while (rs.next()){
                sc_user = rs.getString("user");
            }
            if (sc_user==null){
                jsonObj.put("sc_user", 0);
            }else {
                sql = "select video_address,Image_text,file_address,file_name from class where class_id=" + No + " and unit_no='"+class_No+"' and release_status="+1;
                rs = statement.executeQuery(sql);
                while (rs.next()){
                    jsonObj.put("sc_user", 1);
                    jsonObj.put("Video_address",rs.getString("video_address"));
                    jsonObj.put("text",rs.getString("Image_text"));
                    jsonObj.put("file_address",rs.getString("file_address"));
                    jsonObj.put("file_name",rs.getString("file_name"));
                }
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
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
    }
}
