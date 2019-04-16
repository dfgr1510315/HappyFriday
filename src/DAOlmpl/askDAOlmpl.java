package DAOlmpl;

import DAO.askDAO;
import Model.Ask;
import Server.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

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
    public List get_ask(int page, String SQL) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        List<Ask> asks = new ArrayList<>();
        Ask ask;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            String select = "select ask_id,belong_class_id,ask_title,ask_time,answer_count,visits_count,class_title,cover_address,";
            String new_answerer = "(select answerer from answer where belong_ask_id=ask_id order by answer_time  desc limit 1) new_answerer,";
            String new_answer = "(select answer_text from answer where belong_ask_id=ask_id order by answer_time desc limit 1) new_answer";
            ResultSet rs = statement.executeQuery(select+new_answerer+new_answer+" from ask,class_teacher_table where belong_class_id=class_id and "+SQL);
            while (rs.next()){
                ask = new Ask();
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
}
