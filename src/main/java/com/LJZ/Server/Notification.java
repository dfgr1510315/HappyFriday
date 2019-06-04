package com.LJZ.Server;

import com.LJZ.DAOlmpl.noticeDAOlmpl;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Notification")
public class Notification extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        switch (action) {
            case "get_notice":
                get_notice(request,response);
                break;
            case "delete_notice":
                delete_notice(request,response);
        }
    }

    private void delete_notice(HttpServletRequest request,HttpServletResponse response)throws IOException {
        int notice_id = Integer.parseInt(request.getParameter("notice_id"));
        noticeDAOlmpl nl = new noticeDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(nl.delete_notice(notice_id));
        out.flush();
        out.close();
    }

    private void get_notice(HttpServletRequest request,HttpServletResponse response)throws IOException {
        String user = request.getParameter("user");
        int page = Integer.parseInt(request.getParameter("page"));
        JSONObject jsonObject = new JSONObject();
        noticeDAOlmpl nl = new noticeDAOlmpl();
        jsonObject.put("notice", nl.get_notice(page,user));
        jsonObject.put("count", nl.get_notice_count(user));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
