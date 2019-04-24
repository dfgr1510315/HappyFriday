package Server;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil implements Runnable {
    private String email;// 收件人邮箱
    private String code;// 激活码
    private int action;
    MailUtil(String email, String code, int action) {
        this.email = email;
        this.code = code;
        this.action = action;
    }

    public void run() {
        // 1.创建连接对象javax.mail.Session
        // 2.创建邮件对象 javax.mail.Message
        // 3.发送一封激活邮件
        String from = "1105379011@qq.com";// 发件人电子邮箱
        String host = "smtp.qq.com"; // 指定发送邮件的主机smtp.qq.com(QQ)|smtp.163.com(网易)
        Properties properties = System.getProperties();// 获取系统属性
        //String realPath = this.getClass().getResource("").getPath();
        properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
        properties.setProperty("mail.smtp.auth", "true");// 打开认证

        try {
            //QQ邮箱需要下面这段代码，163邮箱不需要
            /*MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);*/


            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("1105379011@qq.com", "wtiegvrnmejcgjhf"); // 发件人邮箱账号、授权码
                }
            });

            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(from));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            if (action==0){ //注册
                // 2.3设置邮件主题
                message.setSubject("账号激活");
                // 2.4设置邮件内容
                String content = "<html><head></head><body><h1>这是一封激活邮件,激活请点击以下链接</h1><h3><a href='http://172.25.24.78:8080/LJZ/activation?code="
                        + code + "'>http://172.25.24.78:8080/LJZ/activation?code=" + code
                        + "</href></h3></body></html>";
                message.setContent(content, "text/html;charset=UTF-8");
                // 3.发送邮件
                Transport.send(message);
            }else if (action==1){
                // 2.3设置邮件主题
                //172.25.24.78:8080/ServletTest_war
                message.setSubject("更换邮箱");
                // 2.4设置邮件内容
                String content = "<html><head></head><body><h1>更换邮箱请点击以下链接</h1><h3><a href='http://172.25.24.78:8080/LJZ/activation?code="
                        + code + "&email="+email+ "'>确认更换</a></h3></body></html>";
                message.setContent(content, "text/html;charset=UTF-8");
                // 3.发送邮件
                Transport.send(message);
            }

            System.out.println("邮件成功发送!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}