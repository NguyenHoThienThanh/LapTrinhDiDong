package com.example.doancuoiky.model;

import java.io.Serializable;

public class ChiTietVe implements Serializable {
    byte[] trailer;
    String tenPhim, ngayChieu, thoiGianChieu, maPhongChieu, maGhe,hoTen, soDienThoai, maHoaDon, tenComBo;
    int gioiHanDoTuoi;
    double tongTien;

    public ChiTietVe() {
    }

    public byte[] getTrailer() {
        return trailer;
    }

    public void setTrailer(byte[] trailer) {
        this.trailer = trailer;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(String ngayChieu) {
        this.ngayChieu = ngayChieu;
    }

    public String getThoiGianChieu() {
        return thoiGianChieu;
    }

    public void setThoiGianChieu(String thoiGianChieu) {
        this.thoiGianChieu = thoiGianChieu;
    }

    public String getMaPhongChieu() {
        return maPhongChieu;
    }

    public void setMaPhongChieu(String maPhongChieu) {
        this.maPhongChieu = maPhongChieu;
    }

    public String getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(String maGhe) {
        this.maGhe = maGhe;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getTenComBo() {
        return tenComBo;
    }

    public void setTenComBo(String tenComBo) {
        this.tenComBo = tenComBo;
    }

    public int getGioiHanDoTuoi() {
        return gioiHanDoTuoi;
    }

    public void setGioiHanDoTuoi(int gioiHanDoTuoi) {
        this.gioiHanDoTuoi = gioiHanDoTuoi;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}
