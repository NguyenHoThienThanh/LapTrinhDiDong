package com.example.doancuoiky.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doancuoiky.model.RapPhim;

import java.util.ArrayList;

public class RapPhimDAO {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;
    public RapPhimDAO(Context context) {
        helper = new SQLHelper(context);
        sqlDB = helper.openDatabase();
    }
    public ArrayList<RapPhim> findAllRapPhim(){
        ArrayList<RapPhim> rapPhimArrayList = new ArrayList<>();
        Cursor c = sqlDB.query("RapPhim", null,null,
                null,null,null,null);
        // Kiểm tra xem Cursor có dữ liệu không trước khi truy cập
        if (c != null && c.moveToFirst()) {
            do {
                RapPhim rp = new RapPhim();
                rp.setMaRapPhim(c.getString(0));
                rp.setTenRap(c.getString(1));
                rp.setDiaChi(c.getString(2));
                rapPhimArrayList.add(rp);
            } while (c.moveToNext());
        }
        // Đóng Cursor sau khi sử dụng
        if (c != null) {
            c.close();
        }
        return rapPhimArrayList;
    }
}
