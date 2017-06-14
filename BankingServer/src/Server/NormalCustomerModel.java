/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;

/**
 *
 * @author Mr-Tuy
 */
import java.sql.*;
public class NormalCustomerModel {
//    private CustomerModel cm;
//    private AccountModel am;
    public static void addNormalCustomer(String accName, String cusName,SavedQuery queries){
        try{
            Connection con = MixedModel.connect();
            int accID = AccountModel.findAccountID(accName);
            System.out.println("accID: " + accID);
            int cusID = CustomerModel.findCustomerID(cusName);
            System.out.println("cusID: " + cusID);
            queries.addQuery("insert into NormalCustomer values('"+accID+"','"+cusID+"')");
            con.createStatement().executeUpdate("insert into NormalCustomer values('"+accID+"','"+cusID+"')");
//            MixedModel.backUpConnect().createStatement().executeUpdate("insert into NormalCustomer values('"+accID+"','"+cusID+"')");
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static boolean checkNormalCustomer(String accName,String cusName){
        try{
            int quantity = 0;
            Connection con = MixedModel.connect();
            int accID = AccountModel.findAccountID(accName);
            int cusID = CustomerModel.findCustomerID(cusName);
            ResultSet rs = con.createStatement().executeQuery("select * from NormalCustomer where accID = "+accID+" and cusID = "+cusID);
            while(rs.next()){
                quantity++;
            }
            if(quantity==0){
                con.close();
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
