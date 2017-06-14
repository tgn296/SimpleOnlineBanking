/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;

import static bankingserver.MixedModel.connect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Mr-Tuy
 */
public class CustomerModel {
    public static void createCustomer(String name,String pass,String sdt,String cmt, SavedQuery queries){
        int id = 0;
        int intSdt = Integer.parseInt(sdt);
        int intCmt = Integer.parseInt(cmt);
        try{
            Connection con = MixedModel.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Customer");
            while(rs.next()){
                id++;
            }
            id++;
            queries.addQuery("insert into Customer values(" + id + ",'" + name + "','"+pass+"'," + intSdt + ","+intCmt+")");
            con.createStatement().executeUpdate("insert into Customer values(" + id + ",'" + name + "','"+pass+"'," + intSdt + ","+intCmt+")");
//            MixedModel.backUpConnect().createStatement().executeUpdate("insert into Customer values(" + id + ",'" + name + "','"+pass+"'," + intSdt + ","+intCmt+")");
//            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static boolean checkExistingCustomer(String name,String pass){
        try{
            int quantity = 0;
            Connection con = MixedModel.connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Customer where customerName = '" + name + "' and cusPassword = '" + pass + "'");
            while(rs.next()){
                quantity++;
            }
            if(quantity == 0){
                con.close();
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
    public static int findCustomerID(String cusName){
        try{
            Connection con = MixedModel.connect();
            ResultSet rs = con.createStatement().executeQuery("select customerID from Customer where customerName = '"+cusName+"'");
            while(rs.next()){
                return rs.getInt(1);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static ArrayList<String> getAllCustomer(){
        ArrayList<String> cusList = new ArrayList<String>();
        try{
            Connection con = MixedModel.connect();
            ResultSet rs = con.createStatement().executeQuery("select customerName from Customer");
            while(rs.next()){
                cusList.add(rs.getString(1));
            }
            con.close();
            return cusList;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static boolean checkSdtAndCmt(String cusName, int sdt, int cmt){
        int quantity = 0;
        try{
            Connection con = MixedModel.connect();
            ResultSet rs = con.createStatement().executeQuery("select * from Customer where customername = '" + cusName + "' and phoneNumber = " + sdt + " and soCMT = "+cmt);
            while(rs.next()){
                quantity++;
            }
            if(quantity > 0){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
