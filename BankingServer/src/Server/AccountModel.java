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
public class AccountModel {
    
    public static void addAccount(String accName, String mainCustomer,long moneyAmount,SavedQuery queries){
        Date date = new Date();
        int day = date.getDate();
        int month = date.getMonth() + 1;
        int year = date.getYear() + 1900;
        try{
            int id = 0;
            Connection con = MixedModel.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select accountID from Account where accountID = (select max(accountID) from Account)");
            while(rs.next()){
                id = rs.getInt(1);
            }
            id++;
            System.out.println(id);
            int cusID = CustomerModel.findCustomerID(mainCustomer);
            queries.addQuery("insert into Account values("+id+",'"+accName+"',"+cusID+","+moneyAmount+"," + day + "," + month + "," + year +")");
            con.createStatement().executeUpdate("insert into Account values("+id+",'"+accName+"',"+cusID+","+moneyAmount+"," + day + "," + month + "," + year +")");
//            MixedModel.backUpConnect().createStatement().executeUpdate("insert into Account values("+id+",'"+accName+"',"+cusID+","+moneyAmount+"," + day + "," + month + "," + year +")");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static boolean checkMainCustomer(String accName, String cusName){
        try{
            int quantity=0;
            Connection con = MixedModel.connect();
            ResultSet rs = con.createStatement().executeQuery("select * from Account as a,Customer as c where a.accountName = '"+accName+"' and a.mainCustomerID = c.customerID and c.customerName = '"+cusName+"'");
            while(rs.next()){
                quantity++;
            }
            if(quantity==0){
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
    
    public static int findAccountID(String accName){
        try{
            Connection con = MixedModel.connect();
            ResultSet rs = con.createStatement().executeQuery("select accountID from Account where accountName = '"+accName+"'");
            while(rs.next()){
                return rs.getInt(1);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    
    public static long findAccountMoney(String accName){
        try{
            Connection con = MixedModel.connect();
            ResultSet rs = con.createStatement().executeQuery("select moneyLeft from Account where accountName = '"+accName+"'");
            while(rs.next()){
                return rs.getLong(1);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return 0;
    }
    public static void changeMoneyAmount(long moneyChanged,String accName,SavedQuery queries){
        try{
            Connection con = MixedModel.connect();
            long moneyCurrent = findAccountMoney(accName);
            System.out.println("CMN0 "+ moneyCurrent);
            moneyCurrent+=moneyChanged;
            System.out.println("CMN "+ moneyChanged);
            System.out.println("CMN2 "+ moneyCurrent);
            queries.addQuery("update account set moneyLeft = "+moneyCurrent+" where accountName = '"+accName+"'");
            con.createStatement().executeUpdate("update account set moneyLeft = "+moneyCurrent+" where accountName = '"+accName+"'");
//            MixedModel.backUpConnect().createStatement().executeUpdate("update account set moneyLeft = "+moneyCurrent+" where accountName = '"+accName+"'");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static ArrayList<String> findRelatedCustomer(String accName){
        ArrayList<String> cus = new ArrayList<String>();
        try{
            Connection con = MixedModel.connect();
            ResultSet rs = con.createStatement().executeQuery("select distinct customername from customer,account,normalcustomer where (account.accountname = '" + accName + "' and account.mainCustomerID = customer.customerid) or (account.accountID = normalCustomer.accid and normalCustomer.cusid = customer.customerID and accountname = '" + accName + "')");
            while(rs.next()){
                cus.add(rs.getString(1));
            }
            return cus;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static void deleteAccount(String accName,SavedQuery queries){
        try{
            Connection con = MixedModel.connect();
            int accID = findAccountID(accName);
            queries.addQuery("delete from changemoney where accountID = "+accID);
            queries.addQuery("delete from normalcustomer where accid = " + accID);
            queries.addQuery("delete from account where accountid = " +accID);
            con.createStatement().executeUpdate("delete from changemoney where accountID = "+accID);
            con.createStatement().executeUpdate("delete from normalcustomer where accid = " + accID);
            con.createStatement().executeUpdate("delete from account where accountid = " +accID);
//            MixedModel.backUpConnect().createStatement().executeUpdate("delete from normalcustomer where accid = " + accID);
//            MixedModel.backUpConnect().createStatement().executeUpdate("delete from account where accountid = " +accID);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static ArrayList<Integer> getDate(String accName){
        ArrayList<Integer> date = new ArrayList<Integer>();
        try{
            Connection con = MixedModel.connect();
            ResultSet rs = con.createStatement().executeQuery("select * from account where accountName = '" + accName + "'");
            while(rs.next()){
                date.add(rs.getInt(5));
                date.add(rs.getInt(6));
                date.add(rs.getInt(7));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return date;
    }
}
