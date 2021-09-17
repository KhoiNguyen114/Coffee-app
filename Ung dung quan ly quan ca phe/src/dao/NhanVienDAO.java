/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.ResultSet;
import java.util.ArrayList;
import pojo.NhanVien;
/**
 *
 * @author Nhan
 */
public class NhanVienDAO {
    public static ArrayList<NhanVien> layDsNhanVien(){
        ArrayList<NhanVien> dsNV = new ArrayList<>();
        try {
            String sqlSelect ="Select * From NHANVIEN";
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            ResultSet rs = provider.executeQuery(sqlSelect);
            while(rs.next())
            {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MANV"));
                nv.setHotenNV(rs.getString("HOTENNV"));
                nv.setMaCV(rs.getString("MACV"));
                nv.setGioiTinh(rs.getString("GIOITINH"));
                nv.setNgaySinh(rs.getDate("NGAYSINH"));
                nv.setDiaChi(rs.getString("DIACHI"));
                nv.setCa(rs.getInt("CA"));
                
                dsNV.add(nv);
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsNV;
    }
    public static boolean themNV(NhanVien nv)
    {
        boolean kq = false;
        try {
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            String sql = String.format("SET DATEFORMAT DMY INSERT INTO NHANVIEN VALUES('%s',N'%s','%s',N'%s',N'%s',%d,null)", nv.getMaNV(),nv.getHotenNV(),nv.getNgaySinh().toString(),nv.getDiaChi(),nv.getGioiTinh(),nv.getCa());
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
    public static boolean xoaNV(NhanVien nv)
    {
        boolean kq= false;
        try {
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            String sql = String.format("DELETE NHANVIEN WHERE MANV ='%s'",nv.getMaNV());
            int n = provider.executeUpdate(sql);
            if(n>=1)
            {
                kq=true;
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println("e");
        }
        return kq;
    }
    public static boolean suaNV(NhanVien nv)
    {
        boolean kq = false;
        try {
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            String sql = String.format("SET DATEFORMAT DMY UPDATE NHANVIEN SET HOTENNV = N'%s',NGAYSINH ='%s',DIACHI =N'%s',GIOITINH =N'%s',Ca =%d WHERE MANV = '%s'", nv.getHotenNV(),nv.getNgaySinh().toString(),nv.getDiaChi(),nv.getNgaySinh(),nv.getCa(),nv.getMaNV());
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
    
}
