/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author Mr-Tuy
 */
public class BankingServer {
    public static void main(String[] args) {
        SavedQuery queries = new SavedQuery();
        try{
            ServerSocket sock = new ServerSocket(6013);
//            boolean continuous = true;
                
            while(true){
                Socket client = sock.accept();
                DataInputStream din = new DataInputStream(client.getInputStream());
                byte messageType = din.readByte();
                switch(messageType){
                    case 1:
                        System.out.println("xD");
                        String nameAndPass = din.readUTF();
                        Scanner lineScanner = new Scanner(nameAndPass);
                        String name = lineScanner.nextLine();
                        String pass = lineScanner.nextLine();
                        String sdt = lineScanner.nextLine();
                        String cmt = lineScanner.nextLine();
                        int isExisted = CustomerModel.findCustomerID(name);
                        DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                        if(isExisted == 0){
                            dout.writeByte(1);
                            dout.flush();
                            CustomerModel.createCustomer(name, pass, sdt, cmt,queries);
                        }else{
                            dout.writeByte(2);
                            dout.flush();
                        }
                        din.close();
                        break;
                    case 2:
                        System.out.println("xDD");
                        String needToCheck = din.readUTF();
                        Scanner checkScanner = new Scanner(needToCheck);
                        String nameToCheck = checkScanner.nextLine();
                        String passToCheck = checkScanner.nextLine();
                        boolean isExist = CustomerModel.checkExistingCustomer(nameToCheck, passToCheck);
                        DataOutputStream dout2 = new DataOutputStream(client.getOutputStream());
                        if(isExist){
                            dout2.writeByte(1);
                            dout2.flush();
                        }else{
                            dout2.writeByte(2);
                            dout2.flush();
                        }
                        din.close();
                        break;
                    case 3:
                        System.out.println("xD3");
                        String newAccount = din.readUTF();
                        Scanner newAccScanner = new Scanner(newAccount);
                        String accName = newAccScanner.nextLine();
                        String mainCustomer = newAccScanner.nextLine();
                        String moneyInString = newAccScanner.nextLine();
                        long money = Long.parseLong(moneyInString);
                        
                        int isExisted3 = AccountModel.findAccountID(accName);
                        DataOutputStream dout3 = new DataOutputStream(client.getOutputStream());
                        if(isExisted3 == 0){
                            dout3.writeByte(1);
                            dout3.flush();
                            AccountModel.addAccount(accName, mainCustomer,money,queries);

                        }else{
                            dout3.writeByte(2);
                            dout3.flush();
                        }
                        
                        din.close();
                        break;
                    case 4:
                        System.out.println("xD4");
                        String cusName = din.readUTF();
                        System.out.println(cusName);
                        DataOutputStream dout4 = new DataOutputStream(client.getOutputStream());
                        ArrayList<String> cus = CustomerModel.getAllCustomer();
                        ArrayList<String> acc = MixedModel.findAccRelate(cusName);
                        for(String lol : acc){
                            System.out.println(lol);
                        }
                        System.out.println("\n\n\n");
                        
                        dout4.writeByte(100);
                        String str = "";
                        for(int i=0; i<cus.size(); ++i){
                            if(i!=(cus.size()-1)){
                                str+=(cus.get(i)+"\n");
                            }else{
                                str+=(cus.get(i));
                            }
                        }
                        dout4.writeUTF(str);
                        dout4.flush();
                        
                        
                        dout4.writeByte(101);
                        String str2 = "";
                        for(int i=0; i<acc.size(); ++i){
                            if(i!=(acc.size()-1)){
                                str2+=(acc.get(i)+"\n");
                            }else{
                                str2+=(acc.get(i));
                            }
                        }
                        System.out.println(str2);
                        dout4.writeUTF(str2);
                        dout4.flush();
                        din.close();
                        break;
                    case 5:
                        System.out.println("case5");
                        Scanner lineScanner5 = new Scanner(din.readUTF());
                        String accName5 = lineScanner5.nextLine();
                        long moneyIncreased = Long.parseLong(lineScanner5.nextLine());
                        String customer5 = lineScanner5.nextLine();
                        MixedModel.moneyChangedDetail(accName5, moneyIncreased, customer5,queries);
                        System.out.println("aaaaaaaaaaaa"+moneyIncreased);
                        AccountModel.changeMoneyAmount(moneyIncreased, accName5,queries);
                        din.close();
                        break;
                    case 6:
                        System.out.println("case 6");
                        Scanner lineScanner6 = new Scanner(din.readUTF());
                        String accName6 = lineScanner6.nextLine();
                        long moneyTaken = Long.parseLong(lineScanner6.nextLine());
                        moneyTaken*=(-1);
                        String customer6 = lineScanner6.nextLine();
//                        System.out.println(moneyTaken);
                        MixedModel.moneyChangedDetail(accName6, moneyTaken, customer6,queries);
                        AccountModel.changeMoneyAmount(moneyTaken, accName6,queries);
                        din.close();
                        break;
                    case 7:
                        String nameAndAcc = din.readUTF();
                        Scanner lineScanner7 = new Scanner(nameAndAcc);
                        String acc7 = lineScanner7.nextLine();
                        String name7 = lineScanner7.nextLine();
                        NormalCustomerModel.addNormalCustomer(acc7, name7,queries);
                        din.close();
                        break;
                    case 8:
                        System.out.println("case8");
                        DataInputStream din8 = new DataInputStream(client.getInputStream());
                        Scanner lineScanner8 = new Scanner(din8.readUTF());
                        String accName8 = lineScanner8.nextLine();
                        long money8 = AccountModel.findAccountMoney(accName8);
                        DataOutputStream dout8 = new DataOutputStream(client.getOutputStream());
                        dout8.writeUTF(Long.toString(money8));
                        din.close();
                        break;
                    case 9:
                        System.out.println("case 9");
                        DataInputStream din9 = new DataInputStream(client.getInputStream());
                        Scanner lineScanner9 = new Scanner(din9.readUTF());
                        String cusName9 = lineScanner9.nextLine();
                        int sdt9 = Integer.parseInt(lineScanner9.nextLine());
                        int cmt9 = Integer.parseInt(lineScanner9.nextLine());
                        boolean check9 = CustomerModel.checkSdtAndCmt(cusName9, sdt9, cmt9);
                        if(check9 == true){
                            DataOutputStream dout9 = new DataOutputStream(client.getOutputStream());
                            dout9.writeByte(1);
                            dout9.flush();
                        }else{
                            DataOutputStream dout9 = new DataOutputStream(client.getOutputStream());
                            dout9.writeByte(2);
                            dout9.flush();
                        }
                        break;
                    case 10:
                        System.out.println("case 10");
                        Scanner scanner10 = new Scanner(din.readUTF());
                        String accName10 = scanner10.nextLine();
                        ArrayList<String> cusRelated = AccountModel.findRelatedCustomer(accName10);
                        String s = "";
                        for(int i=0;i < cusRelated.size();++i){
                            if(i!=cusRelated.size()-1){
                                s+=cusRelated.get(i);
                                s+="\n";
                            }else{
                                s+=cusRelated.get(i);
                            }
                        }
                        DataOutputStream dout10 = new DataOutputStream(client.getOutputStream());
                        dout10.writeUTF(s);
                        dout10.flush();
                        break;
                    case 11:
                        System.out.println("case 11");
                        Scanner scanner11 = new Scanner(din.readUTF());
                        String accName11 = scanner11.nextLine();
                        String cusName11 = scanner11.nextLine();
                        DataOutputStream dout11 = new DataOutputStream(client.getOutputStream());
                        boolean check = AccountModel.checkMainCustomer(accName11, cusName11);
                        if(check == true){
                            AccountModel.deleteAccount(accName11,queries);
                            dout11.writeByte(1);
                            dout11.flush();
                        }else{
                            dout11.writeByte(2);
                            dout11.flush();
                        }
                        din.close();
                        break;
                    case 12:
                        System.out.println("case 12");
                        Scanner scanner12 = new Scanner(din.readUTF());
                        String accountName12 = scanner12.nextLine();
                        DataOutputStream dout12 = new DataOutputStream(client.getOutputStream());
                        ArrayList<Integer> date = AccountModel.getDate(accountName12);
                        ArrayList<String> dateRelated = MixedModel.getRelatedDate(accountName12);
                        String dateSent = Integer.toString((int)(date.get(0)))+"\n"+Integer.toString((int)(date.get(1)))+"\n"+Integer.toString((int)(date.get(2)))+"\n";
                        for(String dr : dateRelated){
                            dateSent+=dr;
                            dateSent+="\n";
                        }
                        System.out.println(dateSent);
                        dout12.writeUTF(dateSent);
                        dout12.flush();
                        break;
                    case 13:
                        System.out.println("case 13");
                        String string13= "";
                        ArrayList<String> savedQueries = queries.getSavedQuery();
                        for(int index=0; index < savedQueries.size();index++){
                            if(index!=savedQueries.size()){
                                string13+=savedQueries.get(index);
                                string13+="\n";
                            }else{
                                string13+=savedQueries.get(index);
                            }
                        }
                        DataOutputStream dout13 = new DataOutputStream(client.getOutputStream());
                        dout13.writeUTF(string13);
                        queries.clearQuery();
                        dout13.flush();
                }
            }

            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
