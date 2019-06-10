package com.LJZ.Server;

import com.LJZ.DAO.ClassDAO;
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

@WebServlet(name = "Play")
public class Play extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        SqlSession sqlSession = GetSqlSessionFactory.getSqlSession();
        ClassDAO cl = sqlSession.getMapper(ClassDAO.class);
        switch (action) {
            case "get_address":
                get_address(response, request, cl);
                break;
            case "save_viewed":
                save_viewed(request, cl);
                break;
            case "get_viewed":
                get_viewed(response, request, cl);
                break;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response, ClassDAO cl) throws IOException {
        doPost(request, response);
    }

    private void get_viewed(HttpServletResponse response, HttpServletRequest request, ClassDAO cl) throws IOException {
        int class_id = Integer.parseInt(request.getParameter("class_id"));
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("viewed", cl.get_viewed(class_id, start, end));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void save_viewed(HttpServletRequest request, ClassDAO cl) {
        int class_id = Integer.parseInt(request.getParameter("class_id"));
        int day = Integer.parseInt(request.getParameter("day"));
        if (cl.save_viewed(class_id, day) == 0) cl.insertViewed(class_id, day);
    }

    private void get_address(HttpServletResponse response, HttpServletRequest request, ClassDAO cl) throws IOException {
        int No = Integer.parseInt(request.getParameter("No"));
        String class_No = request.getParameter("class_No");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user_id");
        JSONObject jsonObject = new JSONObject();
        if (cl.PlayPower(username, No) != null) jsonObject.put("material", cl.get_material(No, class_No));
        else jsonObject.put("material", 0);
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }
}
