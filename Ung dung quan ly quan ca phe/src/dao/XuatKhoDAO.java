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
import pojo.XuatKho;

/**
 *
 * @author Admin
 */
public class XuatKhoDAO {
    public static ArrayList<XuatKho> layDsXuatKho(){
        ArrayList<XuatKho> dsNK = new ArrayList<>();
        try {
            String sql = "Select * From CTXUATKHO";
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            ResultSet rs = provider.executeQuery(sql);
            while(rs.next())
            {
                XuatKho xk = new XuatKho();
                xk.setMaXuatKho(rs.getString("MAXUATKHO"));
                xk.setMaNguyenLieu(rs.getString("MANL"));
                xk.setNgayXuat(rs.getDate("NGAYXUAT"));
                xk.setSoLuong(rs.getInt("SOLUONG"));
                xk.setThanhTien(rs.getInt("THANHTIEN"));
                dsNK.add(xk);
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsNK;
    }
    public static boolean themXuatKho(XuatKho xk)
    {
        boolean kq = false;
        String sql = String.format("Insert Into CTXUATKHO Values('%s', '%s', '%s', '%d','%d')",xk.getMaXuatKho(),xk.getMaNguyenLieu(),xk.getNgayXuat(),
                xk.getSoLuong(),xk.getThanhTien());
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
    public static boolean ktMa(String maXK)
    {
        try {
            String sql = "Select * From CTXUATKHO Where MAXUATKHO = '"+maXK+"'";
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            ResultSet rs  = provider.executeQuery(sql);
            if(rs.next())
            {
                return true;
            }
            provider.Close();
        } catch (SQLException ex) {
            Logger.getLogger(XuatKhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static boolean capNhatXuatKho(XuatKho xk)
    {
        boolean kq = false;
        String sql = String.format("Update CTXUATKHO Set MANL = '%s', NGAYXUAT = '%s', SOLUONG = %d, THANHTIEN = %d Where MAXUATKHO = '%s'",xk.getMaNguyenLieu(),xk.getNgayXuat(),xk.getSoLuong()
        ,xk.getThanhTien(),xk.getMaXuatKho());
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
    public static boolean xoaXuatKho(String maXK)
    {
        boolean kq = false;
        String sql = "Delete From CTXUATKHO Where MAXUATKHO = '"+maXK+"'";
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
