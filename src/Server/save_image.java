package Server;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class save_image extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session=request.getSession();
        String username=(String) session.getAttribute("user_id");
        String action = request.getParameter("action");
        String image = request.getParameter("image");
        switch (action) {
            case "set_head":
                set_head(username, image);
                break;
            case "set_cover": {
                int No = Integer.parseInt(request.getParameter("No"));
                set_cover(response, image, No);
                break;
            }
            case "get_cover": {
                int No = Integer.parseInt(request.getParameter("No"));
                get_cover(response, No);
                break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private void get_cover(HttpServletResponse response,int class_no){
         try{
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 封面地址 from class_teacher_table where 课程编号="+class_no);
            JSONObject jsonObj = new JSONObject();
            while (rs.next()){
                jsonObj.put("cover_address",rs.getString("封面地址"));
            }
             PrintWriter out = response.getWriter();
             out.flush();
             out.print(jsonObj);
             out.close();
             con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void set_cover(HttpServletResponse response,String image,int class_no){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql = con.prepareStatement("update class_teacher_table set 封面地址=? where 课程编号=?");
            qsql.setString(1, image);
            qsql.setInt(2, class_no);
            ConnectSQL.my_println(image);
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

    private void set_head(String username, String head_image){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql = con.prepareStatement("update personal_table set head=? where username=?");
            qsql.setString(1, head_image);
            qsql.setString(2, username);
            qsql.executeUpdate();
            qsql.close();
            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
