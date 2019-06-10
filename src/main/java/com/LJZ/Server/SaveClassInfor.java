package com.LJZ.Server;

import com.LJZ.DAO.BasicsClassDAO;
import com.LJZ.DB.GetSqlSessionFactory;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SaveClassInfor")
public class SaveClassInfor extends HttpServlet {
    private static SqlSessionFactory factory = (SqlSessionFactory) new ClassPathXmlApplicationContext("application.xml").getBean("sqlSessionFactory");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String Read_or_Save = request.getParameter("Read_or_Save");
        SqlSession sqlSession = GetSqlSessionFactory.getSqlSession();
        BasicsClassDAO bc = sqlSession.getMapper(BasicsClassDAO.class);
        switch (Read_or_Save) {
            case "save":
                save_class(request, response, bc);
                break;
            case "read":
                read_class(request, response, bc);
                break;
            case "search_tips":
                search_tips(request, response, bc);
                break;
            case "search_class":
                search_class(request, response, bc);
                break;
            case "get_infor":
                get_infor(request, response, bc);
                break;
            case "set_infor":
                set_infor(request, response, bc);
                break;
            case "get_new_class":
                get_new_class(request, response, bc);
                break;
            case "delete_class":
                delete_class(request, response, bc);
                break;
            case "release":
                change_class_state(request, response, bc);
                break;
            case "get_file":
                get_file(response, request, bc);
                break;
            case "delete_file":
                delete_file(response, request, bc);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    private void delete_file(HttpServletResponse response, HttpServletRequest request, BasicsClassDAO bc) throws IOException {
        String address = request.getParameter("address");
        PrintWriter out = response.getWriter();
        //ConnectSQL.my_println("RealPath:"+getServletContext().getRealPath("/")+address);
        File file = new File(getServletContext().getRealPath("/") + address);
        file.delete();
        out.print(bc.delete_file(address));
        out.flush();
        out.close();
    }


    private void get_file(HttpServletResponse response, HttpServletRequest request, BasicsClassDAO bc) throws IOException {
        String class_id = request.getParameter("class_id");
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("files", bc.get_files(class_id));
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    //首页和课程类型页面都调用此方法获取课程列
    private void get_new_class(HttpServletRequest request, HttpServletResponse response, BasicsClassDAO bc) throws IOException {
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject();
        int page = Integer.parseInt(request.getParameter("page"));
        if (page == 0) jsonObj.put("new_class", bc.get_class("release_status=1", "class_id desc", "10"));
        else {
            String type = request.getParameter("type");
            String where = "release_status=1";
            if (!type.equals("0")) where = "release_status=1 and class_type=" + type;
            jsonObj.put("new_class", bc.get_class(where, "class_id desc", (25 * (page - 1)) + "," + 25));
            jsonObj.put("count", bc.get_class_count(where));
        }
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    private void search_class(HttpServletRequest request, HttpServletResponse response, BasicsClassDAO bc) throws IOException {
        String keyword = request.getParameter("keyword");
        int page = Integer.parseInt(request.getParameter("page"));
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("class", bc.get_class("class_title like '%" + keyword + "%' and release_status=1", "student_count desc", (6 * (page - 1)) + "," + 6));
        jsonObj.put("count", bc.get_class_count("class_title like '%" + keyword + "%' and release_status=1"));
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    private void search_tips(HttpServletRequest request, HttpServletResponse response, BasicsClassDAO bc) throws IOException {
        String keyword = request.getParameter("keyword");
        PrintWriter out = response.getWriter();
        List list = bc.search_tips(keyword);
        for (Object i : list) {
            out.print("<li class='list_tips'><a>" + i + "</a></li>");
        }
        out.flush();
        out.close();
    }

    private void delete_class(HttpServletRequest request, HttpServletResponse response, BasicsClassDAO bc) throws IOException {
        int No = Integer.parseInt(request.getParameter("No"));
        PrintWriter out = response.getWriter();
        out.print(bc.delete_class(No));
        out.flush();
        out.close();
    }

    private void set_infor(HttpServletRequest request, HttpServletResponse response, BasicsClassDAO bc) throws IOException {
        int No = Integer.parseInt(request.getParameter("No"));
        String title = request.getParameter("title");
        int sel1 = Integer.parseInt(request.getParameter("sel1"));
        String outline = request.getParameter("outline");
        PrintWriter out = response.getWriter();
        out.print(bc.set_infor(No, title, sel1, outline));
        out.flush();
        out.close();
    }

    private void get_infor(HttpServletRequest request, HttpServletResponse response, BasicsClassDAO bc) throws IOException {
        int No = Integer.parseInt(request.getParameter("No"));
        JSONObject jsonObj = new JSONObject();
        PrintWriter out = response.getWriter();
        jsonObj.put("baseInfor", bc.get_infor(No));
        out.print(jsonObj);
        out.flush();
        out.close();
    }


    private void save_class(HttpServletRequest request, HttpServletResponse response, BasicsClassDAO bc) throws IOException {
        int No = Integer.parseInt(request.getParameter("No"));
        String ClassCount = request.getParameter("ClassCount");
        String UnitCount = request.getParameter("UnitCount");
        PrintWriter out = response.getWriter();
        out.print(bc.save_class(UnitCount, ClassCount, No));
        out.flush();
        out.close();
    }

    private void read_class(HttpServletRequest request, HttpServletResponse response, BasicsClassDAO bc) throws IOException {
        int No = Integer.parseInt(request.getParameter("No"));
        JSONObject jsonObj = new JSONObject();
        PrintWriter out = response.getWriter();
        jsonObj.put("classList", bc.read_class(No));
        //System.out.println(jsonObj.get("classList"));
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    private void change_class_state(HttpServletRequest request, HttpServletResponse response, BasicsClassDAO bc) throws IOException {
        int No = Integer.parseInt(request.getParameter("No"));
        int state = Integer.parseInt(request.getParameter("state"));
        PrintWriter out = response.getWriter();
        out.print(bc.change_class_state(No, state));
        out.flush();
        out.close();
    }
}
