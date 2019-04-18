package DAOlmpl;

import DAO.askDAO;
import Model.Answer;
import Model.Reply;
import Model.SubModel.Ask_class;
import Model.SubModel.Ask_infor;
import Model.SubModel.Ask_list;
import Server.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class askDAOlmpl implements askDAO {

    @Override
    public int get_ask_count(String SQL) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        int count = 0;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs  = statement.executeQuery(SQL);
            while (rs.next()) {
                count =  rs.getInt("ask_count");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return count;
    }

    @Override
    public List get_ask(String SQL_from) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Ask_list> asks = new ArrayList<>();
        Ask_list ask;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            String select = "select ask_id,belong_class_id,ask_title,ask_time,answer_count,visits_count,class_title,cover_address,";
            String new_answerer = "(select answerer from answer where belong_ask_id=ask_id order by answer_time  desc limit 1) new_answerer,";
            String new_answer = "(select answer_text from answer where belong_ask_id=ask_id order by answer_time desc limit 1) new_answer";
            ResultSet rs = statement.executeQuery(select+new_answerer+new_answer+SQL_from);
            while (rs.next()){
                ask = new Ask_list();
                ask.setAsk_id(rs.getInt("ask_id"));
                ask.setBelong_class_id(rs.getInt("belong_class_id"));
                ask.setAsk_title(rs.getString("ask_title"));
                ask.setAsk_time(rs.getString("ask_time"));
                ask.setAnswer_count(rs.getInt("answer_count"));
                ask.setVisits_count(rs.getInt("visits_count"));
                ask.setClass_title(rs.getString("class_title"));
                ask.setCover_address(rs.getString("cover_address"));
                ask.setNew_answerer(rs.getString("new_answerer"));
                ask.setNew_answer(rs.getString("new_answer"));
                asks.add(ask);
            }
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
        return asks;
    }

    @Override
    public int post_answer(int class_id, String answer, String answer_time, String answer_text) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int ask_id = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into answer(belong_ask_id,answerer,answer_text,answer_time) values(?,?,?,?)");
            qsql.setInt(1,class_id);
            qsql.setString(2,answer);
            qsql.setString(3,answer_text);
            qsql.setString(4,answer_time);
            qsql.executeUpdate();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID() answerID");
            while (rs.next()){
                ask_id = rs.getInt("answerID");
            }
            rs.close();
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
        return ask_id;
    }

    @Override
    public int post_reply(int class_id, String replyer, String reply_time, String reply_text, String reply_to) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int reply_id = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into reply(belong_answer_id,replyer,text,reply_time,to_reply) values(?,?,?,?,?)");
            qsql.setInt(1,class_id);
            qsql.setString(2,replyer);
            qsql.setString(3,reply_text);
            qsql.setString(4,reply_time);
            qsql.setString(5,reply_to);
            qsql.executeUpdate();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID() reply_id");
            while (rs.next()){
                reply_id = rs.getInt("reply_id");
            }
            rs.close();
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
        return reply_id;
    }

    @Override
    public Ask_infor get_ask_infor(int ask_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        Ask_infor ask_infor = new Ask_infor();
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            qsql  = con.prepareStatement("UPDATE ask SET visits_count =visits_count+1  WHERE ask_id="+ask_id);
            qsql.executeUpdate();
            ResultSet rs = statement.executeQuery("select class_title,belong_class_id,ask_title,asker,ask_text,ask_time,head,unit_no,class_type,visits_count from ask,personal_table,class_teacher_table where ask_id="+ask_id+" and username=asker and belong_class_id=class_id;");
            while (rs.next()){
                ask_infor.setClass_title(rs.getString("class_title"));
                ask_infor.setBelong_class_id(rs.getInt("belong_class_id"));
                ask_infor.setAsk_title(rs.getString("ask_title"));
                ask_infor.setAsker(rs.getString("asker"));
                ask_infor.setAsk_text(rs.getString("ask_text"));
                ask_infor.setAsk_time(rs.getString("ask_time"));
                ask_infor.setHead(rs.getString("head"));
                ask_infor.setUnit_no(rs.getString("unit_no"));
                ask_infor.setClass_type(rs.getInt("class_type"));
                ask_infor.setVisits_count(rs.getInt("visits_count"));
            }
            rs.close();
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
        return ask_infor;
    }

    @Override
    public List get_answer(int ask_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Answer> answers = new ArrayList<>();
        List<Reply> replys;
        Answer answer;
        Reply reply;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            Statement statement1 = con.createStatement();
            ResultSet rs = statement.executeQuery("select head,answer_id,answerer,answer_text,answer_time from answer,personal_table where belong_ask_id="+ask_id+"  and username=answerer order by answer_time;");
            ResultSet ns;
            while (rs.next()){
                answer = new Answer();
                replys = new ArrayList<>();
                answer.setHead(rs.getString("head"));
                answer.setAnswer_id(rs.getInt("answer_id"));
                answer.setAnswerer(rs.getString("answerer"));
                answer.setAnswer_text(rs.getString("answer_text"));
                answer.setAnswer_time(rs.getString("answer_time"));
                //取出该回答下所有的回复
                ns = statement1.executeQuery("select reply.text,replyer,reply_time,head from reply,personal_table where belong_answer_id=" + rs.getInt("answer_id") + " and replyer=username order by reply_time ;");
                while (ns.next()) {
                    reply = new Reply();
                    reply.setText(ns.getString("reply.text"));
                    reply.setReplyer(ns.getString("replyer"));
                    reply.setReply_time(ns.getString("reply_time"));
                    reply.setHead(ns.getString("head"));
                    replys.add(reply);
                }
                ns.close();
                answer.setReplys(replys);
                answers.add(answer);
            }
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
        return answers;
    }

    @Override
    public List get_other_ask(int class_id,String unit_no,int ask_no){
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Integer> ask_id=new ArrayList<>();
        List<String> ask_title = new ArrayList<>();
        List<List> list = new ArrayList<>();
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select ask_id,ask_title from ask where belong_class_id="+class_id+"  and unit_no='"+unit_no+"' and ask_id!="+ask_no+" limit 10");
            while (rs.next()){
                ask_id.add(rs.getInt("ask_id"));
                ask_title.add(rs.getString("ask_title"));
            }
            list.add(ask_id);
            list.add(ask_title);
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
        return list;
    }

    @Override
    public List get_this_class_ask(int class_id, int page) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        Ask_class ask_class;
        List<Ask_class> list = new ArrayList<>();
        String SQL_select = "select ask.unit_no,ask_title,asker,ask_time,head,lesson_title,answer_count,ask_id,visits_count,";
        String new_answerer = "(select answerer from answer where belong_ask_id=ask_id order by answer_time  desc limit 1) new_answerer,";
        String new_answer = "(select answer_text from answer where belong_ask_id=ask_id order by answer_time desc limit 1) new_answer";
        String SQL_from = " from personal_table,ask,class where belong_class_id="+class_id+" and asker=username and belong_class_id=class_id and ask.unit_no=class.unit_no order by ask_time desc limit "+(6*(page-1))+","+6;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_select+new_answerer+new_answer+SQL_from);
            while (rs.next()){
                ask_class = new Ask_class();
                ask_class.setUnit_no(rs.getString("ask.unit_no"));
                ask_class.setAsk_title(rs.getString("ask_title"));
                ask_class.setAsker(rs.getString("asker"));
                ask_class.setAsk_time(rs.getString("ask_time"));
                ask_class.setHead(rs.getString("head"));
                ask_class.setLesson_title(rs.getString("lesson_title"));
                ask_class.setAnswer_count(rs.getInt("answer_count"));
                ask_class.setAsk_id(rs.getInt("ask_id"));
                ask_class.setVisits_count(rs.getInt("visits_count"));
                ask_class.setNew_answerer(rs.getString("new_answerer"));
                ask_class.setNew_answer(rs.getString("new_answer"));
                list.add(ask_class);
            }
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
        return list;
    }

    @Override
    public int post_ask(int class_id, String unit_no, String asker, String ask_text, String time, String ask_title) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        int ask_id = 0;
        try {
            con = dbp.getConnection();
            qsql  = con.prepareStatement("insert into ask(belong_class_id,unit_no,ask_title,asker,ask_text,ask_time) values(?,?,?,?,?,?)");
            qsql.setInt(1,class_id);
            qsql.setString(2,unit_no);
            qsql.setString(3,ask_title);
            qsql.setString(4,asker);
            qsql.setString(5,ask_text);
            qsql.setString(6,time);
            qsql.executeUpdate();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID() ask_id");
            while (rs.next()){
                ask_id = rs.getInt("ask_id");
            }
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
        return ask_id;
    }

}
