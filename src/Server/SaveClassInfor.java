package Server;



import net.sf.json.JSONObject;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "SaveClassInfor")
public class SaveClassInfor extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String Read_or_Save = request.getParameter("Read_or_Save");
        switch (Read_or_Save) {
            case "save":
                int No = Integer.parseInt(request.getParameter("No"));
                String ClassInfor = request.getParameter("ClassInfor");
                String ClassCount = request.getParameter("ClassCount");
                String UnitCount = request.getParameter("UnitCount");
                save_class(No,ClassInfor, UnitCount, ClassCount);
                break;
            case "read":
                No = Integer.parseInt(request.getParameter("No"));
                PrintWriter out = response.getWriter();
                JSONObject jsonObj = read_class(No);
                out.flush();
                out.print(jsonObj);
                out.close();
                break;
            case "get_infor":
                No = Integer.parseInt(request.getParameter("No"));
                get_infor(response,No);
                break;
            case "set_infor":
                No = Integer.parseInt(request.getParameter("No"));
                String title = request.getParameter("title");
                int sel1 = Integer.parseInt(request.getParameter("sel1"));
                String outline = request.getParameter("outline");
                set_infor(response,No,title,sel1,outline);
                break;
            case "get_new_class":
                int limit = Integer.parseInt(request.getParameter("limit"));
                int page = Integer.parseInt(request.getParameter("page"));
                int type = Integer.parseInt(request.getParameter("type"));
                get_new_class(response,limit,page,type);
                break;
            case "delete_class":
                No = Integer.parseInt(request.getParameter("No"));
                delete_class(No);
                break;
            default:
                No = Integer.parseInt(request.getParameter("No"));
                change_class_state(No,Read_or_Save);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private void delete_class(int No){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("delete from class_teacher_table where 课程编号=?");
            qsql.setInt(1,No);
            qsql.executeUpdate();
            qsql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_new_class(HttpServletResponse response,int limit,int page,int type){
        JSONObject jsonObj = new JSONObject();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs;
            String sql;
            String count_sql;
            if (page==0) sql = "select 教师用户名,标题,class_type.课程类型,课程编号,学员数,封面地址,课程概要 from class_teacher_table,class_type where class_type.课程类型编号=class_teacher_table.课程类型 and 状态='已发布' order by 课程编号 desc LIMIT "+limit;
            else{
                if (type==0) {
                    sql = "select 教师用户名,标题,class_type.课程类型,课程编号,学员数,封面地址,课程概要 from class_teacher_table,class_type where class_type.课程类型编号=class_teacher_table.课程类型 and 状态='已发布' order by 课程编号 desc LIMIT "+(25*(page-1))+","+25;
                    count_sql = "SELECT COUNT(*) count FROM class_teacher_table where 状态='已发布'";
                }
                else {
                    sql = "select 教师用户名,标题,class_type.课程类型,课程编号,学员数,封面地址,课程概要 from class_teacher_table,class_type where class_teacher_table.课程类型="+type+" and class_type.课程类型编号=class_teacher_table.课程类型 and 状态='已发布' order by 课程编号 desc LIMIT "+(25*(page-1))+","+25;
                    count_sql = "SELECT COUNT(*) count FROM class_teacher_table where 课程类型="+type+" and 状态='已发布'";
                }
                rs = statement.executeQuery(count_sql);
                while (rs.next()){
                    jsonObj.put("count",rs.getString("count"));
                }
            }
            rs = statement.executeQuery(sql);
            ArrayList<String> title_list = new ArrayList<>();
            ArrayList<String> teacher_list = new ArrayList<>();
            ArrayList<String> type_list = new ArrayList<>();
            ArrayList<Integer> no_list = new ArrayList<>();
            ArrayList<Integer> student_number_list = new ArrayList<>();
            ArrayList<String> cover_list = new ArrayList<>();
            ArrayList<String> outline = new ArrayList<>();
            while (rs.next()){
                title_list.add(rs.getString("标题"));
                teacher_list.add(rs.getString("教师用户名"));
                type_list.add(rs.getString("课程类型"));
                no_list.add(rs.getInt("课程编号"));
                student_number_list.add(rs.getInt("学员数"));
                cover_list.add(rs.getString("封面地址"));
                outline.add(rs.getString("课程概要"));
            }
            jsonObj.put("title",title_list);
            jsonObj.put("teacher",teacher_list);
            jsonObj.put("type",type_list);
            jsonObj.put("no",no_list);
            jsonObj.put("student_number",student_number_list);
            jsonObj.put("cover",cover_list);
            jsonObj.put("outline",outline);
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void set_infor(HttpServletResponse response,int No,String title,int sel1,String outline){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            /*String sql = "select * from usermanager";*/
            /* ResultSet rq = statement.executeQuery(sql);*/
            PreparedStatement qsql = con.prepareStatement("update class_teacher_table set 标题=?, 课程类型=?, 课程概要=? where 课程编号=? ");
            qsql.setString(1,title );
            qsql.setInt(2,sel1 );
            qsql.setString(3,outline );
            qsql.setInt(4,No );
            qsql.executeUpdate();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
            qsql.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_infor(HttpServletResponse response,int No){
        JSONObject jsonObj = new JSONObject();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 标题,课程概要,课程类型 from class_teacher_table where  课程编号="+No );
            while (rs.next()){
                jsonObj.put("title",rs.getString("标题"));
                jsonObj.put("outline",rs.getString("课程概要"));
                jsonObj.put("type",rs.getString("课程类型"));
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save_class(int No,String ClassInfor,String Unitcount,String Classcount){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            /*String sql = "select * from usermanager";*/
            /* ResultSet rq = statement.executeQuery(sql);*/
            PreparedStatement qsql = con.prepareStatement("update class_teacher_table set 课时管理=?, UnitCount=?, ClassCount=? where 课程编号=? ");
            qsql.setString(1,ClassInfor );
            qsql.setString(2,Unitcount );
            qsql.setString(3,Classcount );
            qsql.setInt(4,No );
            qsql.executeUpdate();
            qsql.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject read_class(int No){
        JSONObject jsonObj = new JSONObject();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from class_teacher_table where 课程编号="+No);
            while (rs.next()){
                jsonObj.put("Title",rs.getString("标题"));
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

    private void change_class_state(int No,String state){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql = con.prepareStatement("update class_teacher_table set 状态=? where 课程编号=?");
            qsql.setString(1,state );
            qsql.setInt(2,No );
            qsql.executeUpdate();
            qsql.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
