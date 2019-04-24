package Server;
import DAOlmpl.basicsClassDAOlmpl;
import net.sf.json.JSONObject;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String Read_or_Save = request.getParameter("Read_or_Save");
        switch (Read_or_Save) {
            case "save":
                int No = Integer.parseInt(request.getParameter("No"));
                String ClassCount = request.getParameter("ClassCount");
                String UnitCount = request.getParameter("UnitCount");
                save_class(No, UnitCount, ClassCount,response);
                break;
            case "read":
                No = Integer.parseInt(request.getParameter("No"));
                read_class(No,response);
                break;
            case "search_tips":
                String keyword = request.getParameter("keyword");
                search_tips(keyword,response);
                break;
            case "search_class":
                keyword = request.getParameter("keyword");
                int page = Integer.parseInt(request.getParameter("page"));
                search_class(response,keyword,page);
                break;
            case "get_infor":
                No = Integer.parseInt(request.getParameter("No"));
                get_infor(response,No);
                break;
            case "set_infor":
                No = Integer.parseInt(request.getParameter("No"));
                String title = request.getParameter("title");
                int sel1 = Integer.parseInt(request.getParameter("sel1"));
                String outline = request.getParameter("outline");
                set_infor(response,No,title,sel1,outline);
                break;
            case "get_new_class":
                get_new_class(request,response);
                break;
            case "delete_class":
                No = Integer.parseInt(request.getParameter("No"));
                delete_class(No,response);
                break;
            case "release":
                No = Integer.parseInt(request.getParameter("No"));
                int state = Integer.parseInt(request.getParameter("state"));
                change_class_state(No,state,response);
                break;
            case "get_file":
                get_file(response,request);
                break;
            case "delete_file":
                delete_file(response,request);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }

    private void delete_file(HttpServletResponse response,HttpServletRequest request)throws IOException{
        basicsClassDAOlmpl bc = new basicsClassDAOlmpl();
        String address = request.getParameter("address");
        PrintWriter out = response.getWriter();
        //ConnectSQL.my_println("RealPath:"+getServletContext().getRealPath("/")+address);
        File file = new File(getServletContext().getRealPath("/") +address);
        file.delete();
        out.print(bc.delete_file(address));
        out.flush();
        out.close();
    }


    private void get_file(HttpServletResponse response,HttpServletRequest request)throws IOException{
        basicsClassDAOlmpl bc = new basicsClassDAOlmpl();
        String class_id = request.getParameter("class_id");
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("files",bc.get_files(class_id));
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    //首页和课程类型页面都调用此方法获取课程列
    private void get_new_class(HttpServletRequest request, HttpServletResponse response)throws IOException{
        basicsClassDAOlmpl bc = new basicsClassDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject();
        int page = Integer.parseInt(request.getParameter("page"));
        if (page==0) jsonObj.put("new_class",bc.get_class("release_status=1","class_id desc","10"));
        else {
            String type = request.getParameter("type");
            String where = "release_status=1";
            if (!type.equals("0")) where = "release_status=1 and class_type="+type;
            jsonObj.put("new_class",bc.get_class(where,"class_id desc",(25*(page-1))+","+25));
            jsonObj.put("count",bc.get_class_count(where));
        }
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    private void search_class(HttpServletResponse response,String keyword,int page)throws IOException{
        basicsClassDAOlmpl bc = new basicsClassDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("class",bc.get_class("class_title like '%"+keyword+"%' and release_status=1","student_count desc",(6*(page-1))+","+6));
        jsonObj.put("count",bc.get_class_count("class_title like '%"+keyword+"%' and release_status=1"));
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    private void search_tips(String keyword,HttpServletResponse response)throws IOException{
        basicsClassDAOlmpl bc = new basicsClassDAOlmpl();
        PrintWriter out = response.getWriter();
        List<String> list = bc.search_tips(keyword);
        for (String i : list) {
            out.print("<li class='list_tips'><a>"+i+"</a></li>");
        }
        out.flush();
        out.close();
    }

    private void delete_class(int No,HttpServletResponse response)throws IOException{
        basicsClassDAOlmpl bc = new basicsClassDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(bc.delete_class(No));
        out.flush();
        out.close();
    }

    private void set_infor(HttpServletResponse response,int No,String title,int sel1,String outline)throws IOException{
        basicsClassDAOlmpl bc = new basicsClassDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(bc.set_infor(No,title,sel1,outline));
        out.flush();
        out.close();
    }

    private void get_infor(HttpServletResponse response,int No)throws IOException{
        basicsClassDAOlmpl bc = new basicsClassDAOlmpl();
        JSONObject jsonObj = new JSONObject();
        PrintWriter out = response.getWriter();
        String[] infor = bc.get_infor(No);
        jsonObj.put("title",infor[0]);
        jsonObj.put("outline",infor[1]);
        jsonObj.put("type",infor[2]);
        out.print(jsonObj);
        out.flush();
        out.close();
    }


    private void save_class(int No, String UnitCount, String ClassCount, HttpServletResponse response) throws IOException{
        basicsClassDAOlmpl bc = new basicsClassDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(bc.save_class(No,UnitCount,ClassCount));
        out.flush();
        out.close();
    }

    private void read_class(int No, HttpServletResponse response)throws IOException{
        basicsClassDAOlmpl bc = new basicsClassDAOlmpl();
        JSONObject jsonObj = new JSONObject();
        PrintWriter out = response.getWriter();
        String[] cl = bc.read_class(No);
        jsonObj.put("title",cl[0]);
        jsonObj.put("teacher",cl[1]);
        jsonObj.put("state",cl[2]);
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    private void change_class_state(int No,int state,HttpServletResponse response)throws IOException{
        basicsClassDAOlmpl bc = new basicsClassDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(bc.change_class_state(No,state));
        out.flush();
        out.close();
    }
}
