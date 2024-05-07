package com.example.doancuoiky.model;

public class ThongKeTheoThang {
    private String nam;
    private String thang;
    private double tongTienThang;

    public ThongKeTheoThang() {
    }

    public ThongKeTheoThang(String nam, String thang, double tongTienThang) {
        this.nam = nam;
        this.thang = thang;
        this.tongTienThang = tongTienThang;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public double getTongTienThang() {
        return tongTienThang;
    }

    public void setTongTienThang(double tongTienThang) {
        this.tongTienThang = tongTienThang;
    }
}
