package com.LJZ.Server;

import com.LJZ.DAO.HwDAO;
import com.LJZ.DB.GetSqlSessionFactory;
import com.LJZ.Model.SubModel.SubAddHW;
import com.LJZ.Tool.EasyExcelTest;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
//import java.util.List;

@WebServlet(name = "HomeWorkServlet")
public class HomeWorkServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        SqlSession sqlSession = GetSqlSessionFactory.getSqlSession();
        HwDAO hw = sqlSession.getMapper(HwDAO.class);
        switch (action) {
            case "get_work_list":
                get_work_list(request,response,hw);
                break;
            case "power_work":
                power_work(request,response,hw);
                break;
            case "get":
                get_work(request,response,hw);
                break;
            case "get_homework":
                get_homework(request,response,hw);
                break;
            case "get_stu":
                get_stu(request,response,hw);
                break;
            case "getRandom":
                getRandom(request,response,hw);
                break;
            case "get_text":
                get_text(request,response,hw);
                break;
            case "post_work":
                post_work(request,response,hw);
                break;
            case "delete_work":
                delete_work(request,response,hw);
                break;
            case "add_work":
                add_work(request,response,hw);
                break;
            case "postRandom":
                postRandom(request,response,hw);
                break;
        }
    }
    private void postRandom(HttpServletRequest request, HttpServletResponse response,HwDAO hw)throws IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        int sel = Integer.parseInt(request.getParameter("sel"));
        int cal = Integer.parseInt(request.getParameter("cal"));
        PrintWriter out = response.getWriter();
        out.print(hw.postRandom(sel,cal,id));
        out.flush();
        out.close();
    }

    private void getRandom(HttpServletRequest request, HttpServletResponse response,HwDAO hw)throws IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ran",hw.getRandom(id));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void power_work(HttpServletRequest request, HttpServletResponse response,HwDAO hw)throws IOException{
        int work_id = Integer.parseInt(request.getParameter("work_id"));
        String student = request.getParameter("student");
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title",hw.power_work(student,work_id));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void get_work_list(HttpServletRequest request, HttpServletResponse response,HwDAO hw)throws IOException{
        int class_id = Integer.parseInt(request.getParameter("class_id"));
        String student = request.getParameter("student");
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("work",hw.get_work_list(student,class_id));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void add_work(HttpServletRequest request, HttpServletResponse response,HwDAO hw)throws IOException{
        SubAddHW subAddHW = new SubAddHW();
        subAddHW.setClass_id(Integer.parseInt(request.getParameter("class_id")));
        subAddHW.setCourse_id(Integer.parseInt(request.getParameter("course_id")));
        subAddHW.setCourse_id(Integer.parseInt(request.getParameter("course_id")));
        subAddHW.setFile_name(request.getParameter("file_add"));
        subAddHW.setTime(Integer.parseInt(request.getParameter("time")));
        subAddHW.setTitle(request.getParameter("title"));
        String file_add = getServletContext().getRealPath("/") + "ExcelWrite"+"/"+ subAddHW.getFile_name();
        PrintWriter out = response.getWriter();
        EasyExcelTest easyExcelTest = new EasyExcelTest();
        int[] line = easyExcelTest.getLine(file_add);
        subAddHW.setSelLine(line[0]);
        subAddHW.setCalLine(line[1]);
        hw.add_work(subAddHW);
        //System.out.println(subAddHW.getId());
        out.print(subAddHW.getId());
        out.flush();
        out.close();
    }

    private void delete_work(HttpServletRequest request, HttpServletResponse response,HwDAO hw)throws IOException{
        int id = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();
        out.print(hw.delete_work(id));
        out.flush();
        out.close();
    }

    private void get_text(HttpServletRequest request, HttpServletResponse response,HwDAO hw)throws IOException{
        String stu_id = request.getParameter("stu_id");
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text",hw.get_text(stu_id));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void get_stu(HttpServletRequest request, HttpServletResponse response,HwDAO hw)throws IOException{
        int course_id = Integer.parseInt(request.getParameter("course_id"));//课程Id
        int class_id = Integer.parseInt(request.getParameter("class_id"));//班级Id
        int belong_work = Integer.parseInt(request.getParameter("work_id"));
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("student",hw.get_stu(course_id,class_id));
        jsonObject.put("hw",hw.get_stu_hw(belong_work));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //获取班级作业情况
    private void get_homework(HttpServletRequest request, HttpServletResponse response,HwDAO hw)throws IOException{
        int course_id = Integer.parseInt(request.getParameter("course_id"));
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("HW",hw.get_homework(course_id));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //作业提交
    private void post_work(HttpServletRequest request, HttpServletResponse response,HwDAO hw)throws IOException{
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
        String id_stu = work_id+student;
        PrintWriter out = response.getWriter();
        if (hw.post_work(id_stu,work_id,student,time,0)!=0)
            out.print(hw.post_text(id_stu,question,option,select,sel_standard,calculation,cal_standard,cal_answer));
        out.flush();
        out.close();
    }

    //获取作业
    private void get_work(HttpServletRequest request, HttpServletResponse response,HwDAO hw)throws IOException{
        int work_id = Integer.parseInt(request.getParameter("work_id"));
        EasyExcelTest easyExcelTest = new EasyExcelTest();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        String work = null;
        int[] setCount = new int[2];
        for (Map<String, Object> map:hw.get_class(work_id)){
            work = (String)map.get("file_add");
            setCount[0] = (int)map.get("setSel");
            setCount[1] = (int)map.get("setCal");
        }
        if (work!=null) jsonObject.put("work",easyExcelTest.read(getServletContext().getRealPath("/") + "ExcelWrite"+"/"+work,setCount[0],setCount[1]));
        out.print(jsonObject);
        out.flush();
        out.close();
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
}
