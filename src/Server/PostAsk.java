package Server;

import DAOlmpl.askDAOlmpl;
import Model.SubModel.Ask_infor;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PostAsk")
public class PostAsk extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        String class_No = request.getParameter("class_No");
        String author = request.getParameter("author");
        String time;
        switch (action) {
            case "post":
                int No = Integer.parseInt(request.getParameter("No"));
                String note_editor = request.getParameter("note_editor");
                /* SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式*/
                time = request.getParameter("time");
                String ask_title = request.getParameter("ask_title");
                post_ask(response, No, class_No, author, note_editor, time,ask_title);
                break;
            case "get":
                No = Integer.parseInt(request.getParameter("No"));
                get_ask(response,No,class_No,author);
                break;
            case "get_all_ask" :
                int page = Integer.parseInt(request.getParameter("page"));
                String user = request.getParameter("user");
                get_all_ask(response,user,page);
                break;
            case "get_this_class_ask" :
                No = Integer.parseInt(request.getParameter("No"));
                page = Integer.parseInt(request.getParameter("page"));
                get_this_class_ask(response,No,page);
                break;
            case "get_reply" :
                No = Integer.parseInt(request.getParameter("No"));
                get_reply(response,No);
                break;
            case "post_reply" :
                No = Integer.parseInt(request.getParameter("No"));
                user = request.getParameter("reply");
                note_editor = request.getParameter("reply_text");
                time = request.getParameter("time");
                String reply_to = request.getParameter("reply_to");
                post_reply(response,No,user,time,note_editor,reply_to);
                break;
            case "post_answer" :
                No = Integer.parseInt(request.getParameter("No"));
                user = request.getParameter("answer");
                note_editor = request.getParameter("answer_text");
                time = request.getParameter("time");
                post_answer(response,No,user,time,note_editor);
                break;
            case "get_my_ask" :
                page = Integer.parseInt(request.getParameter("page"));
                get_my_ask(response,page,author);
                break;
            case "get_my_reply_ask" :
                page = Integer.parseInt(request.getParameter("page"));
                user = request.getParameter("answer");
                get_my_reply_ask(response,page,user);
                break;
            case "search_ask":
                search_ask(request,response);
                break;
        }
    }

    private void search_ask(HttpServletRequest request,HttpServletResponse response) throws IOException{
        int page = Integer.parseInt(request.getParameter("page"));
        String keyword = request.getParameter("keyword");
        JSONObject jsonObject = new JSONObject();
        askDAOlmpl al = new askDAOlmpl();
        String SQL = "SELECT COUNT(*) ask_count FROM ask where ask_title like '%"+keyword+"%'";
        jsonObject.put("count", al.get_ask_count(SQL));
        SQL = " from ask,class_teacher_table where belong_class_id=class_id and ask_title like '%"+keyword+"%' limit "+(6*(page-1))+","+6;
        jsonObject.put("ask", al.get_ask(SQL));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //我回复过的提问
    private void get_my_reply_ask(HttpServletResponse response,int page,String answer) throws IOException{
        JSONObject jsonObject = new JSONObject();
        askDAOlmpl al = new askDAOlmpl();
        String SQL = "SELECT COUNT(distinct ask_id) ask_count FROM ask,answer where answerer='"+answer+"' and belong_ask_id=ask_id";
        jsonObject.put("count", al.get_ask_count(SQL));
        SQL = " from ask,class_teacher_table,answer where belong_class_id=class_id and belong_ask_id=ask_id and answerer='"+answer+"' GROUP BY belong_ask_id limit "+(6*(page-1))+","+6;
        jsonObject.put("ask", al.get_ask(SQL));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //我发布的提问
    private void get_my_ask(HttpServletResponse response,int page,String author) throws IOException{
        String SQL_from = " from ask,class_teacher_table where asker='"+author+"'and belong_class_id=class_id order by ask_time desc limit "+(6*(page-1))+","+6;
        String SQL_count = "SELECT COUNT(ask_id) ask_count FROM ask where asker='"+author+"'";
        JSONObject jsonObject = new JSONObject();
        askDAOlmpl al = new askDAOlmpl();
        jsonObject.put("count", al.get_ask_count(SQL_count));
        jsonObject.put("ask", al.get_ask(SQL_from));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //回答讨论
    private void post_answer(HttpServletResponse response,int No,String answer,String answer_time,String answer_text)throws IOException{
        askDAOlmpl al = new askDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(al.post_answer(No,answer,answer_time,answer_text));
        out.flush();
        out.close();
    }

    //在回答下回复
    private void post_reply(HttpServletResponse response,int No,String reply,String reply_time,String reply_text,String reply_to)throws IOException{
        askDAOlmpl al = new askDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(al.post_reply(No,reply,reply_time,reply_text,reply_to));
        out.flush();
        out.close();
    }

    //得到此问答下所有回复内容
    private void get_reply(HttpServletResponse response,int No)throws IOException{
        JSONObject jsonObject = new JSONObject();
        askDAOlmpl al = new askDAOlmpl();
        Ask_infor ask_infor = al.get_ask_infor(No);
        jsonObject.put("ask_infor", ask_infor);
        jsonObject.put("ask_answer", al.get_answer(No));
        jsonObject.put("ask_other", al.get_other_ask(ask_infor.getBelong_class_id(),ask_infor.getUnit_no(),No));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //得到此课程下所有问答
    private void get_this_class_ask(HttpServletResponse response,int No,int page)throws IOException{
        JSONObject jsonObject = new JSONObject();
        askDAOlmpl al = new askDAOlmpl();
        jsonObject.put("ask", al.get_this_class_ask(No,page));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //获取该教师发布的所有课程下的所有问答
    private void get_all_ask(HttpServletResponse response,String user,int page)throws IOException{
        String SQL_from = " from class_teacher_table,ask where teacher='"+user+"' and class_teacher_table.class_id=belong_class_id order by ask_time desc limit "+(6*(page-1))+","+6;
        String SQL_count = "SELECT COUNT(*) ask_count FROM ask,class_teacher_table where teacher='"+user+"' and class_id=belong_class_id";
        JSONObject jsonObject = new JSONObject();
        askDAOlmpl al = new askDAOlmpl();
        jsonObject.put("count", al.get_ask_count(SQL_count));
        jsonObject.put("ask", al.get_ask(SQL_from));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //获取当前播放课时下我的问答
    private void get_ask(HttpServletResponse response,int No,String class_No,String author)throws IOException{
        String SQL_from = " from class_teacher_table,ask where belong_class_id="+No+" and unit_no='"+class_No+"' and asker= '"+author+"' and class_teacher_table.class_id=belong_class_id";
        JSONObject jsonObject = new JSONObject();
        askDAOlmpl al = new askDAOlmpl();
        jsonObject.put("ask", al.get_ask(SQL_from));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }


    private void post_ask(HttpServletResponse response,int No,String class_No,String author,String note_editor,String time,String ask_title)throws IOException{
        askDAOlmpl al = new askDAOlmpl();
        PrintWriter out = response.getWriter();
        out.print(al.post_ask(No,class_No,author,note_editor,time,ask_title));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
