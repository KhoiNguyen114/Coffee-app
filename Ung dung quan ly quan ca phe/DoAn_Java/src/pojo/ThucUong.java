/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author Nhan
 */
public class ThucUong {
    private String maTU;
    private String tenTU;
    private int soLuong;
    private String tinhTrang;
    private int donGia;

    public String getMaTU() {
        return maTU;
    }

    public void setMaTU(String maTU) {
        this.maTU = maTU;
    }

    public String getTenTU() {
        return tenTU;
    }

    public void setTenTU(String tenTU) {
        this.tenTU = tenTU;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public ThucUong() {
    }

    public ThucUong(String maTU, String tenTU, int soLuong, String tinhTrang, int donGia) {
        this.maTU = maTU;
        this.tenTU = tenTU;
        this.soLuong = soLuong;
        this.tinhTrang = tinhTrang;
        this.donGia = donGia;
    }
    
    
}
