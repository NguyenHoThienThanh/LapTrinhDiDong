package com.example.doancuoiky.model;

public class TaiKhoan {
    private String taiKhoan, matKhau;
    private int roleId;

    public TaiKhoan(String taiKhoan, String matKhau, int roleId) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.roleId = roleId;
    }

    public TaiKhoan() {
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
