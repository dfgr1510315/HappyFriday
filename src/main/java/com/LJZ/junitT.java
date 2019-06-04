package com.LJZ;

import com.LJZ.DAO.askDAO;
import com.LJZ.Model.Answer;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class junitT {

    @Test
    public void testSpringMyBatisByMapper() {
        ApplicationContext ctx=new ClassPathXmlApplicationContext("application.xml");
        SqlSessionFactory factory = (SqlSessionFactory) ctx.getBean("sqlSessionFactory");
        SqlSession sqlSession = factory.openSession();
        askDAO al = sqlSession.getMapper(askDAO.class);
        List ask_List = al.get_answer(43);
        for (Object o : ask_List) {
            Answer answer = (Answer) o;
            List replyList = al.get_reply(answer.getAnswer_id());
            answer.setReplys(replyList);
            System.out.println(answer.getReplys());
        }
    }
}
