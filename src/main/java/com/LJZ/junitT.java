package com.LJZ;

import com.LJZ.DAO.ClassDAO;
import com.LJZ.DAO.AskDAO;
import com.LJZ.Model.Lesson;
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
        AskDAO al = sqlSession.getMapper(AskDAO.class);
       /* List ask_List = al.get_answer(43);
        for (Object o : ask_List) {
            Answer answer = (Answer) o;
            List replyList = al.get_reply(answer.getAnswer_id());
            answer.setReplys(replyList);
            System.out.println(answer.getReplys());
        }*/
        String SQL_count = "SELECT COUNT(*) ask_count FROM ask,class_teacher_table where teacher='admin' and class_id=belong_class_id";
        int count = al.get_ask_count(SQL_count);
        System.out.println(count);
    }

    @Test
    public void testBasicsClassDAO(){
        ApplicationContext ctx=new ClassPathXmlApplicationContext("application.xml");
        SqlSessionFactory factory = (SqlSessionFactory) ctx.getBean("sqlSessionFactory");
        SqlSession sqlSession = factory.openSession();
        //BasicsClassDAO basicsClassDAO = sqlSession.getMapper(BasicsClassDAO.class);
      /*  int i = basicsClassDAO.delete_class(24);
        System.out.println(i);*/
     /* List a = basicsClassDAO.search_tips("m");
      System.out.println(a.get(0));*/
       /* List a = basicsClassDAO.read_class(2);
        System.out.println(a.get(0));*/

        ClassDAO classDAO = sqlSession.getMapper(ClassDAO.class);
        Lesson lesson = new Lesson();
        lesson.setUnit_no("1-4");
        lesson.setClass_id(7);
        //int i = classDAO.UpClassContent(lesson,7);
        //int i = classDAO.InClassContent(lesson,7);
        //int i = classDAO.DeClassContent(7,"1-4");
        List list = classDAO.get_class_content(7);
        System.out.println(list);
    }
}
