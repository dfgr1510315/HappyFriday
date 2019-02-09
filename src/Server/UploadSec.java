package Server;

import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

            if (formItems !=null&&formItems.size()>0){
                for (FileItem item : formItems){
                    if (!item.isFormField()){
                        //String fileName = new File(getHash.getMD5()+"."+type).getName();

                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        String oldFileName = fileName;
                        //System.out.println("fileName:"+fileName);
                        ConnectSQL.my_println("没有经过处理的fileName:"+fileName);
                        //System.out.println("filePath"+filePath);
                       // System.out.println("item.getSize():"+item.getSize());
                        ConnectSQL.my_println("filePath:"+filePath);
                        File storeFile = new File(filePath);
                        //System.out.println("OLDstoreFile.length():"+storeFile.length());
                        //System.out.println(item.getContentType());
                        item.write(storeFile);
                        //System.out.println("storeFile.length():"+storeFile.length());

                        getHash getHash = new getHash(uploadPath+"\\"+item.getName());
                        String type = item.getName().substring(item.getName().lastIndexOf(".") + 1).toLowerCase();
                        fileName = getHash.getMD5()+"."+type;
                        //System.out.println("getHash.getMD5():"+getHash.getMD5());
                        //System.out.println("经过MD5计算后的fileName:"+fileName);
                        JSONObject jsonObj = new JSONObject();
                        PrintWriter out = response.getWriter();

                        ToH264 toH264 = new ToH264(uploadPath,oldFileName); //判断文件类型
                        //System.out.println(toH264.getPATH());
                        //toH264.getPATH();

                        if (toH264.getType()==9){   //如果不是视频文件
                            if (Server.getHash.getFile(uploadPath).contains(fileName)){   //是否有重复文件
                                new File(uploadPath+"\\"+oldFileName).delete();
                            }else {
                                new File(uploadPath+"\\"+oldFileName).renameTo(new File(uploadPath+"\\"+fileName));
                                try {
                                    Class.forName(ConnectSQL.driver);
                                    Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
                                    PreparedStatement qsql= con.prepareStatement("insert into File values(?,?)");
                                    qsql.setString(1,UPLOAD_DIRECTORY+"/"+fileName);
                                    qsql.setString(2,oldFileName);
                                    qsql.executeUpdate();
                                    qsql.close();
                                    con.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            jsonObj.put("src",UPLOAD_DIRECTORY+"/"+fileName);
                            jsonObj.put("filename",oldFileName);
                        }else {  //类型为视频
                            if (Server.getHash.getFile(uploadPath).contains(fileName)){   //是否有重复文件
                                ConnectSQL.my_println("getFile(uploadPath).contains(fileName):"+Server.getHash.getFile(uploadPath).contains(fileName));
                                new File(uploadPath+"\\"+oldFileName).delete();
                                try {
                                    Class.forName(ConnectSQL.driver);
                                    Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
                                    Statement statement = con.createStatement();
                                    ResultSet rs = statement.executeQuery("select * from Video where 源视频地址='"+UPLOAD_DIRECTORY+"/"+fileName+"'");
                                    while (rs.next()) {
                                        jsonObj.put("src",rs.getString("视频地址"));
                                        jsonObj.put("video_name",rs.getString("视频名称"));
                                        //ConnectSQL.my_println("fileName::"+fileName);
                                        jsonObj.put("cuSRC",UPLOAD_DIRECTORY+"/"+fileName);
                                    }
                                    con.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else {
                                toH264.getPATH();
                                new File(uploadPath+"\\"+oldFileName).renameTo(new File(uploadPath+"\\"+fileName));
                                String src;
                                if (type.equals("mp4")) {
                                    src = fileName;
                                }else src = toH264.getName();
                                try {
                                    Class.forName(ConnectSQL.driver);
                                    Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
                                    PreparedStatement qsql= con.prepareStatement("insert into Video values(?,?,?)");
                                    qsql.setString(1,UPLOAD_DIRECTORY+"/"+fileName);
                                    qsql.setString(2,UPLOAD_DIRECTORY+"/"+src);
                                    qsql.setString(3,oldFileName);
                                    qsql.executeUpdate();
                                    qsql.close();
                                    con.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                jsonObj.put("video_name",oldFileName);
                                //ConnectSQL.my_println("fileName::"+fileName);
                                jsonObj.put("cuSRC",UPLOAD_DIRECTORY+"/"+fileName);
                                jsonObj.put("src",UPLOAD_DIRECTORY+"/"+src);
                            }
                        }
                        out.flush();
                        out.print(jsonObj);
                        out.close();
                    }
                }
            }
        }catch (Exception ex){
            request.setAttribute("message","错误信息："+ex.getMessage());

        }
        /*getServletContext().getRequestDispatcher("/homepage.jsp").forward(request,response);*/
       /* response.sendRedirect("/homepage.jsp");*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }



/*    public static void main(String args[]){
        System.out.println(getFile());
    }*/

}
