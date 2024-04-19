package com.example.doancuoiky.model;

public class PhongChieuPhim {
<<<<<<< HEAD
    private String maPhongChieu, maRapPhim;
    private int soChoNgoi;

    public PhongChieuPhim() {
    }

    public PhongChieuPhim(String maPhongChieu, String maRapPhim, int soChoNgoi) {
        this.maPhongChieu = maPhongChieu;
        this.maRapPhim = maRapPhim;
        this.soChoNgoi = soChoNgoi;
=======
    private String maPhongChieu;
    private int soChoNgoi;
    private String maRapPhim;

    public PhongChieuPhim(String maPhongChieu, int soChoNgoi, String maRapPhim) {
        this.maPhongChieu = maPhongChieu;
        this.soChoNgoi = soChoNgoi;
        this.maRapPhim = maRapPhim;
    }

    public PhongChieuPhim() {
>>>>>>> 8ed55834da8159953bd3d78c3194e4bc181a68b2
    }

    public String getMaPhongChieu() {
        return maPhongChieu;
    }

    public void setMaPhongChieu(String maPhongChieu) {
        this.maPhongChieu = maPhongChieu;
    }

<<<<<<< HEAD
=======
    public int getSoChoNgoi() {
        return soChoNgoi;
    }

    public void setSoChoNgoi(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
    }

>>>>>>> 8ed55834da8159953bd3d78c3194e4bc181a68b2
    public String getMaRapPhim() {
        return maRapPhim;
    }

    public void setMaRapPhim(String maRapPhim) {
        this.maRapPhim = maRapPhim;
    }

<<<<<<< HEAD
    public int getSoChoNgoi() {
        return soChoNgoi;
    }

    public void setSoChoNgoi(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
=======
    @Override
    public String toString() {
        return "PhongChieuPhim{" +
                "maPhongChieu='" + maPhongChieu + '\'' +
                ", soChoNgoi=" + soChoNgoi +
                ", maRapPhim='" + maRapPhim + '\'' +
                '}';
>>>>>>> 8ed55834da8159953bd3d78c3194e4bc181a68b2
    }
}
