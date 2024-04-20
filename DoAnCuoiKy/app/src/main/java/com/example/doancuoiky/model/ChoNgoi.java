package com.example.doancuoiky.model;

import java.io.Serializable;

public class ChoNgoi implements Serializable {
    private String maChoNgoi;
    private String hangGhe;
    private int soGhe;
    private String maPhongChieu;
    public ChoNgoi() {
    }

    public ChoNgoi(String maChoNgoi, String hangGhe, int soGhe,  String maPhongChieu) {
        this.maChoNgoi = maChoNgoi;
        this.hangGhe = hangGhe;
        this.soGhe = soGhe;
        this.maPhongChieu = maPhongChieu;
    }

    public String getMaChoNgoi() {
        return maChoNgoi;
    }

    public void setMaChoNgoi(String maChoNgoi) {
        this.maChoNgoi = maChoNgoi;
    }

    public String getHangGhe() {
        return hangGhe;
    }

    public void setHangGhe(String hangGhe) {
        this.hangGhe = hangGhe;
    }


    public String getMaPhongChieu() {
        return maPhongChieu;
    }

    public void setMaPhongChieu(String maPhongChieu) {
        this.maPhongChieu = maPhongChieu;
    }
    public int getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }

}
