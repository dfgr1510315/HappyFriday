package Server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;

import java.io.FileFilter;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import java.nio.channels.FileChannel;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.Collections;

import java.util.Comparator;

import java.util.List;

import java.util.UUID;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VideoUpload")
public class VideoUpload extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String serverPath = "D:/";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

       /* System.out.println("进入合并后台...");

        String action = request.getParameter("action");

        if ("mergeChunks".equals(action)) {

            // 获得需要合并的目录

            String fileMd5 = request.getParameter("fileMd5");

            // 读取目录所有文件

            File f = new File(serverPath + "/" + fileMd5);

            File[] fileArray = f.listFiles(new FileFilter() {

                // 排除目录，只要文件

                @Override

                public boolean accept(File pathname) {

                    if (pathname.isDirectory()) {

                        return false;

                    }

                    return true;

                }

            });

            // 转成集合，便于排序

            List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));

            // 从小到大排序

            Collections.sort(fileList, new Comparator<File>() {

                @Override

                public int compare(File o1, File o2) {

                    if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {

                        return -1;

                    }

                    return 1;

                }

            });

            // 新建保存文件

            File outputFile = new File(serverPath + "/" + UUID.randomUUID().toString() + ".zip");

            // 创建文件

            outputFile.createNewFile();

            // 输出流

            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            FileChannel outChannel = fileOutputStream.getChannel();

            // 合并

            FileChannel inChannel;

            for (File file : fileList) {

                inChannel = new FileInputStream(file).getChannel();

                inChannel.transferTo(0, inChannel.size(), outChannel);

                inChannel.close();

                // 删除分片

                file.delete();

            }

            // 关闭流

            fileOutputStream.close();

            outChannel.close();

            // 清除文件加

            File tempFile = new File(serverPath + "/" + fileMd5);

            if (tempFile.isDirectory() && tempFile.exists()) {

                tempFile.delete();

            }

            System.out.println("合并文件成功");

        }*/

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

        doGet(request, response);

    }
}
