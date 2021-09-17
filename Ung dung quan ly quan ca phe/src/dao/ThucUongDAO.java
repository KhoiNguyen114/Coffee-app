/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.ResultSet;
import java.util.ArrayList;
import pojo.ThucUong;
/**
 *
 * @author Nhan
 */
public class ThucUongDAO {
    public static ArrayList<ThucUong> layDsTU()
    {
        ArrayList<ThucUong> dsTU = new ArrayList<>();
        try {
            String sqlselect = "Select * from THUCUONG";
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            ResultSet rs = provider.executeQuery(sqlselect);
            while(rs.next())
            {
                ThucUong tu = new ThucUong();
                tu.setMaTU(rs.getString("MATU"));
                tu.setTenTU(rs.getString("TENTU"));
                tu.setSoLuong(rs.getInt("SOLUONG"));
                tu.setDonGia(rs.getInt("DONGIA"));
                tu.setTinhTrang(rs.getString("TINHTRANG"));
                dsTU.add(tu);
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsTU;
    }
    public static boolean themTU(ThucUong tu)
    {
        boolean kq= false;
        try {
            SQLServerProvider provider = new SQLServerProvider();
            String sql ="";
            if (tu.getSoLuong()==0)
            {
              sql = String.format("INSERT INTO THUCUONG VALUES('%s','%s','%d',N'Không Còn','%d')", tu.getMaTU(),tu.getTenTU(),tu.getSoLuong(),tu.getDonGia());
            }
            else if (tu.getSoLuong()>0)
            {
            sql = String.format("INSERT INTO THUCUONG VALUES('%s','%s','%d',N'Còn','%d')", tu.getMaTU(),tu.getTenTU(),tu.getSoLuong(),tu.getDonGia());
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
    public static boolean xoaTU(ThucUong tu)
    {
        boolean kq= false;
        try {
            SQLServerProvider provider = new SQLServerProvider();
            String sql = String.format("DELETE THUCUONG WHERE MATU = '%s'", tu.getMaTU());
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
    public static boolean updateTU(ThucUong tu)
    {
        boolean kq=false;
        try {
            SQLServerProvider provider = new SQLServerProvider();
            String sql = String.format("UPDATE THUCUONG SET TENTU ='%s',SOLUONG = %d,DONGIA = %d WHERE MATU ='%s'",tu.getTenTU(),tu.getSoLuong(),tu.getDonGia(),tu.getMaTU());
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
    public static ArrayList<ThucUong> timTU(String information){
        ArrayList<ThucUong> dstu = new ArrayList<>();
        try {
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            String sql = String.format("SELECT * FROM THUCUONG WHERE MATU = '%s' or TENTU ='%s'",information,information);
            ResultSet rs = provider.executeQuery(sql);
            while(rs.next())
            {
                ThucUong tu = new ThucUong();
                tu.setMaTU(rs.getString("MATU"));
                tu.setTenTU(rs.getString("TENTU"));
                tu.setSoLuong(rs.getInt("SOLUONG"));
                tu.setDonGia(rs.getInt("DONGIA"));
                tu.setTinhTrang(rs.getString("TINHTRANG"));
                dstu.add(tu);
            }
            provider.Close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return dstu;
    }
}
