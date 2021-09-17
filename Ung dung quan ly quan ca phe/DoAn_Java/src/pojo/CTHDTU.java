/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author Admin
 */
public class CTHDTU {
    private String maHD;
    private String maTU;
    private int soLuongTU;
    private int thanhTienTU;

    public CTHDTU() {
    }

    public CTHDTU(String maHD, String maTU, int soLuongTU, int thanhTienTU) {
        this.maHD = maHD;
        this.maTU = maTU;
        this.soLuongTU = soLuongTU;
        this.thanhTienTU = thanhTienTU;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaTU() {
        return maTU;
    }

    public void setMaTU(String maTU) {
        this.maTU = maTU;
    }

    public int getSoLuongTU() {
        return soLuongTU;
    }

    public void setSoLuongTU(int soLuongTU) {
        this.soLuongTU = soLuongTU;
    }

    public int getThanhTienTU() {
        return thanhTienTU;
    }

    public void setThanhTienTU(int thanhTienTU) {
        this.thanhTienTU = thanhTienTU;
    }

    
    
}
