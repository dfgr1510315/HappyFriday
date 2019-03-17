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
            case "search":
                String keyword = request.getParameter("keyword");
                search(keyword,response);
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

    private void search(String keyword,HttpServletResponse response){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            PrintWriter out = response.getWriter();
            ResultSet rs = statement.executeQuery("select class_title from class_teacher_table where class_title like '%"+keyword+"%'" );
            //ConnectSQL.my_println("查询");
            while (rs.next()){
                out.print("<li class='list_tips'><a>"+rs.getString("class_title")+"</a></li>");
            }
            out.flush();
            out.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delete_class(int No){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("delete from class_teacher_table where class_id=?");
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
            String public_select_sql = "select teacher,class_title,class_type.class_type,class_id,student_count,cover_address,outline from class_teacher_table,class_type ";
            String public_where_sql = "where class_type.class_type_id=class_teacher_table.class_type and release_status=1 order by class_id desc LIMIT ";
            if (page==0) sql = public_select_sql+public_where_sql+limit;
            else{
                if (type==0) {
                    sql = public_select_sql+public_where_sql+(25*(page-1))+","+25;
                    count_sql = "SELECT COUNT(*) count FROM class_teacher_table where release_status=1";
                }
                else {
                    sql = public_select_sql+" where class_teacher_table.class_type="+type+" and class_type.class_type_id=class_teacher_table.class_type and release_status=1 order by class_id desc LIMIT "+(25*(page-1))+","+25;
                    count_sql = "SELECT COUNT(*) count FROM class_teacher_table where class_type="+type+" and release_status=1";
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
                title_list.add(rs.getString("class_title"));
                teacher_list.add(rs.getString("teacher"));
                type_list.add(rs.getString("class_type.class_type"));
                no_list.add(rs.getInt("class_id"));
                student_number_list.add(rs.getInt("student_count"));
                cover_list.add(rs.getString("cover_address"));
                outline.add(rs.getString("outline"));
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
            PreparedStatement qsql = con.prepareStatement("update class_teacher_table set class_title=?, class_type=?, outline=? where class_id=? ");
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
            ResultSet rs = statement.executeQuery("select class_title,outline,class_type from class_teacher_table where  class_id="+No );
            while (rs.next()){
                jsonObj.put("title",rs.getString("class_title"));
                jsonObj.put("outline",rs.getString("outline"));
                jsonObj.put("type",rs.getString("class_type"));
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
            PreparedStatement qsql = con.prepareStatement("update class_teacher_table set layout=?, UnitCount=?, ClassCount=? where class_id=? ");
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
            ResultSet rs = statement.executeQuery("select class_title,layout,UnitCount,ClassCount,teacher,release_status from class_teacher_table where class_id="+No);
            while (rs.next()){
                jsonObj.put("Title",rs.getString("class_title"));
                jsonObj.put("Class_html",rs.getString("layout"));
                jsonObj.put("UnitCount",rs.getString("UnitCount"));
                jsonObj.put("ClassCount",rs.getString("ClassCount"));
                jsonObj.put("教师用户名",rs.getString("teacher"));
                jsonObj.put("state",rs.getInt("release_status"));
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
            PreparedStatement qsql = con.prepareStatement("update class_teacher_table set release_status=? where class_id=?");
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
