/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.BanDAO;
import dao.DatHangDTDAO;
import dao.DatHangTUDAO;
import dao.DiemTamDAO;
import dao.HoaDonDAO;
import dao.KhoDAO;
import dao.NhanVienDAO;
import dao.SQLServerProvider;
import dao.ThucUongDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pojo.Ban;
import pojo.DatHangDT;
import pojo.DatHangTU;
import pojo.DiemTam;
import pojo.HoaDon;
import pojo.Kho;
import pojo.NhanVien;
import pojo.ThucUong;

/**
 *
 * @author Nhan
 */
public class QuanLy_CaPhe extends javax.swing.JFrame {

    /**
     * Creates new form QuanLy_CaPhe
     */
    Statement stament;
    ResultSet rs;
    SQLServerProvider provider;
    DefaultTableModel model;
    DefaultComboBoxModel<String> dcbm;
    Connection connection;
    CallableStatement cs;
    
    ArrayList<NhanVien> dsNV;
    ArrayList<ThucUong> dsTU;
    ArrayList<DiemTam> dsDT;
    ArrayList<HoaDon> dsHD;
    ArrayList<Kho> dsNL;
    ArrayList<Ban> dsB;
    ArrayList<DatHangTU> dsdhtu;
    ArrayList<DatHangDT> dsdhdt;
    
    public static String maDH;
    public static String tenDH;
    public static int soLuong;
    public static String maHoaDon;
    public static int tienBanHD;
    

    public  boolean login = false;
    public  String loaitaikhoan="admin";

    public  void setLoaitaikhoan(String loaitaikhoan) {
        this.loaitaikhoan = loaitaikhoan;
    }

    public boolean isLogin() {
        return login;
    }

    public String getLoaitaikhoan() {
        return loaitaikhoan;
    }

    
    
    public  void setLogin(boolean login) {
        this.login = login;
    }
    
