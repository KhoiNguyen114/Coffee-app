/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class NhapKho {
    private String maNhapKho;
    private String maNguyenLieu;
    private Date ngayNhap;
    private int soLuong;
    private int thanhTien;

    public NhapKho() {
    }

    public NhapKho(String maNhapKho, String maNguyenLieu, Date ngayNhap, int soLuong, int thanhTien) {
        this.maNhapKho = maNhapKho;
        this.maNguyenLieu = maNguyenLieu;
        this.ngayNhap = ngayNhap;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public String getMaNhapKho() {
        return maNhapKho;
    }

    public void setMaNhapKho(String maNhapKho) {
        this.maNhapKho = maNhapKho;
    }

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }
    
    
}
