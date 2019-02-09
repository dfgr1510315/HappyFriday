package Server;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


    class ToH264 {

        //private  static String PATH = "D:\\IntelliJ IDEA 2018.2\\javaweb\\ServletTest\\out\\artifacts\\ServletTest_war_exploded\\Upload\\2.avi";
        private static String PATH ;
        private static String FileName ;
        private static String NewPATH;
        private static String returnName;

        ToH264(String path,String filename){
            PATH=path;
            FileName=filename;
            System.out.println("ToH264-PATH:"+PATH);
            System.out.println("ToH264-FileName:"+FileName);
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


        public static void main(String args[]) {
            PATH = "D:\\IntelliJ IDEA 2018.2\\javaweb\\ServletTest\\out\\artifacts\\ServletTest_war_exploded\\Upload";
            FileName="2.avi";
           /* if (!checkfile()) {   //判断路径是不是一个文件
                System.out.println(FileName + " is not file");
                return;
            }
            if (process()) {        //执行转码任务
                System.out.println("ok");
                getHash getHash = new getHash("D:\\IntelliJ IDEA 2018.2\\javaweb\\ServletTest\\out\\artifacts\\ServletTest_war_exploded\\Upload\\1a1557d31e0ae796fd42a556f84788c7.avi");
                System.out.println(getHash.getMD5());
            }*/
            //getPATH();
        }

        private static boolean checkfile() {
            File file = new File(PATH+"\\"+FileName);
            return file.isFile();
        }

        private static int process() {
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

        private static int checkContentType() {
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
        private static String processAVI(int type) {
            List<String> commend = new ArrayList<String>();
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
        private static boolean processmp4(String oldfilepath) {
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
              /*  ProcessBuilder(array).start()创建子进程Process之后，
                一定要及时取走子进程的输出信息和错误信息，否则输出信息流和错误信息流很可能因为信息太多导致被填满，最终导致子进程阻塞住，然后执行不下去。*/
                InputStream isErr = process.getErrorStream();
                int data=0;
                while((data=isErr.read())!=-1);
                    //System.err.println((byte)data);
                NewPATH = PATH+"\\"+FileName.substring(0,FileName.lastIndexOf(".")).toLowerCase()+".mp4";

                getHash getHash = new getHash(NewPATH);
                returnName =getHash.getMD5()+".mp4";
                new File(NewPATH).renameTo(new File(PATH+"\\"+getHash.getMD5()+".mp4"));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

