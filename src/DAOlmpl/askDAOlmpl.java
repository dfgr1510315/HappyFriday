package DAOlmpl;

import DAO.askDAO;
import Server.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class askDAOlmpl implements askDAO {

    @Override
    public int get_ask_count(int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        String SQL = "select count(ask_id) ask_count from ask where belong_class_id=" + class_id;
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
}
