package com.LJZ.Server;

import com.LJZ.DAO.ClassDAO;
import com.LJZ.DAO.UserDAO;
import com.LJZ.DB.GetSqlSessionFactory;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class save_image extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        SqlSession sqlSession = GetSqlSessionFactory.getSqlSession();
        ClassDAO cl = sqlSession.getMapper(ClassDAO.class);
        switch (action) {
            case "set_head":
                set_head(response, request);
                break;
            case "set_cover": {
                int No = Integer.parseInt(request.getParameter("No"));
                set_cover(response, request, No, cl);
                break;
            }
            case "get_cover": {
                int No = Integer.parseInt(request.getParameter("No"));
                get_cover(response, No, cl);
                break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    private void get_cover(HttpServletResponse response, int class_no, ClassDAO cl) throws IOException {
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cover", cl.get_cover(class_no));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void set_cover(HttpServletResponse response, HttpServletRequest request, int class_no, ClassDAO cl) throws IOException {
        String image = request.getParameter("image");
        PrintWriter out = response.getWriter();
        out.print(cl.set_cover(class_no, image));
        out.flush();
        out.close();
    }

    private void set_head(HttpServletResponse response, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user_id");
        String head_image = request.getParameter("image");
        SqlSession sqlSession = GetSqlSessionFactory.getSqlSession();
        UserDAO ul = sqlSession.getMapper(UserDAO.class);
        PrintWriter out = response.getWriter();
        out.print(ul.change_head(head_image,username));
        out.flush();
        out.close();
    }
}
