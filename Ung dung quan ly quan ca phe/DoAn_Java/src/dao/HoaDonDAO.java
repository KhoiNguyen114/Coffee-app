/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojo.HoaDon;

/**
 *
 * @author Nhan
 */
public class HoaDonDAO {
    public static ArrayList<HoaDon> layDsHoaDon()
    {
        ArrayList<HoaDon> dsHD = new ArrayList<>();
        try {
            String sqlSelect = "Select * from HOADON";
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            ResultSet rs = provider.executeQuery(sqlSelect);
            while(rs.next())
            {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MAHD"));
                hd.setMaKH(rs.getString("MAKH"));
                hd.setMaBan(rs.getString("MABAN"));
                hd.setMaNV(rs.getString("MANV"));
                hd.setNgayLap(rs.getDate("NGAYLAP"));
                hd.setGiamGia(rs.getFloat("GIAMGIA"));
                hd.setThanhTien(rs.getInt("THANHTIEN"));
                hd.setTienBan(rs.getInt("TIENBAN"));
                dsHD.add(hd);
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsHD;
    }
     public static boolean ktMa(String maHD)
    {
        try {
            String sql = "Select * From HOADON Where MAHD = '"+maHD+"'";
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
    public static boolean themHoaDon(HoaDon hd)
    {
        boolean kq = false;
        String sql = String.format("Insert Into HOADON(MAHD,MAKH,MABAN,NGAYLAP,MANV) Values('%s', '%s', '%s','%s','%s')",hd.getMaHD(),hd.getMaKH(),hd.getMaBan()
        ,hd.getNgayLap(),hd.getMaNV());
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
    public static boolean xoaHoaDon(String maHD)
    {
        boolean kq = false;
        String sql = "Delete From HOADON Where MAHD = '"+maHD+"'";
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
    public static boolean capNhatHoaDon(HoaDon hd)
    {
        boolean kq = false;
        String sql = String.format("Update HOADON Set MAKH = '%s', MABAN = '%s', NGAYLAP = '%s', MANV = '%s' Where MAHD = '%s'",hd.getMaKH(),hd.getMaBan(),hd.getNgayLap(),
                hd.getMaNV(),hd.getMaHD());
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
