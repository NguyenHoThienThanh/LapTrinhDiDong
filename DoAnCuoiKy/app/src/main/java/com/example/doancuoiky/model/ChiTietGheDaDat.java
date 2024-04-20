package com.example.doancuoiky.model;

public class ChiTietGheDaDat {
    private String maSuatChieu;
    private String maChoNgoi;
    private int tinhTrang;
    private boolean isSelected;

    public ChiTietGheDaDat() {
    }

    public ChiTietGheDaDat(String maSuatChieu, String maChoNgoi, int tinhTrang) {
        this.maSuatChieu = maSuatChieu;
        this.maChoNgoi = maChoNgoi;
        this.tinhTrang = tinhTrang;
    }

    public String getMaSuatChieu() {
        return maSuatChieu;
    }

    public void setMaSuatChieu(String maSuatChieu) {
        this.maSuatChieu = maSuatChieu;
    }

    public String getMaChoNgoi() {
        return maChoNgoi;
    }

    public void setMaChoNgoi(String maChoNgoi) {
        this.maChoNgoi = maChoNgoi;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
