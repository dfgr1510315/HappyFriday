package com.LJZ.Server;

import com.LJZ.DAO.ClassDAO;
import com.LJZ.DAOlmpl.noteDAOlmpl;
import com.LJZ.DB.GetSqlSessionFactory;
import com.LJZ.Model.SubModel.Course_infor;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "Learn_list")
public class Learn_list extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        SqlSession sqlSession = GetSqlSessionFactory.getSqlSession();
        ClassDAO cdl = sqlSession.getMapper(ClassDAO.class);
        switch (action) {
            case "get_class":
                String s_No = request.getParameter("No");
                get_class(request, response, s_No, cdl);
                break;
            case "join_class":
                join_class(request, response, cdl);
                break;
            case "get_play_class":
                s_No = request.getParameter("No");
                get_play_class(response, s_No, cdl);
                break;
        }

    }

    private void join_class(HttpServletRequest request, HttpServletResponse response, ClassDAO cdl) throws IOException {
        int No = Integer.parseInt(request.getParameter("No"));
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user_id");
        String time = request.getParameter("time");
        int classification = Integer.parseInt(request.getParameter("classification"));
        PrintWriter out = response.getWriter();
        out.print(cdl.join_class(No, username, time, classification));
        out.flush();
        out.close();
    }

    private void get_play_class(HttpServletResponse response, String sNo, ClassDAO cdl) throws IOException {
        int No = Integer.parseInt(sNo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("chapter", cdl.get_chapter(No)); //获取目录
        jsonObject.put("title", cdl.get_class_name(No));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void get_class(HttpServletRequest request, HttpServletResponse response, String sNo, ClassDAO cdl) throws IOException {
        int No = Integer.parseInt(sNo);
        //AskDAO askDAO = sqlSession.getMapper(AskDAO.class);
        JSONObject jsonObject = new JSONObject();
        List my_class = cdl.get_class_infor(No);
        Course_infor course_infor = (Course_infor) my_class.get(0);
        boolean pass = isHave(request, sNo, course_infor.getRelease_status());
        if (!pass) {
            jsonObject.put("release_status", 0);
        } else {
            jsonObject.put("class_infor", my_class);
            jsonObject.put("chapter", cdl.get_chapter(No)); //获取目录
            jsonObject.put("recommend", cdl.get_recommend(course_infor.getClass_type()));//获取推荐课程
            jsonObject.put("note_count", new noteDAOlmpl().get_note_count("select count(note_id) count from note where belong_class_id=" + No));//获取笔记数量
            //jsonObject.put("ask_count",askDAO.get_ask_count("select count(ask_id) ask_count from ask where belong_class_id=" + No));//获取问答数量
        }
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private boolean isHave(HttpServletRequest request, String string, int release_status) {
        HttpSession session = request.getSession();
        String usertype = (String) session.getAttribute("usertype");
        if (release_status == 1) {
            return true;
        } else if (usertype == null || usertype.equals("student")) {
            return false;
        } else {
            String[] class_id = ((String) session.getAttribute("class_id")).split(",");
            for (String p : class_id) {
                if (string.equals(p)) return true;
            }
        }
        return false;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

}
