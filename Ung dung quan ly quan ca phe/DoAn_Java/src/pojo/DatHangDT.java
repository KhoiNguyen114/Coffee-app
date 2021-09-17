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
public class DatHangDT {
    private String maDT;
    private String tenDT;
    private int soLuong;
    private int donGia;

    public DatHangDT(String maDT, String tenDT, int soLuong, int donGia) {
        this.maDT = maDT;
        this.tenDT = tenDT;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public DatHangDT() {
    }

    public String getMaDT() {
        return maDT;
    }

    public void setMaDT(String maDT) {
        this.maDT = maDT;
    }

    public String getTenDT() {
        return tenDT;
    }

    public void setTenDT(String tenDT) {
        this.tenDT = tenDT;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
    
}
