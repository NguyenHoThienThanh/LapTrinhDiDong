package com.example.doancuoiky.model;

public class ThongKeTheoPhim {

    private String tenPhim;
    private int soLuongVe;
    private String thang;


    public ThongKeTheoPhim() {
    }

    public ThongKeTheoPhim(String tenPhim, int soLuongVe, String thang) {
        this.tenPhim = tenPhim;
        this.soLuongVe = soLuongVe;
        this.thang = thang;
    }

    // Getters and setters
    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public int getSoLuongVe() {
        return soLuongVe;
    }

    public void setSoLuongVe(int soLuongVe) {
        this.soLuongVe = soLuongVe;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }
}
