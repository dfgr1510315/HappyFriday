package com.LJZ.Server;

import com.LJZ.DAO.AskDAO;
import com.LJZ.Model.Answer;
import com.LJZ.Model.SubModel.Ask_infor;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "PostAsk")
public class PostAsk extends HttpServlet {
    private static SqlSessionFactory factory = (SqlSessionFactory) new ClassPathXmlApplicationContext("application.xml").getBean("sqlSessionFactory");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        try (SqlSession sqlSession = factory.openSession()){
            AskDAO al = sqlSession.getMapper(AskDAO.class);
            switch (action) {
                case "get":
                    get_ask(response, request,al);
                    break;
                case "get_all_ask" :
                    get_all_ask(response, request,al);
                    break;
                case "get_this_class_ask" :
                    get_this_class_ask(response, request,al);
                    break;
                case "get_reply" :
                    get_reply(response, request,al);
                    break;
                case "get_my_ask" :
                    get_my_ask(response,request,al);
                    break;
                case "get_my_reply_ask" :
                    get_my_reply_ask(response,request,al);
                    break;
                case "search_ask":
                    search_ask(request,response,al);
                    break;
                case "post":
                    post_ask(response, request,al);
                    break;
                case "post_reply" :
                    post_reply(response, request,al);
                    break;
                case "post_answer" :
                    post_answer(response,request,al);
                    break;
            }
        }

    }

    private void search_ask(HttpServletRequest request,HttpServletResponse response,AskDAO al) throws IOException{
        int page = Integer.parseInt(request.getParameter("page"));
        String keyword = request.getParameter("keyword");
        JSONObject jsonObject = new JSONObject();
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
    private void get_my_reply_ask(HttpServletResponse response,HttpServletRequest request,AskDAO al) throws IOException{
        int page = Integer.parseInt(request.getParameter("page"));
        String answer = request.getParameter("answer");
        JSONObject jsonObject = new JSONObject();
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
    private void get_my_ask(HttpServletResponse response,HttpServletRequest request,AskDAO al) throws IOException{
        int page = Integer.parseInt(request.getParameter("page"));
        String author = request.getParameter("author");
        String SQL_from = " from ask,class_teacher_table where asker='"+author+"'and belong_class_id=class_id order by ask_time desc limit "+(6*(page-1))+","+6;
        String SQL_count = "SELECT COUNT(ask_id) ask_count FROM ask where asker='"+author+"'";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count", al.get_ask_count(SQL_count));
        jsonObject.put("ask", al.get_ask(SQL_from));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //回答讨论
    private void post_answer(HttpServletResponse response,HttpServletRequest request,AskDAO al)throws IOException{
        int No = Integer.parseInt(request.getParameter("No"));
        String answer = request.getParameter("answer");
        String answer_text = request.getParameter("answer_text");
        String answer_time = request.getParameter("time");
        String Landlord = request.getParameter("landlord");
        System.out.println(Landlord);
        PrintWriter out = response.getWriter();
        out.print(al.post_answer(No,answer,answer_time,answer_text));
        al.inNotice(answer,No,3,Landlord,answer_time);
        out.flush();
        out.close();
    }

    //在回答下回复
    private void post_reply(HttpServletResponse response,HttpServletRequest request,AskDAO al)throws IOException{
        int No = Integer.parseInt(request.getParameter("No"));
        int askId = Integer.parseInt(request.getParameter("askId"));
        String reply = request.getParameter("reply");
        String reply_text = request.getParameter("reply_text");
        String reply_time = request.getParameter("time");
        String reply_to = request.getParameter("reply_to");
        PrintWriter out = response.getWriter();
        out.print(al.post_reply(No,reply,reply_time,reply_text,reply_to));
        //System.out.println(reply+","+reply_to);
        al.inNotice(reply,askId,4,reply_to,reply_time);
        out.flush();
        out.close();
    }

    //得到此问答下所有回复内容
    private void get_reply(HttpServletResponse response,HttpServletRequest request,AskDAO al)throws IOException{
        int No = Integer.parseInt(request.getParameter("No"));
        JSONObject jsonObject = new JSONObject();
        al.visits_count(No);
        Ask_infor ask_infor = al.get_ask_infor(No);
        List ask_List = al.get_answer(No);
        for (Object o : ask_List) {
            Answer answer = (Answer) o;
            List replyList = al.get_reply(answer.getAnswer_id());
            answer.setReplys(replyList);
        }
        jsonObject.put("ask_infor", ask_infor);
        jsonObject.put("ask_answer", ask_List);
        jsonObject.put("ask_other", al.get_other_ask(ask_infor.getBelong_class_id(),ask_infor.getUnit_no(),No));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //得到此课程下所有问答
    private void get_this_class_ask(HttpServletResponse response,HttpServletRequest request,AskDAO al)throws IOException{
        int No = Integer.parseInt(request.getParameter("No"));
        int page = Integer.parseInt(request.getParameter("page"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ask", al.get_this_class_ask(No,6*(page-1)));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //获取该教师发布的所有课程下的所有问答
    private void get_all_ask(HttpServletResponse response,HttpServletRequest request,AskDAO al)throws IOException{
        int page = Integer.parseInt(request.getParameter("page"));
        String user = request.getParameter("user");
        String SQL_from = " from class_teacher_table,ask where teacher='"+user+"' and class_teacher_table.class_id=belong_class_id order by ask_time desc limit "+(6*(page-1))+","+6;
        String SQL_count = "SELECT COUNT(*) ask_count FROM ask,class_teacher_table where teacher='"+user+"' and class_id=belong_class_id";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count", al.get_ask_count(SQL_count));
        jsonObject.put("ask", al.get_ask(SQL_from));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }

    //获取当前播放课时下我的问答
    private void get_ask(HttpServletResponse response,HttpServletRequest request,AskDAO al)throws IOException{
        String class_No = request.getParameter("class_No");
        int No = Integer.parseInt(request.getParameter("No"));
        String author = request.getParameter("author");
        String SQL_from = " from class_teacher_table,ask where belong_class_id="+No+" and unit_no='"+class_No+"' and asker= '"+author+"' and class_teacher_table.class_id=belong_class_id";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ask", al.get_ask(SQL_from));
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }


    private void post_ask(HttpServletResponse response,HttpServletRequest request,AskDAO al)throws IOException{
        String class_No = request.getParameter("class_No");
        String author = request.getParameter("author");
        int No = Integer.parseInt(request.getParameter("No"));
        String note_editor = request.getParameter("note_editor");
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = request.getParameter("time");
        String ask_title = request.getParameter("ask_title");
        PrintWriter out = response.getWriter();
        out.print(al.post_ask(No,class_No,author,note_editor,time,ask_title));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
