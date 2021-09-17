/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.ResultSet;
import java.util.ArrayList;
import pojo.KhachHang;
/**
 *
 * @author Nhan
 */
public class KhachHangDAO {
    public static ArrayList<KhachHang> layDsKhachHang(){
        ArrayList<KhachHang> dsKH = new ArrayList<>();
        try {
            String sqlSelect = "Select * from KHACHHANG";
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            ResultSet rs = provider.executeQuery(sqlSelect);
            while(rs.next())
            {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString("MAKH"));
                kh.setTenKH(rs.getString("TENKH"));
                kh.setGioiTinh(rs.getString("GIOITINH"));
                dsKH.add(kh);
            }
            provider.Close();
        } catch (Exception e) {
        }
        return dsKH;
    }
}
