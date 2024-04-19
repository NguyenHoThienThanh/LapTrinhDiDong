package com.example.doancuoiky.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doancuoiky.model.PhongChieuPhim;

import java.util.ArrayList;

public class PhongChieuPhimDAO {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;
    public PhongChieuPhimDAO(Context context) {
        helper = new SQLHelper(context);
        sqlDB = helper.openDatabase();
    }

    public ArrayList<PhongChieuPhim> findAllPhongChieuPhim(){
        ArrayList<PhongChieuPhim> phongChieuPhimArrayList = new ArrayList<>();
        Cursor c = sqlDB.query("PhongChieuPhim", null,null,
                null,null,null,null);
        // Kiểm tra xem Cursor có dữ liệu không trước khi truy cập
        if (c != null && c.moveToFirst()) {
            do {
                PhongChieuPhim pcp = new PhongChieuPhim();
                pcp.setMaPhongChieu(c.getString(0));
                pcp.setSoChoNgoi(c.getInt(1));
                pcp.setMaRapPhim(c.getString(2));
                phongChieuPhimArrayList.add(pcp);
            } while (c.moveToNext());
        }
        // Đóng Cursor sau khi sử dụng
        if (c != null) {
            c.close();
        }
        return phongChieuPhimArrayList;
    }
}
