package com.example.doancuoiky.model;

public class TaiKhoan {
    private String taiKhoan, matKhau;
    private int roleID;

    public TaiKhoan() {
    }

    public TaiKhoan(String taiKhoan, String matKhau, int roleID) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.roleID = roleID;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "taiKhoan='" + taiKhoan + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", roleID=" + roleID +
                '}';
    }
}
