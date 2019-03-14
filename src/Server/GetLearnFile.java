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
        ArrayList<String> Source_Video_Name = new ArrayList<>();
        ArrayList<String> Source_Video_Src = new ArrayList<>();
        ArrayList<String> Editor = new ArrayList<>();
        ArrayList<String> File_Href = new ArrayList<>();
        ArrayList<String> File_Name = new ArrayList<>();
        ArrayList<String> State = new ArrayList<>();

        int No = Integer.parseInt(request.getParameter("No"));

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

                Source_Video_Name.add(jsonOne1.getString("Source_Video_Name"));

                Source_Video_Src.add(jsonOne1.getString("Source_Video_Src"));

                Editor.add(jsonOne1.getString("Editor"));

                String inputPath=jsonOne1.getString("File_Href");

                File_Href.add(inputPath);

                String file_Name =jsonOne1.getString("File_Name");

                File_Name.add(file_Name);
                //System.out.println("File_Name"+File_Name);

                State.add(jsonOne1.getString("State"));
            }
        }
        ConnectMysql(No,Serial_No,Unit_Name,Class_Name,Video_Src,Source_Video_Src,Source_Video_Name,Editor,File_Href,File_Name,State);
    }

    private void ConnectMysql(int No,ArrayList<String> Serial_No, ArrayList<String> Unit_name, ArrayList<String> Class_name, ArrayList<String> Video_src, ArrayList<String> Source_Video_Src,ArrayList<String> Source_Video_Name, ArrayList<String> Editor, ArrayList<String> File_Href,ArrayList<String> File_Name, ArrayList<String> state){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs;
            PreparedStatement qsql;
            rs = statement.executeQuery("select unit_no from class where class_id='"+No+"'");
            ArrayList<String> Unit = new ArrayList<>();
            while (rs.next()){
                Unit.add(rs.getString("章节序号"));
            }
            System.out.println(Unit);
            System.out.println(Serial_No);
            for (int i=0;i<Serial_No.size();i++){
                if (Unit.contains(Serial_No.get(i))) {
                    qsql = con.prepareStatement("update class set unit_title=?, lesson_title=?, release_status=?,video_address=?,Image_text=?,file_address=?,file_name=?,source_video_title=? ,source_video_address=? where unit_no=? and class_id=?");
                    qsql.setString(1,Unit_name.get(Integer.parseInt(Serial_No.get(i).substring(0,Serial_No.get(i).indexOf("-")))-1));
                    qsql.setString(2,Class_name.get(i));
                    qsql.setString(3,state.get(i));
                    qsql.setString(4,Video_src.get(i));
                    qsql.setString(5,Editor.get(i));
                    qsql.setString(6,File_Href.get(i));
                    qsql.setString(7,File_Name.get(i));
                    qsql.setString(8,Source_Video_Name.get(i));
                    qsql.setString(9,Source_Video_Src.get(i));
                    qsql.setString(10,Serial_No.get(i));
                    qsql.setInt(11,No);
                    qsql.executeUpdate();
                    qsql.close();
                }else {
                    qsql = con.prepareStatement("insert into class values(?,?,?,?,?,?,?,?,?,?,?)");
                    qsql.setInt(1,No);
                    qsql.setString(2,Serial_No.get(i));
                    //System.out.println(Unit_name.get(Integer.parseInt(Serial_No.get(i).substring(0,Serial_No.get(i).indexOf("_")))-1));
                    qsql.setString(3,Unit_name.get(Integer.parseInt(Serial_No.get(i).substring(0,Serial_No.get(i).indexOf("-")))-1));
                    qsql.setString(4,Class_name.get(i));
                    qsql.setString(5,state.get(i));
                    qsql.setString(6,Source_Video_Src.get(i));
                    qsql.setString(7,Source_Video_Name.get(i));
                    qsql.setString(8,Video_src.get(i));
                    qsql.setString(9,Editor.get(i));
                    qsql.setString(10,File_Href.get(i));
                    qsql.setString(11,File_Name.get(i));
                    qsql.executeUpdate();
                    qsql.close();
                }
            }
            for (String aUnit : Unit) {
                if (!Serial_No.contains(aUnit)) {
                    qsql = con.prepareStatement("delete FROM class WHERE unit_no=?and class_id=?");
                    qsql.setString(1, aUnit);
                    qsql.setInt(2, No);
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
