package com.LJZ.DB;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MyBatisUtil {
    private static final String configFile = "mybatis-config.xml";

    /**
     * 创建连接
     */
    public static SqlSession getSession() {
        SqlSession session = null;
        try {
            InputStream is = Resources.getResourceAsStream(configFile);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
            session = factory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    public static void closeSession(SqlSession session) {
        session.close();
    }

    public static void main(String[] args) {
        SqlSession session = MyBatisUtil.getSession();
        System.out.println(session);
        session.close();
    }
}
