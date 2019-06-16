package com.LJZ.DAOlmpl;

import com.LJZ.DAO.AskTest;
import com.LJZ.DB.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class AskTestlmpl implements AskTest {
   /* @Override
    public int get_ask_count1(int ask_id) {
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        int id = 0;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select belong_class_id from ask where ask_id=43");
            while (rs.next()){
                id = rs.getInt("belong_class_id");
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
        return id;
    }*/

    private static final String namespace = "com.LJZ.DAO.AskTest.";

    @Override
    public int get_ask_count1(int ask_id) {
        SqlSession session = MyBatisUtil.getSession();
        int i = 0;
        try {
            i = session.selectOne(namespace + "get_ask_count1",43);
            //注意：此处有陷阱，如果做了更新、插入或删除操作，必须使用：
            //session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            MyBatisUtil.closeSession(session);
        }
        return i;
    }
}
