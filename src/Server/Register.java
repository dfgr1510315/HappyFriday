package Server;

import DAOlmpl.loginDAOlmpl;
import Model.History;
import Model.UserBase;
import com.alibaba.druid.pool.DruidPooledConnection;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;


@WebServlet(name = "Register")
public class Register extends HttpServlet {
    private static final int loginNofind = 1;
    private static final int loginSuccess = 2;
    private static final int loginPWerror = 3;
    private static final int registerSuccess = 4;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String state = request.getParameter("state");
        //ConnectSQL.my_println("获取到的状态、用户名和密码为：" + state + username + password);
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
            case "get_email":
                get_email(request,response);
                break;
        }
    }

    private void get_email(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        String email = (String) session.getAttribute("email");
        PrintWriter out = response.getWriter();
        out.flush();
        out.print(email);
        out.close();
    }

    private void user_infor(HttpServletRequest request,HttpServletResponse response){
        HttpSession session=request.getSession();
        JSONObject jsonObj = new JSONObject();
        String username=(String) session.getAttribute("user_id");
        //ConnectSQL.my_println("username"+username);
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
        loginDAOlmpl lgl = new loginDAOlmpl();
        boolean flag = lgl.change_email(username,email,code);
        if (flag){
            new Thread(new MailUtil(email,code,1)).start();
        }
        out.println(flag);
        out.flush();
        out.close();
    }

    private void forgetPW(HttpServletResponse response,String forget_user) throws IOException{
        PrintWriter out = response.getWriter();
        loginDAOlmpl lgl = new loginDAOlmpl();
        String[] list = lgl.forgetPW(forget_user);
        if (list.length!=0){
            new Thread(new Mailforget(list[1],list[0])).start();
            out.println(true);
        }else out.println(false);
        out.flush();
        out.close();
    }

    private void register(HttpServletResponse response,String username,String password,String email,int active) throws IOException{
        String code=CodeUtil.generateUniqueCode();
        PrintWriter out = response.getWriter();
        loginDAOlmpl lgl = new loginDAOlmpl();
        boolean flag = lgl.register(username,password,email,active,code);
        if (flag){
            new Thread(new MailUtil(email, code,0)).start();
        }
        out.println(flag);
        out.flush();
        out.close();
    }

    private void login(HttpServletRequest request,HttpServletResponse response,String name,String password)throws IOException{
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        loginDAOlmpl lgl = new loginDAOlmpl();
        UserBase userBase = lgl.login(name);
        if (userBase.getUsername()!=null&& userBase.getPaw().equals(password)){ //账户密码正确判断
            HttpSession session = request.getSession();
            session.setAttribute("user_id",userBase.getUsername());
            session.setAttribute("usertype",userBase.getUsertype());
            session.setAttribute("head_image",userBase.getHead_image());
            session.setAttribute("email",userBase.getEmail());
            if (userBase.getUsertype().equals("teacher")){//账户类型判断,是教师则去获取所教课程的ID
                String class_id= lgl.get_teacher_class(userBase.getUsername());
                session.setAttribute("class_id",class_id);
            }
            List list = lgl.get_history(name);
            /*Cookie cookie=new Cookie("history", list.toString());
            cookie.setMaxAge(3600*24);
            response.addCookie(cookie);*/
            //session.setAttribute("history",list);
            jsonObject.put("state",loginSuccess);//2
            jsonObject.put("head_image",userBase.getHead_image());

        }else jsonObject.put("state",loginPWerror);
        out.print(jsonObject);
        out.flush();
        out.close();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
