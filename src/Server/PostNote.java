package Server;

import DAOlmpl.noteDAOlmpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
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
        String action = request.getParameter("action");
        int No;
        String class_No = request.getParameter("class_No");
        String author = request.getParameter("author");
        String time;
        switch (action) {
            case "post":
                No = Integer.parseInt(request.getParameter("No"));
                String note_editor = request.getParameter("note_editor");
               /* SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式*/
                time = request.getParameter("time");
                post_note(response, No, class_No, author, note_editor, time);
                break;
            case "get":
                No = Integer.parseInt(request.getParameter("No"));
                get_note(response, No, class_No, author);
                break;
            case "delete":
                int note_no = Integer.parseInt(request.getParameter("note_no"));
                delete_note(response, note_no);
                break;
            case "change":
                note_no = Integer.parseInt(request.getParameter("note_no"));
                note_editor = request.getParameter("note_editor");
                time = request.getParameter("time");
                change_note(response, note_no,note_editor,time);
                break;
            case "get_my_all_note":
                get_my_all_note(response, author);
                break;
            case "get_this_class_note":
                No = Integer.parseInt(request.getParameter("No"));
                int page = Integer.parseInt(request.getParameter("page"));
                get_this_class_note(response, No,page);
                break;
            case "search_note":
                page = Integer.parseInt(request.getParameter("page"));
                note_editor = request.getParameter("keyword");
                search_note(response,note_editor,page);
                break;
        }
    }

    private void search_note(HttpServletResponse response,String keyword,int page) throws IOException{
        JSONObject jsonObject = new JSONObject();
        noteDAOlmpl nl = new noteDAOlmpl();
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
    private void get_this_class_note(HttpServletResponse response,int No,int page) throws IOException{
        JSONObject jsonObject = new JSONObject();
        noteDAOlmpl nl = new noteDAOlmpl();
        jsonObject.put("note", nl.get_this_class_note(No,page));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //获取我的所有笔记
    private void get_my_all_note(HttpServletResponse response,String author)throws IOException{
        String SQL = "note.unit_no=class.unit_no and class_teacher_table.class_id=belong_class_id and class.class_id=belong_class_id and author= '"+author+"'";
        JSONObject jsonObject = new JSONObject();
        noteDAOlmpl nl = new noteDAOlmpl();
        jsonObject.put("note", nl.get_note(SQL));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void change_note(HttpServletResponse response,int note_no,String note_editor,String time)throws IOException{
        noteDAOlmpl nl = new noteDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(nl.change_note(note_no,note_editor,time));
        out.flush();
        out.close();
    }

    private void delete_note(HttpServletResponse response,int note_no)throws IOException{
        noteDAOlmpl nl = new noteDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(nl.delete_note(note_no));
        out.flush();
        out.close();
    }

    //获取当前播放课时下我的笔记
    private void get_note(HttpServletResponse response,int No,String class_No,String author)throws IOException{
        JSONObject jsonObject = new JSONObject();
        noteDAOlmpl nl = new noteDAOlmpl();
        jsonObject.put("note", nl.get_lesson_note(No,class_No,author));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    private void post_note(HttpServletResponse response,int No,String class_No,String author,String note_editor,String time)throws IOException{
        noteDAOlmpl nl = new noteDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(nl.post_note(No,class_No,author,note_editor,time));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
