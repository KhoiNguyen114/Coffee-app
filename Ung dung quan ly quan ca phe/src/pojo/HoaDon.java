/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.Date;

/**
 *
 * @author Nhan
 */
public class HoaDon {
    private String maHD;
    private String maKH;
    private String maBan;
    private Date ngayLap;
    private int tienBan;
    private float giamGia;
    private int thanhTien;
    private String maNV;

    public HoaDon() {
    }

    public HoaDon(String maHD, String maKH, String maBan, Date ngayLap, int tienBan, float giamGia, int thanhTien, String maNV) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maBan = maBan;
        this.ngayLap = ngayLap;
        this.tienBan = tienBan;
        this.giamGia = giamGia;
        this.thanhTien = thanhTien;
        this.maNV = maNV;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getTienBan() {
        return tienBan;
    }

    public void setTienBan(int tienBan) {
        this.tienBan = tienBan;
    }

    public float getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(float giamGia) {
        this.giamGia = giamGia;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    
    
}
