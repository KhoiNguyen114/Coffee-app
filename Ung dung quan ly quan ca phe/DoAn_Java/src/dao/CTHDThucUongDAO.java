/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import pojo.CTHDTU;
/**
 *
 * @author Admin
 */
public class CTHDThucUongDAO {
    public static boolean themCTHDTU(CTHDTU tu)
    {
        boolean kq = false;
        String sql = String.format("Insert Into CTHDTHUCUONG Values('%s', '%s','%d','%d')",tu.getMaHD(),tu.getMaTU(),tu.getSoLuongTU(),tu.getThanhTienTU());
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
