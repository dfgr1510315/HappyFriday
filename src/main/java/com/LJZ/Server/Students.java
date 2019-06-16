package com.LJZ.Server;

import com.LJZ.DAO.StudentDAO;
import com.LJZ.DB.GetSqlSessionFactory;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Students")
public class Students extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        SqlSession sqlSession = GetSqlSessionFactory.getSqlSession();
        StudentDAO sl = sqlSession.getMapper(StudentDAO.class);
        switch (action) {
            case "get_class_students":
                get_class_students(request,response,sl);
                break;
            case "save_schedule" :
                save_schedule(request,sl);
                break;
            case "remove_student" :
                remove_student(request,response,sl);
                break;
            case "get_schedule" :
                get_schedule(request,response,sl);
                break;
            case "get_class":
                get_class(request,response,sl);
                break;
            case "create_class":
                create_class(request,response,sl);
                break;
            case "delete_class":
                delete_class(request,response,sl);
                break;
            case "move_student":
                move_student(request,response,sl);
                break;
        }
    }

    //移动学生到指定班级
    private void move_student(HttpServletRequest request,HttpServletResponse response,StudentDAO sl)throws IOException{
        String user = request.getParameter("student");
        int id = Integer.parseInt(request.getParameter("id"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        PrintWriter out = response.getWriter();
        out.print(sl.move_student(id,user,courseId));//int
        out.flush();
        out.close();
    }

    //删除班级
    private void delete_class(HttpServletRequest request,HttpServletResponse response,StudentDAO sl)throws IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();
        out.print(sl.delete_class(id));//int
        out.flush();
        out.close();
    }

    //创建班级
    private void create_class(HttpServletRequest request,HttpServletResponse response,StudentDAO sl)throws IOException{
        int No = Integer.parseInt(request.getParameter("No"));
        String class_name = request.getParameter("class_name");
        PrintWriter out = response.getWriter();
        out.print(sl.create_class(No,class_name));//IdPOJO
        out.flush();
        out.close();
    }

    //获取此课程下所有开设的班级
    private void get_class(HttpServletRequest request,HttpServletResponse response,StudentDAO sl)throws IOException{
        int No = Integer.parseInt(request.getParameter("No"));
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("class",sl.get_class(No));
        out.print(jsonObject);//list
        out.flush();
        out.close();
    }

    //移除学生
    private void remove_student(HttpServletRequest request,HttpServletResponse response,StudentDAO sl)throws IOException{
        int No = Integer.parseInt(request.getParameter("No"));
        String user = request.getParameter("student");
        PrintWriter out = response.getWriter();
        out.print(sl.remove_student(No,user));//int
        out.flush();
        out.close();
    }

    //存储课程学习进度
    private void save_schedule(HttpServletRequest request,StudentDAO sl){
        int No = Integer.parseInt(request.getParameter("No"));
        String user = request.getParameter("user");
        String last_time = request.getParameter("last_time");
        int schedule = Integer.parseInt(request.getParameter("schedule"));
        sl.save_schedule(schedule,last_time,No,user);
    }

    //获取课程学习进度
    private void get_schedule(HttpServletRequest request,HttpServletResponse response,StudentDAO sl)throws IOException{
        int No = Integer.parseInt(request.getParameter("No"));
        String user = request.getParameter("student");
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("schedule",sl.get_schedule(No,user));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //获取此班级所有学生
    private void get_class_students(HttpServletRequest request,HttpServletResponse response,StudentDAO sl)throws IOException{
        int No = Integer.parseInt(request.getParameter("No"));
        int classification = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("students",sl.get_class_students(No,classification));
        out.print(jsonObject);//list
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
