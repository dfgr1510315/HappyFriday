package Server;


import javax.websocket.server.ServerEndpoint;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ServerEndpoint("/websocket_toH264")
public class ToH264 {
        //private  static String PATH = "D:\\IntelliJ IDEA 2018.2\\javaweb\\ServletTest\\out\\artifacts\\ServletTest_war_exploded\\Upload\\2.avi";
        //private static CopyOnWriteArraySet<ToH264> webSocketSet = new CopyOnWriteArraySet<ToH264>();
        private  String PATH ;
        private  String FileName ;
        private  String NewPATH;
        private  String returnName;
        private  String  Username;
        private  String  Oldfilename;

        ToH264(String path, String filename,String username,String oldfilename){
            Oldfilename = oldfilename;
            Username = username;
            PATH=path;
            FileName=filename;
        }

        public String getName(){
            return returnName;
        }

        public int getType(){
            return checkContentType();
        }

        void getPATH(){
            if (!checkfile()) {   //判断路径是不是一个文件
                System.out.println(PATH+"\\"+FileName + " is not file");
                return;
            }
            if (process()==0) {        //执行转码任务
                System.out.println("ok");
                System.out.println("NewPATH:"+NewPATH);
             /*   getHash getHash = new getHash(NewPATH);
                System.out.println("NewPATH:"+NewPATH);
                System.out.println("getHash.getMD5()"+getHash.getMD5());*/
            }else if (process()==1) returnName=FileName;
        }


       /* public  void main(String args[]) {
            PATH = "D:\\IntelliJ IDEA 2018.2\\javaweb\\ServletTest\\out\\artifacts\\ServletTest_war_exploded\\Upload";
            FileName="2.avi";

        }*/

        private  boolean checkfile() {
            File file = new File(PATH+"\\"+FileName);
            ConnectSQL.my_println("check:"+PATH+"\\"+FileName);
            return file.isFile();
        }

        private  int process() {
            // 判断视频的类型
            int type = checkContentType();
            //boolean status = false;
            //如果是ffmpeg可以转换的类型直接转码，否则先用mencoder转码成AVI
            if (type == 0) {
                System.out.println("直接将文件转为mp4文件");
                //status = processmp4(PATH+"\\"+FileName);
                if (processmp4(PATH+"\\"+FileName)) return 0;// 直接将文件转为mp4文件
            } else if (type == 1) {
                String avifilepath = processAVI(type);
                if (avifilepath == null)
                    return 1;// avi文件没有得到
                if (processmp4(PATH+"\\"+FileName)) return 0;// 将avi转为mp4
            }else if (type==2){ //判断源文件就是MP4文件，不做处理，直接返回原路径
                return 1;
            }
            return 2;
        }

