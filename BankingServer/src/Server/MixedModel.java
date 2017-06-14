/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Mr-Tuy
 */
public class MixedModel {
    public static Connection connect() throws Exception{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        System.out.println("#- Driver loaded");
            
        String dbUrl = "jdbc:sqlserver://VAIO-MR-TUY\\BRILLIANT;databasename=Banking;user=sa;password=987654321";
        Connection con = DriverManager.getConnection(dbUrl);
        return con;
    }
    public static Connection backUpConnect() throws Exception{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        System.out.println("#- Driver loaded");
            
        String dbUrl = "jdbc:sqlserver://VAIO-MR-TUY\\BRILLIANT;databasename=BackUp;user=sa;password=987654321";
        Connection con = DriverManager.getConnection(dbUrl);
        return con;
    }
    public static ArrayList<String> findAccRelate(String cusName){
        ArrayList<String> acc = new ArrayList<String>();
        try{
            Connection con = connect();
            ResultSet rs = con.createStatement().executeQuery("select distinct accountName from Account as a,Customer as c,NormalCustomer as nm where (a.mainCustomerID = c.customerID and c.customerName='"+cusName+"') or (a.accountID = nm.accID and nm.cusID = c.customerID and c.customerName = '"+cusName+"')");
            while(rs.next()){
                acc.add(rs.getString(1));
            }
            con.close();
            return acc;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void moneyChangedDetail(String accName, long changedAmount, String cusName,SavedQuery queries){
        int accID = AccountModel.findAccountID(accName);
        int cusID = CustomerModel.findCustomerID(cusName);
        Date date = new Date();
        int day = date.getDate();
        int month = date.getMonth() + 1;
        int year = date.getYear() + 1900;
        try{
            Connection con = connect();
            queries.addQuery("insert into ChangeMoney values(" + cusID + "," + accID + "," + changedAmount + "," + day + "," + month + "," + year + ")");
            con.createStatement().executeUpdate("insert into ChangeMoney values(" + cusID + "," + accID + "," + changedAmount + "," + day + "," + month + "," + year + ")");
//            backUpConnect().createStatement().executeUpdate("insert into ChangeMoney values(" + cusID + "," + accID + "," + changedAmount + "," + day + "," + month + "," + year + ")");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static ArrayList<String> getRelatedDate(String accName){
        ArrayList<String> result = new ArrayList<String>();
        try{
            Connection con = connect();
            int accID = AccountModel.findAccountID(accName);
            ResultSet rs = con.createStatement().executeQuery("select * from ChangeMoney where accountID = "+accID);
            while(rs.next()){
                String money = Long.toString(rs.getLong(3));
                String date = Integer.toString(rs.getInt(4));
                String month = Integer.toString(rs.getInt(5));
                String year = Integer.toString(rs.getInt(6));
                result.add(date+"-"+month+"-"+year+"             "+money+"  d");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
