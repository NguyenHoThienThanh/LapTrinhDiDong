package com.example.doancuoiky.model;
public class HoaDon {
    private String maHoaDon;
    private String maSuatChieu;
    private String maKhachHang;
    private String maCombo;
    private double tongTien;
    private String maGhe;
    private String ngayLapHoaDon;

    public HoaDon() {
    }

    public String getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(String maGhe) {
        this.maGhe = maGhe;
    }

    public HoaDon(String maHoaDon, String maSuatChieu, String maKhachHang, String maCombo, double tongTien, String  ngayLapHoaDon, String maGhe) {
        this.maHoaDon = maHoaDon;
        this.maSuatChieu = maSuatChieu;
        this.maKhachHang = maKhachHang;
        this.maCombo = maCombo;
        this.tongTien = tongTien;
        this.ngayLapHoaDon = ngayLapHoaDon;
        this.maGhe = maGhe;
    }

    public String getNgayLapHoaDon() {
        return ngayLapHoaDon;
    }

    public void setNgayLapHoaDon(String ngayLapHoaDon) {
        this.ngayLapHoaDon = ngayLapHoaDon;
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

