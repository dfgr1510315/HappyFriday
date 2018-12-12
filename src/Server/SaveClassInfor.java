package Server;



import net.sf.json.JSONObject;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/*@WebServlet(name = "SaveClassInfor")*/
public class SaveClassInfor extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String ClassName = request.getParameter("ClassName");
        String Read_or_Save = request.getParameter("Read_or_Save");
        String ClassCount = request.getParameter("ClassCount");
        String UnitCount = request.getParameter("UnitCount");
        switch (Read_or_Save) {
            case "save":
                String ClassInfor = request.getParameter("ClassInfor");
                ConnectMysql(ClassInfor, ClassName, UnitCount, ClassCount);
                break;
            case "read":
                PrintWriter out = response.getWriter();
                JSONObject jsonObj = ConnectMysql(ClassName);
                out.flush();
                out.print(jsonObj);
                out.close();
                break;
            default:
                ConnectMysql(ClassName,Read_or_Save);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private void ConnectMysql(String ClassInfor,String ClassName,String Unitcount,String Classcount){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            if (!con.isClosed()) System.out.println("数据库连接上了");
            /*String sql = "select * from usermanager";*/
            Statement statement = con.createStatement();
            /* ResultSet rq = statement.executeQuery(sql);*/
            PreparedStatement qsql = con.prepareStatement("update class_teacher_table set 课时管理=?, UnitCount=?, ClassCount=? where 标题=?");
            qsql.setString(1,ClassInfor );
            qsql.setString(2,Unitcount );
            qsql.setString(3,Classcount );
            qsql.setString(4,ClassName );
            qsql.executeUpdate();
            qsql.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject ConnectMysql(String ClassName){
        JSONObject jsonObj = new JSONObject();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            if (!con.isClosed()) System.out.println("数据库连接上了");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from class_teacher_table where 标题='"+ClassName+"'");
            while (rs.next()){
                jsonObj.put("Class_html",rs.getString("课时管理"));
                jsonObj.put("UnitCount",rs.getString("UnitCount"));
                jsonObj.put("ClassCount",rs.getString("ClassCount"));
                jsonObj.put("教师用户名",rs.getString("教师用户名"));
                jsonObj.put("state",rs.getString("状态"));
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    private void ConnectMysql(String ClassName,String state){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql = con.prepareStatement("update class_teacher_table set 状态=? where 标题=?");
            qsql.setString(1,state );
            qsql.setString(2,ClassName );
            qsql.executeUpdate();
            qsql.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
