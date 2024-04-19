package com.example.doancuoiky.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;

public class SQLHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CinemaDB";
    public static final int DATABASE_VERSION = 3;

    public static final String TABLE_NHAN_VIEN = "NhanVien";
    public static final String TABLE_NHAN_VIEN_MANHANVIEN = "maNhanVien";
    public static final String TABLE_NHAN_VIEN_HOTEN = "hoTen";
    public static final String TABLE_NHAN_VIEN_NGAYSINH = "ngaySinh";
    public static final String TABLE_NHAN_VIEN_GIOITINH = "gioiTinh";
    public static final String TABLE_NHAN_VIEN_DIACHI = "diaChi";
    public static final String TABLE_NHAN_VIEN_EMAIL = "email";
    public static final String TABLE_NHAN_VIEN_SODIENTHOAI= "sodienThoai";

    public static final String TABLE_KHACH_HANG = "KhachHang";
    public static final String TABLE_KHACH_HANG_MAKHACHHANG = "maKhachHang";
    public static final String TABLE_KHACH_HANG_HOTEN = "hoTen";
    public static final String TABLE_KHACH_HANG_NGAYSINH = "ngaySinh";
    public static final String TABLE_KHACH_HANG_GIOITINH = "gioiTinh";
    public static final String TABLE_KHACH_HANG_DIACHI = "diaChi";
    public static final String TABLE_KHACH_HANG_EMAIL = "email";
    public static final String TABLE_KHACH_HANG_SODIENTHOAI= "sodienThoai";

    public static final String TABLE_TAI_KHOAN = "TaiKhoan";
    public static final String TABLE_TAI_KHOAN_TAIKHOAN = "taiKhoan";
    public static final String TABLE_TAI_KHOAN_MATKHAU = "matKhau";
    public static final String TABLE_TAI_KHOAN_ROLEID = "roleID";

    public static final String TABLE_RAP_PHIM = "RapPhim";
    public static final String TABLE_RAP_PHIM_MARAPPHIM = "maRap";
    public static final String TABLE_RAP_PHIM_TENRAP = "tenRap";
    public static final String TABLE_RAP_PHIM_DIACHI = "diaChi";

    public static final String TABLE_PHONG_CHIEU_PHIM = "PhongChieuPhim";
    public static final String TABLE_PHONG_CHIEU_PHIM_MAPHONGCHIEU = "maPhongChieu";
    public static final String TABLE_PHONG_CHIEU_PHIM_SOCHONGOI = "soChoNgoi";
    public static final String TABLE_PHONG_CHIEU_PHIM_MARAPPHIM = "maRapPhim";

    public static final String TABLE_CHO_NGOI = "ChoNgoi";
    public static final String TABLE_CHO_NGOI_MACHONGOI = "maChoNgoi";
    public static final String TABLE_CHO_NGOI_HANGGHE = "hangGhe";
    public static final String TABLE_CHO_NGOI_SOGHE = "soGhe";
    public static final String TABLE_CHO_NGOI_TINHTRANG = "tinhTrang";
    public static final String TABLE_CHO_NGOI_MAPHONGCHIEU = "maPhongChieu";

    public static final String TABLE_PHIM = "Phim";
    public static final String TABLE_PHIM_MAPHIM = "maPhim";
    public static final String TABLE_PHIM_TENPHIM = "tenPhim";
    public static final String TABLE_PHIM_THOILUONG = "thoiLuong";
    public static final String TABLE_PHIM_GIOIHANDOTUOI = "gioiHanDoTuoi";
    public static final String TABLE_PHIM_MOTAPHIM = "moTaPhim";
    public static final String TABLE_PHIM_DIENVIEN = "dienVien";
    public static final String TABLE_PHIM_TRAILER = "trailer";
    public static final String TABLE_PHIM_GIAVE = "giaVe";


    public static final String TABLE_MAN_HINH_CHIEU_PHIM = "ManHinhChieuPhim";
    public static final String TABLE_MAN_HINH_CHIEU_PHIM_MAMANHINH = "maManHinh";
    public static final String TABLE_MAN_HINH_CHIEU_PHIM_MAPHONGCHIEU = "maPhongChieu";
    public static final String TABLE_MAN_HINH_CHIEU_PHIM_MAPHIM = "maPhim";
    public static final String TABLE_MAN_HINH_CHIEU_PHIM_THOIGIANCHIEU = "thoiGianChieu";


    public static final String TABLE_COMBO_BAP_NUOC = "ComboBapNuoc";

    public static final String TABLE_COMBO_BAP_NUOC_MACOMBO = "maCombo";
    public static final String TABLE_COMBO_BAP_NUOC_HINHANH = "hinhAnh";
    public static final String TABLE_COMBO_BAP_NUOC_TENCOMBO = "tenCombo";
    public static final String TABLE_COMBO_BAP_NUOC_SOLUONG = "soLuong";
    public static final String TABLE_COMBO_BAP_NUOC_GIA = "gia";

    public static final String TABLE_HOA_DON = "HoaDon";
    public static final String TABLE_HOA_DON_MAHOADON = "maHoaDon";
    public static final String TABLE_HOA_DON_MAMANHINH = "maManHinh";
    public static final String TABLE_HOA_DON_MAKHACHHANG = "maKhachHang";
    public static final String TABLE_HOA_DON_MACOMBO = "maCombo";
    public static final String TABLE_HOA_DON_TONGTIEN = "tongTien";


    // Table Create Statements
    public static final String CREATE_TABLE_NHAN_VIEN = "CREATE TABLE " + TABLE_NHAN_VIEN + "("
            + TABLE_NHAN_VIEN_MANHANVIEN + " TEXT PRIMARY KEY,"
            + TABLE_NHAN_VIEN_HOTEN + " TEXT,"
            + TABLE_NHAN_VIEN_NGAYSINH + " TEXT,"
            + TABLE_NHAN_VIEN_GIOITINH + " INTEGER DEFAULT 0,"
            + TABLE_NHAN_VIEN_DIACHI + " TEXT,"
            + TABLE_NHAN_VIEN_EMAIL + " TEXT,"
            + TABLE_NHAN_VIEN_SODIENTHOAI + " TEXT)";
    public static final String CREATE_TABLE_KHACH_HANG = "CREATE TABLE " + TABLE_KHACH_HANG + "("
            + TABLE_KHACH_HANG_MAKHACHHANG + " TEXT PRIMARY KEY,"
            + TABLE_KHACH_HANG_HOTEN + " TEXT,"
            + TABLE_KHACH_HANG_NGAYSINH + " TEXT,"
            + TABLE_KHACH_HANG_GIOITINH + " INTEGER DEFAULT 0,"
            + TABLE_KHACH_HANG_DIACHI + " TEXT,"
            + TABLE_KHACH_HANG_EMAIL + " TEXT,"
            + TABLE_KHACH_HANG_SODIENTHOAI + " TEXT)";

    public static final String CREATE_TABLE_TAI_KHOAN = "CREATE TABLE " + TABLE_TAI_KHOAN + "("
            + TABLE_TAI_KHOAN_TAIKHOAN + " TEXT PRIMARY KEY,"
            + TABLE_TAI_KHOAN_MATKHAU + " TEXT,"
            + TABLE_TAI_KHOAN_ROLEID + " INTEGER)";

    public static final String CREATE_TABLE_RAP_PHIM = "CREATE TABLE " + TABLE_RAP_PHIM + "("
            + TABLE_RAP_PHIM_MARAPPHIM + " TEXT PRIMARY KEY,"
            + TABLE_RAP_PHIM_TENRAP + " TEXT,"
            + TABLE_RAP_PHIM_DIACHI + " TEXT)";

    public static final String CREATE_TABLE_PHONG_CHIEU_PHIM = "CREATE TABLE " + TABLE_PHONG_CHIEU_PHIM + "("
            + TABLE_PHONG_CHIEU_PHIM_MAPHONGCHIEU + " TEXT PRIMARY KEY,"
            + TABLE_PHONG_CHIEU_PHIM_SOCHONGOI + " INTEGER,"
            + TABLE_PHONG_CHIEU_PHIM_MARAPPHIM +" TEXT,"
            + "FOREIGN KEY(" + TABLE_PHONG_CHIEU_PHIM_MARAPPHIM + ") REFERENCES " + TABLE_RAP_PHIM + "(" + TABLE_RAP_PHIM_MARAPPHIM + "))";

    public static final String CREATE_TABLE_CHO_NGOI = "CREATE TABLE " + TABLE_CHO_NGOI + "("
            + TABLE_CHO_NGOI_MACHONGOI + " TEXT PRIMARY KEY,"
            + TABLE_CHO_NGOI_HANGGHE + " TEXT,"
            + TABLE_CHO_NGOI_SOGHE +" INTEGER,"
            + TABLE_CHO_NGOI_TINHTRANG +" INTEGER DEFAULT 0,"
            + TABLE_CHO_NGOI_MAPHONGCHIEU +" TEXT,"
            + "FOREIGN KEY(" + TABLE_CHO_NGOI_MAPHONGCHIEU + ") REFERENCES " + TABLE_PHONG_CHIEU_PHIM + "(" + TABLE_PHONG_CHIEU_PHIM_MAPHONGCHIEU + "))";
    public static final String CREATE_TABLE_PHIM = "CREATE TABLE " + TABLE_PHIM + "("
            + TABLE_PHIM_MAPHIM + " TEXT PRIMARY KEY,"
            + TABLE_PHIM_TENPHIM + " TEXT,"
            + TABLE_PHIM_THOILUONG +" INT,"
            + TABLE_PHIM_GIOIHANDOTUOI +" INT,"
            + TABLE_PHIM_MOTAPHIM + " TEXT,"
            + TABLE_PHIM_DIENVIEN + " TEXT,"
            + TABLE_PHIM_TRAILER + " TEXT,"
            + TABLE_PHIM_GIAVE + " FLOAT)";
    public static final String CREATE_TABLE_MAN_HINH_CHIEU_PHIM = "CREATE TABLE " + TABLE_MAN_HINH_CHIEU_PHIM + "("
            + TABLE_MAN_HINH_CHIEU_PHIM_MAMANHINH + " TEXT PRIMARY KEY,"
            + TABLE_MAN_HINH_CHIEU_PHIM_MAPHONGCHIEU + " TEXT,"
            + TABLE_MAN_HINH_CHIEU_PHIM_MAPHIM +" TEXT,"
            + TABLE_MAN_HINH_CHIEU_PHIM_THOIGIANCHIEU +" TEXT,"
            + "FOREIGN KEY(" + TABLE_MAN_HINH_CHIEU_PHIM_MAPHONGCHIEU + ") REFERENCES " + TABLE_PHONG_CHIEU_PHIM+ "(" + TABLE_PHONG_CHIEU_PHIM_MAPHONGCHIEU + "),"
            + "FOREIGN KEY(" + TABLE_MAN_HINH_CHIEU_PHIM_MAPHIM + ") REFERENCES " + TABLE_PHIM + "(" + TABLE_PHIM_MAPHIM + "))";
    public static final String CREATE_TABLE_COMBO_BAP_NUOC = "CREATE TABLE " + TABLE_COMBO_BAP_NUOC + "("
            + TABLE_COMBO_BAP_NUOC_HINHANH + " INTEGER,"
            + TABLE_COMBO_BAP_NUOC_MACOMBO + " TEXT PRIMARY KEY,"
            + TABLE_COMBO_BAP_NUOC_TENCOMBO + " TEXT,"
            + TABLE_COMBO_BAP_NUOC_SOLUONG +" INTEGER,"
            + TABLE_COMBO_BAP_NUOC_GIA +" FLOAT)";
    public static final String CREATE_TABLE_HOA_DON = "CREATE TABLE " + TABLE_HOA_DON + "("
            + TABLE_HOA_DON_MAHOADON + " TEXT PRIMARY KEY,"
            + TABLE_HOA_DON_MAMANHINH + " TEXT,"
            + TABLE_HOA_DON_MAKHACHHANG +" TEXT,"
            + TABLE_HOA_DON_MACOMBO +" TEXT,"
            + TABLE_HOA_DON_TONGTIEN +" FLOAT,"
            + "FOREIGN KEY(" + TABLE_HOA_DON_MAMANHINH + ") REFERENCES " + TABLE_MAN_HINH_CHIEU_PHIM + "(" + TABLE_MAN_HINH_CHIEU_PHIM_MAMANHINH + "),"
            + "FOREIGN KEY(" + TABLE_HOA_DON_MAKHACHHANG + ") REFERENCES " + TABLE_KHACH_HANG + "(" + TABLE_KHACH_HANG_MAKHACHHANG + "),"
            + "FOREIGN KEY(" + TABLE_HOA_DON_MACOMBO + ") REFERENCES " + TABLE_COMBO_BAP_NUOC + "(" + TABLE_COMBO_BAP_NUOC_MACOMBO + "))";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_NHAN_VIEN);
        db.execSQL(CREATE_TABLE_KHACH_HANG);
        db.execSQL(CREATE_TABLE_TAI_KHOAN);
        db.execSQL(CREATE_TABLE_RAP_PHIM);
        db.execSQL(CREATE_TABLE_PHONG_CHIEU_PHIM);
        db.execSQL(CREATE_TABLE_CHO_NGOI);
        db.execSQL(CREATE_TABLE_PHIM);
        db.execSQL(CREATE_TABLE_MAN_HINH_CHIEU_PHIM);
        db.execSQL(CREATE_TABLE_COMBO_BAP_NUOC);
        db.execSQL(CREATE_TABLE_HOA_DON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHAN_VIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KHACH_HANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAI_KHOAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAP_PHIM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHONG_CHIEU_PHIM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHO_NGOI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHIM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAN_HINH_CHIEU_PHIM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMBO_BAP_NUOC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOA_DON);
        // create new tables
        onCreate(db);
    }
}
