package Server;

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
                post_reply(response,No,user,time,note_editor);
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
            case "get_my_reply_ask" :
                page = Integer.parseInt(request.getParameter("page"));
                user = request.getParameter("answer");
                get_my_reply_ask(response,page,user);
        }
    }

    private void get_my_reply_ask(HttpServletResponse response,int page,String answer){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select distinct 课时序号,问题描述,提问者,时间,head,课时标题,回答数,问题编号,访问次数,所属课程编号,标题,(select 回答者 from answer where 所属问题编号=问题编号 order by 回答时间  desc limit 1) 最新回答者,(select 内容 from answer where 所属问题编号=问题编号 order by 回答时间 desc limit 1) 最新回复 from personal_table,ask,class,class_teacher_table,answer where 回答者='"+answer+"'and 所属问题编号=问题编号 and 提问者=username and 所属课程编号=class.课程编号 and class.课程编号=class_teacher_table.课程编号 and 课时序号=章节序号 order by 时间 desc limit "+(6*(page-1))+","+6);
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
                ask_no.add(rs.getString("问题编号"));
                asker.add(rs.getString("提问者"));
                times.add(rs.getInt("访问次数"));
                title_No_list.add(rs.getString("课时序号"));
                title_list.add(rs.getString("课时标题"));
                time_list.add(rs.getString("时间"));
                describe_list.add(rs.getString("问题描述"));
                answer_count.add(rs.getString("回答数"));
                new_answer.add(rs.getString("最新回答者"));
                new_answer_text.add(rs.getString("最新回复"));
                class_no.add(rs.getString("所属课程编号"));
                class_title.add(rs.getString("标题"));
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
            rs = statement.executeQuery("SELECT COUNT(distinct 问题编号) count FROM ask,answer where 回答者='"+answer+"' and 所属问题编号=问题编号");
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_my_ask(HttpServletResponse response,int page,String author){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 课时序号,问题描述,时间,课时标题,回答数,问题编号,访问次数,所属课程编号,标题,(select 回答者 from answer where 所属问题编号=问题编号 order by 回答时间  desc limit 1) 最新回答者,(select 内容 from answer where 所属问题编号=问题编号 order by 回答时间 desc limit 1) 最新回复 from personal_table,ask,class,class_teacher_table where 提问者='"+author+"' and 提问者=username and 所属课程编号=class.课程编号 and class.课程编号=class_teacher_table.课程编号 and 课时序号=章节序号 order by 时间 desc limit "+(6*(page-1))+","+6);
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
                ask_no.add(rs.getString("问题编号"));
                times.add(rs.getInt("访问次数"));
                title_No_list.add(rs.getString("课时序号"));
                title_list.add(rs.getString("课时标题"));
                time_list.add(rs.getString("时间"));
                describe_list.add(rs.getString("问题描述"));
                answer_count.add(rs.getString("回答数"));
                new_answer.add(rs.getString("最新回答者"));
                new_answer_text.add(rs.getString("最新回复"));
                class_no.add(rs.getString("所属课程编号"));
                class_title.add(rs.getString("标题"));
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
            rs = statement.executeQuery("SELECT COUNT(*) count FROM ask where 提问者='"+author+"'");
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void post_answer(HttpServletResponse response,int No,String answer,String answer_time,String answer_text){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("insert into answer(所属问题编号,回答者,内容,回答时间) values(?,?,?,?)");
            qsql.setInt(1,No);
            qsql.setString(2,answer);
            qsql.setString(3,answer_text);
            qsql.setString(4,answer_time);
            qsql.executeUpdate();
            qsql.close();
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
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void post_reply(HttpServletResponse response,int No,String reply,String reply_time,String reply_text){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("insert into reply values(?,?,?,?)");
            qsql.setInt(1,No);
            qsql.setString(2,reply);
            qsql.setString(3,reply_text);
            qsql.setString(4,reply_time);
            qsql.executeUpdate();
            qsql.close();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_reply(HttpServletResponse response,int No){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            PreparedStatement qsql  = con.prepareStatement("UPDATE ask SET 访问次数 =访问次数+1  WHERE 问题编号="+No);
            qsql.executeUpdate();
            qsql.close();
            ResultSet rs = statement.executeQuery("select 标题,所属课程编号,问题描述,提问者,内容,时间,head,课时序号,课程类型,访问次数 from ask,personal_table,class_teacher_table where 问题编号="+No+" and username=提问者 and 所属课程编号=课程编号;");
            JSONObject jsonObj = new JSONObject();
            String ask_title = null;
            int class_no = 0;
            String ask_describe = null;
            String asker = null;
            String ask_text = null;
            String ask_time = null;
            String asker_head = null;
            String unit_no = null;
            int type = 0;
            int times = 0;
            ArrayList<String> answer_head = new ArrayList<>();
            ArrayList<String> answer_no = new ArrayList<>();
            ArrayList<String> answer = new ArrayList<>();
            ArrayList<String> answer_text = new ArrayList<>();
            ArrayList<String> answer_time = new ArrayList<>();
            ArrayList<Integer> other_ask_no = new ArrayList<>();
            ArrayList<String> other_ask_title = new ArrayList<>();
            while (rs.next()){
                ask_title = rs.getString("标题");
                class_no = rs.getInt("所属课程编号");
                ask_describe = rs.getString("问题描述");
                asker = rs.getString("提问者");
                ask_text = rs.getString("内容");
                ask_time = rs.getString("时间");
                asker_head = rs.getString("head");
                unit_no = rs.getString("课时序号");
                type = rs.getInt("课程类型");
                times = rs.getInt("访问次数");
            }

            rs = statement.executeQuery("select 问题编号,问题描述 from ask where 所属课程编号="+class_no+"  and 课时序号='"+unit_no+"' and 问题编号!="+No+" limit 10");
            while (rs.next()){
                other_ask_title.add(rs.getString("问题描述"));
                other_ask_no.add(rs.getInt("问题编号"));
            }

            rs = statement.executeQuery("select head,回答编号,回答者,内容,回答时间 from answer,personal_table where 所属问题编号="+No+"  and username=回答者 order by 回答时间 ;");
            while (rs.next()){
                answer_head.add(rs.getString("head"));
                answer_no.add(rs.getString("回答编号"));
                answer.add(rs.getString("回答者"));
                answer_text.add(rs.getString("内容"));
                answer_time.add(rs.getString("回答时间"));
            }

            int count = 0;
            for (String anAnswer_no : answer_no) {
                rs = statement.executeQuery("select reply.*,head from reply,personal_table where 所属回答编号=" + anAnswer_no + " and 回复者=username order by 回复时间 ;");
                ArrayList<String> reply = new ArrayList<>();
                ArrayList<String> reply_text = new ArrayList<>();
                ArrayList<String> reply_time = new ArrayList<>();
                ArrayList<String> reply_head = new ArrayList<>();
                JSONObject reply_jsonobj = new JSONObject();
                while (rs.next()) {
                    reply.add(rs.getString("回复者"));
                    reply_text.add(rs.getString("内容"));
                    reply_time.add(rs.getString("回复时间"));
                    reply_head.add(rs.getString("head"));
                }
                reply_jsonobj.put("reply",reply);
                reply_jsonobj.put("reply_text",reply_text);
                reply_jsonobj.put("reply_time",reply_time);
                reply_jsonobj.put("reply_head",reply_head);
                jsonObj.put("ask_no"+count,reply_jsonobj);
                count++;
            }

            jsonObj.put("ask_title",ask_title);
            jsonObj.put("class_no",class_no+"");
            jsonObj.put("ask_describe",ask_describe);
            jsonObj.put("asker",asker);
            jsonObj.put("ask_text",ask_text);
            jsonObj.put("ask_time",ask_time);
            jsonObj.put("asker_head",asker_head);
            jsonObj.put("unit_no",unit_no);
            jsonObj.put("type",type);
            jsonObj.put("times",times);
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
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void get_this_class_ask(HttpServletResponse response,int No,int page){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 课时序号,问题描述,提问者,时间,head,课时标题,回答数,问题编号,访问次数,(select 回答者 from answer where 所属问题编号=问题编号 order by 回答时间  desc limit 1) 最新回答者,(select 内容 from answer where 所属问题编号=问题编号 order by 回答时间 desc limit 1) 最新回复 from personal_table,ask,class where 所属课程编号="+No+" and 提问者=username and 所属课程编号=课程编号 and 课时序号=章节序号 order by 时间 desc limit "+(6*(page-1))+","+6);
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
                ask_no.add(rs.getString("问题编号"));
                times.add(rs.getInt("访问次数"));
                title_No_list.add(rs.getString("课时序号"));
                title_list.add(rs.getString("课时标题"));
                time_list.add(rs.getString("时间"));
                describe_list.add(rs.getString("问题描述"));
                asker.add(rs.getString("提问者"));
                head.add(rs.getString("head"));
                answer_count.add(rs.getString("回答数"));
                new_answer.add(rs.getString("最新回答者"));
                new_answer_text.add(rs.getString("最新回复"));
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
            rs = statement.executeQuery("SELECT COUNT(*) count FROM ask where 所属课程编号="+No);
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_All_ask(HttpServletResponse response,String user,int page){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 标题,时间,问题描述,问题编号,所属课程编号,课时序号,提问者,回答数,访问次数,(select 回答者 from answer where 所属问题编号=问题编号 order by 回答时间  desc limit 1) 最新回答者,(select 内容 from answer where 所属问题编号=问题编号 order by 回答时间 desc limit 1) 最新回复 from class_teacher_table,ask where 教师用户名='admin' and 课程编号=所属课程编号 order by 时间 desc limit "+(6*(page-1))+","+6);
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> ask_no = new ArrayList<>();
            ArrayList<String> class_no = new ArrayList<>();
            ArrayList<String> unit_no = new ArrayList<>();
            ArrayList<String> asker = new ArrayList<>();
            ArrayList<String> new_answer = new ArrayList<>();
            ArrayList<String> new_answer_text = new ArrayList<>();
            ArrayList<String> title_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> describe_list = new ArrayList<>();
            ArrayList<String> answer_count = new ArrayList<>();
            ArrayList<Integer> times = new ArrayList<>();
            while (rs.next()){
                title_list.add(rs.getString("标题"));
                time_list.add(rs.getString("时间"));
                describe_list.add(rs.getString("问题描述"));
                ask_no.add(rs.getString("问题编号"));
                class_no.add(rs.getString("所属课程编号"));
                unit_no.add(rs.getString("课时序号"));
                asker.add(rs.getString("提问者"));
                new_answer.add(rs.getString("最新回答者"));
                new_answer_text.add(rs.getString("最新回复"));
                answer_count.add(rs.getString("回答数"));
                times.add(rs.getInt("访问次数"));
            }
            jsonObj.put("title",title_list);
            jsonObj.put("time",time_list);
            jsonObj.put("describe",describe_list);
            jsonObj.put("ask_no",ask_no);
            jsonObj.put("class_no",class_no);
            jsonObj.put("unit_no",unit_no);
            jsonObj.put("asker",asker);
            jsonObj.put("new_answer",new_answer);
            jsonObj.put("new_answer_text",new_answer_text);
            jsonObj.put("answer_count",answer_count);
            jsonObj.put("times",times);
            rs = statement.executeQuery("SELECT COUNT(*) count FROM ask,class_teacher_table where 教师用户名='"+user+"' and 课程编号=所属课程编号");
            while (rs.next()){
                jsonObj.put("count",rs.getString("count"));
            }
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_ask(HttpServletResponse response,int No,String class_No,String author){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 问题描述,时间,回答数,访问次数 from ask where 所属课程编号="+No+" and 课时序号='"+class_No+"' and 提问者= '"+author+"'");
            JSONObject jsonObj = new JSONObject();
            ArrayList<String> title_list = new ArrayList<>();
            ArrayList<String> time_list = new ArrayList<>();
            ArrayList<String> answer = new ArrayList<>();
            ArrayList<String> browse = new ArrayList<>();
            while (rs.next()){
                title_list.add(rs.getString("问题描述"));
                time_list.add(rs.getString("时间"));
                answer.add(rs.getString("回答数"));
                browse.add(rs.getString("访问次数"));
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
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void post_ask(HttpServletResponse response,int No,String class_No,String author,String note_editor,String time,String ask_title){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            PreparedStatement qsql  = con.prepareStatement("insert into ask(所属课程编号,课时序号,问题描述,提问者,内容,时间) values(?,?,?,?,?,?)");
            qsql.setInt(1,No);
            qsql.setString(2,class_No);
            qsql.setString(3,ask_title);
            qsql.setString(4,author);
            qsql.setString(5,note_editor);
            qsql.setString(6,time);
            qsql.executeUpdate();
            qsql.close();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg","1");
            PrintWriter out = response.getWriter();
            //System.out.println(jsonObj+"????");
            out.flush();
            out.print(jsonObj);
            out.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
