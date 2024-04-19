package com.example.doancuoiky.model;

public class Phim {
    private String maPhim, tenPhim, moTaPhim, dienVien, trailer;
    private int thoiLuong, gioiHanDoTuoi;
    private float giaVe;

    public Phim(String maPhim, String tenPhim, String moTaPhim, String dienVien, String trailer, int thoiLuong, int gioiHanDoTuoi, float giaVe) {
        this.maPhim = maPhim;
        this.tenPhim = tenPhim;
        this.moTaPhim = moTaPhim;
        this.dienVien = dienVien;
        this.trailer = trailer;
        this.thoiLuong = thoiLuong;
        this.gioiHanDoTuoi = gioiHanDoTuoi;
        this.giaVe = giaVe;
    }

    public Phim() {
    }

    public String getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(String maPhim) {
        this.maPhim = maPhim;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getMoTaPhim() {
        return moTaPhim;
    }

    public void setMoTaPhim(String moTaPhim) {
        this.moTaPhim = moTaPhim;
    }

    public String getDienVien() {
        return dienVien;
    }

    public void setDienVien(String dienVien) {
        this.dienVien = dienVien;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public int getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public int getGioiHanDoTuoi() {
        return gioiHanDoTuoi;
    }

    public void setGioiHanDoTuoi(int gioiHanDoTuoi) {
        this.gioiHanDoTuoi = gioiHanDoTuoi;
    }

    public float getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(float giaVe) {
        this.giaVe = giaVe;
    }
}
