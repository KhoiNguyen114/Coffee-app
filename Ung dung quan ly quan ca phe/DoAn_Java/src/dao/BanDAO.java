/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import pojo.Ban;

/**
 *
 * @author Nhan
 */
public class BanDAO {
    public static ArrayList<Ban> laydsBan(){
        ArrayList<Ban> dsB = new ArrayList<>();
        try {
            SQLServerProvider provider = new SQLServerProvider();
            String sqlSelect = "Select * From BAN";
            provider.Open();
            ResultSet rs = provider.executeQuery(sqlSelect);
            while(rs.next())
            {
                Ban b = new Ban();
                b.setMaBan(rs.getString("MABAN"));
                b.setSlKhach(rs.getInt("SLKHACH"));
                b.setTinhTrang(rs.getInt("TINHTRANG"));
                dsB.add(b);
            }
            provider.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsB;
    }
}
