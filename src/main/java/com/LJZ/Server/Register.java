package com.LJZ.Server;

import com.LJZ.DAO.LoginDAO;
import com.LJZ.DB.GetSqlSessionFactory;
import com.LJZ.Model.UserBase;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class Register extends HttpServlet {
    private static final int loginSuccess = 2;
    private static final int loginPwError = 3;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String state = request.getParameter("state");
        SqlSession sqlSession = GetSqlSessionFactory.getSqlSession();
        LoginDAO lgl = sqlSession.getMapper(LoginDAO.class);
        //ConnectSQL.my_println("获取到的状态、用户名和密码为：" + state + username + password);
        switch (state){
            case "user_infor":
                user_infor(request,response,lgl);
                break;
            case "login":
                login(request,response,lgl);
                break;
            case "Logout":
                login_out(request);
                break;
            case "register":
                register(request,response,lgl);
                break;
            case "forgetPW":
                forgetPW(request,response,lgl);
                break;
            case "ChangeEmail":
                ChangeEmail(request,response,lgl);
                break;
        }
    }

    private void login_out(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }


    private void user_infor(HttpServletRequest request,HttpServletResponse response,LoginDAO lgl)throws IOException{
        HttpSession session=request.getSession();
        JSONObject jsonObject = new JSONObject();
        String username=(String) session.getAttribute("user_id");
        PrintWriter out = response.getWriter();
        jsonObject.put("read",lgl.unread(username));
        jsonObject.put("user",username);
        out.println(jsonObject);
        out.flush();
        out.close();
    }

    private void ChangeEmail(HttpServletRequest request,HttpServletResponse response,LoginDAO lgl) throws IOException{
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        System.out.println(username);
        String code=CodeUtil.generateUniqueCode();
        PrintWriter out = response.getWriter();
        int flag = lgl.change_email(code,username);
        if (flag!=0) new Thread(new MailUtil(email,code,1)).start();
        out.println(flag);
        out.flush();
        out.close();
    }

    private void forgetPW(HttpServletRequest request,HttpServletResponse response,LoginDAO lgl) throws IOException{
        PrintWriter out = response.getWriter();
        String forget_user = request.getParameter("forget_user");
        List<HashMap<String,Object>> list = lgl.forgetPW(forget_user);
        if (list.size()!=0){
            new Thread(new Mailforget((String) list.get(0).get("email"),(String)list.get(0).get("password"))).start();
            out.println(true);
        }else out.println(false);
        out.flush();
        out.close();
    }

    private void register(HttpServletRequest request,HttpServletResponse response,LoginDAO lgl) throws IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String code=CodeUtil.generateUniqueCode();
        PrintWriter out = response.getWriter();
        int flag = lgl.register(username,password,email,0,code);
        if (flag!=0) new Thread(new MailUtil(email, code,0)).start();
        //System.out.println(flag);
        out.println(flag);
        out.flush();
        out.close();
    }

    private void login(HttpServletRequest request,HttpServletResponse response,LoginDAO lgl)throws IOException{
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserBase userBase = lgl.login(username);
        if (userBase.getUsername()!=null&& userBase.getPassword().equals(password)){ //账户密码正确判断
            HttpSession session = request.getSession();
            session.setAttribute("user_id",userBase.getUsername());
            session.setAttribute("usertype",userBase.getUsertype());
            //session.setAttribute("head_image",userBase.getHead_image());
            //session.setAttribute("email",userBase.getEmail());
            if (userBase.getUsertype().equals("teacher")){//账户类型判断,是教师则去获取所教课程的ID
                List class_id= lgl.get_teacher_class(userBase.getUsername());
                session.setAttribute("class_id",class_id);
            }
            jsonObject.put("history",lgl.get_history(username));
            jsonObject.put("user_infor",userBase);
            jsonObject.put("state",loginSuccess);//2
        }else jsonObject.put("state",loginPwError);
        out.print(jsonObject);
        out.flush();
        out.close();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
