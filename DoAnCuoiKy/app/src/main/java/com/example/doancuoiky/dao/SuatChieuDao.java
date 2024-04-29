package com.example.doancuoiky.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.doancuoiky.model.ChiTietSuatChieu;
import com.example.doancuoiky.model.Phim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SuatChieuDao {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;

    public SuatChieuDao(Context context) {
        helper = new SQLHelper(context);
        helper.processCopy();
        sqlDB = helper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<ChiTietSuatChieu> getDateBySuatChieu(String maPhim) {
        ArrayList<ChiTietSuatChieu> dateList = new ArrayList<>();
        sqlDB = helper.getReadableDatabase();

        String query = "SELECT DISTINCT Phim.maPhim, SuatChieu.ngayChieu FROM Phim INNER JOIN SuatChieu ON Phim.maPhim = SuatChieu.maPhim WHERE Phim.maPhim=?";
        // Thêm tham số vào câu lệnh truy vấn
        Cursor c = sqlDB.rawQuery(query, new String[]{maPhim});
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        c.moveToFirst();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            ChiTietSuatChieu ctsc = new ChiTietSuatChieu();
            ctsc.setMaPhim(c.getString(0));
            ctsc.setNgayChieu(c.getString(1));
            dateList.add(ctsc);
            c.moveToNext();
        }
        // Đóng Cursor sau khi sử dụng
        c.close();
        return dateList;
    }

    @SuppressLint("Range")
    public ArrayList<ChiTietSuatChieu> getTimeBySuatChieu(String maPhim, String ngayChieu) {
        ArrayList<ChiTietSuatChieu> timeList = new ArrayList<>();
        sqlDB = helper.getReadableDatabase();
        String query = "SELECT SuatChieu.maSuatChieu, SuatChieu.maPhongChieu, SuatChieu.thoiGianChieu, SuatChieu.ngayChieu FROM Phim INNER JOIN SuatChieu ON Phim.maPhim = SuatChieu.maPhim WHERE Phim.maPhim=? AND SuatChieu.ngayChieu=?";
        Cursor c = sqlDB.rawQuery(query, new String[]{maPhim, ngayChieu});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            ChiTietSuatChieu ctsc = new ChiTietSuatChieu();
            ctsc.setMaSuatChieu(c.getString(0));
            ctsc.setMaPhongChieu(c.getString(1));
            ctsc.setGioChieu(c.getString(2));
            ctsc.setNgayChieu(c.getString(3));
            timeList.add(ctsc);
            c.moveToNext();
        }
        c.close();
        return timeList;
    }
    public ChiTietSuatChieu findOneByMaSuatChieu(String maSuatChieu){
        sqlDB = helper.getReadableDatabase();
        ChiTietSuatChieu chiTietSuatChieu = new ChiTietSuatChieu();
        String query = "select * FROM SuatChieu where maSuatChieu = ?";
        Cursor c =sqlDB.rawQuery(query, new String[]{maSuatChieu} );
        if(c != null && c.moveToFirst()){
            do{
                chiTietSuatChieu.setMaSuatChieu(c.getString(0));
                chiTietSuatChieu.setMaPhongChieu(c.getString(1));
                chiTietSuatChieu.setMaPhim(c.getString(2));
                chiTietSuatChieu.setGioChieu(c.getString(3));
                chiTietSuatChieu.setNgayChieu(c.getString(4));

            }while(c.moveToNext());
        }
        if(c != null){
            c.close();
        }

        return chiTietSuatChieu;
    }
}
