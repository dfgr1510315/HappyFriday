package com.LJZ.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

class getHash {
    private String path;

    getHash(String path){
        this.path=path;
    }

    String getMD5(){
        List<String> commend = new ArrayList<>();
        commend.add("certutil");
        commend.add("-hashfile");
        commend.add(path);
        commend.add("MD5");
        try {
            //调用线程命令计算MD5
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process process = builder.start();
            BufferedReader br =new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            String line;
            int i=0;
            StringBuilder sb=new StringBuilder();
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                if (i==1) {
                    sb.append(line);
                    break;
                }
                i++;
            }
            //ConnectSQL.my_println("11 22 33  ".replaceAll("\\s*", ""));
            return sb.toString().replaceAll("\\s*", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getFile(String uploadPath){
        // 获得指定文件对象
        File file = new File(uploadPath);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        List<String> list = new ArrayList<>();
        int n = 0;
        assert array != null;
        for (File anArray : array) {
            if (anArray.isFile())//如果是文件
            {
               /* for (int j = 0; j < deep; j++)//输出前置空格
                    System.out.print(" ");*/
                // 只输出文件名字
                //list.add(anArray.getName().substring(0, anArray.getName().indexOf(".")));
                list.add(anArray.getName());
            }
        }
        return list;
    }
}