        private  int checkContentType() {
            String type = FileName.substring(FileName.lastIndexOf(".") + 1).toLowerCase();
            // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，mp4等）
            switch (type) {
                case "avi":
                    return 0;
                case "mpg":
                    return 0;
                case "wmv":
                    return 0;
                case "3gp":
                    return 0;
                case "mov":
                    return 0;
                case "mp4":
                    return 2;
                case "asf":
                    return 0;
                case "asx":
                    return 0;
                case "flv":
                    return 0;

                // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
                // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
                case "wmv9":
                    return 1;
                case "rm":
                    return 1;
                case "rmvb":
                    return 1;
            }
            return 9;
        }


        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        private  String processAVI(int type) {
            List<String> commend = new ArrayList<>();
            commend.add("mencoder");
            commend.add(PATH+"\\"+FileName);
            commend.add("-oac");
            commend.add("lavc");
            commend.add("-lavcopts");
            commend.add("acodec=mp3:abitrate=64");
            commend.add("-ovc");
            commend.add("xvid");
            commend.add("-xvidencopts");
            commend.add("bitrate=600");
            commend.add("-of");
            commend.add("avi");
            commend.add("-o");
            //commend.add("【存放转码后视频的路径，记住一定是.avi后缀的文件名】");
            //commend.add("D:\\IntelliJ IDEA 2018.2\\javaweb\\ServletTest\\out\\artifacts\\ServletTest_war_exploded\\Upload\\2.avi");
            commend.add(PATH+"\\"+FileName.substring(0,FileName.lastIndexOf(".")).toLowerCase()+".avi");
            try {
                //调用线程命令启动转码
                ProcessBuilder builder = new ProcessBuilder();
                builder.command(commend);
                builder.start();
                return PATH+"\\"+FileName.substring(0,FileName.lastIndexOf(".")).toLowerCase()+".avi";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，mp4等）
        private  boolean processmp4(String oldfilepath) {
            if (!checkfile()) {
                System.out.println(oldfilepath + " is not file");
                return false;
            }

            // 文件命名
            Calendar c = Calendar.getInstance();
            String savename = String.valueOf(c.getTimeInMillis())+ Math.round(Math.random() * 100000);
            List<String> commend = new ArrayList<String>();
            commend.add("ffmpeg");
            commend.add("-i");
            System.out.println("oldfilepath:"+oldfilepath);
            commend.add(oldfilepath);
            commend.add("-vcodec");
            commend.add("h264");
         /*   commend.add("-ab");*/
         /*   commend.add("56");*/
         /*   commend.add("-ar");*/
         /*   commend.add("22050");*/
         /*   commend.add("-qscale");*/
         /*   commend.add("8");*/
         /*   commend.add("-r");*/
         /*   commend.add("15");*/
         /*   commend.add("-s");*/
         /*   commend.add("600x500");*/
            //commend.add("【存放转码后视频的路径，记住一定是.mp4后缀的文件名】");
             //commend.add("D:\\IntelliJ IDEA 2018.2\\javaweb\\ServletTest\\out\\artifacts\\ServletTest_war_exploded\\Upload\\2.mp4");
            commend.add(PATH+"\\"+FileName.substring(0,FileName.lastIndexOf(".")).toLowerCase()+".mp4");
            try {
               /* Runtime runtime = Runtime.getRuntime();
                Process proce = null;
                //视频截图命令，封面图。  8是代表第8秒的时候截图
                String cmd = "";
                String cut = "     D:\\ffmpeg\\ffmpeg.exe   -i   "
                        + oldfilepath
                        + "   -y   -f   image2   -ss   8   -t   0.001   -s   600x500   D:\\ffmpeg\\output\\"
                        + "a.jpg";
                String cutCmd = cmd + cut;
                proce = runtime.exec(cutCmd);*/
                //调用线程命令进行转码
                ProcessBuilder builder = new ProcessBuilder(commend);
                builder.command(commend);
                Process process = builder.start();
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                //StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                Pattern pattern =  Pattern.compile("Duration: (.*?),");
                Pattern now_time =  Pattern.compile("time=(.*?) ");
                Matcher m;
                Matcher n;
                int time=0;
                int now;

                while ((line = br.readLine()) != null) {
                    m = pattern.matcher(line);
                    n = now_time.matcher(line);
                    if (time==0 && m.find()) {
                        time = getTimelen(m.group(1));
                        //ConnectSQL.my_println("time:"+time);
                    }else if (n.find()){
                        //ConnectSQL.my_println("n.group(1):"+n.group(1));
                        now = getTimelen(n.group(1));
                        //ConnectSQL.my_println("now:"+now);
                        //ConnectSQL.my_println((float)now/(float) time*100);
                        //ConnectSQL.my_println("username:"+Username);
                        WebSocketTest.sendMessage(Username+Oldfilename,(float)now/(float) time*100+"");
                    }
                    //stringBuilder.append(line);
                }
                //从视频信息中解析时长
               /* String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
                pattern = Pattern.compile(regexDuration);
                m = pattern.matcher(stringBuilder.toString());
                if (m.find()) {
                    time = getTimelen(m.group(1));
                    System.out.println("视频时长：" + time + "s , 开始时间：" + m.group(2) + ", 比特率：" + m.group(3) + "kb/s");
                }
                String regexVideo = "Vedio: (.*?), (.*?), (.*?)[,\\s]";
                pattern = Pattern.compile(regexVideo);
                m = pattern.matcher(stringBuilder.toString());
                if (m.find()) {
                    System.out.println("编码格式：" + m.group(1) + ", 视频格式：" + m.group(2) + ", 分辨率：" + m.group(3) + "kb/s");
                }*/

              /*  ProcessBuilder(array).start()创建子进程Process之后，
                一定要及时取走子进程的输出信息和错误信息，否则输出信息流和错误信息流很可能因为信息太多导致被填满，最终导致子进程阻塞住，然后执行不下去。*/
                InputStream isErr = process.getErrorStream();
                int data;
                while((data=isErr.read())!=-1) ;

                NewPATH = PATH+"\\"+FileName.substring(0,FileName.lastIndexOf(".")).toLowerCase()+".mp4";

                getHash getHash = new getHash(NewPATH);
                returnName =getHash.getMD5()+".mp4";
                new File(NewPATH).renameTo(new File(PATH+"\\"+getHash.getMD5()+".mp4"));
                br.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }


        private int getTimelen(String timelen) {
            int min = 0;
            String strs[] = timelen.split(":");
            if (strs[0].compareTo("0") > 0) {
                // 秒
                min += Integer.valueOf(strs[0]) * 60 * 60;
            }
            if (strs[1].compareTo("0") > 0) {
                min += Integer.valueOf(strs[1]) * 60;
            }
            if (strs[2].compareTo("0") > 0) {
                min += Math.round(Float.valueOf(strs[2]));
            }
            return min;
        }
    }

