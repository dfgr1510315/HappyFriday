package com.LJZ.Server;

import com.LJZ.DAOlmpl.classDAOlmpl;
import com.LJZ.DAOlmpl.userDAOlmpl;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class save_image extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session=request.getSession();
        String username=(String) session.getAttribute("user_id");
        String action = request.getParameter("action");
        String image = request.getParameter("image");
        switch (action) {
            case "set_head":
                set_head(response,username, image);
                break;
            case "set_cover": {
                int No = Integer.parseInt(request.getParameter("No"));
                set_cover(response, image, No);
                break;
            }
            case "get_cover": {
                int No = Integer.parseInt(request.getParameter("No"));
                get_cover(response, No);
                break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }

    private void get_cover(HttpServletResponse response,int class_no)throws IOException {
        classDAOlmpl cl = new classDAOlmpl();
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cover",cl.get_cover(class_no));
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void set_cover(HttpServletResponse response,String image,int class_no)throws IOException{
        classDAOlmpl cl = new classDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(cl.set_cover(class_no,image));
        out.flush();
        out.close();
    }

    private void set_head(HttpServletResponse response,String username, String head_image)throws IOException{
        userDAOlmpl ul = new userDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(ul.change_head(username,head_image));
        out.flush();
        out.close();
    }
}
