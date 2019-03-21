package Server;


import com.alibaba.druid.pool.DruidPooledConnection;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
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
        String action = request.getParameter("action");
        if (action.equals("1")){  //更改用户信息
            String ID = request.getParameter("ID");
            String nike = request.getParameter("nike");
            String sex = request.getParameter("sex");
            String birth = request.getParameter("birth");
            String teacher = request.getParameter("teacher");
            String introduction = request.getParameter("introduction");
            int status = ConnectMysql(ID, nike, sex, birth, teacher, introduction);
            int msg;
            if (status == 0) {
                msg = loginSuccess;
                PrintWriter out = response.getWriter();
                out.print(msg);
                out.flush();
                out.close();
            }
        }else if (action.equals("2")){ //搜索用户
            String keyword = request.getParameter("keyword");
            int page = Integer.parseInt(request.getParameter("page"));
            search_user(response,keyword,page);
        }

    }

    private void search_user(HttpServletResponse response,String keyword,int page){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            PrintWriter out = response.getWriter();
            JSONObject jsonObject = new JSONObject();
            ArrayList<String> nike = new ArrayList<>();
            ArrayList<String> usertype = new ArrayList<>();
            ArrayList<String> sex = new ArrayList<>();
            ArrayList<String> head = new ArrayList<>();
            ArrayList<String> information = new ArrayList<>();
            ResultSet rs = statement.executeQuery("select nike,usertype,sex,head,information from personal_table where nike  like '%"+keyword+"%' limit "+(6*(page-1))+","+6);
            while (rs.next()){
                nike.add(rs.getString("nike"));
                usertype.add(rs.getString("usertype"));
                sex.add(rs.getString("sex"));
                head.add(rs.getString("head"));
                information.add(rs.getString("information"));
            }
            rs = statement.executeQuery("SELECT COUNT(*) count FROM personal_table where nike like '%"+keyword+"%'");
            while (rs.next()){
                jsonObject.put("count",rs.getString("count"));
            }
            jsonObject.put("nike",nike);
            jsonObject.put("usertype",usertype);
            jsonObject.put("sex",sex);
            jsonObject.put("head",head);
            jsonObject.put("information",information);
            out.print(jsonObject);
            out.flush();
            out.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    private int ConnectMysql(String ID, String nike, String sex, String birth, String teacher, String introduction) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("update personal_table set nike=?,sex=?,birth=?,information=?,teacher=? where username=?");
            qsql.setString(1, nike);
            qsql.setString(2, sex);
            qsql.setString(3, birth);
            qsql.setString(4, introduction);
            qsql.setString(5, teacher);
            qsql.setString(6, ID);
            qsql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return 0;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
