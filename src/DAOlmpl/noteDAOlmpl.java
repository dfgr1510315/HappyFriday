package DAOlmpl;

import DAO.noteDAO;
import Server.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class noteDAOlmpl implements noteDAO {
    @Override
    public int get_note_count(int class_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        String SQL = "select count(note_id) note_count from note where belong_class_id=" + class_id;
        int count = 0;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs  = statement.executeQuery(SQL);
            while (rs.next()) {
                count =  rs.getInt("note_count");
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
