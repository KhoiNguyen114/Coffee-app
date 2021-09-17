/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import pojo.DiemTam;

/**
 *
 * @author Nhan
 */
public class DiemTamDAO {
    public static ArrayList<DiemTam> layDsDiemTam(){
        ArrayList<DiemTam> dsDT = new ArrayList<>();
        try {
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            String sqlselect = "Select * from DIEMTAM";
            ResultSet rs = provider.executeQuery(sqlselect);
            while(rs.next())
            {
                DiemTam dt = new DiemTam();
                dt.setMaDT(rs.getString("MADT"));
                dt.setTenDT(rs.getString("TENDT"));
                dt.setSoLuong(rs.getInt("SOLUONG"));
                dt.setTinhTrang(rs.getString("TINHTRANG"));
                dt.setDonGia(rs.getInt("DONGIA"));
                dsDT.add(dt);
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsDT;
    }
     public static boolean themDT(DiemTam dt)
    {
        boolean kq= false;
        try {
            SQLServerProvider provider = new SQLServerProvider();
            String sql="";
            if(dt.getSoLuong() ==0)
            {
                sql = String.format("INSERT INTO DIEMTAM VALUES('%s','%s','%d',N'Không Còn','%d')", dt.getMaDT(),dt.getTenDT(),dt.getSoLuong(),dt.getDonGia());
            }
            else if (dt.getSoLuong()>0)
            {
            sql = String.format("INSERT INTO DIEMTAM VALUES('%s','%s','%d',N'Còn','%d')", dt.getMaDT(),dt.getTenDT(),dt.getSoLuong(),dt.getDonGia());
            }
            provider.Open();
            int n = provider.executeUpdate(sql);
            if(n>=1)
            {
                kq=true;
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return kq;
    }
      public static boolean xoaDT(DiemTam dt)
    {
        boolean kq= false;
        try {
            SQLServerProvider provider = new SQLServerProvider();
            String sql = String.format("DELETE DIEMTAM WHERE MADT = '%s'", dt.getMaDT());
            provider.Open();
            int n = provider.executeUpdate(sql);
            if(n>=1)
            {
                kq=true;
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return kq;
    }
       public static boolean updateDT(DiemTam dt)
    {
        boolean kq=false;
        try {
            SQLServerProvider provider = new SQLServerProvider();
            String sql = String.format("UPDATE DIEMTAM SET TENDT ='%s',SOLUONG = %d,DONGIA = %d WHERE MADT ='%s'",dt.getTenDT(),dt.getSoLuong(),dt.getDonGia(),dt.getMaDT());
            provider.Open();
            int n = provider.executeUpdate(sql);
            if(n>=1)
            {
                kq = true;
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return kq;
    }
        public static ArrayList<DiemTam> timDT(String information){
        ArrayList<DiemTam> dsDT = new ArrayList<>();
        try {
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            String sql = String.format("SELECT * FROM DIEMTAM WHERE MADT = '%s' or TENDT ='%s'",information,information);
            ResultSet rs = provider.executeQuery(sql);
            while(rs.next())
            {
                DiemTam dt = new DiemTam();
                dt.setMaDT(rs.getString("MATU"));
                dt.setTenDT(rs.getString("TENTU"));
                dt.setSoLuong(rs.getInt("SOLUONG"));
                dt.setDonGia(rs.getInt("DONGIA"));
                dt.setTinhTrang(rs.getString("TINHTRANG"));
                dsDT.add(dt);
            }
            provider.Close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsDT;
    }
}