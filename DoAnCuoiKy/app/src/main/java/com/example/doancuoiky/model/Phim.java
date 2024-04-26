package com.example.doancuoiky.model;

public class Phim {
    private String maPhim, tenPhim, moTaPhim, dienVien, quocGia, theLoai;
    private int thoiLuong, gioiHanDoTuoi;
    private float giaVe;
    private byte[] trailer;

    public Phim() {
    }

    public Phim(String maPhim, String tenPhim, String moTaPhim, String dienVien, String quocGia, String theLoai, int thoiLuong, int gioiHanDoTuoi, float giaVe, byte[] trailer) {
        this.maPhim = maPhim;
        this.tenPhim = tenPhim;
        this.moTaPhim = moTaPhim;
        this.dienVien = dienVien;
        this.quocGia = quocGia;
        this.theLoai = theLoai;
        this.thoiLuong = thoiLuong;
        this.gioiHanDoTuoi = gioiHanDoTuoi;
        this.giaVe = giaVe;
        this.trailer = trailer;
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

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
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

    public byte[] getTrailer() {
        return trailer;
    }

    public void setTrailer(byte[] trailer) {
        this.trailer = trailer;
    }
}
