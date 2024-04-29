package com.example.doancuoiky.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.doancuoiky.model.ChoNgoi;
import com.example.doancuoiky.model.PhongChieuPhim;

import java.util.ArrayList;

public class PhongChieuPhimDAO {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;
    public PhongChieuPhimDAO(Context context) {
        helper = new SQLHelper(context);
//        helper.processCopy();
        sqlDB = helper.getWritableDatabase();
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
                phongChieuPhimArrayList.add(pcp);
            } while (c.moveToNext());
        }
        // Đóng Cursor sau khi sử dụng
        if (c != null) {
            c.close();
        }
        return phongChieuPhimArrayList;
    }
    public boolean insertPhongChieuPhim(PhongChieuPhim pcp) {
        ContentValues values = new ContentValues();
        values.put("SoChoNgoi", pcp.getSoChoNgoi());
        try {
            // Thực hiện thêm dữ liệu vào bảng
            long result = sqlDB.insert("PhongChieuPhim", null, values);
            // Nếu result = -1 thì thêm không thành công, ngược lại thêm thành công
            return result != -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
