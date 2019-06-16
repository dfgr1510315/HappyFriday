package com.LJZ.Server;

import com.LJZ.DAO.UserDAO;
import com.LJZ.DB.GetSqlSessionFactory;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "changeInfor")
public class ChangeInfor extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        SqlSession sqlSession = GetSqlSessionFactory.getSqlSession();
        UserDAO udl = sqlSession.getMapper(UserDAO.class);
        switch (action) {
            case "1":   //更改用户信息
                change_user(request,response,udl);
                break;
            case "2":  //搜索用户
                search_user(request,response,udl);
                break;
            case "3": //获取用户信息
                get_infor(request,response,udl);
                break;
        }
    }

    private void change_user(HttpServletRequest request,HttpServletResponse response,UserDAO udl) throws IOException{
        String username = request.getParameter("ID");
        String nike = request.getParameter("nike");
        String sex = request.getParameter("sex");
        String birth = request.getParameter("birth");
        String teacher = request.getParameter("teacher");
        String introduction = request.getParameter("introduction");
        PrintWriter out = response.getWriter();
        out.println(udl.change_user(nike,sex,birth,introduction,teacher,username));
        out.flush();
        out.close();
    }

    private void get_infor(HttpServletRequest request, HttpServletResponse response,UserDAO udl) throws IOException{
        JSONObject jsonObj = new JSONObject();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        jsonObj.put("user_infor",udl.get_user((String) session.getAttribute("user_id")));
        out.println(jsonObj);
        out.flush();
        out.close();
    }

    private void search_user(HttpServletRequest request,HttpServletResponse response,UserDAO udl) throws IOException{
        String keyword = request.getParameter("keyword");
        int page = Integer.parseInt(request.getParameter("page"));
        JSONObject jsonObj = new JSONObject();
        PrintWriter out = response.getWriter();
        jsonObj.put("user_infor",udl.search_user(keyword,page));
        jsonObj.put("user_count",udl.count_user(keyword));
        out.println(jsonObj);
        out.flush();
        out.close();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
