package Server;

import Bean.InforBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;



/*@WebServlet(name = "ChangeInfor")*/
public class ChangeInfor extends HttpServlet {
    private static final int loginError = 1;
    private static final int loginSuccess = 2;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String requestShow = request.getParameter("requestShow");
        String ID = request.getParameter("ID");
        if (requestShow!=null){
            ConnectMysql(ID,request,response);
            System.out.println(requestShow+"???");

        }else {
            String nike = request.getParameter("nike");
            String name = request.getParameter("name");
            String sex = request.getParameter("sex");
            String birth = request.getParameter("birth");
            String teacher  = request.getParameter("teacher");
            String introduction = request.getParameter("introduction");
            int status = ConnectMysql(ID,nike,name,sex,birth,teacher,introduction);
            int msg;
            if (status==0) {
                msg = loginSuccess;
                PrintWriter out = response.getWriter();
                out.print(msg);
                out.flush();
                out.close();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private int ConnectMysql(String ID,String nike,String name,String sex,String birth,String teacher,String introduction){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/usermanager";
        String user = "root";
        String Mysqlpassword = "138859";
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url,user,Mysqlpassword);
            if (!con.isClosed()) System.out.println("数据库连接上了");
            /*String sql = "select * from usermanager";*/
            Statement statement = con.createStatement();
           /* ResultSet rq = statement.executeQuery(sql);*/
            PreparedStatement qsql = con.prepareStatement("update personal_data set nike=?,name=?,sex=?,birth=?,information=?,teacher=? where ID=?");
            qsql.setString(1,nike);
            qsql.setString(2,name);
            qsql.setString(3,sex);
            qsql.setString(4,birth);
            qsql.setString(5,introduction);
            qsql.setString(6, teacher);
            qsql.setString(7,ID);
            qsql.executeUpdate();
            qsql.close();
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    private void ConnectMysql(String ID,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/usermanager";
        String user = "root";
        String Mysqlpassword = "138859";
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url,user,Mysqlpassword);
            if (!con.isClosed()) System.out.println("数据库连接上了");
            String sql = "select * from personal_data where ID='"+ID+"'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<InforBean> list = new ArrayList<InforBean>();
            while (rs.next()){
                InforBean inforBean = new InforBean();
                inforBean.setNike(rs.getString("nike"));
                inforBean.setName(rs.getString("name"));
                inforBean.setSex( rs.getString("sex"));
                inforBean.setBirth(rs.getString("birth"));
                inforBean.setInformation(rs.getString("information"));
                inforBean.setTeacher(rs.getString("teacher"));
                list.add(inforBean);
            }
            request.setAttribute("list", list);
            //System.out.println(nike+sex+birth+information+teacher+name);
            rs.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        request.getRequestDispatcher("PersonalCenter.jsp").forward(request,response);
    }
}
