package com.example.doancuoiky.model;

import java.util.Date;

public class ChiTietSuatChieu {
    private int idSuatChieu;
    private String maPhim;
    private Date ngayChieu;
    private String gioChieu;
    private String rapChieu;
    private int soGheConLai;

    public ChiTietSuatChieu(int idSuatChieu, String maPhim, Date ngayChieu, String gioChieu, String rapChieu, int soGheConLai) {
        this.idSuatChieu = idSuatChieu;
        this.maPhim = maPhim;
        this.ngayChieu = ngayChieu;
        this.gioChieu = gioChieu;
        this.rapChieu = rapChieu;
        this.soGheConLai = soGheConLai;
    }

    public int getIdSuatChieu() {
        return idSuatChieu;
    }

    public void setIdSuatChieu(int idSuatChieu) {
        this.idSuatChieu = idSuatChieu;
    }

    public String getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(String maPhim) {
        this.maPhim = maPhim;
    }

    public Date getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(Date ngayChieu) {
        this.ngayChieu = ngayChieu;
    }

    public String getGioChieu() {
        return gioChieu;
    }

    public void setGioChieu(String gioChieu) {
        this.gioChieu = gioChieu;
    }

    public String getRapChieu() {
        return rapChieu;
    }

    public void setRapChieu(String rapChieu) {
        this.rapChieu = rapChieu;
    }

    public int getSoGheConLai() {
        return soGheConLai;
    }

    public void setSoGheConLai(int soGheConLai) {
        this.soGheConLai = soGheConLai;
    }

    @Override
    public String toString() {
        return "ChiTietSuatChieu{" +
                "idSuatChieu=" + idSuatChieu +
                ", maPhim='" + maPhim + '\'' +
                ", ngayChieu=" + ngayChieu +
                ", gioChieu='" + gioChieu + '\'' +
                ", rapChieu='" + rapChieu + '\'' +
                ", soGheConLai=" + soGheConLai +
                '}';
    }
}
