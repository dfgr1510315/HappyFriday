package Server;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class Mailforget implements Runnable {
    private String email;// 收件人邮箱
    private String PW;// 密码

    public Mailforget(String email, String PW) {
        this.email = email;
        this.PW = PW;
    }

    public void run() {
        // 1.创建连接对象javax.mail.Session
        // 2.创建邮件对象 javax.mail.Message
        // 3.发送一封激活邮件
        String from = "1105379011@qq.com";// 发件人电子邮箱
        String host = "smtp.qq.com"; // 指定发送邮件的主机smtp.qq.com(QQ)|smtp.163.com(网易)

        Properties properties = System.getProperties();// 获取系统属性

        properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
        properties.setProperty("mail.smtp.auth", "true");// 打开认证

        try {

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
            // 2.3设置邮件主题
            message.setSubject("密码找回");
            // 2.4设置邮件内容
            String content = "<html><head></head><body><h2>请妥善保管你的密码：</h2><h3>"+PW+"</h3></body></html>";
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.发送邮件
            Transport.send(message);
            ConnectSQL.my_println("邮件成功发送!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}