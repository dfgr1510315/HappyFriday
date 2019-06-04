package com.LJZ.Server;

import com.LJZ.DAOlmpl.loginDAOlmpl;
import com.LJZ.Model.UserBase;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


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
            case "user_infor":
                user_infor(request,response);
                break;
            case "login":
                login(request,response,username, password);
                break;
            case "Logout":
                login_out(request);
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

    private void login_out(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }


    private void user_infor(HttpServletRequest request,HttpServletResponse response)throws IOException{
        HttpSession session=request.getSession();
        JSONObject jsonObject = new JSONObject();
        String username=(String) session.getAttribute("user_id");
        PrintWriter out = response.getWriter();
        loginDAOlmpl lgl = new loginDAOlmpl();
        jsonObject.put("read",lgl.unread(username));
        jsonObject.put("user",username);
        out.println(jsonObject);
        out.flush();
        out.close();
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
        System.out.println(flag);
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
            //session.setAttribute("head_image",userBase.getHead_image());
            //session.setAttribute("email",userBase.getEmail());
            if (userBase.getUsertype().equals("teacher")){//账户类型判断,是教师则去获取所教课程的ID
                String class_id= lgl.get_teacher_class(userBase.getUsername());
                session.setAttribute("class_id",class_id);
            }
            jsonObject.put("history",lgl.get_history(name));
            jsonObject.put("user_infor",userBase);
            jsonObject.put("state",loginSuccess);//2
        }else jsonObject.put("state",loginPWerror);
        out.print(jsonObject);
        out.flush();
        out.close();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
