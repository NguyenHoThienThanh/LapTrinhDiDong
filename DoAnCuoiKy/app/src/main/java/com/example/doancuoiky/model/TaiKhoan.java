package com.example.doancuoiky.model;

public class TaiKhoan {
    private String taiKhoan, matKhau;
<<<<<<< HEAD
    private int roleId;

    public TaiKhoan(String taiKhoan, String matKhau, int roleId) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.roleId = roleId;
    }
=======
    private int roleID;
>>>>>>> 8ed55834da8159953bd3d78c3194e4bc181a68b2

    public TaiKhoan() {
    }

<<<<<<< HEAD
=======
    public TaiKhoan(String taiKhoan, String matKhau, int roleID) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.roleID = roleID;
    }

>>>>>>> 8ed55834da8159953bd3d78c3194e4bc181a68b2
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

<<<<<<< HEAD
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
=======
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
>>>>>>> 8ed55834da8159953bd3d78c3194e4bc181a68b2
    }
}
