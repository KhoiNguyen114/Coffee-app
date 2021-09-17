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
public class Ban {
    private String maBan;
    private int slKhach;
    private int tinhTrang;

    public Ban() {
    }

    public Ban(String maBan, int slKhach, int tinhTrang) {
        this.maBan = maBan;
        this.slKhach = slKhach;
        this.tinhTrang = tinhTrang;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public int getSlKhach() {
        return slKhach;
    }

    public void setSlKhach(int slKhach) {
        this.slKhach = slKhach;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    
}
