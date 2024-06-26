package com.example.doancuoiky.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.doancuoiky.model.ChiTietSuatChieu;
import com.example.doancuoiky.model.Phim;
import com.example.doancuoiky.model.PhongChieuPhim;

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

        while (c.moveToNext()) {
            ChiTietSuatChieu ctsc = new ChiTietSuatChieu();
            ctsc.setMaSuatChieu(c.getString(0));
            ctsc.setMaPhongChieu(c.getString(1));
            ctsc.setGioChieu(c.getString(2));
            ctsc.setNgayChieu(c.getString(3));
            timeList.add(ctsc);
        }

        c.close();
        return timeList;
    }

    public ChiTietSuatChieu findOneByMaSuatChieu(String maSuatChieu) {
        sqlDB = helper.getReadableDatabase();
        ChiTietSuatChieu chiTietSuatChieu = new ChiTietSuatChieu();
        String query = "select * FROM SuatChieu where maSuatChieu = ?";
        Cursor c = sqlDB.rawQuery(query, new String[]{maSuatChieu});
        if (c != null && c.moveToFirst()) {
            do {
                chiTietSuatChieu.setMaSuatChieu(c.getString(0));
                chiTietSuatChieu.setMaPhongChieu(c.getString(1));
                chiTietSuatChieu.setMaPhim(c.getString(2));
                chiTietSuatChieu.setGioChieu(c.getString(3));
                chiTietSuatChieu.setNgayChieu(c.getString(4));

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }

        return chiTietSuatChieu;
    }

    public ArrayList<ChiTietSuatChieu> findAll() {
        ArrayList<ChiTietSuatChieu> list = new ArrayList<>();
        Cursor c = sqlDB.query("SuatChieu", null, null, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            do {
                ChiTietSuatChieu ct = new ChiTietSuatChieu();
                ct.setMaSuatChieu(c.getString(0));
                ct.setMaPhongChieu(c.getString(1));
                ct.setMaPhim(c.getString(2));
                ct.setNgayChieu(c.getString(4));
                ct.setGioChieu(c.getString(3));
                list.add(ct);
            } while (c.moveToNext());

            if (c != null) {
                c.close();
            }
        }
        return list;
    }

    public boolean insert(ChiTietSuatChieu suatChieu) {
        sqlDB = helper.getReadableDatabase();
        sqlDB = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maPhongChieu", suatChieu.getMaPhongChieu());
        values.put("maPhim", suatChieu.getMaPhim());
        values.put("thoiGianChieu", suatChieu.getGioChieu());
        values.put("ngayChieu", suatChieu.getNgayChieu());

        long res = sqlDB.insert("SuatChieu", null, values);
        if (res == -1) return false;
        else return true;
    }

    public boolean update(ChiTietSuatChieu suatChieu) {
        sqlDB = helper.getReadableDatabase();
        sqlDB = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maSuatChieu", suatChieu.getMaSuatChieu());
        values.put("maPhongChieu", suatChieu.getMaPhongChieu());
        values.put("maPhim", suatChieu.getMaPhim());
        values.put("thoiGianChieu", suatChieu.getGioChieu());
        values.put("ngayChieu", suatChieu.getNgayChieu());

        long res = sqlDB.update("SuatChieu", values, "maSuatChieu=?", new String[]{suatChieu.getMaSuatChieu()});
        if (res == -1) return false;
        else return true;
    }

    public boolean delete(String maSuatChieu) {
        sqlDB = helper.getReadableDatabase();
        sqlDB = helper.getWritableDatabase();
        long res = sqlDB.delete("SuatChieu", "maSuatChieu=?", new String[]{maSuatChieu});
        if (res == -1) return false;
        else return true;
    }
}
