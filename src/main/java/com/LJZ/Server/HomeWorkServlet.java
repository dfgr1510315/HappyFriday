package com.LJZ.Server;

import com.LJZ.DAOlmpl.hwDAOlmpl;
import com.LJZ.Tool.EasyExcelTest;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
//import java.util.List;

@WebServlet(name = "HomeWorkServlet")
public class HomeWorkServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        switch (action) {
            case "get_work_list":
                get_work_list(request,response);
                break;
            case "power_work":
                power_work(request,response);
                break;
            case "get":
                get_work(request,response);
                break;
            case "get_homework":
                get_homework(request,response);
                break;
            case "get_stu":
                get_stu(request,response);
                break;
            case "getRandom":
                getRandom(request,response);
                break;
            case "get_text":
                get_text(request,response);
                break;
            case "post_work":
                post_work(request,response);
                break;
            case "delete_work":
                delete_work(request,response);
                break;
            case "add_work":
                add_work(request,response);
                break;
            case "postRandom":
                postRandom(request,response);
                break;
        }
    }
    private void postRandom(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        int sel = Integer.parseInt(request.getParameter("sel"));
        int cal = Integer.parseInt(request.getParameter("cal"));
        hwDAOlmpl hw = new hwDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(hw.postRandom(id,sel,cal));
        out.flush();
        out.close();
    }

    private void getRandom(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        hwDAOlmpl hw = new hwDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(Arrays.toString(hw.getRandom(id)));
        out.flush();
        out.close();
    }

    private void power_work(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int work_id = Integer.parseInt(request.getParameter("work_id"));
        String student = request.getParameter("student");
        hwDAOlmpl hw = new hwDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title",hw.power_work(work_id,student));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void get_work_list(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int class_id = Integer.parseInt(request.getParameter("class_id"));
        String student = request.getParameter("student");
        hwDAOlmpl hw = new hwDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("work",hw.get_work_list(student,class_id));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void add_work(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int class_id = Integer.parseInt(request.getParameter("class_id"));
        int course_id = Integer.parseInt(request.getParameter("course_id"));
        String file_name = request.getParameter("file_add");
        String file_add = getServletContext().getRealPath("/") + "ExcelWrite"+"/"+ file_name;
        int time = Integer.parseInt(request.getParameter("time"));
        String title = request.getParameter("title");
        hwDAOlmpl hw = new hwDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(hw.add_work(class_id,course_id,file_add,file_name,time,title));
        out.flush();
        out.close();
    }

    private void delete_work(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        hwDAOlmpl hw = new hwDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(hw.delete_work(id));
        out.flush();
        out.close();
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
        String question = request.getParameter("question");//选择题问题
        String option = request.getParameter("option");//选项
        String select = request.getParameter("select");//学生选择的答案
        String sel_standard = request.getParameter("sel_standard");//选择题标准答案
        String calculation = request.getParameter("calculation");//计算题
        String cal_standard = request.getParameter("cal_standard");//计算题标准答案
        String cal_answer = request.getParameter("cal_answer");//计算题学生答案
        hwDAOlmpl hw = new hwDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(hw.post_work(work_id,student,time,question,option,select,sel_standard,calculation,cal_standard,cal_answer));
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
        String[] work = hw.get_class(work_id);
        jsonObject.put("work",easyExcelTest.read(getServletContext().getRealPath("/") + "ExcelWrite"+"/"+work[0],Integer.parseInt(work[1]),Integer.parseInt(work[2])));
        out.print(jsonObject);
        out.flush();
        out.close();
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
