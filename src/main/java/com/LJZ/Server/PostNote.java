package com.LJZ.Server;

import com.LJZ.DAO.NoteDAO;
import com.LJZ.DB.GetSqlSessionFactory;
import com.LJZ.Model.SubModel.NoteInsert;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PostNote")
public class PostNote extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        SqlSession sqlSession = GetSqlSessionFactory.getSqlSession();
        NoteDAO nl = sqlSession.getMapper(NoteDAO.class);
        String action = request.getParameter("action");
        switch (action) {
            case "post":
                post_note(request,response,nl);
                break;
            case "get":
                get_note(request,response,nl);
                break;
            case "delete":
                delete_note(request,response,nl);
                break;
            case "change":
                change_note(request,response,nl);
                break;
            case "get_my_all_note":
                get_my_all_note(request,response,nl);
                break;
            case "get_this_class_note":
                get_this_class_note(request,response,nl);
                break;
            case "search_note":
                search_note(request,response,nl);
                break;
        }
    }

    private void search_note(HttpServletRequest request,HttpServletResponse response,NoteDAO nl) throws IOException{
        int page = Integer.parseInt(request.getParameter("page"));
        String keyword = request.getParameter("keyword");
        JSONObject jsonObject = new JSONObject();
        String SQL = "note.unit_no=class.unit_no and class_teacher_table.class_id=belong_class_id and class.class_id=belong_class_id and text like '%"+keyword+"%' limit "+(6*(page-1))+","+6;
        jsonObject.put("note", nl.get_note(SQL));
        SQL = "SELECT COUNT(note_id) count from note where text like '%"+keyword+"%'";
        jsonObject.put("count", nl.get_note_count(SQL));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //得到此课程下所有笔记
    private void get_this_class_note(HttpServletRequest request,HttpServletResponse response,NoteDAO nl) throws IOException{
        JSONObject jsonObject = new JSONObject();
        int No = Integer.parseInt(request.getParameter("No"));
        int page = Integer.parseInt(request.getParameter("page"));
        jsonObject.put("note", nl.get_this_class_note(No,page));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //获取我的所有笔记
    private void get_my_all_note(HttpServletRequest request,HttpServletResponse response,NoteDAO nl)throws IOException{
        String author = request.getParameter("author");
        String SQL = "note.unit_no=class.unit_no and class_teacher_table.class_id=belong_class_id and class.class_id=belong_class_id and author= '"+author+"'";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("note", nl.get_note(SQL));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void change_note(HttpServletRequest request,HttpServletResponse response,NoteDAO nl)throws IOException{
        int note_no = Integer.parseInt(request.getParameter("note_no"));
        String note_editor = request.getParameter("note_editor");
        String time = request.getParameter("time");
        PrintWriter out = response.getWriter();
        out.print(nl.change_note(note_editor,time,note_no));
        out.flush();
        out.close();
    }

    private void delete_note(HttpServletRequest request,HttpServletResponse response,NoteDAO nl)throws IOException{
        int note_no = Integer.parseInt(request.getParameter("note_no"));
        PrintWriter out = response.getWriter();
        out.print(nl.delete_note(note_no));
        out.flush();
        out.close();
    }

    //获取当前播放课时下我的笔记
    private void get_note(HttpServletRequest request,HttpServletResponse response,NoteDAO nl)throws IOException{
        JSONObject jsonObject = new JSONObject();
        int No = Integer.parseInt(request.getParameter("No"));
        String class_No = request.getParameter("class_No");
        String author = request.getParameter("author");
        jsonObject.put("note", nl.get_lesson_note(No,class_No,author));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void post_note(HttpServletRequest request,HttpServletResponse response,NoteDAO nl)throws IOException{
        NoteInsert noteInsert = new NoteInsert();
        noteInsert.setUnit_no(request.getParameter("class_No"));
        noteInsert.setAuthor(request.getParameter("author"));
        noteInsert.setBelong_class_id(Integer.parseInt(request.getParameter("No")));
        noteInsert.setText(request.getParameter("note_editor"));
        noteInsert.setNote_time(request.getParameter("time"));
        /* SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式*/
        nl.post_note(noteInsert);
        PrintWriter out = response.getWriter();
        out.print(noteInsert.getNote_id());
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
