package Server;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;


@WebServlet(name = "Register")
public class Register extends HttpServlet {
    private static final int loginNofind = 1;
    private static final int loginSuccess = 2;
    private static final int loginPWerror = 3;
    private static final int registerSuccess = 4;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String state = request.getParameter("state");
        ConnectSQL.my_println("获取到的状态、用户名和密码为：" + state + username + password);
        switch (state){
            case "login":
                login(request,response,username, password);
                break;
            case "Logout":
                HttpSession session = request.getSession();
                session.invalidate();
                break;
            case "register":
                String email = request.getParameter("email");
                int active = 0;
                register(response,username,password,email,active);
                break;
            case "forgetPW":
                String forget_user = request.getParameter("forget_user");
                forgetPW(response,forget_user);
                break;
            case "ChangeEmail":
                email = request.getParameter("email");
                ChangeEmail(response,username,email);
                break;
        }
    }

    private void ChangeEmail(HttpServletResponse response,String username,String email) throws IOException{
        String code=CodeUtil.generateUniqueCode();
        PrintWriter out = response.getWriter();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement psql = con.prepareStatement("update login_table set code=? where username=?");
            psql.setString(1,code);
            psql.setString(2,username);
            int state = psql.executeUpdate();
            out.flush();
            out.println(state);
            psql.close();
            con.close();
            new Thread(new MailUtil(email,code,1)).start();
        } catch (Exception e) {
            e.printStackTrace();
            out.flush();
            out.println(0);
        }
        out.close();
    }

    private void forgetPW(HttpServletResponse response,String forget_user) throws IOException{
        //生成激活码
        PrintWriter out = response.getWriter();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select password,email from login_table where username='"+forget_user+"'");
            String password = null;
            String email = null;
            while (rs.next()){
                password = rs.getString("password");
                email = rs.getString("email");
            }
            if (password==null){
                out.flush();
                out.println(0);
                return;
            }
            out.flush();
            out.println(1);
            con.close();
            new Thread(new Mailforget(email,password)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.close();
    }

    private void register(HttpServletResponse response,String username,String password,String email,int active) throws IOException{
        //生成激活码
        String code=CodeUtil.generateUniqueCode();
        PrintWriter out = response.getWriter();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement psql = con.prepareStatement("insert into  login_table values(?,?,?,?,?)");
            psql.setString(1,username);
            psql.setString(2,password);
            psql.setString(3,email);
            psql.setInt(4,active);
            psql.setString(5,code);
            int state = psql.executeUpdate();
            ConnectSQL.my_println("state:"+state);
            out.flush();
            out.println(state);
            psql.close();
            con.close();
            new Thread(new MailUtil(email, code,0)).start();
        } catch (Exception e) {
            e.printStackTrace();
            out.flush();
            out.println(0);
        }
        out.close();
    }

    private void login(HttpServletRequest request,HttpServletResponse response,String name,String password){
        ConnectSQL.my_println("login");
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url,ConnectSQL.user,ConnectSQL.Mysqlpassword);
            String sql = "select login_table.*,nike,head,usertype,(select readed from notice where username='"+name+"' order by time desc limit 1) readed from login_table,personal_table where login_table.username=personal_table.username and active=1 and login_table.username='"+name+"'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String username = null;
            String paw = null;
            String nike = null;
            String head_image = null;
            String usertype = null;
            String email = null;
            int readed=-1;
            PrintWriter out = response.getWriter();
            JSONObject jsonObject = new JSONObject();
            while (rs.next()){
                username = rs.getString("login_table.username");
                paw = rs.getString("password");
                nike = rs.getString("nike");
                head_image = rs.getString("head");
                usertype = rs.getString("usertype");
                email = rs.getString("email");
                readed = rs.getInt("readed");
            }
            if (username!=null&&paw!=null) {
                if (password.equals(paw)){
                    HttpSession session = request.getSession();
                    session.setAttribute("user_id",username);
                    session.setAttribute("usertype",usertype);
                    session.setAttribute("head_image",head_image);
                    session.setAttribute("email",email);
                    if (usertype.equals("teacher")){
                        String class_id="";
                        sql = "select 课程编号 from class_teacher_table where 教师用户名='"+username+"'";
                        rs = statement.executeQuery(sql);
                        while (rs.next()){
                            class_id=class_id +","+rs.getInt("课程编号");
                        }
                        session.setAttribute("class_id",class_id);
                        ConnectSQL.my_println(class_id);
                    }

                    /*Cookie cookie=new Cookie("JSESSIONID", session.getId());
                    cookie.setMaxAge(3600*24);
                    response.addCookie(cookie);*/
                    jsonObject.put("state",loginSuccess);//2
                    jsonObject.put("nike",nike);
                    jsonObject.put("head_image",head_image);
                    jsonObject.put("readed",readed);
                    out.print(jsonObject);
                    out.flush();
                    out.close();
                }
                else {
                    jsonObject.put("state",loginPWerror);//3
                    out.print(jsonObject);
                    out.flush();
                    out.close();
                }
            }else {
                jsonObject.put("state",loginNofind);//1
                out.print(jsonObject);
                out.flush();
                out.close();
            }
            con.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
