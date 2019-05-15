package Server;

import DAOlmpl.hwDAOlmpl;
import Tool.EasyExcelTest;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.List;

@WebServlet(name = "HomeWorkServlet")
public class HomeWorkServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        switch (action) {
            case "get":
                get_work(request,response);
                break;
            case "post_work":
                post_work(request,response);
                break;
            case "get_homework":
                get_homework(request,response);
                break;
            case "get_stu":
                get_stu(request,response);
                break;
            case "get_text":
                get_text(request,response);
                break;
        }
    }

    private void get_text(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String stu_id = request.getParameter("stu_id");
        hwDAOlmpl hw = new hwDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text",hw.get_text(stu_id));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void get_stu(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int course_id = Integer.parseInt(request.getParameter("course_id"));
        int class_id = Integer.parseInt(request.getParameter("class_id"));
        int belong_work = Integer.parseInt(request.getParameter("work_id"));
        hwDAOlmpl hw = new hwDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("HW",hw.get_stu(class_id,course_id,belong_work));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //获取班级作业情况
    private void get_homework(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int course_id = Integer.parseInt(request.getParameter("course_id"));
        hwDAOlmpl hw = new hwDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("HW",hw.get_homework(course_id));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //作业提交
    private void post_work(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int work_id = Integer.parseInt(request.getParameter("work_id"));
        String student = request.getParameter("student");
        String time = request.getParameter("time");
        String question = request.getParameter("question");
        String option = request.getParameter("option");
        String select = request.getParameter("select");
        String subjective = request.getParameter("subjective");
        hwDAOlmpl hw = new hwDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(hw.post_work(work_id,student,time,question,option,select,subjective));
        out.flush();
        out.close();
    }

    //获取作业
    private void get_work(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int work_id = Integer.parseInt(request.getParameter("work_id"));
        hwDAOlmpl hw = new hwDAOlmpl();
        EasyExcelTest easyExcelTest = new EasyExcelTest();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("work",easyExcelTest.read(getServletContext().getRealPath("/") + "ExcelWrite"+"/"+hw.get_class(work_id)));
        out.print(jsonObject);
        out.flush();
        out.close();
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
