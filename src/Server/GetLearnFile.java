package Server;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


@WebServlet(name = "GetLearnFile")
public class GetLearnFile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");

        ArrayList<String> Serial_No = new ArrayList<>();
        ArrayList<String> Unit_Name = new ArrayList<>();
        ArrayList<String> Class_Name = new ArrayList<>();
        ArrayList<String> Video_Src = new ArrayList<>();
        ArrayList<String> Editor = new ArrayList<>();
        ArrayList<String> File_Href = new ArrayList<>();
        ArrayList<String> State = new ArrayList<>();


        String Title = request.getParameter("Class_Title");

        String ds = java.net.URLDecoder.decode(request.getParameter("ds"),"UTF-8");
        JSONArray json=JSONArray.fromObject(ds);

        for(Object obj : json){
            JSONObject jsonOne = (JSONObject)obj;

            Unit_Name.add(jsonOne.getString("Unit_Name"));//章节名
            //System.out.println(Unit_Name);
            String Class = jsonOne.getString("Class");
            JSONArray classOne = JSONArray.fromObject(Class);
            for (Object obj1:classOne){//章节下的所有课程
                JSONObject jsonOne1 = (JSONObject)obj1;

                Serial_No.add(jsonOne1.getString("Serial_No"));

                Class_Name.add(jsonOne1.getString("Class_Name"));
                //System.out.println(jsonOne1.getString("Class_Name"));

                Video_Src.add(jsonOne1.getString("Video_Src"));
                //System.out.println(jsonOne1.getString("Video_Src"));

                Editor.add(jsonOne1.getString("Editor"));

                String inputPath=jsonOne1.getString("File_Href");
                File_Href.add(inputPath);
                //System.out.println(jsonOne1.getString("File_Href"));
                State.add(jsonOne1.getString("State"));
                /*System.out.println(Unit_Name);
                System.out.println(Serial_No);
                System.out.println(Class_Name);
                System.out.println(Video_Src);
                System.out.println(File_Href);*/
            }
        }
        ConnectMysql(Title,Serial_No,Unit_Name,Class_Name,Video_Src,Editor,File_Href,State);
    }

    private void ConnectMysql(String Title,ArrayList<String> Serial_No, ArrayList<String> Unit_name, ArrayList<String> Class_name, ArrayList<String> Video_src, ArrayList<String> Editor, ArrayList<String> File_Href, ArrayList<String> state){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            PreparedStatement qsql;
            ResultSet rs = statement.executeQuery("select 章节序号 from class where 课程标题='"+Title+"'");
            ArrayList<String> Unit = new ArrayList<>();
            while (rs.next()){
                Unit.add(rs.getString("章节序号"));
            }
            for (int i=0;i<Serial_No.size();i++){
                if (Unit.contains(Serial_No.get(i))) {
                    qsql = con.prepareStatement("update class set 章节标题=?, 课时标题=?, 发布状态=?,视频地址=?,图文信息=?,文件地址=? where 章节序号=?");
                    qsql.setString(1,Unit_name.get(Integer.parseInt(Serial_No.get(i).substring(0,Serial_No.get(i).indexOf("_")))-1));
                    qsql.setString(2,Class_name.get(i));
                    qsql.setString(3,state.get(i));
                    qsql.setString(4,Video_src.get(i));
                    qsql.setString(5,Editor.get(i));
                    qsql.setString(6,File_Href.get(i));
                    qsql.setString(7,Serial_No.get(i));
                    qsql.executeUpdate();
                    qsql.close();
                }else {

                    qsql = con.prepareStatement("insert into class values(?,?,?,?,?,?,?,?)");
                    qsql.setString(1,Title);
                    qsql.setString(2,Serial_No.get(i));
                    //System.out.println(Unit_name.get(Integer.parseInt(Serial_No.get(i).substring(0,Serial_No.get(i).indexOf("_")))-1));
                    qsql.setString(3,Unit_name.get(Integer.parseInt(Serial_No.get(i).substring(0,Serial_No.get(i).indexOf("_")))-1));
                    qsql.setString(4,Class_name.get(i));
                    qsql.setString(5,state.get(i));
                    qsql.setString(6,Video_src.get(i));
                    qsql.setString(7,Editor.get(i));
                    qsql.setString(8,File_Href.get(i));
                    qsql.executeUpdate();
                    qsql.close();
                }
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
