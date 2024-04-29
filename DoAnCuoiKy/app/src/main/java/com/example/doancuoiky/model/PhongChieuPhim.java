package com.example.doancuoiky.model;

public class PhongChieuPhim {
    private String maPhongChieu;
    private int soChoNgoi;

    public PhongChieuPhim() {
    }

    public PhongChieuPhim(String maPhongChieu, int soChoNgoi) {
        this.maPhongChieu = maPhongChieu;
        this.soChoNgoi = soChoNgoi;
    }

    public PhongChieuPhim(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
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

    @Override
    public String toString() {
        return "PhongChieuPhim{" +
                "maPhongChieu='" + maPhongChieu + '\'' +
                ", soChoNgoi=" + soChoNgoi +
                '}';
    }
}

