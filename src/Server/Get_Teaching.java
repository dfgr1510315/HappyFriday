package Server;

import DAOlmpl.classDAOlmpl;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Get_Teaching")
public class Get_Teaching extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session=request.getSession();
        String type = request.getParameter("type");
        String username=(String) session.getAttribute("user_id");
        switch (type) {
            case "found_class":
                add_class(request,response, username);
                break;
            case "get_Class":
                get_Infor(response, username);
                break;
            case "get_my_class":
                get_my_class(response, username);
                break;
            case "add_collection":
                String class_no = request.getParameter("class_no");
                set_collection(response,class_no, username,"1");
                break;
            case "remove_collection":
                class_no = request.getParameter("class_no");
                set_collection(response,class_no, username,"0");
                break;
            case "delete_class":
                class_no = request.getParameter("class_no");
                delete_class(response,class_no, username);
                break;
        }
    }

    private void delete_class(HttpServletResponse response,String class_No,String username)throws IOException{
        classDAOlmpl cdl = new classDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(cdl.delete_student_class(class_No,username));
        out.flush();
        out.close();
    }

    private void set_collection(HttpServletResponse response,String class_No,String username,String type)throws IOException{
        classDAOlmpl cdl = new classDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(cdl.set_collection(class_No,username,type));
        out.flush();
        out.close();
    }

    private void get_my_class(HttpServletResponse response,String username)throws IOException{
        classDAOlmpl cdl = new classDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("class_list",cdl.get_student_class(username));
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    private void add_class(HttpServletRequest request,HttpServletResponse response,String username) throws IOException{
        String Class_Name = request.getParameter("Class_Name");
        int type = Integer.parseInt(request.getParameter("Class_Type"));
        classDAOlmpl cdl = new classDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject();
        int returnId = cdl.add_class(username,Class_Name,type);
        jsonObj.put("class_id",returnId);
        HttpSession session=request.getSession();
        String class_id = (String)session.getAttribute("class_id");
        session.setAttribute("class_id",class_id+","+returnId);
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    private void get_Infor(HttpServletResponse response,String username) throws IOException{
        classDAOlmpl cdl = new classDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("class_list",cdl.get_class(username));
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
