package com.LJZ.Server;

import com.LJZ.DAO.ClassDAO;
import com.LJZ.DB.GetSqlSessionFactory;
import com.LJZ.Model.Lesson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetLearnFile")
public class GetLearnFile extends HttpServlet {
    //private static SqlSessionFactory factory = (SqlSessionFactory) new ClassPathXmlApplicationContext("application.xml").getBean("sqlSessionFactory");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        SqlSession sqlSession = GetSqlSessionFactory.getSqlSession();
        ClassDAO cdl = sqlSession.getMapper(ClassDAO.class);
        switch (action){
            case "post":
                set_class_content(response,request,cdl);
                break;
            case "get":
                get_class_content(response,request,cdl);
                break;
        }

    }

    private void get_class_content(HttpServletResponse response,HttpServletRequest request,ClassDAO cdl)throws IOException{
        int No = Integer.parseInt(request.getParameter("No"));
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("class_content",cdl.get_class_content(No));
        out.print(jsonObj);
        out.flush();
        out.close();
    }

    private void set_class_content(HttpServletResponse response,HttpServletRequest request,ClassDAO cdl)throws IOException {
        Lesson lesson;
        int No = Integer.parseInt(request.getParameter("No"));
        String ds = java.net.URLDecoder.decode(request.getParameter("ds"), StandardCharsets.UTF_8);
        JSONArray json=JSONArray.fromObject(ds);
        List unit = cdl.getUnitNo(No);
        List<String> Serial_No = new ArrayList<>();
        for(Object obj : json){
            JSONObject jsonOne = (JSONObject)obj;
            String Unit_Name = jsonOne.getString("Unit_Name");
            String Class = jsonOne.getString("Class");
            JSONArray classOne = JSONArray.fromObject(Class);
            for (Object obj1:classOne){//章节下的所有课程
                JSONObject jsonOne1 = (JSONObject)obj1;
                lesson = new Lesson();
                lesson.setUnit_title(Unit_Name);//章节名
                lesson.setUnit_no(jsonOne1.getString("Serial_No"));
                lesson.setLesson_title(jsonOne1.getString("Class_Name"));
                lesson.setVideo_address(jsonOne1.getString("Video_Src"));
                lesson.setSource_video_title(jsonOne1.getString("Source_Video_Name"));
                lesson.setSource_video_address(jsonOne1.getString("Source_Video_Src"));
                lesson.setImage_text(jsonOne1.getString("Editor"));
                lesson.setFile_address(jsonOne1.getString("File_Href"));
                lesson.setFile_name(jsonOne1.getString("File_Name"));
                lesson.setRelease_status(jsonOne1.getInt("State"));
                if (unit.contains(jsonOne1.getString("Serial_No"))){
                    cdl.UpClassContent(lesson,No);
                }else {
                    cdl.InClassContent(lesson,No);
                }
                Serial_No.add(jsonOne1.getString("Serial_No"));
            }
        }
        for (Object aUnit : unit) {
            String sUnit = (String) aUnit;
            if (!Serial_No.contains(sUnit)) {
                cdl.DeClassContent(No,sUnit);
            }
        }
        PrintWriter out = response.getWriter();
        out.print(true);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
