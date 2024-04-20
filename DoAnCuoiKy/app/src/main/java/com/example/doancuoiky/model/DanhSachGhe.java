package com.example.doancuoiky.model;

import java.io.Serializable;

public class DanhSachGhe implements Serializable {
    private String maChoNgoi;
    private String hangGhe;
    private int soGhe;
    private String maPhongChieu;
    private String maSuatChieu;
    private int tinhTrang;
    private boolean isSelected;

    public DanhSachGhe(String maChoNgoi, String hangGhe, int soGhe, String maPhongChieu, String maSuatChieu, int tinhTrang) {
        this.maChoNgoi = maChoNgoi;
        this.hangGhe = hangGhe;
        this.soGhe = soGhe;
        this.maPhongChieu = maPhongChieu;
        this.maSuatChieu = maSuatChieu;
        this.tinhTrang = tinhTrang;
    }

    public DanhSachGhe() {
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

    public int getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }

    public String getMaPhongChieu() {
        return maPhongChieu;
    }

    public void setMaPhongChieu(String maPhongChieu) {
        this.maPhongChieu = maPhongChieu;
    }

    public String getMaSuatChieu() {
        return maSuatChieu;
    }

    public void setMaSuatChieu(String maSuatChieu) {
        this.maSuatChieu = maSuatChieu;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
