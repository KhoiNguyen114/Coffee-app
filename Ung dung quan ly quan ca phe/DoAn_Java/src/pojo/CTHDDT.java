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
public class CTHDDT {
    private String maHD;
    private String maDT;
    private int soLuongDT;
    private int thanhTienDT;

    public CTHDDT() {
    }

    public CTHDDT(String maHD, String maDT, int soLuongDT, int thanhTienDT) {
        this.maHD = maHD;
        this.maDT = maDT;
        this.soLuongDT = soLuongDT;
        this.thanhTienDT = thanhTienDT;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaDT() {
        return maDT;
    }

    public void setMaDT(String maDT) {
        this.maDT = maDT;
    }

    public int getSoLuongDT() {
        return soLuongDT;
    }

    public void setSoLuongDT(int soLuongDT) {
        this.soLuongDT = soLuongDT;
    }

    public int getThanhTienDT() {
        return thanhTienDT;
    }

    public void setThanhTienDT(int thanhTienDT) {
        this.thanhTienDT = thanhTienDT;
    }

   
    
    
}
