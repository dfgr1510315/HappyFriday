package Server;

import com.alibaba.druid.pool.DruidPooledConnection;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


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
            case "user_infor":
                user_infor(request,response);
                break;
        }
    }

    private void user_infor(HttpServletRequest request,HttpServletResponse response){
        HttpSession session=request.getSession();
        JSONObject jsonObj = new JSONObject();
        String username=(String) session.getAttribute("user_id");
        ConnectSQL.my_println("username"+username);
        if (username!=null){
           /* schedule = Arrays.toString((int[]) session.getAttribute("schedule"));
            last_time = Arrays.toString((String[]) session.getAttribute("last_time"));
            title = Arrays.toString((String[]) session.getAttribute("title"));*/
            DBPoolConnection dbp = DBPoolConnection.getInstance();
            DruidPooledConnection con =null;
            try {
                PrintWriter out = response.getWriter();
                con = dbp.getConnection();
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("SELECT readed from notice where to_user='"+username+"' order by time desc limit 1;");
                while (rs.next()) jsonObj.put("new_notice",rs.getInt("readed")); /*new_notice = rs.getInt("readed")*/;
                jsonObj.put("username",username);
                jsonObj.put("head_image",session.getAttribute("head_image"));
                jsonObj.put("usertype",session.getAttribute("usertype"));
                jsonObj.put("history_class_id",session.getAttribute("history_class_id"));
                jsonObj.put("schedule",session.getAttribute("schedule"));
                jsonObj.put("last_time",session.getAttribute("last_time"));
                jsonObj.put("title",session.getAttribute("title"));
                out.flush();
                out.print(jsonObj);
                out.close();
                rs.close();
            }catch (Exception e){
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
    }

    private void ChangeEmail(HttpServletResponse response,String username,String email) throws IOException{
        String code=CodeUtil.generateUniqueCode();
        PrintWriter out = response.getWriter();
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("update login_table set code=? where username=?");
            qsql.setString(1,code);
            qsql.setString(2,username);
            int state = qsql.executeUpdate();
            out.flush();
            out.println(state);
            new Thread(new MailUtil(email,code,1)).start();
        } catch (Exception e) {
            e.printStackTrace();
            out.flush();
            out.println(0);
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
        out.close();
    }

    private void forgetPW(HttpServletResponse response,String forget_user) throws IOException{
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select password,email from login_table where username='"+forget_user+"'");
            String password = null;
            String email = null;
            while (rs.next()){
                password = rs.getString("password");
                email = rs.getString("email");
            }
            PrintWriter out = response.getWriter();
            if (password==null){
                out.flush();
                out.println(0);
                return;
            }
            out.flush();
            out.println(1);
            out.close();
            new Thread(new Mailforget(email,password)).start();
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

    private void register(HttpServletResponse response,String username,String password,String email,int active) throws IOException{
        //生成激活码
        String code=CodeUtil.generateUniqueCode();
        PrintWriter out = response.getWriter();
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql = con.prepareStatement("insert into  login_table values(?,?,?,?,?)");
            qsql.setString(1,username);
            qsql.setString(2,password);
            qsql.setString(3,email);
            qsql.setInt(4,active);
            qsql.setString(5,code);
            int state = qsql.executeUpdate();
            ConnectSQL.my_println("state:"+state);
            out.flush();
            out.println(state);
            new Thread(new MailUtil(email, code,0)).start();
        } catch (Exception e) {
            e.printStackTrace();
            out.flush();
            out.println(0);
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
        out.close();
    }

    private void login(HttpServletRequest request,HttpServletResponse response,String name,String password){
        ConnectSQL.my_println("login");
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            String sql = "select login_table.username,password,email,nike,head,usertype from login_table,personal_table where login_table.username=personal_table.username and active=1 and login_table.username='"+name+"'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String username = null;
            String paw = null;
            String nike = null;
            String head_image = null;
            String usertype = null;
            String email = null;

            PrintWriter out = response.getWriter();
            JSONObject jsonObject = new JSONObject();
            while (rs.next()){
                username = rs.getString("login_table.username");
                paw = rs.getString("password");
                nike = rs.getString("nike");
                head_image = rs.getString("head");
                usertype = rs.getString("usertype");
                email = rs.getString("email");
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
                        sql = "select class_id from class_teacher_table where teacher='"+username+"'";
                        rs = statement.executeQuery(sql);
                        while (rs.next()){
                            class_id=class_id +","+rs.getInt("class_id");
                        }
                        session.setAttribute("class_id",class_id);
                        ConnectSQL.my_println(class_id);
                    }
                    sql = "SELECT class,schedule,last_time,class_title from sc,class_teacher_table where user='"+username+"' and class=class_id order by time desc limit 6";
                    rs = statement.executeQuery(sql);
                    //ArrayList<Integer> history_class_id = new ArrayList<>();
                    int[] history_class_id = new int[6];
                    int[] schedule = new int[6];
                    String[] last_time = new String[6];
                    String[] title = new String[6];
                    //ArrayList<Integer> schedule = new ArrayList<>();
                    //ArrayList<String> last_time = new ArrayList<>();
                   //ArrayList<String> title = new ArrayList<>();
                    for (int i=0;i<6;i++){
                        if (rs.next()){
                            history_class_id[i]=rs.getInt("class");
                            schedule[i]=rs.getInt("schedule");
                            last_time[i]=rs.getString("last_time");
                            title[i]=rs.getString("class_title");
                        }
                    }
                    session.setAttribute("history_class_id",history_class_id);
                    session.setAttribute("schedule",schedule);
                    session.setAttribute("last_time",last_time);
                    session.setAttribute("title",title);
                    /*Cookie cookie=new Cookie("JSESSIONID", session.getId());
                    cookie.setMaxAge(3600*24);
                    response.addCookie(cookie);*/
                    jsonObject.put("state",loginSuccess);//2
                    jsonObject.put("nike",nike);
                    jsonObject.put("head_image",head_image);

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
            rs.close();
        }catch (Exception e){
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


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
