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
public class Kho {
    private String maNL;
    private String tenNL;
    private String dvt;
    private int soLuong;
    private int Gia;

    public Kho() {
    }

    public Kho(String maNL, String tenNL, String dvt, int soLuong, int Gia) {
        this.maNL = maNL;
        this.tenNL = tenNL;
        this.dvt = dvt;
        this.soLuong = soLuong;
        this.Gia = Gia;
    }

    public String getMaNL() {
        return maNL;
    }

    public void setMaNL(String maNL) {
        this.maNL = maNL;
    }

    public String getTenNL() {
        return tenNL;
    }

    public void setTenNL(String tenNL) {
        this.tenNL = tenNL;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int Gia) {
        this.Gia = Gia;
    }
    
    
}
