package Server;

import com.alibaba.druid.pool.DruidPooledConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "Activation")
public class Activation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String code=request.getParameter("code");
        String email = request.getParameter("email");
        ConnectSQL.my_println("email:"+email);
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            if (email==null){
                qsql = con.prepareStatement("update login_table set active=?,code=? where code=?");
                qsql.setInt(1, 1);
                qsql.setString(2, null);
                qsql.setString(3, code);
                int state = qsql.executeUpdate();
                ConnectSQL.my_println("state"+state);
                if (state==0){
                    PrintWriter pw=response.getWriter();
                    pw.write("<script language='javascript'>alert('激活失败');window.location.href='/LJZ/HTML_JSP/homepage.html'</script>");
                }else {
                    PrintWriter pw=response.getWriter();
                    pw.write("<script language='javascript'>alert('激活成功');window.location.href='/LJZ/HTML_JSP/homepage.html'</script>");
                }
            }else {
                qsql = con.prepareStatement("update login_table set email=?,code=? where code=?");
                qsql.setString(1, email);
                qsql.setString(2, null);
                qsql.setString(3, code);
                int state = qsql.executeUpdate();
                if (state==0){
                    PrintWriter pw=response.getWriter();
                    pw.write("<script language='javascript'>alert('更换失败');window.location.href='/LJZ/HTML_JSP/homepage.html'</script>");
                }else {
                    PrintWriter pw=response.getWriter();
                    Cookie cookie = new Cookie("email", email);
                    cookie.setMaxAge(30*12*5*24*60*60);
                    cookie.setPath("/HTML_JSP");
                    response.addCookie(cookie);
                    pw.write("<script language='javascript'>alert('更换成功');window.location.href='/LJZ/HTML_JSP/homepage.html';</script>");
                }
            }

            //request.getRequestDispatcher("HTML_JSP/homepage.html").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }
}
