package Server;

import net.sf.json.JSONArray;
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
import java.util.List;


@WebServlet(name = "UploadSec")
public class UploadSec extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "Upload";

    // 上传配置
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        if (!ServletFileUpload.isMultipartContent(request)) {
            //检测是否为多媒体上传
            PrintWriter writer = response.getWriter();
            writer.println("错误：表单必须包含 !@!!!!!enctype=multipart/form-data");
            writer.flush();
            return;
        }

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
    /*    String uploadPath = "D:/";*/
        System.out.println(getServletContext());
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())  uploadDir.mkdir(); //如果当前目录不存在则创建

        try {
            //解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems !=null&&formItems.size()>0){
                for (FileItem item : formItems){
                    if (!item.isFormField()){
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        System.out.println(fileName);
                        File storeFile = new File(filePath);
                        System.out.println(filePath);
                        item.write(storeFile);
                        request.setAttribute("message","文件上传成功");
                        PrintWriter out = response.getWriter();
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("src",UPLOAD_DIRECTORY+"/"+fileName);
                        jsonObj.put("filename",fileName);
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
}