    public QuanLy_CaPhe() {
        initComponents();
        this.setTitle("Quản Lý Cà Phê");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        
        model= new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Tên thức uống");
        model.addColumn("Số lượng");
        model.addColumn("Tình trạng");
        model.addColumn("Đơn giá");
        tbTU.setModel(model);
        loadTU();
        // load điểm tâm
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Tên món");
        model.addColumn("Số lượng");
        model.addColumn("Tình trạng");
        model.addColumn("Đơn giá");
        tbDT.setModel(model);
        loadDT();
        //load kho
        model = new DefaultTableModel();
        model.addColumn("Mã nguyên liệu");
        model.addColumn("Tên nguyên liệu");
        model.addColumn("Số lượng");
        model.addColumn("Trạng thái");
        model.addColumn("Thành tiền");
        tbKhoNguyenLieu.setModel(model);
        loadKho();
        
        //loadDH
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Tên");
        model.addColumn("Số lượng");
        model.addColumn("Đơn giá");
        tbDH.setModel(model);
        loadDHDT();
        dcbm = new DefaultComboBoxModel<>();
        dcbm.addElement("Điểm Tâm");
        dcbm.addElement("Thức Uống");
        cbTUDT.setModel(dcbm);
        ketnoiCSDL();
        
       // load danh sách bàn
        model = new DefaultTableModel();
        model.addColumn("Mã Bàn");
        model.addColumn("Số lượng khách");
        JTableBan.setModel(model);
        loadBan();
        // Load Danh Sách Nhân Viên
        model = new DefaultTableModel();
        model.addColumn("Mã Nhân Viên");
        model.addColumn("Họ Tên Nhân Viên");
        model.addColumn("Ngày Sinh");
        model.addColumn("Giới Tính");
        model.addColumn("Địa Chỉ");
        model.addColumn("Ca");
        model.setRowCount(0);
        JTableNV.setModel(model);
        loadNV();
        
        //load Hóa đơn
        model = new DefaultTableModel();
        model.addColumn("Mã hóa đơn");
        model.addColumn("Mã khách hàng");
        model.addColumn("Mã bàn");
        model.addColumn("Ngày lập");
        model.addColumn("Tiền bán");
        model.addColumn("Giảm giá");
        model.addColumn("Thành tiền");
        model.addColumn("Mã nhân viên");
        tbHoaDon.setModel(model);
        loadHD();
        
    }
    // Phân Quyền
    public void Run()
    {
        if(this.loaitaikhoan.equalsIgnoreCase("nhanvien"))
        {
            TabbedPanel.setEnabledAt(0, false);
            TabbedPanel.setEnabledAt(1, false);
            TabbedPanel.setEnabledAt(2,false);
            TabbedPanel.setEnabledAt(3,true);
            TabbedPanel.setEnabledAt(4,true);
            TabbedPanel.setEnabledAt(5, false);
        
        }
    }
    /// Load DỮ Liệu
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
    public void loadTU()
    {
        DefaultTableModel dtm = new DefaultTableModel();
        dsTU = ThucUongDAO.layDsTU();
        dtm.setRowCount(0);
        tbTU.setModel(dtm);
        for(ThucUong tu : dsTU)
        {
            String mtu = tu.getMaTU();
            String tentu= tu.getTenTU();
            int soluong = tu.getSoLuong();
            int dongia = tu.getDonGia();
            String tinhtrang = tu.getTinhTrang();
            String []thucuong ={mtu,tentu,""+soluong,tinhtrang,""+dongia};
            model.addRow(thucuong);
        }
        tbTU.setModel(model);
    }
    public void loadDT()
    {
        DefaultTableModel dtm = new DefaultTableModel();
        dsDT = DiemTamDAO.layDsDiemTam();
        dtm.setRowCount(0);
        tbDT.setModel(dtm);
        for(DiemTam dt : dsDT)
        {
            String mdt = dt.getMaDT();
            String tendt= dt.getTenDT();
            int soluong = dt.getSoLuong();
            int dongia = dt.getDonGia();
            String tinhtrang = dt.getTinhTrang();
            String []diemtam ={mdt,tendt,""+soluong,tinhtrang,""+dongia};
            model.addRow(diemtam);
        }
        tbDT.setModel(model);
    }
    public void loadKho()
    {
        DefaultTableModel dtm = new DefaultTableModel();
        dsNL = KhoDAO.layDsKho();
        dtm.setRowCount(0);
        tbKhoNguyenLieu.setModel(dtm);
        for(Kho k : dsNL)
        {
            String maNL = k.getMaNL();
            String tenNL = k.getTenNL();
            String donViTinh = k.getDvt();
            int soluong = k.getSoLuong();
            int dongia = k.getGia();
            Vector<Object> vec = new Vector<Object>();
            vec.add(maNL);
            vec.add(tenNL);
            vec.add(donViTinh);
            vec.add(soluong);
            vec.add(dongia);
            model.addRow(vec);
        }
        tbKhoNguyenLieu.setModel(model);
    }
    public void loadDHTU()
    {
        DefaultTableModel dtm = (DefaultTableModel) tbDH.getModel();        
        dsdhtu = DatHangTUDAO.layDsTU();
        dtm.setRowCount(0);
        tbDH.setModel(dtm);
        for(DatHangTU tu : dsdhtu)
        {
            Vector<Object> vec = new Vector<Object>();
            vec.add(tu.getMaTU());
            vec.add(tu.getTenTU());
            vec.add(tu.getSoLuong());
            vec.add(tu.getDonGia());
            dtm.addRow(vec);
        }
        tbDH.setModel(dtm);
    }
    public void loadDHDT()
    {
        DefaultTableModel dtm = (DefaultTableModel) tbDH.getModel();       
        dsdhdt = DatHangDTDAO.layDsDiemTam();
        dtm.setRowCount(0);
        tbDH.setModel(dtm);
        for(DatHangDT dt : dsdhdt)
        {
            Vector<Object> vec = new Vector<Object>();
            vec.add(dt.getMaDT());
            vec.add(dt.getTenDT());
            vec.add(dt.getSoLuong());
            vec.add(dt.getDonGia());
            dtm.addRow(vec);
        }
        tbDH.setModel(dtm);
    }
    public void loadBan()
    {
        try {
            dsB = BanDAO.laydsBan();
            model.setRowCount(0);
            JTableBan.setModel(model);
            for(Ban b : dsB)
            {
                String mab = b.getMaBan();
                String slK =""+ b.getSlKhach();
                String []ban = {mab,slK};
                model.addRow(ban);
            }
            JTableBan.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void loadHD()
    {
        DefaultTableModel dtm = (DefaultTableModel) tbHoaDon.getModel();
        dtm.setRowCount(0);
        tbHoaDon.setModel(dtm);
        dsHD = HoaDonDAO.layDsHoaDon();
        for(HoaDon hd : dsHD)
        {
            Vector<Object> vec = new Vector<Object>();
            vec.add(hd.getMaHD());
            vec.add(hd.getMaKH());
            vec.add(hd.getMaBan());
            vec.add(hd.getNgayLap());
            vec.add(hd.getTienBan());
            vec.add(hd.getGiamGia());
            vec.add(hd.getThanhTien());
            vec.add(hd.getMaNV());
            dtm.addRow(vec);
        }
        tbHoaDon.setModel(dtm);
    }
    public void ketnoiCSDL(){
        String server = "LAPTOP-1LO8U7LN\\SQLEXPRESS";
        String user ="sa";
        String password = "nguyen114814";
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabbedPanel = new javax.swing.JTabbedPane();
        pnDrinks = new javax.swing.JPanel();
        pnTU = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbTU = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIDd = new javax.swing.JTextField();
        txtNamed = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSLd = new javax.swing.JTextField();
        txtDGd = new javax.swing.JTextField();
        lbTTd = new javax.swing.JLabel();
        btnAddD = new javax.swing.JButton();
        btnDeld = new javax.swing.JButton();
        btnUpdD = new javax.swing.JButton();
        btnFindD = new javax.swing.JButton();
        pnDT = new javax.swing.JPanel();
        pnDoAn = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbDT = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtIDT = new javax.swing.JTextField();
        txtNameDT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSLDT = new javax.swing.JTextField();
        txtDGDT = new javax.swing.JTextField();
        lbDT = new javax.swing.JLabel();
        btnAddDT = new javax.swing.JButton();
        btnDelDT = new javax.swing.JButton();
        btnUpdDT = new javax.swing.JButton();
        btnFindDT = new javax.swing.JButton();
        pnKhoNguyenLieu = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbKhoNguyenLieu = new javax.swing.JTable();
        btnNhapKho = new javax.swing.JButton();
        btnXuatKho = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        tfMaNL = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tfTenNL = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfDVT = new javax.swing.JTextField();
        tfSoLuong = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        pnBan = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        JTableBan = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        pnDatHang = new javax.swing.JPanel();
        btnTinhTien = new javax.swing.JButton();
        btnLamMoiDH = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        lbThanhTien = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbDH = new javax.swing.JTable();
        btnXoaDH = new javax.swing.JButton();
        cbTUDT = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbThongTin = new javax.swing.JTable();
        pnNhanVien = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        JTableNV = new javax.swing.JTable();
        addNV = new javax.swing.JButton();
        delNV = new javax.swing.JButton();
        updateNV = new javax.swing.JButton();
        findNV = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtMNV = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtTNV = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtGT = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtNS = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDC = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtCa = new javax.swing.JTextField();
        pnHoaDon = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        tfMaHD = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        tfMaBan = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        tfMaKH = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        tfNgayLap = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        tfMaNhanVien = new javax.swing.JTextField();
        btnThemHD = new javax.swing.JButton();
        btnXoaHD = new javax.swing.JButton();
        btnSuaHD = new javax.swing.JButton();
        btnCapNhatHD = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mNu = new javax.swing.JMenu();
        Menu = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbTU.setModel(new javax.swing.table.DefaultTableModel(
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
        tbTU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTUMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbTUMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbTU);

        javax.swing.GroupLayout pnTULayout = new javax.swing.GroupLayout(pnTU);
        pnTU.setLayout(pnTULayout);
        pnTULayout.setHorizontalGroup(
            pnTULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        pnTULayout.setVerticalGroup(
            pnTULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("ID:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Tên :");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Số lượng:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Đơn giá:");

        lbTTd.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        btnAddD.setText("Thêm");
        btnAddD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDActionPerformed(evt);
            }
        });

        btnDeld.setText("Xóa");
        btnDeld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeldActionPerformed(evt);
            }
        });

