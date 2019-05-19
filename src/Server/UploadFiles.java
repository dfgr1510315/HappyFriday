package Server;

import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "UploadFiles")
public class UploadFiles extends HttpServlet {

    // 上传文件存储目录
    //private static final String UPLOAD_DIRECTORY = "image";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        //配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        String UPLOAD_DIRECTORY = null;
        String uploadPath = null;//相对于当前应用的目录
        File uploadDir;
        try{
            //解析请求的内容提取文件数据
            List<FileItem> formItems = upload.parseRequest(request);
            //String values = null;
            if (formItems !=null&&formItems.size()>0){
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        String oldFileName = fileName;
                        File storeFile = new File(filePath);
                        item.write(storeFile);

                        getHash getHash = new getHash(uploadPath+"\\"+item.getName());
                        String type = item.getName().substring(item.getName().lastIndexOf(".") + 1).toLowerCase();
                        fileName = getHash.getMD5()+"."+type;
                        ConnectSQL.my_println(uploadPath);
                        if (Server.getHash.getFile(uploadPath).contains(fileName)){   //是否有重复文件
                            new File(uploadPath+"\\"+oldFileName).delete();
                        }else {
                            new File(uploadPath+"\\"+oldFileName).renameTo(new File(uploadPath+"\\"+fileName));
                        }
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("file_add","/"+UPLOAD_DIRECTORY+"/"+fileName);
                        PrintWriter out = response.getWriter();
                        out.print(jsonObj);
                        out.flush();
                        out.close();
                    }else {
                        UPLOAD_DIRECTORY = item.getString("utf-8");
                        uploadPath = getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
                        uploadDir = new File(uploadPath);
                        if (!uploadDir.exists())  uploadDir.mkdir(); //如果当前目录不存在则创建
                    }
                }
            }
        }catch (Exception ex){
            request.setAttribute("message","错误信息："+ex.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
