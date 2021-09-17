/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.NhanVienDAO;
import dao.SQLServerProvider;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import pojo.NhanVien;

/**
 *
 * @author Nhan
 */
public class FindNV extends javax.swing.JDialog {

    Statement stament;
    ResultSet rs;
    SQLServerProvider provider;
    DefaultTableModel model;
    CallableStatement call;
    Connection connection;
    
    ArrayList<NhanVien> dsNV;
    /**
     * Creates new form FindNV
     */
    public FindNV(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
         this.setSize(600,450);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Tìm kiếm Nhân Viên");
        
        model = new DefaultTableModel();
        model.addColumn("Mã Nhân Viên");
        model.addColumn("Họ Tên Nhân Viên");
        model.addColumn("Ngày Sinh");
        model.addColumn("Giới Tính");
        model.addColumn("Địa Chỉ");
        model.addColumn("Ca");
        model.setRowCount(0);
        JTableNV.setModel(model);
        ketnoiCSDL();
        loadNV();
    }

    public void ketnoiCSDL(){
        String server = "DESKTOP-8O63SGA\\SQLEXPRESS";
        String user ="sa";
        String password = "sa123";
        String databasename = "DEAN_JAVA";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://"+server
                    + ":1433;databaseName="+databasename
                    +";user="+user
                    +";password="+password;
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    public void loadNV()
    {
        DefaultTableModel dtm = new DefaultTableModel();
        dsNV = NhanVienDAO.layDsNhanVien();
        dtm.setRowCount(0);
        JTableNV.setModel(dtm);
        for(NhanVien nv : dsNV)
        {
            String mnv = nv.getMaNV();
            String tenv = nv.getHotenNV();
            String ngaysinh = nv.getNgaySinh().toString();
            String diachi = nv.getDiaChi();
            String gioitinh = nv.getGioiTinh();
            String ca = ""+nv.getCa();
            String []nhanvien = {mnv,tenv,ngaysinh,gioitinh,diachi,ca};
            model.addRow(nhanvien);
        }
        JTableNV.setModel(model);
        
    }
    /**
     * 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtInfo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTableNV = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nhập Điều Kiện:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TÌM NHÂN VIÊN");

        jButton1.setText("Tìm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 255)), "Danh Sách Nhân Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(255, 0, 51))); // NOI18N
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JTableNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(JTableNV);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:  
        String info = txtInfo.getText();
       
       
            try {
                if(info.length()==0)
                  {
                       model.setRowCount(0);
                        JTableNV.setModel(model);
                            loadNV();
                 }
                else
                {
                CallableStatement call;
                call = connection.prepareCall("{call FIND_NV(?)}");
                call.setString(1, txtInfo.getText());
                rs=call.executeQuery();
                model.setRowCount(0);
                JTableNV.setModel(model);
                while(rs.next())
                {
                String manv = rs.getString("MANV");
                String tenv= rs.getString("HOTENNV");
                String gt = rs.getString("GIOITINH");
                String ngs = rs.getString("NGAYSINH");
                String dc = rs.getString("DIACHI");
                String ca = rs.getString("CA");
                String []nv = {manv,tenv,ngs,gt,dc,ca};
                model.addRow(nv);
                }
                JTableNV.setModel(model);

            } 
            }catch (Exception e) {
                System.out.println(e);
            }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FindNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FindNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FindNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FindNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FindNV dialog = new FindNV(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTableNV;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtInfo;
    // End of variables declaration//GEN-END:variables
}