        btnUpdD.setText("Sửa");
        btnUpdD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdDActionPerformed(evt);
            }
        });

        btnFindD.setText("Tìm Kiếm");
        btnFindD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnDrinksLayout = new javax.swing.GroupLayout(pnDrinks);
        pnDrinks.setLayout(pnDrinksLayout);
        pnDrinksLayout.setHorizontalGroup(
            pnDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnTU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnDrinksLayout.createSequentialGroup()
                .addGroup(pnDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDrinksLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(lbTTd, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnDrinksLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnDrinksLayout.createSequentialGroup()
                                .addGroup(pnDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIDd)
                                    .addComponent(txtNamed, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSLd)
                                    .addComponent(txtDGd, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
                            .addGroup(pnDrinksLayout.createSequentialGroup()
                                .addComponent(btnAddD, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeld, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdD, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnFindD)))))
                .addContainerGap(242, Short.MAX_VALUE))
        );
        pnDrinksLayout.setVerticalGroup(
            pnDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDrinksLayout.createSequentialGroup()
                .addComponent(pnTU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIDd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSLd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNamed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDGd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbTTd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddD)
                    .addComponent(btnDeld)
                    .addComponent(btnUpdD)
                    .addComponent(btnFindD))
                .addGap(0, 46, Short.MAX_VALUE))
        );

        TabbedPanel.addTab("Đồ Uống", pnDrinks);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbDT.setModel(new javax.swing.table.DefaultTableModel(
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
        tbDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDTMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbDTMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tbDT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("ID:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Tên :");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Số lượng:");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Đơn giá:");

        lbDT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        btnAddDT.setText("Thêm");
        btnAddDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDTActionPerformed(evt);
            }
        });

        btnDelDT.setText("Xóa");
        btnDelDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelDTActionPerformed(evt);
            }
        });

        btnUpdDT.setText("Sửa");
        btnUpdDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdDTActionPerformed(evt);
            }
        });

        btnFindDT.setText("Tìm Kiếm");
        btnFindDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindDTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnDoAnLayout = new javax.swing.GroupLayout(pnDoAn);
        pnDoAn.setLayout(pnDoAnLayout);
        pnDoAnLayout.setHorizontalGroup(
            pnDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnDoAnLayout.createSequentialGroup()
                .addGroup(pnDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDoAnLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(lbDT, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnDoAnLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnDoAnLayout.createSequentialGroup()
                                .addGroup(pnDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIDT)
                                    .addComponent(txtNameDT, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSLDT)
                                    .addComponent(txtDGDT, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
                            .addGroup(pnDoAnLayout.createSequentialGroup()
                                .addComponent(btnAddDT, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelDT, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdDT, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnFindDT)))))
                .addContainerGap(242, Short.MAX_VALUE))
        );
        pnDoAnLayout.setVerticalGroup(
            pnDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDoAnLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtIDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSLDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNameDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDGDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbDT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnDoAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddDT)
                    .addComponent(btnDelDT)
                    .addComponent(btnUpdDT)
                    .addComponent(btnFindDT))
                .addGap(0, 59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnDTLayout = new javax.swing.GroupLayout(pnDT);
        pnDT.setLayout(pnDTLayout);
        pnDTLayout.setHorizontalGroup(
            pnDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnDoAn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnDTLayout.setVerticalGroup(
            pnDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDTLayout.createSequentialGroup()
                .addComponent(pnDoAn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TabbedPanel.addTab("Điểm Tâm", pnDT);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbKhoNguyenLieu.setModel(new javax.swing.table.DefaultTableModel(
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
        tbKhoNguyenLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKhoNguyenLieuMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbKhoNguyenLieuMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbKhoNguyenLieu);

        btnNhapKho.setText("Nhập kho");
        btnNhapKho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapKhoActionPerformed(evt);
            }
        });

        btnXuatKho.setText("Xuất kho");
        btnXuatKho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatKhoActionPerformed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("ID:");

        tfMaNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfMaNLActionPerformed(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Tên:");

        tfTenNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTenNLActionPerformed(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Đơn vị tính:");

        tfDVT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfDVTActionPerformed(evt);
            }
        });

        tfSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSoLuongActionPerformed(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Số lượng:");

        javax.swing.GroupLayout pnKhoNguyenLieuLayout = new javax.swing.GroupLayout(pnKhoNguyenLieu);
        pnKhoNguyenLieu.setLayout(pnKhoNguyenLieuLayout);
        pnKhoNguyenLieuLayout.setHorizontalGroup(
            pnKhoNguyenLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnKhoNguyenLieuLayout.createSequentialGroup()
                .addGroup(pnKhoNguyenLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnKhoNguyenLieuLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(pnKhoNguyenLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(pnKhoNguyenLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfTenNL, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(tfMaNL))
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnKhoNguyenLieuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnNhapKho, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addGroup(pnKhoNguyenLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnKhoNguyenLieuLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(btnXuatKho, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnKhoNguyenLieuLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnKhoNguyenLieuLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tfDVT, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(267, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
        );
        pnKhoNguyenLieuLayout.setVerticalGroup(
            pnKhoNguyenLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnKhoNguyenLieuLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addGroup(pnKhoNguyenLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11)
                    .addComponent(tfDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfMaNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnKhoNguyenLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tfTenNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(tfSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(pnKhoNguyenLieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNhapKho)
                    .addComponent(btnXuatKho))
                .addGap(22, 22, 22))
        );

        TabbedPanel.addTab("Kho Nguyên Liệu", pnKhoNguyenLieu);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 51, 255)), "Danh Sách Bàn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JTableBan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(JTableBan);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );

        jButton2.setText("Đặt Bàn");

        jButton3.setText("Hủy  Bàn");

        javax.swing.GroupLayout pnBanLayout = new javax.swing.GroupLayout(pnBan);
        pnBan.setLayout(pnBanLayout);
        pnBanLayout.setHorizontalGroup(
            pnBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnBanLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
        );
        pnBanLayout.setVerticalGroup(
            pnBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnBanLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        TabbedPanel.addTab("Bàn", pnBan);

        btnTinhTien.setText("Tính tiền");
        btnTinhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTinhTienActionPerformed(evt);
            }
        });

        btnLamMoiDH.setText("Làm mới");
        btnLamMoiDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiDHActionPerformed(evt);
            }
        });

        jLabel13.setText("Thành tiền:");

        lbThanhTien.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbThanhTien.setForeground(new java.awt.Color(255, 51, 51));
        lbThanhTien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbThanhTien.setText("0");

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbDH.setModel(new javax.swing.table.DefaultTableModel(
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
        tbDH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDHMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbDH);

        btnXoaDH.setText("Xóa");
        btnXoaDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDHActionPerformed(evt);
            }
        });

        cbTUDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTUDTActionPerformed(evt);
            }
        });

        jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane7.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbThongTin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã", "Tên", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane7.setViewportView(tbThongTin);

        javax.swing.GroupLayout pnDatHangLayout = new javax.swing.GroupLayout(pnDatHang);
        pnDatHang.setLayout(pnDatHangLayout);
        pnDatHangLayout.setHorizontalGroup(
            pnDatHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDatHangLayout.createSequentialGroup()
                .addGap(390, 390, 390)
                .addComponent(btnTinhTien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addComponent(btnXoaDH, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLamMoiDH)
                .addGap(27, 27, 27))
            .addGroup(pnDatHangLayout.createSequentialGroup()
                .addGap(424, 424, 424)
                .addComponent(jLabel13)
                .addGap(51, 51, 51)
                .addComponent(lbThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnDatHangLayout.createSequentialGroup()
                .addGroup(pnDatHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbTUDT, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        pnDatHangLayout.setVerticalGroup(
            pnDatHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDatHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnDatHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnDatHangLayout.createSequentialGroup()
                        .addComponent(cbTUDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addGroup(pnDatHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTinhTien)
                    .addComponent(btnLamMoiDH)
                    .addComponent(btnXoaDH))
                .addGap(29, 29, 29)
                .addGroup(pnDatHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lbThanhTien))
                .addGap(39, 39, 39))
        );

        TabbedPanel.addTab("Đặt Hàng", pnDatHang);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(51, 204, 255)), "Nhân Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(255, 0, 0))); // NOI18N
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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
        JTableNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTableNVMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(JTableNV);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );

        addNV.setText("Thêm Nhân Viên");
        addNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNVActionPerformed(evt);
            }
        });

        delNV.setText("Xóa Nhân Viên");
        delNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delNVActionPerformed(evt);
            }
        });

        updateNV.setText("Sửa");
        updateNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateNVActionPerformed(evt);
            }
        });

        findNV.setText("Tìm");

        jLabel14.setText("Mã Nhân Viên");

        jLabel15.setText("Tên Nhân Viên");

        jLabel16.setText("Giới Tính");

        jLabel17.setText("Ngày Sinh");

        jLabel18.setText("Địa chỉ");

        jLabel19.setText("Ca");

        javax.swing.GroupLayout pnNhanVienLayout = new javax.swing.GroupLayout(pnNhanVien);
        pnNhanVien.setLayout(pnNhanVienLayout);
        pnNhanVienLayout.setHorizontalGroup(
            pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnNhanVienLayout.createSequentialGroup()
                .addGroup(pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnNhanVienLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addNV, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(delNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(updateNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(findNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnNhanVienLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnNhanVienLayout.createSequentialGroup()
                                .addGroup(pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnNhanVienLayout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtGT))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnNhanVienLayout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtMNV, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(52, 52, 52)
                                .addGroup(pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTNV)
                                    .addComponent(txtNS)))
                            .addGroup(pnNhanVienLayout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDC)))))
                .addContainerGap())
            .addGroup(pnNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCa, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnNhanVienLayout.setVerticalGroup(
            pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnNhanVienLayout.createSequentialGroup()
                .addGroup(pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnNhanVienLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(findNV)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtMNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtGT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtNS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );

        TabbedPanel.addTab("Nhân Viên", pnNhanVien);

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(51, 51, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("HÓA ĐƠN");

        jScrollPane15.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane15.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
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
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tbHoaDon);

        jLabel41.setText("Mã HD:");

        jLabel42.setText("Mã Bàn:");

        jLabel43.setText("Mã KH:");

        jLabel44.setText("Ngày lập:");

        jLabel45.setText("Mã nhân viên:");

        btnThemHD.setText("Thêm");
        btnThemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHDActionPerformed(evt);
            }
        });

        btnXoaHD.setText("Xóa");
        btnXoaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHDActionPerformed(evt);
            }
        });

        btnSuaHD.setText("Sửa");
        btnSuaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaHDActionPerformed(evt);
            }
        });

        btnCapNhatHD.setText("Cập nhật ");
        btnCapNhatHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnHoaDonLayout = new javax.swing.GroupLayout(pnHoaDon);
        pnHoaDon.setLayout(pnHoaDonLayout);
        pnHoaDonLayout.setHorizontalGroup(
            pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15)
            .addGroup(pnHoaDonLayout.createSequentialGroup()
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnHoaDonLayout.createSequentialGroup()
                        .addGap(318, 318, 318)
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnHoaDonLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel45)
                            .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel42)
                                .addComponent(jLabel41)))
                        .addGap(18, 18, 18)
                        .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(pnHoaDonLayout.createSequentialGroup()
                                    .addComponent(tfMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel43)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                    .addComponent(tfMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnHoaDonLayout.createSequentialGroup()
                                    .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tfMaNhanVien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                        .addComponent(tfMaBan, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel44)
                                    .addGap(18, 18, 18)
                                    .addComponent(tfNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnHoaDonLayout.createSequentialGroup()
                                .addComponent(btnThemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnXoaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(btnSuaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(btnCapNhatHD)))))
                .addContainerGap(165, Short.MAX_VALUE))
        );
        pnHoaDonLayout.setVerticalGroup(
            pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(tfMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(tfMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(tfMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(tfNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addComponent(tfMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemHD)
                    .addComponent(btnXoaHD)
                    .addComponent(btnSuaHD)
                    .addComponent(btnCapNhatHD))
                .addContainerGap())
        );

        TabbedPanel.addTab("Hóa Đơn", pnHoaDon);

        mNu.setText("File");

        Menu.setText("Đăng Xuất");
        Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuActionPerformed(evt);
            }
        });
        mNu.add(Menu);

        jMenuItem1.setText("Thoát");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mNu.add(jMenuItem1);

        jMenuBar1.add(mNu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuActionPerformed

    private void btnFindDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindDActionPerformed
        // TODO add your handling code here:
        TimKiemThucUong dialogFind = new TimKiemThucUong(this, true);
        dialogFind.setVisible(true);
    }//GEN-LAST:event_btnFindDActionPerformed

    private void btnUpdDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdDActionPerformed
        // TODO add your handling code here:
        try {
            String maTU = txtIDd.getText();
            DefaultTableModel selectedM = (DefaultTableModel) tbTU.getModel();
            boolean like = false;
            for (int i=0;i<selectedM.getRowCount();i++)
            {
                if(maTU.equalsIgnoreCase(selectedM.getValueAt(i, 0).toString()))
                {
                    like = true;
                }
            }
            if(like == false)
            {
                JOptionPane.showMessageDialog(null, "Mã Thức uống không có trong danh sách, không thể update! Xin vui lòng thử lại","Lỗi",JOptionPane.ERROR_MESSAGE);
                return;
            }
            ThucUong upTU = new ThucUong();
            upTU.setMaTU(txtIDd.getText());
            upTU.setTenTU(txtNamed.getText());
            upTU.setSoLuong(Integer.parseInt(txtSLd.getText()));
            upTU.setDonGia(Integer.parseInt(txtDGd.getText()));
            int n = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn update?", "Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
            if(n == JOptionPane.YES_OPTION)
            {
                boolean kq = ThucUongDAO.updateTU(upTU);
                if(kq)
                    JOptionPane.showMessageDialog(null, "Update thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    JOptionPane.showMessageDialog(null, "Update thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
                }
                model.setRowCount(0);
                tbTU.setModel(model);
                loadTU();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnUpdDActionPerformed

    private void btnDeldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeldActionPerformed
        // TODO add your handling code here:
        if(tbTU.getSelectedRow() == -1)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 dòng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ThucUong delTU = new ThucUong();
        delTU.setMaTU(txtIDd.getText());
        delTU.setTenTU(txtNamed.getText());
        delTU.setSoLuong(Integer.parseInt(txtSLd.getText()));
        delTU.setDonGia(Integer.parseInt(txtDGd.getText()));
        int n = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa?", "Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
        if(n == JOptionPane.YES_OPTION)
        {
            boolean kq = ThucUongDAO.xoaTU(delTU);
            if(kq)
                JOptionPane.showMessageDialog(null, "Xóa thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            else
            {
                JOptionPane.showMessageDialog(null, "Xóa thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
            }
            model.setRowCount(0);
            tbTU.setModel(model);
            loadTU();
        }
    }//GEN-LAST:event_btnDeldActionPerformed

    private void btnAddDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDActionPerformed
        // TODO add your handling code here:
        ThucUong addTU = new ThucUong();
        addTU.setMaTU(txtIDd.getText());
        addTU.setTenTU(txtNamed.getText());
        addTU.setSoLuong(Integer.parseInt(txtSLd.getText()));
        addTU.setDonGia(Integer.parseInt(txtDGd.getText()));
        int n = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm?", "Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
        if(n == JOptionPane.YES_OPTION)
        {
            boolean kq = ThucUongDAO.themTU(addTU);
            if(kq)
                JOptionPane.showMessageDialog(null, "Thêm thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            else
            {
                JOptionPane.showMessageDialog(null, "Thêm thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
            }
            model.setRowCount(0);
            tbTU.setModel(model);
            loadTU();
        }
    }//GEN-LAST:event_btnAddDActionPerformed

    private void tbTUMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTUMouseReleased
        // TODO add your handling code here:
        if(tbTU.isEditing())
        {
            int r = tbTU.getSelectedRow();
            int c = tbTU.getSelectedColumn();
            tbTU.getCellEditor(r, c).cancelCellEditing();
        }
    }//GEN-LAST:event_tbTUMouseReleased

    private void tbTUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTUMouseClicked
        // TODO add your handling code here:

        try {
            int index = tbTU.getSelectedRow();
            DefaultTableModel selectedM = (DefaultTableModel) tbTU.getModel();
            txtIDd.setText(selectedM.getValueAt(index, 0).toString());
            txtNamed.setText(selectedM.getValueAt(index, 1).toString());
            txtSLd.setText(selectedM.getValueAt(index, 2).toString());
            txtDGd.setText(selectedM.getValueAt(index, 4).toString());
            if(selectedM.getValueAt(index,3).toString().equalsIgnoreCase("Con"))
            lbTTd.setText("MẶT HÀNG CÒN");
            else
            lbTTd.setText("MẶT HÀNG KHÔNG CÒN");
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_tbTUMouseClicked

    private void tbDTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDTMouseClicked
        // TODO add your handling code here:
        try{
            int index = tbDT.getSelectedRow();
            DefaultTableModel selectedM = (DefaultTableModel) tbDT.getModel();
            txtIDT.setText(selectedM.getValueAt(index, 0).toString());
            txtNameDT.setText(selectedM.getValueAt(index, 1).toString());
            txtSLDT.setText(selectedM.getValueAt(index, 2).toString());
            txtDGDT.setText(selectedM.getValueAt(index, 4).toString());
            if(selectedM.getValueAt(index,3).toString().equalsIgnoreCase("Con"))
            lbDT.setText("MẶT HÀNG CÒN");
            else
            lbDT.setText("MẶT HÀNG KHÔNG CÒN");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }//GEN-LAST:event_tbDTMouseClicked

    private void tbDTMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDTMouseReleased
        // TODO add your handling code here:
        if(tbDT.isEditing())
        {
            int r = tbDT.getSelectedRow();
            int c = tbDT.getSelectedColumn();
            tbDT.getCellEditor(r, c).cancelCellEditing();
        }
        
    }//GEN-LAST:event_tbDTMouseReleased

    private void tbKhoNguyenLieuMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKhoNguyenLieuMouseReleased
        // TODO add your handling code here:
        if(tbKhoNguyenLieu.isEditing())
        {
            int r = tbKhoNguyenLieu.getSelectedRow();
            int c = tbKhoNguyenLieu.getSelectedColumn();
            tbKhoNguyenLieu.getCellEditor(r, c).cancelCellEditing();
        }
    }//GEN-LAST:event_tbKhoNguyenLieuMouseReleased

    private void tfMaNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfMaNLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfMaNLActionPerformed

    private void tfTenNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTenNLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTenNLActionPerformed

    private void tfDVTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfDVTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfDVTActionPerformed

    private void tfSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSoLuongActionPerformed

    private void tbKhoNguyenLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKhoNguyenLieuMouseClicked
        // TODO add your handling code here:
        int a = tbKhoNguyenLieu.getSelectedRow();
        tfMaNL.setText(tbKhoNguyenLieu.getValueAt(a, 0).toString());
        tfTenNL.setText(tbKhoNguyenLieu.getValueAt(a, 1).toString());
        tfDVT.setText(tbKhoNguyenLieu.getValueAt(a, 2).toString());
        tfSoLuong.setText(tbKhoNguyenLieu.getValueAt(a, 3).toString());
    }//GEN-LAST:event_tbKhoNguyenLieuMouseClicked

    private void btnFindDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindDTActionPerformed
        // TODO add your handling code here:
        TimKiemDiemTam a = new TimKiemDiemTam(this, true);
        a.setVisible(true);
    }//GEN-LAST:event_btnFindDTActionPerformed

    private void btnUpdDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdDTActionPerformed
        // TODO add your handling code here:
        try {
            String maDT = txtIDT.getText();
            DefaultTableModel selectedM = (DefaultTableModel) tbDT.getModel();
            boolean like = false;
            for (int i=0;i<selectedM.getRowCount();i++)
            {
                if(maDT.equalsIgnoreCase(selectedM.getValueAt(i, 0).toString()))
                {
                    like = true;
                }
            }
            if(like == false)
            {
                JOptionPane.showMessageDialog(null, "Mã Điểm tâm không có trong danh sách, không thể update! Xin vui lòng thử lại","Lỗi",JOptionPane.ERROR_MESSAGE);
                return;
            }
            DiemTam upDT = new DiemTam();
            upDT.setMaDT(txtIDT.getText());
            upDT.setTenDT(txtNameDT.getText());
            upDT.setSoLuong(Integer.parseInt(txtSLDT.getText()));
            upDT.setDonGia(Integer.parseInt(txtDGDT.getText()));
            int n = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn update?", "Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
            if(n == JOptionPane.YES_OPTION)
            {
                boolean kq = DiemTamDAO.updateDT(upDT);
                if(kq)
                JOptionPane.showMessageDialog(null, "Update thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    JOptionPane.showMessageDialog(null, "Update thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
                }
                model.setRowCount(0);
                tbDT.setModel(model);
                loadDT();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnUpdDTActionPerformed

    private void btnDelDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelDTActionPerformed
        // TODO add your handling code here:
        if(tbDT.getSelectedRow() == -1)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 dòng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            DiemTam delDT = new DiemTam();
            delDT.setMaDT(txtIDT.getText());
            delDT.setTenDT(txtNameDT.getText());
            delDT.setSoLuong(Integer.parseInt(txtSLDT.getText()));
            delDT.setDonGia(Integer.parseInt(txtDGDT.getText()));
            int n = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa?", "Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
            if(n == JOptionPane.YES_OPTION)
            {
                boolean kq = DiemTamDAO.xoaDT(delDT);
                if(kq)
                JOptionPane.showMessageDialog(null, "Xóa thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
                }
                model.setRowCount(0);
                tbDT.setModel(model);
                loadDT();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnDelDTActionPerformed

    private void btnAddDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDTActionPerformed
        // TODO add your handling code here:
        DiemTam addDT = new DiemTam();
        addDT.setMaDT(txtIDT.getText());
        addDT.setTenDT(txtNameDT.getText());
        addDT.setSoLuong(Integer.parseInt(txtSLDT.getText()));
        addDT.setDonGia(Integer.parseInt(txtDGDT.getText()));
        int n = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm?", "Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
        if(n == JOptionPane.YES_OPTION)
        {
            boolean kq = DiemTamDAO.themDT(addDT);
            if(kq)
            JOptionPane.showMessageDialog(null, "Thêm thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            else
            {
                JOptionPane.showMessageDialog(null, "Thêm thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
            }
            model.setRowCount(0);
            tbTU.setModel(model);
            loadDT();
        }
    }//GEN-LAST:event_btnAddDTActionPerformed

    private void btnNhapKhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapKhoActionPerformed
        // TODO add your handling code here:
        Nhap_Kho a = new Nhap_Kho(this, true);
        a.setVisible(true);
    }//GEN-LAST:event_btnNhapKhoActionPerformed

    private void btnXuatKhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatKhoActionPerformed
        // TODO add your handling code here:
        Xuat_Kho a = new Xuat_Kho(this, true);
        a.setVisible(true);
    }//GEN-LAST:event_btnXuatKhoActionPerformed

    private void btnTinhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTinhTienActionPerformed
        // TODO add your handling code here:
        int tong = 0;
        for(int i = 0; i<tbThongTin.getRowCount() ; i++)
        {
            int a = Integer.parseInt(tbThongTin.getValueAt(i, 5).toString());
            tong += a;
        }
        lbThanhTien.setText(String.valueOf(tong));
    }//GEN-LAST:event_btnTinhTienActionPerformed

    private void btnLamMoiDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiDHActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            for(int i = 0 ; i<tbThongTin.getRowCount(); i++)
            {
                String ma = tbThongTin.getValueAt(i, 1).toString().trim();
                if(ma.startsWith("TU"))
                {

                    cs = connection.prepareCall("{call TRUDISOLUONGTU(?,?)}");
                    cs.setString(1, tbThongTin.getValueAt(i, 1).toString());
                    cs.setInt(2, Integer.parseInt(tbThongTin.getValueAt(i, 3).toString()));
                    int n = cs.executeUpdate();
                    if(n>0)
                    {
                        System.out.println(n);
                    }

                }
                else if(ma.startsWith("DT"))
                {
                    cs = connection.prepareCall("{call TRUDISOLUONGDT(?,?)}");
                    cs.setString(1, tbThongTin.getValueAt(i, 1).toString());
                    cs.setInt(2, Integer.parseInt(tbThongTin.getValueAt(i, 3).toString()));
                    int n = cs.executeUpdate();
                    if(n>0)
                    {
                        System.out.println(n);
                    }

                }
            }
            provider.Close();
            DefaultTableModel tablemo = (DefaultTableModel) tbThongTin.getModel();
            tablemo.setRowCount(0);
            tbThongTin.setModel(tablemo);
            if(cbTUDT.getSelectedItem().toString().equalsIgnoreCase("Điểm Tâm"))
            {
                DefaultTableModel tablemod = (DefaultTableModel) tbDH.getModel();
                tablemod.setRowCount(0);
                tbDH.setModel(tablemod);
                loadDHDT();
            }
            else
            {
                DefaultTableModel tablemod = (DefaultTableModel) tbDH.getModel();
                tablemod.setRowCount(0);
                tbDH.setModel(tablemod);
                loadDHTU();
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuanLy_CaPhe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLamMoiDHActionPerformed

    private void tbDHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDHMouseClicked
        // TODO add your handling code here:
        if(tbDH.getSelectedRow() == -1)
        {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 dòng để thêm vào hóa đơn","Lỗi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(evt.getClickCount()>=2)
        {
            int r = tbDH.getSelectedRow();
            maDH = tbDH.getValueAt(r, 0).toString();
            tenDH = tbDH.getValueAt(r, 1).toString();
            soLuong = Integer.parseInt(tbDH.getValueAt(r, 2).toString());
            TuyChonDatHang a = new TuyChonDatHang(this, true);
            a.setVisible(true);
            String ten = tbDH.getValueAt(r, 1).toString();
            int dg;

            dg = Integer.parseInt(tbDH.getValueAt(r, 3).toString());
            ThemSanPham(maDH, ten, soLuong, dg);
        }
    }//GEN-LAST:event_tbDHMouseClicked

    private void btnXoaDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDHActionPerformed
        // TODO add your handling code here:
        if(tbThongTin.getSelectedRow() == - 1)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 dòng để xóa","Lỗi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        else
        {
            int a = tbThongTin.getSelectedRow();
            DefaultTableModel tablemo = (DefaultTableModel) tbThongTin.getModel();
            tablemo.removeRow(a);
            tbThongTin.setModel(tablemo);
        }
    }//GEN-LAST:event_btnXoaDHActionPerformed

    private void cbTUDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTUDTActionPerformed
        // TODO add your handling code here:
        String a = cbTUDT.getSelectedItem().toString();
        if(a.equalsIgnoreCase("Điểm Tâm"))
        {
            DefaultTableModel tablemo = (DefaultTableModel) tbDH.getModel();
            tablemo.setRowCount(0);
            tbDH.setModel(tablemo);
            loadDHDT();
        }
        if(a.equalsIgnoreCase("Thức Uống"))
        {
            DefaultTableModel tablemo = (DefaultTableModel) tbDH.getModel();
            tablemo.setRowCount(0);
            tbDH.setModel(tablemo);
            loadDHTU();
        }
    }//GEN-LAST:event_cbTUDTActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void JTableNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTableNVMouseClicked
        // TODO add your handling code here:
        try {
            int index = JTableNV.getSelectedRow();
            DefaultTableModel dtm = (DefaultTableModel) JTableNV.getModel();
            txtMNV.setText(dtm.getValueAt(index, 0).toString());
            txtTNV.setText(dtm.getValueAt(index, 1).toString());
            txtNS.setText(dtm.getValueAt(index, 2).toString());
            txtGT.setText(dtm.getValueAt(index, 3).toString());
            txtDC.setText(dtm.getValueAt(index, 4).toString());
            txtCa.setText(dtm.getValueAt(index, 5).toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_JTableNVMouseClicked

    private void addNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNVActionPerformed
        // TODO add your handling code here:
        NhanVien addNV = new NhanVien();
        addNV.setMaNV(txtMNV.getText());
        addNV.setHotenNV(txtTNV.getText());
        addNV.setDiaChi(txtDC.getText());
        addNV.setNgaySinh(Date.valueOf(txtNS.getText().toString()));
        addNV.setGioiTinh(txtGT.getText());
        addNV.setCa(Integer.parseInt(txtCa.getText()));
        int n = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm?", "Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
        if(n == JOptionPane.YES_OPTION)
        {
            boolean kq = NhanVienDAO.themNV(addNV);
            if(kq)
            JOptionPane.showMessageDialog(null, "Thêm thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            else
            {
                JOptionPane.showMessageDialog(null, "Thêm thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
            }
            model.setRowCount(0);
            tbTU.setModel(model);
            loadNV();
        }
    }//GEN-LAST:event_addNVActionPerformed

    private void delNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delNVActionPerformed
        // TODO add your handling code here:
       if(JTableNV.getSelectedRow() == -1)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 dòng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
             NhanVien delNV = new NhanVien();
             delNV.setMaNV(txtMNV.getText());
             delNV.setHotenNV(txtTNV.getText());
             delNV.setDiaChi(txtDC.getText());
             delNV.setNgaySinh(Date.valueOf(txtNS.getText().toString()));
             delNV.setGioiTinh(txtGT.getText());
            int n = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa?", "Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
            if(n == JOptionPane.YES_OPTION)
            {
                boolean kq = NhanVienDAO.xoaNV(delNV);
                if(kq)
                JOptionPane.showMessageDialog(null, "Xóa thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
                }
                model.setRowCount(0);
                JTableNV.setModel(model);
                loadNV();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }//GEN-LAST:event_delNVActionPerformed

    private void updateNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateNVActionPerformed
        // TODO add your handling code here:
         try {
            String maNV = txtMNV.getText();
            DefaultTableModel selectedM = (DefaultTableModel) JTableNV.getModel();
            boolean like = false;
            for (int i=0;i<selectedM.getRowCount();i++)
            {
                if(maNV.equalsIgnoreCase(selectedM.getValueAt(i, 0).toString()))
                {
                    like = true;
                }
            }
            if(like == false)
            {
                JOptionPane.showMessageDialog(null, "Mã Nhân viên không có trong danh sách, không thể update! Xin vui lòng thử lại","Lỗi",JOptionPane.ERROR_MESSAGE);
                return;
            }
             NhanVien upNV = new NhanVien();
             upNV.setMaNV(txtMNV.getText());
             upNV.setHotenNV(txtTNV.getText());
             upNV.setDiaChi(txtDC.getText());
             upNV.setNgaySinh(Date.valueOf(txtNS.getText().toString()));
             upNV.setGioiTinh(txtGT.getText());
            int n = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn update?", "Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
            if(n == JOptionPane.YES_OPTION)
            {
                boolean kq = NhanVienDAO.suaNV(upNV);
                if(kq)
                JOptionPane.showMessageDialog(null, "Update thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                else
                {
                    JOptionPane.showMessageDialog(null, "Update thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
                }
                model.setRowCount(0);
                JTableNV.setModel(model);
                loadNV();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_updateNVActionPerformed

    private void tbHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMouseClicked
        // TODO add your handling code here:
        int a = tbHoaDon.getSelectedRow();
        tfMaHD.setText(tbHoaDon.getValueAt(a, 0).toString());
        tfMaKH.setText(tbHoaDon.getValueAt(a, 1).toString());
        tfMaNhanVien.setText(tbHoaDon.getValueAt(a, 7).toString());
        tfNgayLap.setText(tbHoaDon.getValueAt(a, 3).toString());
        tfMaBan.setText(tbHoaDon.getValueAt(a, 2).toString());
    }//GEN-LAST:event_tbHoaDonMouseClicked

    private void btnThemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHDActionPerformed
        // TODO add your handling code here:
        if(tfMaBan.getText().length() == 0 || tfMaHD.getText().length() == 0 || tfMaNhanVien.getText().length() == 0 ||
            tfMaKH.getText().length() == 0 || tfNgayLap.getText().length() == 0)
        {
            JOptionPane.showMessageDialog(null, "Dữ liệu không được để trống","Lỗi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(HoaDonDAO.ktMa(tfMaHD.getText().toString()))
        {
            JOptionPane.showMessageDialog(null, "Mã hóa đơn này đã tồn tại! Hãy thử lại","Lỗi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        else
        {
            HoaDon hd = new HoaDon();
            maHoaDon = tfMaHD.getText().toString().trim();
            tienBanHD = ThemHoaDon.tongTU + ThemHoaDon.tongDT;
            hd.setMaHD(maHoaDon);
            hd.setMaBan(tfMaBan.getText().toString());
            hd.setMaKH(tfMaKH.getText().toString());
            hd.setMaNV(tfMaNhanVien.getText().toString());
            hd.setNgayLap(Date.valueOf(tfNgayLap.getText().toString()));
            boolean kq = HoaDonDAO.themHoaDon(hd);
            if(kq)
            {
                JOptionPane.showMessageDialog(null, "Hóa đơn mới đã được tạo ra! Hãy tiến hành nhập chi tiết cho hóa đơn đó","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                ThemHoaDon a = new ThemHoaDon(this, true);
                a.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Hóa đơn tạo thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
                return;
            }

        }
    }//GEN-LAST:event_btnThemHDActionPerformed

    private void btnXoaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHDActionPerformed
        // TODO add your handling code here:
        if(tbHoaDon.getSelectedRow() == -1)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn 1 dòng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int n = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa?", "Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
        if(n == JOptionPane.YES_OPTION)
        {
            boolean kq = HoaDonDAO.xoaHoaDon(tfMaHD.getText().toString().trim());
            if(kq)
            JOptionPane.showMessageDialog(null, "Xóa thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            else
            {
                JOptionPane.showMessageDialog(null, "Xóa thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
            }
            DefaultTableModel dtm = (DefaultTableModel) tbHoaDon.getModel();
            dtm.setRowCount(0);
            tbHoaDon.setModel(dtm);
            loadHD();
        }
    }//GEN-LAST:event_btnXoaHDActionPerformed

    private void btnSuaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaHDActionPerformed
        // TODO add your handling code here:
        if(tfMaBan.getText().length() == 0 || tfMaHD.getText().length() == 0 || tfMaNhanVien.getText().length() == 0 ||
            tfMaKH.getText().length() == 0 || tfNgayLap.getText().length() == 0)
        {
            JOptionPane.showMessageDialog(null, "Dữ liệu không được để trống","Lỗi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(HoaDonDAO.ktMa(tfMaHD.getText().toString()))
        {
            HoaDon hd = new HoaDon();
            hd.setMaHD(tfMaHD.getText());
            hd.setMaBan(tfMaBan.getText());
            hd.setMaKH(tfMaKH.getText());
            hd.setNgayLap(Date.valueOf(tfNgayLap.getText().toString()));
            hd.setMaNV(tfMaNhanVien.getText());
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Mã hóa đơn này không tồn tại nên không thể update! Hãy thử lại","Lỗi",JOptionPane.ERROR_MESSAGE);
            return;
        }
    }//GEN-LAST:event_btnSuaHDActionPerformed

    private void btnCapNhatHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatHDActionPerformed
        // TODO add your handling code here:
        SQLServerProvider provider = new SQLServerProvider();
        provider.Open();
        for(int i = 0 ; i<tbHoaDon.getRowCount(); i++)
        {
            try {
                cs = connection.prepareCall("{call CAPNHATTIENBANSAUKHITHEM(?)}");
                cs.setString(1, tbHoaDon.getValueAt(i, 0).toString());
                int n = cs.executeUpdate();
                if(n>0)
                {
                    System.out.println(n);
                }
            } catch (SQLException ex) {
                Logger.getLogger(QuanLy_CaPhe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        provider.Close();
        DefaultTableModel tablemo = (DefaultTableModel) tbHoaDon.getModel();
        tablemo.setRowCount(0);
        tbHoaDon.setModel(tablemo);
        loadHD();
    }//GEN-LAST:event_btnCapNhatHDActionPerformed
    private void ThemSanPham(String ma, String ten, int soluong, int dongia)
    {
        DefaultTableModel tablemo = (DefaultTableModel) tbThongTin.getModel();
        Vector<Object> vec = new Vector<Object>();
        int a = tbThongTin.getRowCount() + 1;
        int b = soluong*dongia;
        vec.add(a);
        vec.add(ma);
        vec.add(ten);
        vec.add(soluong);
        vec.add(dongia);
        vec.add(b);
        tablemo.addRow(vec);
    }    public void emptyTextD()
    {
        txtNamed.setText("");
        txtSLd.setText("");
        txtDGd.setText("");
        txtIDd.setText("");
    }
    public void emptyTextF()
    {
        txtNameDT.setText("");
        txtSLDT.setText("");
        txtIDT.setText("");
        txtDGDT.setText("");
    }
    /**
     * @param args the command line arguments
     */
    public void emptyTextNV()
    {
        txtMNV.setText("");
        txtTNV.setText("");
        txtCa.setText("");
        txtDC.setText("");
        txtGT.setText("");
        txtNS.setText("");
    }
    public static void main(String[] args) {
        QuanLy_CaPhe gui = new QuanLy_CaPhe();
        gui.setVisible(true);
        if (gui.login == false)
                {
                    DangNhap dn = new DangNhap();
                    dn.setVisible(true);
                }
                else if(gui.login == true)
                {
                    new QuanLy_CaPhe().setVisible(true);
                    
                }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTableBan;
    private javax.swing.JTable JTableNV;
    private javax.swing.JMenuItem Menu;
    private javax.swing.JTabbedPane TabbedPanel;
    private javax.swing.JButton addNV;
    private javax.swing.JButton btnAddD;
    private javax.swing.JButton btnAddDT;
    private javax.swing.JButton btnCapNhatHD;
    private javax.swing.JButton btnDelDT;
    private javax.swing.JButton btnDeld;
    private javax.swing.JButton btnFindD;
    private javax.swing.JButton btnFindDT;
    private javax.swing.JButton btnLamMoiDH;
    private javax.swing.JButton btnNhapKho;
    private javax.swing.JButton btnSuaHD;
    private javax.swing.JButton btnThemHD;
    private javax.swing.JButton btnTinhTien;
    private javax.swing.JButton btnUpdD;
    private javax.swing.JButton btnUpdDT;
    private javax.swing.JButton btnXoaDH;
    private javax.swing.JButton btnXoaHD;
    private javax.swing.JButton btnXuatKho;
    private javax.swing.JComboBox<String> cbTUDT;
    private javax.swing.JButton delNV;
    private javax.swing.JButton findNV;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lbDT;
    private javax.swing.JLabel lbTTd;
    private javax.swing.JLabel lbThanhTien;
    private javax.swing.JMenu mNu;
    private javax.swing.JPanel pnBan;
    private javax.swing.JPanel pnDT;
    private javax.swing.JPanel pnDatHang;
    private javax.swing.JPanel pnDoAn;
    private javax.swing.JPanel pnDrinks;
    private javax.swing.JPanel pnHoaDon;
    private javax.swing.JPanel pnKhoNguyenLieu;
    private javax.swing.JPanel pnNhanVien;
    private javax.swing.JPanel pnTU;
    private javax.swing.JTable tbDH;
    private javax.swing.JTable tbDT;
    private javax.swing.JTable tbHoaDon;
    private javax.swing.JTable tbKhoNguyenLieu;
    private javax.swing.JTable tbTU;
    private javax.swing.JTable tbThongTin;
    private javax.swing.JTextField tfDVT;
    private javax.swing.JTextField tfMaBan;
    private javax.swing.JTextField tfMaHD;
    private javax.swing.JTextField tfMaKH;
    private javax.swing.JTextField tfMaNL;
    private javax.swing.JTextField tfMaNhanVien;
    private javax.swing.JTextField tfNgayLap;
    private javax.swing.JTextField tfSoLuong;
    private javax.swing.JTextField tfTenNL;
    private javax.swing.JTextField txtCa;
    private javax.swing.JTextField txtDC;
    private javax.swing.JTextField txtDGDT;
    private javax.swing.JTextField txtDGd;
    private javax.swing.JTextField txtGT;
    private javax.swing.JTextField txtIDT;
    private javax.swing.JTextField txtIDd;
    private javax.swing.JTextField txtMNV;
    private javax.swing.JTextField txtNS;
    private javax.swing.JTextField txtNameDT;
    private javax.swing.JTextField txtNamed;
    private javax.swing.JTextField txtSLDT;
    private javax.swing.JTextField txtSLd;
    private javax.swing.JTextField txtTNV;
    private javax.swing.JButton updateNV;
    // End of variables declaration//GEN-END:variables
}
