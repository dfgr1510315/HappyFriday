package Server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "Activation")
public class Activation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String code=request.getParameter("code");
        String email = request.getParameter("email");
        ConnectSQL.my_println("email:"+email);
        /*UserService userService=new UserServiceImpl();
        if(userService.activeUser(code)){
            request.getRequestDispatcher("/welcome.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("/fail.jsp").forward(request, response);
        }*/
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            if (email==null){
                PreparedStatement qsql = con.prepareStatement("update login_table set active=?,code=? where code=?");
                qsql.setInt(1, 1);
                qsql.setString(2, null);
                qsql.setString(3, code);
                int state = qsql.executeUpdate();
                qsql.close();
                con.close();
                ConnectSQL.my_println("state"+state);
                if (state==0){
                    PrintWriter pw=response.getWriter();
                    pw.write("<script language='javascript'>alert('激活失败');window.location.href='HTML_JSP/homepage.jsp'</script>");
                }else {
                    PrintWriter pw=response.getWriter();
                    pw.write("<script language='javascript'>alert('激活成功');window.location.href='HTML_JSP/homepage.jsp'</script>");
                }
            }else {
                PreparedStatement qsql = con.prepareStatement("update login_table set email=?,code=? where code=?");
                qsql.setString(1, email);
                qsql.setString(2, null);
                qsql.setString(3, code);
                int state = qsql.executeUpdate();
                qsql.close();
                con.close();
                if (state==0){
                    PrintWriter pw=response.getWriter();
                    pw.write("<script language='javascript'>alert('更换失败');window.location.href='HTML_JSP/homepage.jsp'</script>");
                }else {
                    PrintWriter pw=response.getWriter();
                    pw.write("<script language='javascript'>alert('更换成功');window.location.href='HTML_JSP/homepage.jsp'</script>");
                }
            }

            //request.getRequestDispatcher("HTML_JSP/homepage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
