package com.example.doancuoiky.model;

public class RapPhim {
    private String maRapPhim, tenRap, diaChi;

    public RapPhim() {
    }

    public RapPhim(String maRapPhim, String tenRap, String diaChi) {
        this.maRapPhim = maRapPhim;
        this.tenRap = tenRap;
        this.diaChi = diaChi;
    }

    public String getMaRapPhim() {
        return maRapPhim;
    }

    public void setMaRapPhim(String maRapPhim) {
        this.maRapPhim = maRapPhim;
    }

    public String getTenRap() {
        return tenRap;
    }

    public void setTenRap(String tenRap) {
        this.tenRap = tenRap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "RapPhim{" +
                "maRapPhim='" + maRapPhim + '\'' +
                ", tenRap='" + tenRap + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}