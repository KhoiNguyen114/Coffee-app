/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import pojo.DiemTam;
import pojo.DatHangTU;

/**
 *
 * @author Admin
 */
public class DatHangTUDAO {
    public static ArrayList<DatHangTU> layDsTU()
    {
        ArrayList<DatHangTU> dsTU = new ArrayList<>();
        try {
            String sqlselect = "Select * from THUCUONG";
            SQLServerProvider provider = new SQLServerProvider();
            provider.Open();
            ResultSet rs = provider.executeQuery(sqlselect);
            while(rs.next())
            {
                DatHangTU tu = new DatHangTU();
                tu.setMaTU(rs.getString("MATU"));
                tu.setTenTU(rs.getString("TENTU"));
                tu.setSoLuong(rs.getInt("SOLUONG"));
                tu.setDonGia(rs.getInt("DONGIA"));
                dsTU.add(tu);
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsTU;
    }
    
}
