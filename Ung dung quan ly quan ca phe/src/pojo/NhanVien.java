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
public class NhanVien {
    private String maNV;
    private String hotenNV;
    private Date ngaySinh;
    private String diaChi;
    private String gioiTinh;
    private int ca;
    private String maCV;

    public NhanVien() {
    }

    public NhanVien(String maNV, String hotenNV, Date ngaySinh, String diaChi, String gioiTinh, int ca, String maCV) {
        this.maNV = maNV;
        this.hotenNV = hotenNV;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.ca = ca;
        this.maCV = maCV;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHotenNV() {
        return hotenNV;
    }

    public void setHotenNV(String hotenNV) {
        this.hotenNV = hotenNV;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getCa() {
        return ca;
    }

    public void setCa(int ca) {
        this.ca = ca;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }
    
}
