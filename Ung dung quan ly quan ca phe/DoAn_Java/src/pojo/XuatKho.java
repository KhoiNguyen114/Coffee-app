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
public class XuatKho {
    private String maXuatKho;
    private String maNguyenLieu;
    private Date ngayXuat;
    private int soLuong;
    private int thanhTien;

    public XuatKho() {
    }

    public XuatKho(String maXuatKho, String maNguyenLieu, Date ngayXuat, int soLuong, int thanhTien) {
        this.maXuatKho = maXuatKho;
        this.maNguyenLieu = maNguyenLieu;
        this.ngayXuat = ngayXuat;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public String getMaXuatKho() {
        return maXuatKho;
    }

    public void setMaXuatKho(String maXuatKho) {
        this.maXuatKho = maXuatKho;
    }

    public String getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(String maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public Date getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(Date ngayXuat) {
        this.ngayXuat = ngayXuat;
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
