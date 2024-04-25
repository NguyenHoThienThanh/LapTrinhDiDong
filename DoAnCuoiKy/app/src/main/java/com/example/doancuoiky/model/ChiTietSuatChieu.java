package com.example.doancuoiky.model;

import java.util.Date;

public class ChiTietSuatChieu {
    private String maSuatChieu;
    private String maPhongChieu;
    private String maPhim;
    private String ngayChieu;
    private String gioChieu;

    public ChiTietSuatChieu(String maSuatChieu, String maPhongChieu, String maPhim, String ngayChieu, String gioChieu) {
        this.maSuatChieu = maSuatChieu;
        this.maPhongChieu = maPhongChieu;
        this.maPhim = maPhim;
        this.ngayChieu = ngayChieu;
        this.gioChieu = gioChieu;
    }

    public ChiTietSuatChieu() {
    }

    public String getMaSuatChieu() {
        return maSuatChieu;
    }

    public void setMaSuatChieu(String maSuatChieu) {
        this.maSuatChieu = maSuatChieu;
    }

    public String getMaPhongChieu() {
        return maPhongChieu;
    }

    public void setMaPhongChieu(String maPhongChieu) {
        this.maPhongChieu = maPhongChieu;
    }

    public String getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(String maPhim) {
        this.maPhim = maPhim;
    }

    public String getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(String ngayChieu) {
        this.ngayChieu = ngayChieu;
    }

    public String getGioChieu() {
        return gioChieu;
    }

    public void setGioChieu(String gioChieu) {
        this.gioChieu = gioChieu;
    }
}