package Server;

import com.alibaba.druid.pool.DruidPooledConnection;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "PostAsk")
public class PostAsk extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            case "get_All_ask" :
                int page = Integer.parseInt(request.getParameter("page"));
                String user = request.getParameter("user");
                get_All_ask(response,user,page);
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
                page = Integer.parseInt(request.getParameter("page"));
                user = request.getParameter("keyword");
                search_ask(response,page,user);
                break;
        }
    }

    private void search_ask(HttpServletResponse response,int page,String keyword){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            PrintWriter out = response.getWriter();
            JSONObject jsonObject = new JSONObject();
            ArrayList<Integer> ask_id = new ArrayList<>();
            ArrayList<Integer> belong_class_id = new ArrayList<>();
            ArrayList<String> ask_title = new ArrayList<>();
            ArrayList<String> ask_time = new ArrayList<>();
            ArrayList<Integer> answer_count = new ArrayList<>();
            ArrayList<Integer> visits_count = new ArrayList<>();
            ArrayList<String> class_title = new ArrayList<>();
            ArrayList<String> cover_address = new ArrayList<>();
            ArrayList<String> new_answerer = new ArrayList<>();
            ArrayList<String> new_answer = new ArrayList<>();
            ResultSet rs = statement.executeQuery("select ask_id,belong_class_id,ask_title,ask_time,answer_count,visits_count,class_title,cover_address,(select answerer from answer where belong_ask_id=ask_id order by answer_time  desc limit 1) new_answerer,(select answer_text from answer where belong_ask_id=ask_id order by answer_time desc limit 1) new_answer from ask,class_teacher_table where belong_class_id=class_id and ask_title like '%"+keyword+"%' limit "+(6*(page-1))+","+6);
            while (rs.next()){
                ask_id.add(rs.getInt("ask_id"));
                belong_class_id.add(rs.getInt("belong_class_id"));
                ask_title.add(rs.getString("ask_title"));
                ask_time.add(rs.getString("ask_time"));
                answer_count.add(rs.getInt("answer_count"));
                visits_count.add(rs.getInt("visits_count"));
                class_title.add(rs.getString("class_title"));
                cover_address.add(rs.getString("cover_address"));
                new_answerer.add(rs.getString("new_answerer"));
                new_answer.add(rs.getString("new_answer"));
            }
            rs = statement.executeQuery("SELECT COUNT(*) count FROM ask where ask_title like '%"+keyword+"%'");
            while (rs.next()){
                jsonObject.put("count",rs.getString("count"));
            }
            jsonObject.put("ask_id",ask_id);
            jsonObject.put("belong_class_id",belong_class_id);
            jsonObject.put("ask_title",ask_title);
            jsonObject.put("ask_time",ask_time);
            jsonObject.put("answer_count",answer_count);
            jsonObject.put("visits_count",visits_count);
            jsonObject.put("class_title",class_title);
            jsonObject.put("cover_address",cover_address);
            jsonObject.put("new_answerer",new_answerer);
            jsonObject.put("new_answer",new_answer);
            out.print(jsonObject);
            out.flush();
            out.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    private void get_my_reply_ask(HttpServletResponse response,int page,String answer){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select distinct ask.unit_no,ask_title,asker,ask_time,head,lesson_title,answer_count,ask_id,visits_count,belong_class_id,class_title,(select answerer from answer where belong_ask_id=ask_id order by answer_time  desc limit 1) new_answer,(select answer_text from answer where belong_ask_id=ask_id order by answer_time desc limit 1) new_reply from personal_table,ask,class,class_teacher_table,answer where answerer='"+answer+"'and belong_ask_id=ask_id and asker=username and belong_class_id=class.class_id and class.class_id=class_teacher_table.class_id and class.unit_no=ask.unit_no order by ask_time desc limit "+(6*(page-1))+","+6);
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> ask_no = new ArrayList<>();
            ArrayList<String> asker = new ArrayList<>();
            ArrayList<Integer> times = new ArrayList<>();
            ArrayList<String> title_No_list = new ArrayList<>();
            ArrayList<String> title_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> describe_list = new ArrayList<>();
            ArrayList<String> answer_count = new ArrayList<>();
            ArrayList<String> new_answer = new ArrayList<>();
            ArrayList<String> new_answer_text = new ArrayList<>();
            ArrayList<String> class_no = new ArrayList<>();
            ArrayList<String> class_title = new ArrayList<>();
            ArrayList<String> head = new ArrayList<>();
            while (rs.next()){
                ask_no.add(rs.getString("ask_id"));
                asker.add(rs.getString("asker"));
                times.add(rs.getInt("visits_count"));
                title_No_list.add(rs.getString("unit_no"));
                title_list.add(rs.getString("lesson_title"));
                time_list.add(rs.getString("ask_time"));
                describe_list.add(rs.getString("ask_title"));
                answer_count.add(rs.getString("answer_count"));
                new_answer.add(rs.getString("new_answer"));
                new_answer_text.add(rs.getString("new_reply"));
                class_no.add(rs.getString("belong_class_id"));
                class_title.add(rs.getString("class_title"));
                head.add(rs.getString("head"));
            }
            jsonObj.put("ask_no",ask_no);
            jsonObj.put("asker",asker);
            jsonObj.put("times",times);
            jsonObj.put("title_No_list",title_No_list);
            jsonObj.put("title",title_list);
            jsonObj.put("time",time_list);
            jsonObj.put("describe",describe_list);
            jsonObj.put("answer_count",answer_count);
            jsonObj.put("new_answer",new_answer);
            jsonObj.put("new_answer_text",new_answer_text);
            jsonObj.put("class_no",class_no);
            jsonObj.put("class_title",class_title);
            jsonObj.put("head",head);
            rs = statement.executeQuery("SELECT COUNT(distinct ask_id) count FROM ask,answer where answerer='"+answer+"' and belong_ask_id=ask_id");
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    private void get_my_ask(HttpServletResponse response,int page,String author){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select ask.unit_no,ask_title,ask_time,lesson_title,answer_count,ask_id,visits_count,belong_class_id,class_title,(select answerer from answer where belong_ask_id=ask_id order by answer_time  desc limit 1) new_answerer,(select answer_text from answer where belong_ask_id=ask_id order by answer_time desc limit 1) new_answer from personal_table,ask,class,class_teacher_table where asker='"+author+"' and asker=username and belong_class_id=class.class_id and class.class_id=class_teacher_table.class_id and ask.unit_no=class.unit_no order by ask_time desc limit "+(6*(page-1))+","+6);
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> ask_no = new ArrayList<>();
            ArrayList<Integer> times = new ArrayList<>();
            ArrayList<String> title_No_list = new ArrayList<>();
            ArrayList<String> title_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> describe_list = new ArrayList<>();
            ArrayList<String> answer_count = new ArrayList<>();
            ArrayList<String> new_answer = new ArrayList<>();
            ArrayList<String> new_answer_text = new ArrayList<>();
            ArrayList<String> class_no = new ArrayList<>();
            ArrayList<String> class_title = new ArrayList<>();
            while (rs.next()){
                ask_no.add(rs.getString("ask_id"));
                times.add(rs.getInt("visits_count"));
                title_No_list.add(rs.getString("unit_no"));
                title_list.add(rs.getString("lesson_title"));
                time_list.add(rs.getString("ask_time"));
                describe_list.add(rs.getString("ask_title"));
                answer_count.add(rs.getString("answer_count"));
                new_answer.add(rs.getString("new_answerer"));
                new_answer_text.add(rs.getString("new_answer"));
                class_no.add(rs.getString("belong_class_id"));
                class_title.add(rs.getString("class_title"));
            }
            jsonObj.put("ask_no",ask_no);
            jsonObj.put("times",times);
            jsonObj.put("title_No_list",title_No_list);
            jsonObj.put("title",title_list);
            jsonObj.put("time",time_list);
            jsonObj.put("describe",describe_list);
            jsonObj.put("answer_count",answer_count);
            jsonObj.put("new_answer",new_answer);
            jsonObj.put("new_answer_text",new_answer_text);
            jsonObj.put("class_no",class_no);
            jsonObj.put("class_title",class_title);
            rs = statement.executeQuery("SELECT COUNT(*) count FROM ask where asker='"+author+"'");
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    private void post_answer(HttpServletResponse response,int No,String answer,String answer_time,String answer_text){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into answer(belong_ask_id,answerer,answer_text,answer_time) values(?,?,?,?)");
            qsql.setInt(1,No);
            qsql.setString(2,answer);
            qsql.setString(3,answer_text);
            qsql.setString(4,answer_time);
            qsql.executeUpdate();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID() answerID");
            JSONObject jsonObj = new JSONObject();
            while (rs.next()){
                jsonObj.put("answerID",rs.getString("answerID"));
            }
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    private void post_reply(HttpServletResponse response,int No,String reply,String reply_time,String reply_text,String reply_to){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into reply(belong_answer_id,replyer,text,reply_time,to_reply) values(?,?,?,?,?)");
            qsql.setInt(1,No);
            qsql.setString(2,reply);
            qsql.setString(3,reply_text);
            qsql.setString(4,reply_time);
            qsql.setString(5,reply_to);
            qsql.executeUpdate();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    private void get_reply(HttpServletResponse response,int No){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            qsql  = con.prepareStatement("UPDATE ask SET visits_count =visits_count+1  WHERE ask_id="+No);
            qsql.executeUpdate();
            ResultSet rs = statement.executeQuery("select class_title,belong_class_id,ask_title,asker,ask_text,ask_time,head,unit_no,class_type,visits_count from ask,personal_table,class_teacher_table where ask_id="+No+" and username=asker and belong_class_id=class_id;");
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> answer_head = new ArrayList<>();
            ArrayList<String> answer_no = new ArrayList<>();
            ArrayList<String> answer = new ArrayList<>();
            ArrayList<String> answer_text = new ArrayList<>();
            ArrayList<String> answer_time = new ArrayList<>();
            ArrayList<Integer> other_ask_no = new ArrayList<>();
            ArrayList<String> other_ask_title = new ArrayList<>();
            while (rs.next()){
                jsonObj.put("ask_title",rs.getString("class_title"));
                jsonObj.put("class_no",rs.getString("belong_class_id"));
                jsonObj.put("ask_describe",rs.getString("ask_title"));
                jsonObj.put("asker",rs.getString("asker"));
                jsonObj.put("ask_text",rs.getString("ask_text"));
                jsonObj.put("ask_time",rs.getString("ask_time"));
                jsonObj.put("asker_head",rs.getString("head"));
                jsonObj.put("unit_no",rs.getString("unit_no"));
                jsonObj.put("type",rs.getInt("class_type"));
                jsonObj.put("times",rs.getString("visits_count"));
            }

            rs = statement.executeQuery("select ask_id,ask_title from ask where belong_class_id="+jsonObj.get("class_no")+"  and unit_no='"+jsonObj.get("unit_no")+"' and ask_id!="+No+" limit 10");
            while (rs.next()){
                other_ask_title.add(rs.getString("ask_title"));
                other_ask_no.add(rs.getInt("ask_id"));
            }

            rs = statement.executeQuery("select head,answer_id,answerer,answer_text,answer_time from answer,personal_table where belong_ask_id="+No+"  and username=answerer order by answer_time;");
            while (rs.next()){
                answer_head.add(rs.getString("head"));
                answer_no.add(rs.getString("answer_id"));
                answer.add(rs.getString("answerer"));
                answer_text.add(rs.getString("answer_text"));
                answer_time.add(rs.getString("answer_time"));
            }

            int count = 0;
            for (String anAnswer_no : answer_no) {
                rs = statement.executeQuery("select reply.text,replyer,reply_time,head from reply,personal_table where belong_answer_id=" + anAnswer_no + " and replyer=username order by reply_time ;");
                ArrayList<String> reply = new ArrayList<>();
                ArrayList<String> reply_text = new ArrayList<>();
                ArrayList<String> reply_time = new ArrayList<>();
                ArrayList<String> reply_head = new ArrayList<>();
                JSONObject reply_jsonobj = new JSONObject();
                while (rs.next()) {
                    reply.add(rs.getString("replyer"));
                    reply_text.add(rs.getString("text"));
                    reply_time.add(rs.getString("reply_time"));
                    reply_head.add(rs.getString("head"));
                }
                reply_jsonobj.put("reply",reply);
                reply_jsonobj.put("reply_text",reply_text);
                reply_jsonobj.put("reply_time",reply_time);
                reply_jsonobj.put("reply_head",reply_head);
                jsonObj.put("ask_no"+count,reply_jsonobj);
                count++;
            }

            jsonObj.put("other_ask_no",other_ask_no);
            jsonObj.put("other_ask_title",other_ask_title);

            jsonObj.put("answer_head",answer_head);
            jsonObj.put("answer_no",answer_no);
            jsonObj.put("answer",answer);
            jsonObj.put("answer_text",answer_text);
            jsonObj.put("answer_time",answer_time);
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }


    private void get_this_class_ask(HttpServletResponse response,int No,int page){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select ask.unit_no,ask_title,asker,ask_time,head,lesson_title,answer_count,ask_id,visits_count,(select answerer from answer where belong_ask_id=ask_id order by answer_time  desc limit 1) new_answerer,(select answer_text from answer where belong_ask_id=ask_id order by answer_time desc limit 1) new_answer from personal_table,ask,class where belong_class_id="+No+" and asker=username and belong_class_id=class_id and ask.unit_no=class.unit_no order by ask_time desc limit "+(6*(page-1))+","+6);
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> ask_no = new ArrayList<>();
            ArrayList<Integer> times = new ArrayList<>();
            ArrayList<String> title_No_list = new ArrayList<>();
            ArrayList<String> title_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> describe_list = new ArrayList<>();
            ArrayList<String> asker = new ArrayList<>();
            ArrayList<String> head = new ArrayList<>();
            ArrayList<String> answer_count = new ArrayList<>();
            ArrayList<String> new_answer = new ArrayList<>();
            ArrayList<String> new_answer_text = new ArrayList<>();
            while (rs.next()){
                ask_no.add(rs.getString("ask_id"));
                times.add(rs.getInt("visits_count"));
                title_No_list.add(rs.getString("unit_no"));
                title_list.add(rs.getString("lesson_title"));
                time_list.add(rs.getString("ask_time"));
                describe_list.add(rs.getString("ask_title"));
                asker.add(rs.getString("asker"));
                head.add(rs.getString("head"));
                answer_count.add(rs.getString("answer_count"));
                new_answer.add(rs.getString("new_answerer"));
                new_answer_text.add(rs.getString("new_answer"));
            }
            jsonObj.put("ask_no",ask_no);
            jsonObj.put("times",times);
            jsonObj.put("title_No_list",title_No_list);
            jsonObj.put("asker",asker);
            jsonObj.put("head",head);
            jsonObj.put("title",title_list);
            jsonObj.put("time",time_list);
            jsonObj.put("describe",describe_list);
            jsonObj.put("answer_count",answer_count);
            jsonObj.put("new_answer",new_answer);
            jsonObj.put("new_answer_text",new_answer_text);
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    private void get_All_ask(HttpServletResponse response,String user,int page){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            String new_answerer_sql = "(select answerer from answer where belong_ask_id=ask_id order by answer_time  desc limit 1) new_answerer";
            String new_answer_sql = "(select answer_text from answer where belong_ask_id=ask_id order by answer_time desc limit 1) new_answer";
            ResultSet rs = statement.executeQuery("select class_title,ask_time,ask_title,ask_id,belong_class_id,ask.unit_no,lesson_title,asker,answer_count,visits_count,"+new_answerer_sql+","+new_answer_sql+" from class_teacher_table,ask,class where class_teacher_table.class_id=class.class_id and ask.unit_no=class.unit_no and teacher='"+user+"' and class_teacher_table.class_id=belong_class_id order by ask_time desc limit "+(6*(page-1))+","+6);
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> ask_no = new ArrayList<>();
            ArrayList<String> class_no = new ArrayList<>();
            ArrayList<String> unit_no = new ArrayList<>();
            ArrayList<String> lesson_title = new ArrayList<>();
            ArrayList<String> asker = new ArrayList<>();
            ArrayList<String> new_answer = new ArrayList<>();
            ArrayList<String> new_answer_text = new ArrayList<>();
            ArrayList<String> title_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> describe_list = new ArrayList<>();
            ArrayList<String> answer_count = new ArrayList<>();
            ArrayList<Integer> times = new ArrayList<>();
            while (rs.next()){
                title_list.add(rs.getString("class_title"));
                time_list.add(rs.getString("ask_time"));
                describe_list.add(rs.getString("ask_title"));
                ask_no.add(rs.getString("ask_id"));
                class_no.add(rs.getString("belong_class_id"));
                lesson_title.add(rs.getString("lesson_title"));
                unit_no.add(rs.getString("unit_no"));
                asker.add(rs.getString("asker"));
                new_answer.add(rs.getString("new_answerer"));
                new_answer_text.add(rs.getString("new_answer"));
                answer_count.add(rs.getString("answer_count"));
                times.add(rs.getInt("visits_count"));
            }
            jsonObj.put("title",title_list);
            jsonObj.put("time",time_list);
            jsonObj.put("describe",describe_list);
            jsonObj.put("ask_no",ask_no);
            jsonObj.put("class_no",class_no);
            jsonObj.put("unit_no",unit_no);
            jsonObj.put("lesson_title",lesson_title);
            jsonObj.put("asker",asker);
            jsonObj.put("new_answer",new_answer);
            jsonObj.put("new_answer_text",new_answer_text);
            jsonObj.put("answer_count",answer_count);
            jsonObj.put("times",times);
            rs = statement.executeQuery("SELECT COUNT(*) count FROM ask,class_teacher_table where teacher='"+user+"' and class_id=belong_class_id");
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    private void get_ask(HttpServletResponse response,int No,String class_No,String author){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select ask_title,ask_time,answer_count,visits_count from ask where belong_class_id="+No+" and unit_no='"+class_No+"' and asker= '"+author+"'");
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> title_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> answer = new ArrayList<>();
            ArrayList<String> browse = new ArrayList<>();
            while (rs.next()){
                title_list.add(rs.getString("ask_title"));
                time_list.add(rs.getString("ask_time"));
                answer.add(rs.getString("answer_count"));
                browse.add(rs.getString("visits_count"));
            }
            jsonObj.put("title",title_list);
            jsonObj.put("time",time_list);
            jsonObj.put("answer",answer);
            jsonObj.put("browse",browse);
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    private void post_ask(HttpServletResponse response,int No,String class_No,String author,String note_editor,String time,String ask_title){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into ask(belong_class_id,unit_no,ask_title,asker,ask_text,ask_time) values(?,?,?,?,?,?)");
            qsql.setInt(1,No);
            qsql.setString(2,class_No);
            qsql.setString(3,ask_title);
            qsql.setString(4,author);
            qsql.setString(5,note_editor);
            qsql.setString(6,time);
            qsql.executeUpdate();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
