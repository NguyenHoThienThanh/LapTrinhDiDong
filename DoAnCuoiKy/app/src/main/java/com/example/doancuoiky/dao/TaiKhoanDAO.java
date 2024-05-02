package com.example.doancuoiky.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doancuoiky.model.TaiKhoan;

public class TaiKhoanDAO {

    private SQLiteDatabase sqlDB;
    private SQLHelper helper;

    public TaiKhoanDAO(Context context) {
        helper = new SQLHelper(context);
        helper.processCopy();
        sqlDB = helper.getWritableDatabase();
    }

    public TaiKhoan findOneByTaiKhoan(String account) {
        sqlDB = helper.getReadableDatabase();
        TaiKhoan taiKhoan = new TaiKhoan();
        String query = "SELECT * FROM TaiKhoan where soDienThoai = ?";
        Cursor c = sqlDB.rawQuery(query, new String[]{account});
        if (c != null && c.moveToFirst()) {
            do {
                taiKhoan.setTaiKhoan(c.getString(0));
                taiKhoan.setMatKhau(c.getString(1));
                taiKhoan.setRoleId(c.getInt(2));
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return taiKhoan;
    }

    public boolean update(TaiKhoan taiKhoan) {
        sqlDB = helper.getReadableDatabase();
        sqlDB = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matKhau", taiKhoan.getMatKhau());
        try {
            if (sqlDB.update("TaiKhoan", values, "soDienThoai = ?", new String[]{taiKhoan.getTaiKhoan()}) < 0) {
                return false;
            }
        } catch (Exception ex) {
        }
        return true;
    }

    public TaiKhoan login(String soDT, String matKhau){
        sqlDB = helper.getReadableDatabase();
        TaiKhoan taiKhoan = null;
        String query = "SELECT * FROM TaiKhoan where soDienThoai = ? AND matKhau = ?";
        Cursor c = sqlDB.rawQuery(query, new String[]{soDT, matKhau});
        if (c != null && c.moveToFirst()) {
            taiKhoan = new TaiKhoan();
            do {
                taiKhoan.setTaiKhoan(c.getString(0));
                taiKhoan.setMatKhau(c.getString(1));
                taiKhoan.setRoleId(c.getInt(2));
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return taiKhoan;
    }
}
