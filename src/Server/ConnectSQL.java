package Server;

public class ConnectSQL{
    static public String driver = "com.mysql.cj.jdbc.Driver";
    static public String url = "jdbc:mysql://localhost:3306/user_data?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&useTimezone = true";
    static public String user = "root";
    static public String Mysqlpassword = "138859";
    static void my_println(Object st){
        boolean flag = true;
        if (flag) System.out.println(st);
    }
}
