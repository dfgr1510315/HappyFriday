package Server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "ChangePW")
public class ChangePW extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String username = request.getParameter("username");
        String form_currentPassword = request.getParameter("form_currentPassword");
        String form_confirmPassword = request.getParameter("form_confirmPassword");
        int state = changepw(username,form_currentPassword,form_confirmPassword);
        PrintWriter out = response.getWriter();
        out.print(state);
        out.flush();
        out.close();
    }

    private int changepw(String username,String form_currentPassword, String form_confirmPassword) {
        int state = 0;
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select password from login_table where username='"+username+"'");
            String currentPW = "";
            while (rs.next()) currentPW = rs.getString("password");
            if (currentPW.equals(form_currentPassword)){
                PreparedStatement qsql = con.prepareStatement("update login_table set password=? where username=?");
                qsql.setString(1, form_confirmPassword);
                qsql.setString(2, username);
                qsql.executeUpdate();
                qsql.close();
            }else state = 1;
            con.close();

        } catch (Exception e) {
            state = 2;
            e.printStackTrace();
        }
        return state;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


}