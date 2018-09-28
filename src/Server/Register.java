package Server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


@WebServlet(name = "Register")
public class Register extends HttpServlet {
    private static final int loginError = 1;
    private static final int loginSuccess = 2;
    private static final int registerError = 3;
    private static final int registerSuccess = 4;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String statu = request.getParameter("statu");
        System.out.println("获取到的状态、用户名和密码为：" + statu + username + password);
        int Mysqlstate = ConnectMysql(username, password);
        int msg;
        if (statu.equals("login")) {
            if (Mysqlstate == 2) {
                System.out.println("login success!!");
                msg = loginSuccess;
                PrintWriter out = response.getWriter();
                out.print(msg);
                out.flush();
                out.close();
            } else {
                System.out.println("login false");
                msg = loginError;
                PrintWriter out = response.getWriter();
                out.flush();
                out.print(msg);
               /* out.println("<script>");
                out.println("alert('用户名或密码错误');");
                out.println("</script>");*/
                out.close();
            }
        } else if (statu.equals("register")) {
            if (Mysqlstate == 1 || Mysqlstate == 2) {
                System.out.println("login fail!!");
                PrintWriter out = response.getWriter();
                out.flush();
                out.println("<script>");
                out.println("history.back();");
                out.println("alert('很遗憾，用户名已被使用');");
                out.println("</script>");
                out.close();
            } else {
                System.out.println("register success");
                PrintWriter out = response.getWriter();
                out.flush();
                out.println("<script>");
                out.println("alert('注册成功');");
                out.println("location.href='LoginPC.jsp';");
                out.println("</script>");
                out.close();
            }
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }


    private int ConnectMysql(String name,String password){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/usermanager";
        String user = "root";
        String Mysqlpassword = "138859";
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url,user,Mysqlpassword);
            if (!con.isClosed()) System.out.println("数据库连上了");
            String sql = "select * from user_table";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String username;
            String paw;
            while (rs.next()){
                username = rs.getString("username");
                paw = rs.getString("password");
                System.out.println(username+"\t"+paw);
                if (name.equals(username)) {
                    if (password.equals(paw)){
                        con.close();
                        rs.close();
                        return 2;//用户名和密码都吻合，登录成功
                    }
                    con.close();
                    rs.close();
                    return 1;//仅用户名吻合，用户名重复
                }
            }
            PreparedStatement psql = con.prepareStatement("insert into  user_table(username,password)"+"values(?,?)");
            psql.setString(1,name);
            psql.setString(2,password);
            psql.executeUpdate();
            psql.close();
            con.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


}
