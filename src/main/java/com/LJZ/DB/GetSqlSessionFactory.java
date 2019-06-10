package com.LJZ.DB;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetSqlSessionFactory {
    //private static SqlSessionFactory sqlSessionFactory;
    //private static ThreadLocal<SqlSession> tl = new ThreadLocal<>();
    private static ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
    private GetSqlSessionFactory(){ }
  /*  private static SqlSessionFactory getSqlSessionFactory(){
        if (sqlSessionFactory == null){
            sqlSessionFactory = (SqlSessionFactory) new ClassPathXmlApplicationContext("application.xml").getBean("sqlSessionFactory");
        }
        return sqlSessionFactory;
    }*/
   /* public static SqlSession getSqlSession(){
        SqlSession sqlSession = tl.get();
        if (sqlSession == null){
            System.out.println("Null");
            sqlSession = getSqlSessionFactory().openSession();
            tl.set(sqlSession);
        }
        System.out.println(sqlSession.hashCode());
        return getSqlSessionFactory().openSession();
    }*/
   public static SqlSession getSqlSession(){
       return (SqlSession)context.getBean("sqlSession");
   }
}
