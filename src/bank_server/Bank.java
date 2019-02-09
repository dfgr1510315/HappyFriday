package bank_server;

import Server.ConnectSQL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Calendar;
import java.util.TimeZone;

@WebServlet(name = "Bank")
public class Bank extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        String type = request.getParameter("type");
        String user = request.getParameter("user");
        //System.out.println(type);
        switch (type) {
            case "定期存款产品":
                connSQL(response);
                break;
            case "定期存款":
                String No = request.getParameter("No");
                String money = request.getParameter("money");
                Time_deposit_user(No, user, money);
                break;
            case "获取余额":
                get_balance(user, response);
                break;
            case "获取定期存款表":
                get_time_deposit(user,response);
                break;
            case "定期存款取款":
                money = request.getParameter("money");
                No = request.getParameter("no");
                Withdraw_money(user,response,No,money);
                break;
            case "活期存款":
                money = request.getParameter("money");
                Deposit(user,money);
                break;
            case "活期取款":
                money = request.getParameter("money");
                Withdraw(user,money,response);
                break;
        }
    }

    private void Withdraw(String user,String money, HttpServletResponse response){//活期取款
        int msg;
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url,ConnectSQL.user,ConnectSQL.Mysqlpassword);
            PreparedStatement psql = con.prepareStatement("update bank_user set 账户余额=账户余额-? where 客户ID=?");
            psql.setDouble(1,Double.parseDouble(money));
            psql.setString(2,user);
            psql.executeUpdate();
            msg = 1;
            psql.close();
            con.close();
        }catch (Exception e){
            msg = 2;
            e.printStackTrace();
        }

        try {
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(msg);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void Deposit(String user,String money){//活期存款
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url,ConnectSQL.user,ConnectSQL.Mysqlpassword);
            PreparedStatement psql = con.prepareStatement("update bank_user set 账户余额=账户余额+? where 客户ID=?");
            psql.setDouble(1,Double.parseDouble(money));
            psql.setString(2,user);
            psql.executeUpdate();
            psql.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void Withdraw_money(String user,HttpServletResponse response,String no,String money){ //取出定期存款
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url,ConnectSQL.user,ConnectSQL.Mysqlpassword);
            PreparedStatement psql;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 账户余额,活期利率,起存日期,存储金额 from bank_user,time_deposit_user,user_type where 客户ID ='"+user+"' and 凭证ID='"+no+"' and 账户类型号=类型号");
            rs.next();
            PrintWriter out = response.getWriter();
            int msg;
            if (rs.getDouble("存储金额")-Double.parseDouble(money)<0){
                msg = 1;
            } else {
                if (rs.getDouble("存储金额")-Double.parseDouble(money)==0){
                    psql = con.prepareStatement("delete from time_deposit_user  where 凭证ID=?");
                    psql.setString(1,no);
                    psql.executeUpdate();
                }else {
                    psql = con.prepareStatement("update time_deposit_user set 存储金额=存储金额-? where 凭证ID=?");
                    psql.setDouble(1,Double.parseDouble(money));
                    psql.setString(2,no);
                    psql.executeUpdate();
                }
                Timestamp s = new Timestamp(System.currentTimeMillis());
                int day = (int)((s.getTime()-rs.getTimestamp("起存日期",Calendar.getInstance(TimeZone.getTimeZone("GMT+8"))).getTime())/ (1000*60*1440));
                double rate = rs.getDouble("活期利率")*day+Double.parseDouble(money);
                //System.out.println( rs.getDouble("活期利率")*day);
                psql = con.prepareStatement("update bank_user set 账户余额=账户余额+? where 客户ID=?");
                psql.setDouble(1,rate);
                psql.setString(2,user);
                psql.executeUpdate();
                msg = 2;
                psql.close();
            }
            out.flush();
            out.print(msg);
            out.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void get_time_deposit(String user,HttpServletResponse response){
        JSONArray json= new JSONArray();
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url,ConnectSQL.user,ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 凭证ID,存储金额,产品名称,起存日期,到期日期 from time_deposit_user,time_deposit_product where 所属客户 ='"+user+"'"+"and 产品序号=序号");
            while (rs.next()){
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("凭证号",rs.getString("凭证ID"));
                jsonObj.put("存储金额",rs.getString("存储金额"));
                jsonObj.put("产品名称",rs.getString("产品名称"));
                jsonObj.put("起存日期",(rs.getTimestamp("起存日期")).toString());
                jsonObj.put("到期日期",(rs.getTimestamp("到期日期")).toString());
                json.add(jsonObj);
            }
            //System.out.println(jsonObj+"!!!!!");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(json);
            rs.close();
            out.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void get_balance(String user,HttpServletResponse response){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url,ConnectSQL.user,ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 账户余额,账户类型,活期利率 from bank_user,user_type where 客户ID ='"+user+"' and 账户类型号=类型号");
            JSONObject jsonObj = new JSONObject();
            while (rs.next()){
                jsonObj.put("账户余额",rs.getDouble("账户余额"));
                jsonObj.put("账户类型",rs.getString("账户类型"));
                jsonObj.put("活期利率",rs.getDouble("活期利率"));
            }
            //System.out.println(jsonObj+"!!!!!");
            PrintWriter out = response.getWriter();
            out.flush();
            out.print(jsonObj);
            rs.close();
            out.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void Time_deposit_user(String No,String user,String money){
        try {
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url,ConnectSQL.user,ConnectSQL.Mysqlpassword);
            PreparedStatement psql = con.prepareStatement("insert into  Time_deposit_user(所属客户,存储金额,产品序号,起存日期,到期日期)"+"values(?,?,?,?,?)");
            psql.setString(1,user);
            psql.setDouble(2,Double.parseDouble(money));
            psql.setString(3,No);
            Timestamp s = new Timestamp(System.currentTimeMillis());
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select 存期 from time_deposit_product where 序号 ='"+No+"'");
            rs.next();
            long day_add = Long.parseLong(rs.getString("存期"));
            Timestamp e = new Timestamp(System.currentTimeMillis()+day_add);
            psql.setTimestamp(4,s);
            psql.setTimestamp(5,e);
            psql.executeUpdate();
            psql.close();
            psql = con.prepareStatement("update bank_user set 账户余额=账户余额-? where 客户ID=?");
            psql.setDouble(1,Double.parseDouble(money));
            psql.setString(2,user);
            psql.executeUpdate();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void connSQL(HttpServletResponse response){
        JSONArray json= new JSONArray();
        try {
            PrintWriter out = response.getWriter();
            Class.forName(ConnectSQL.driver);
            Connection con = DriverManager.getConnection(ConnectSQL.url, ConnectSQL.user, ConnectSQL.Mysqlpassword);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from Time_deposit_product");
            while (rs.next()){
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("序号",rs.getString("序号"));
                jsonObj.put("产品名称",rs.getString("产品名称"));
                jsonObj.put("币种",rs.getString("币种"));
                //jsonObj.put("存期",rs.getString("存期"));
                jsonObj.put("挂牌利率",rs.getString("挂牌利率"));
                jsonObj.put("起存金额",rs.getString("起存金额"));
                json.add(jsonObj);
            }
            out.flush();
            out.print(json);
            rs.close();
            con.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
