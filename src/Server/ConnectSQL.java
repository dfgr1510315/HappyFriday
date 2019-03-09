package Server;

public class ConnectSQL{
    static public String driver = "com.mysql.cj.jdbc.Driver";
   /* static public String url = "jdbc:mysql://localhost:3306/user_data?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&useTimezone = true";
    static public String user = "root";
    static public String Mysqlpassword = "138859";*/
    static public String url = "jdbc:mysql://120.76.210.158:3611/class_web?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&useTimezone = true";
    static public String user = "t260mysql";
    static public String Mysqlpassword = "t260mysql";
    static void my_println(Object st){
        if (true) System.out.println(st);
    }
}
