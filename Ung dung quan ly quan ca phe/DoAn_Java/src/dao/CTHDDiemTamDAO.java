/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import pojo.CTHDDT;
/**
 *
 * @author Admin
 */
public class CTHDDiemTamDAO {
    public static boolean themCTHDDT(CTHDDT dt)
    {
        boolean kq = false;
        String sql = String.format("Insert Into CTHDDIEMTAM Values('%s', '%s','%d','%d')",dt.getMaHD(),dt.getMaDT(),dt.getSoLuongDT(),dt.getThanhTienDT());
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
