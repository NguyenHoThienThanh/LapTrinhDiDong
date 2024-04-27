package com.example.doancuoiky.model;

import java.util.Date;

public class KhachHang {
    private String maKhachHang, hoTen, diaChi, email, soDienThoai, userName;
    private int gioiTinh;
    private String ngaySinh;
    private byte[] avatar;

    public KhachHang() {
    }

    public KhachHang(String maKhachHang, String hoTen, String diaChi, String email, String soDienThoai, String userName, int gioiTinh, String ngaySinh, byte[] avatar) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.userName = userName;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.avatar = avatar;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
