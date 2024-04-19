package com.example.doancuoiky.model;

import java.io.Serializable;

public class ComboBapNuoc implements Serializable {
    private byte[] hinhAnh;
    private String maCombo;
    private String tenCombo;
    private String moTa;
    private float gia;
    private int soLuong = 0;

    private int soLuongDat = 0;


    public ComboBapNuoc() {
    }

    public ComboBapNuoc(byte[] hinhAnh, String maCombo, String tenCombo, String moTa, float gia, int soLuong, int soLuongDat) {
        this.hinhAnh = hinhAnh;
        this.maCombo = maCombo;
        this.tenCombo = tenCombo;
        this.moTa = moTa;
        this.gia = gia;
        this.soLuong = soLuong;
        this.soLuongDat = soLuongDat;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
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
