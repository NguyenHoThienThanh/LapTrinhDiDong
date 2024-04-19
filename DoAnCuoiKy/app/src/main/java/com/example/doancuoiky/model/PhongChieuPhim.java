package com.example.doancuoiky.model;

public class PhongChieuPhim {
    private String maPhongChieu;
    private int soChoNgoi;
    private String maRapPhim;

    public PhongChieuPhim(String maPhongChieu, int soChoNgoi, String maRapPhim) {
        this.maPhongChieu = maPhongChieu;
        this.soChoNgoi = soChoNgoi;
        this.maRapPhim = maRapPhim;
    }

    public PhongChieuPhim() {
    }

    public String getMaPhongChieu() {
        return maPhongChieu;
    }

    public void setMaPhongChieu(String maPhongChieu) {
        this.maPhongChieu = maPhongChieu;
    }

    public int getSoChoNgoi() {
        return soChoNgoi;
    }

    public void setSoChoNgoi(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
    }

    public String getMaRapPhim() {
        return maRapPhim;
    }

    public void setMaRapPhim(String maRapPhim) {
        this.maRapPhim = maRapPhim;
    }

    @Override
    public String toString() {
        return "PhongChieuPhim{" +
                "maPhongChieu='" + maPhongChieu + '\'' +
                ", soChoNgoi=" + soChoNgoi +
                ", maRapPhim='" + maRapPhim + '\'' +
                '}';
    }
}
