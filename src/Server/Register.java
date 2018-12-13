package Server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;



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
        String state = request.getParameter("state");
        System.out.println("获取到的状态、用户名和密码为：" + state + username + password);
        int Mysql_state = ConnectMysql(username, password);
        int msg;
        switch (state){
            case "login":
                if (Mysql_state == 2){
                    System.out.println("login success!!");
                    msg = loginSuccess;
                    HttpSession session = request.getSession();
                    session.setAttribute("user_id",username);
                    Cookie cookie=new Cookie("JSESSIONID", session.getId());
                    cookie.setMaxAge(60*20);
                    response.addCookie(cookie);
                    PrintWriter out = response.getWriter();
                    out.print(msg);
                    out.flush();
                    out.close();
                }else {
                    System.out.println("login false");
                    msg = loginError;
                    PrintWriter out = response.getWriter();
                    out.flush();
                    out.print(msg);
                    out.close();
                }
                break;
            case  "register":
                if (Mysql_state == 1 || Mysql_state == 2) {
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
                break;
            case "Logout":
                HttpSession session = request.getSession();
                session.setAttribute("user_id",username);
                Cookie cookie=new Cookie("JSESSIONID", session.getId());
                cookie.setMaxAge(0);
                response.addCookie(cookie);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }


    private int ConnectMysql(String name,String password){
        /*String url = "jdbc:mysql://localhost:3306/mysql";*/
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url,ConnectSQL.user,ConnectSQL.Mysqlpassword);
            if (!con.isClosed()) System.out.println("数据库连上了");
            String sql = "select * from login_table";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String username;
            String paw;
            while (rs.next()){
                username = rs.getString("username");
                paw = rs.getString("password");
                //System.out.println(username);
                //System.out.println(username+"\t"+paw);
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
            PreparedStatement psql = con.prepareStatement("insert into  login_table(username,password)"+"values(?,?)");
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
