package com.example.doancuoiky.model;

public class PhongChieuPhim {
    private String maPhongChieu, maRapPhim;
    private int soChoNgoi;

    public PhongChieuPhim() {
    }

    public PhongChieuPhim(String maPhongChieu, String maRapPhim, int soChoNgoi) {
        this.maPhongChieu = maPhongChieu;
        this.maRapPhim = maRapPhim;
        this.soChoNgoi = soChoNgoi;
    }

    public String getMaPhongChieu() {
        return maPhongChieu;
    }

    public void setMaPhongChieu(String maPhongChieu) {
        this.maPhongChieu = maPhongChieu;
    }

    public String getMaRapPhim() {
        return maRapPhim;
    }

    public void setMaRapPhim(String maRapPhim) {
        this.maRapPhim = maRapPhim;
    }

    public int getSoChoNgoi() {
        return soChoNgoi;
    }

    public void setSoChoNgoi(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
    }
}
