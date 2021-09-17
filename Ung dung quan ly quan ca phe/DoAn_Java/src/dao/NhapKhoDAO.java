/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pojo.NhapKho;

/**
 *
 * @author Admin
 */
public class NhapKhoDAO {
    public static ArrayList<NhapKho> layDsNhapKho(){
        ArrayList<NhapKho> dsNK = new ArrayList<>();
        try {
            String sql = "Select * From CTNHAPKHO";
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            ResultSet rs = provider.executeQuery(sql);
            while(rs.next())
            {
                NhapKho nk = new NhapKho();
                nk.setMaNhapKho(rs.getString("MANHAPKHO"));
                nk.setMaNguyenLieu(rs.getString("MANL"));
                nk.setNgayNhap(rs.getDate("NGAYNHAP"));
                nk.setSoLuong(rs.getInt("SOLUONG"));
                nk.setThanhTien(rs.getInt("THANHTIEN"));
                dsNK.add(nk);
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsNK;
    }
    public static boolean themNhapKho(NhapKho nk)
    {
        boolean kq = false;
        String sql = String.format("Insert Into CTNHAPKHO Values('%s', '%s', '%s', '%d','%d')",nk.getMaNhapKho(),nk.getMaNguyenLieu(),nk.getNgayNhap(),
                nk.getSoLuong(),nk.getThanhTien());
        SQLServerProvider provider = new SQLServerProvider();
        provider.Open();
        int n = provider.executeUpdate(sql);
        if(n>0)
        {
            kq = true;
        }
        provider.Close();
        return kq;
    }
    public static boolean ktMa(String maNK)
    {
        try {
            String sql = "Select * From CTNHAPKHO Where MANHAPKHO = '"+maNK+"'";
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            ResultSet rs  = provider.executeQuery(sql);
            if(rs.next())
            {
                return true;
            }
            provider.Close();
        } catch (SQLException ex) {
            Logger.getLogger(NhapKhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static boolean capNhatNhapKho(NhapKho nk)
    {
        boolean kq = false;
        String sql = String.format("Update CTNHAPKHO Set MANL = '%s', NGAYNHAP = '%s', SOLUONG = %d, THANHTIEN = %d Where MANHAPKHO = '%s'",nk.getMaNguyenLieu(),nk.getNgayNhap(),nk.getSoLuong()
        ,nk.getThanhTien(),nk.getMaNhapKho());
        SQLServerProvider provider = new SQLServerProvider();
        provider.Open();
        int n = provider.executeUpdate(sql);
        if(n>0)
        {
            kq = true;
        }
        provider.Close();
        return kq;
    }
    public static boolean xoaNhapKho(String maNK)
    {
        boolean kq = false;
        String sql = "Delete From CTNHAPKHO Where MANHAPKHO = '"+maNK+"'";
        SQLServerProvider provider = new SQLServerProvider();
        provider.Open();
        int n = provider.executeUpdate(sql);
        if(n>0)
        {
            kq = true;
        }
        provider.Close();
        return kq;
    }
    
}
