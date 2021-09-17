/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import pojo.DatHangDT;

/**
 *
 * @author Admin
 */
public class DatHangDTDAO {
    public static ArrayList<DatHangDT> layDsDiemTam(){
        ArrayList<DatHangDT> dsDT = new ArrayList<>();
        try {
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            String sqlselect = "Select * from DIEMTAM";
            ResultSet rs = provider.executeQuery(sqlselect);
            while(rs.next())
            {
                DatHangDT dt = new DatHangDT();
                dt.setMaDT(rs.getString("MADT"));
                dt.setTenDT(rs.getString("TENDT"));
                dt.setSoLuong(rs.getInt("SOLUONG"));
                dt.setDonGia(rs.getInt("DONGIA"));
                dsDT.add(dt);
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsDT;
    }
}
