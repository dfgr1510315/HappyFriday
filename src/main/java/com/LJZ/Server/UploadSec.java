package com.LJZ.Server;

import com.LJZ.DB.DBPoolConnection;
import com.alibaba.druid.pool.DruidPooledConnection;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "UploadSec")
public class UploadSec extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "Upload";

    // 上传配置
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 300;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 400; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 500; // 50MB

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        String username=(String) session.getAttribute("user_id");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
       /* if (!ServletFileUpload.isMultipartContent(request)) {
            //检测是否为多媒体上传
            PrintWriter writer = response.getWriter();
            writer.println("错误：表单必须包含 !@!!!!!enctype=multipart/form-data");
            writer.flush();
            return;
        }*/
        //配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置内存临界值 ，超过后将产生临时文件存储在临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD); //3MB
        //设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);  //设置文件最大上限
        upload.setSizeMax(MAX_REQUEST_SIZE);
        upload.setHeaderEncoding("UTF-8");
        String uploadPath = getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;//相对于当前应用的目录
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())  uploadDir.mkdir(); //如果当前目录不存在则创建
        try {
            //解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            List<String> values = new ArrayList<>();
            JSONObject jsonObj = new JSONObject();
            PrintWriter out = response.getWriter();
            if (formItems !=null&&formItems.size()>0){
                for (FileItem item : formItems){
                    if (item.isFormField()){
                        values.add(item.getString("utf-8"));
                    }
                    else {
                        String fileName = new File(item.getName()).getName();//获取文件名
                        String filePath = uploadPath + File.separator + fileName;//文件的绝对路径 如D:\IDEA\IdeaProjects\out\artifacts\ServletTest_war_exploded\Upload\chromedriver_win32.zip
                        String oldFileName = fileName;//在文件名更换为md5前保存原文件名
                        File storeFile = new File(filePath);
                        item.write(storeFile);//写入磁盘
                        getHash getHash = new getHash(uploadPath+"\\"+item.getName());
                        String type = item.getName().substring(item.getName().lastIndexOf(".") + 1).toLowerCase();//获取文件类型
                        fileName = getHash.getMD5()+"."+type;//将文件名重命名为md5值
                        ConnectSQL.my_println("fileName:"+fileName);
                        if (values.get(0).equals("file")){
                            if (com.LJZ.Server.getHash.getFile(uploadPath).contains(fileName)){   //是否有重复文件
                                File file = new File(uploadPath+"\\"+oldFileName);
                                file.delete();
                            }
                            else file_record(uploadPath,oldFileName,fileName,values);//是新文件则记录
                            jsonObj.put("src",UPLOAD_DIRECTORY+"/"+fileName);
                            jsonObj.put("filename",oldFileName);
                        }
                        else { //教学视频则进行转码
                            ToH264 toH264 = new ToH264(uploadPath,fileName,username,oldFileName);
                            ConnectSQL.my_println("uploadPath:"+uploadPath+";oldFileName:"+oldFileName+";username:"+username+";oldFileName:"+oldFileName);
                            if (com.LJZ.Server.getHash.getFile(uploadPath).contains(fileName)) get_vd(uploadPath,oldFileName,fileName,jsonObj); //有重复视频则从数据库获取视频信息
                            else {
                                new File(uploadPath+"\\"+oldFileName).renameTo(new File(uploadPath+"\\"+fileName));
                                vd_record(toH264,oldFileName,fileName,type,jsonObj);
                            }
                        }
                    }
                }
            }
            out.print(jsonObj);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //记录文件
    private void file_record(String uploadPath,String oldFileName,String fileName,List<String> values){
        File file = new File(uploadPath+"\\"+oldFileName);
        file.renameTo(new File(uploadPath+"\\"+fileName));
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql= con.prepareStatement("insert into file values(?,?,?)");
            qsql.setString(1,UPLOAD_DIRECTORY+"/"+fileName);
            qsql.setString(2,oldFileName);
            qsql.setInt(3,Integer.parseInt(values.get(1)));
            qsql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
    }

    //如果教学视频重复则删除刚上传的视频并从数据库中获取src
    private void get_vd(String uploadPath,String oldFileName,String fileName,JSONObject jsonObj){
        new File(uploadPath+"\\"+oldFileName).delete();
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        try {
            con = dbp.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select video_address,video_title from Video where source_video_address='"+UPLOAD_DIRECTORY+"/"+fileName+"'");
            while (rs.next()) {
                jsonObj.put("src",rs.getString("video_address"));
                jsonObj.put("video_name",rs.getString("video_title"));
                jsonObj.put("cuSRC",UPLOAD_DIRECTORY+"/"+fileName);
            }
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
    }

    //记录教学视频
    private void vd_record(ToH264 toH264,String oldFileName,String fileName,String type,JSONObject jsonObj){
        toH264.getPATH();
        String src;
        if (type.equals("mp4")) {
            src = fileName;
        }else src = toH264.getName();
        ConnectSQL.my_println("src:"+src);
        DBPoolConnection dbp = DBPoolConnection.getInstance();
        DruidPooledConnection con =null;
        PreparedStatement qsql = null;
        try {
            con = dbp.getConnection();
            qsql= con.prepareStatement("insert into Video values(?,?,?)");
            qsql.setString(1,UPLOAD_DIRECTORY+"/"+fileName);
            qsql.setString(2,UPLOAD_DIRECTORY+"/"+src);
            qsql.setString(3,oldFileName);
            qsql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (qsql!=null)
                try{
                    qsql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            if (con!=null)
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        jsonObj.put("video_name",oldFileName);
        jsonObj.put("cuSRC",UPLOAD_DIRECTORY+"/"+fileName);
        jsonObj.put("src",UPLOAD_DIRECTORY+"/"+src);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }

}
