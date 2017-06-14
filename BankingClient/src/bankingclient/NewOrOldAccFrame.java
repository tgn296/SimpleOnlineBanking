/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author this
 */

public class NewOrOldAccFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewOrOldAccFrame
     */
    private DNFrame dn;
    private ChonThaoTacFrame tt;
    private TaoTaiKhoanFrame ttk;
    private String customerName;
    public NewOrOldAccFrame(DNFrame vdn) {
        initComponents();
        tt = new ChonThaoTacFrame(this);
        ttk = new TaoTaiKhoanFrame(this);
        this.customerName = null;
        this.dn = vdn;
        this.setVisible(false);
        jBt_new.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ttk.resetUI();
                ttk.setVisible(true);
                NewOrOldAccFrame.this.setVisible(false);
                ttk.setMainCustomer(customerName);
            }
        });
        jBt_old.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    Socket client = new Socket("113.22.46.207",6013);
                    DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                    dout.writeByte(4);
                    dout.writeUTF(customerName);
                    dout.flush();
                    DataInputStream din = new DataInputStream(client.getInputStream());
                    ArrayList<String> acc = new ArrayList<String>();
                    ArrayList<String> cus = new ArrayList<String>();
                    for(int i=0; i<2; ++i){
                        byte messageType = din.readByte();
                        switch(messageType){
                            case 100:
//                                System.out.println("case100");
                                Scanner lineScanner = new Scanner(din.readUTF());
                                while(lineScanner.hasNextLine()){
                                    String line = lineScanner.nextLine();
//                                    System.out.println(line);
                                    cus.add(line);
                                }
                                tt.setCusList(cus);
                                tt.getJCB2().setModel(new DefaultComboBoxModel(cus.toArray()));
                                break;
                            case 101:
//                                System.out.println("case101");
                                Scanner lineScanner2 = new Scanner(din.readUTF());
                                while(lineScanner2.hasNextLine()){
                                    String line2 = lineScanner2.nextLine();
                                    acc.add(line2);
//                                    System.out.println(line2);
                                }
                                tt.setAccList(acc);
                                tt.getJCB1().setModel(new DefaultComboBoxModel(acc.toArray()));
                                break;
                        }
                    }
                    dout.close();
                    din.close();
                    client.close();
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                tt.resetUI();
                tt.setVisible(true);
                NewOrOldAccFrame.this.setVisible(false);
                
            }
        });
        jBt_ql.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dn.resetUI();
                dn.setVisible(true);
                NewOrOldAccFrame.this.setVisible(false);
            }
        });
    }

    public void setMainCustomer(String main){
        this.customerName = main;
    }
    
    public String getCustomer(){
        return this.customerName;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBt_new = new javax.swing.JButton();
        jBt_old = new javax.swing.JButton();
        jBt_ql = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jBt_new.setText("Tạo Tài Khoản Mới");

        jBt_old.setText("Tài Khoản Đã Có");

        jBt_ql.setText("Quay Lại");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 0));
        jLabel1.setText("Quản Lý Tài Khoản");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jBt_new, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(jBt_old, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(jBt_ql, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBt_new)
                    .addComponent(jBt_old))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jBt_ql)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBt_new;
    private javax.swing.JButton jBt_old;
    private javax.swing.JButton jBt_ql;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}