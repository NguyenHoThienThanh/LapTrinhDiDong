package com.example.doancuoiky.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doancuoiky.model.ChiTietGheDaDat;

import java.util.ArrayList;
import java.util.List;

public class ChiTietGheDaDatDAO {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;

    public ChiTietGheDaDatDAO(Context context) {
        helper = new SQLHelper(context);
        sqlDB = helper.getWritableDatabase();
    }
    public List<ChiTietGheDaDat> getBookedSeatsByShow(String maSuatChieu) {
        List<ChiTietGheDaDat> bookedSeatList = new ArrayList<>();

        // Truy vấn dữ liệu từ bảng "ChiTietGheDaDat" cho suất chiếu cụ thể
        Cursor cursor = sqlDB.query("ChiTietGheDaDat", null, "maSuatChieu = ?", new String[]{maSuatChieu}, null, null, null);
        while (cursor.moveToNext()) {
            ChiTietGheDaDat bookedSeat = new ChiTietGheDaDat();
            bookedSeat.setMaChoNgoi(cursor.getString(1));
            bookedSeat.setTinhTrang(cursor.getInt(2));
            // Thêm ghế đã đặt vào danh sách
            bookedSeatList.add(bookedSeat);
        }
        cursor.close();

        return bookedSeatList;
    }
}
