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
public class DiemTam {
    private String maDT;
    private String tenDT;
    private int soLuong;
    private String tinhTrang;
    private int donGia;

    public DiemTam() {
    }

    public DiemTam(String maDT, String tenDT, int soLuong, String tinhTrang, int donGia) {
        this.maDT = maDT;
        this.tenDT = tenDT;
        this.soLuong = soLuong;
        this.tinhTrang = tinhTrang;
        this.donGia = donGia;
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
    
    
}
