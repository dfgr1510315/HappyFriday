package Server;

import DAOlmpl.studentDAOlmpl;
import net.sf.json.JSONObject;

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
        switch (action) {
            case "get_class_students":
                int No = Integer.parseInt(request.getParameter("No"));
                int page = Integer.parseInt(request.getParameter("id"));
                get_class_students(response,No,page);
                break;
            case "save_schedule" :
                No = Integer.parseInt(request.getParameter("No"));
                String user = request.getParameter("user");
                String last_time = request.getParameter("last_time");
                int schedule = Integer.parseInt(request.getParameter("schedule"));
                save_schedule(No,schedule,user,last_time);
                break;
            case "remove_student" :
                No = Integer.parseInt(request.getParameter("No"));
                user = request.getParameter("student");
                remove_student(response,No,user);
                break;
            case "get_schedule" :
                No = Integer.parseInt(request.getParameter("No"));
                user = request.getParameter("student");
                get_schedule(response,No,user);
                break;
            case "get_class":
                No = Integer.parseInt(request.getParameter("No"));
                get_class(response,No);
                break;
            case "create_class":
                No = Integer.parseInt(request.getParameter("No"));
                user = request.getParameter("class_name");
                create_class(response,No,user);
                break;
            case "delete_class":
                No = Integer.parseInt(request.getParameter("id"));
                delete_class(response,No);
                break;
            case "move_student":
                user = request.getParameter("student");
                No = Integer.parseInt(request.getParameter("id"));
                move_student(response,No,user);
                break;
        }
    }

    //移动学生到指定班级
    private void move_student(HttpServletResponse response,int id,String user)throws IOException{
        studentDAOlmpl sl = new studentDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(sl.move_student(id,user));//bool
        out.flush();
        out.close();
    }

    //删除班级
    private void delete_class(HttpServletResponse response,int id)throws IOException{
        studentDAOlmpl sl = new studentDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(sl.delete_class(id));//bool
        out.flush();
        out.close();
    }

    //创建班级
    private void create_class(HttpServletResponse response,int No,String class_name)throws IOException{
        studentDAOlmpl sl = new studentDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(sl.create_class(No,class_name));//id
        out.flush();
        out.close();
    }

    //获取此课程下所有开设的班级
    private void get_class(HttpServletResponse response,int No)throws IOException{
        studentDAOlmpl sl = new studentDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("class",sl.get_class(No));
        out.print(jsonObject);//list
        out.flush();
        out.close();
    }

    //移除学生
    private void remove_student(HttpServletResponse response,int No,String user)throws IOException{
        studentDAOlmpl sl = new studentDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(sl.remove_student(No,user));//bool
        out.flush();
        out.close();
    }

    //存储课程学习进度
    private void save_schedule(int No,int percentage,String user,String last_time){
        studentDAOlmpl sl = new studentDAOlmpl();
        sl.save_schedule(No,percentage,user,last_time);
    }

    //获取课程学习进度
    private void get_schedule(HttpServletResponse response,int No,String user)throws IOException{
        studentDAOlmpl sl = new studentDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        String[] strings = sl.get_schedule(No,user);
        jsonObject.put("schedule",strings[0]);
        jsonObject.put("last_time",strings[1]);
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //获取此班级所有学生
    private void get_class_students(HttpServletResponse response,int No,int classification)throws IOException{
        studentDAOlmpl sl = new studentDAOlmpl();
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
