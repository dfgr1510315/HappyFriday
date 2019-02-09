package bank_server;

import Server.ConnectSQL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "l_r_bank")
public class l_r_bank extends HttpServlet {
    private static final int loginError = 1;
    private static final int loginSuccess = 2;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String state = request.getParameter("state");
        PrintWriter out = response.getWriter();
        if (state.equals("login")) out.print(Conn(username,password));
        else {
            String user_type = request.getParameter("user_type");
            String name = request.getParameter("name");
            String country = request.getParameter("country");
            String phone = request.getParameter("phone");
            String card_type = request.getParameter("card_type");
            String card_no = request.getParameter("card_no");
            String address = request.getParameter("address");
            String postcodes = request.getParameter("Postcodes");
            String sex = request.getParameter("sex");
            out.print(Conn(username,password,user_type,name,country,phone,card_type,card_no,address,postcodes,sex));
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private int Conn(String username,String password,String user_type,String name,String country,String phone,String card_type,String card_no,
    String address,String postcodes,String sex){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url,ConnectSQL.user,ConnectSQL.Mysqlpassword);
            PreparedStatement psql = con.prepareStatement("insert into  Bank_user(客户ID,姓名,性别,国籍,手机号码,证件类型,证件号码,住址,邮政编码,账户密码,账户类型号)"+"values(?,?,?,?,?,?,?,?,?,?,?)");
            psql.setString(1,username);
            psql.setString(2,name);
            psql.setString(3,sex);
            psql.setString(4,country);
            psql.setString(5,phone);
            psql.setString(6,card_type);
            psql.setString(7,card_no);
            psql.setString(8,address);
            psql.setInt(9,Integer.parseInt(postcodes));
            psql.setString(10,password);
            psql.setInt(11,Integer.parseInt(user_type));
            psql.executeUpdate();
            psql.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return loginSuccess;
    }

    private int Conn(String username,String password){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url,ConnectSQL.user,ConnectSQL.Mysqlpassword);
            String sql = "select 客户ID,账户密码 from Bank_user";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String _username;
            String paw;
            while (rs.next()){
                _username = rs.getString("客户ID");
                paw = rs.getString("账户密码");
                if (_username.equals(username)) {
                    if (password.equals(paw)){
                        con.close();
                        rs.close();
                        return loginSuccess;
                    }
                    rs.close();
                    con.close();
                }
            }
            con.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return loginError;
    }
}
