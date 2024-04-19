package com.example.doancuoiky.model;

import java.io.Serializable;

public class ComboBapNuoc implements Serializable {
    private int hinhAnh;
    private String maCombo;
    private String tenCombo;
    private float gia;
    private int soLuong = 0;

    private int soLuongDat = 0;

    public ComboBapNuoc(int hinhAnh, String maCombo, String tenCombo, float gia, int soLuong) {
        this.hinhAnh = hinhAnh;
        this.maCombo = maCombo;
        this.tenCombo = tenCombo;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public ComboBapNuoc() {
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMaCombo() {
        return maCombo;
    }

    public void setMaCombo(String maCombo) {
        this.maCombo = maCombo;
    }

    public String getTenCombo() {
        return tenCombo;
    }

    public void setTenCombo(String tenCombo) {
        this.tenCombo = tenCombo;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSoLuongDat() {
        return soLuongDat;
    }

    public void setSoLuongDat(int soLuongDat) {
        this.soLuongDat = soLuongDat;
    }
}
