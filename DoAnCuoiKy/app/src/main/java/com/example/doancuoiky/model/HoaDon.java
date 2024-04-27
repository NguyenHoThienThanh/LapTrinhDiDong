package com.example.doancuoiky.model;
public class HoaDon {
    private String maHoaDon;
    private String maSuatChieu;
    private String maKhachHang;
    private String maCombo;
    private double tongTien;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String maSuatChieu, String maKhachHang, String maCombo, double tongTien) {
        this.maHoaDon = maHoaDon;
        this.maSuatChieu = maSuatChieu;
        this.maKhachHang = maKhachHang;
        this.maCombo = maCombo;
        this.tongTien = tongTien;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaSuatChieu() {
        return maSuatChieu;
    }

    public void setMaSuatChieu(String maSuatChieu) {
        this.maSuatChieu = maSuatChieu;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaCombo() {
        return maCombo;
    }

    public void setMaCombo(String maCombo) {
        this.maCombo = maCombo;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}
