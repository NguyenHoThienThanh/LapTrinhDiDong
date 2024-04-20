package com.example.doancuoiky.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doancuoiky.model.ChoNgoi;
import com.example.doancuoiky.model.DanhSachGhe;

import java.util.ArrayList;

public class ChoNgoiDAO {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;

    public ChoNgoiDAO(Context context) {
        helper = new SQLHelper(context);
        helper.processCopy();
        sqlDB = helper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<DanhSachGhe> getSeatBySuatChieu(String maSuatChieu, String maPhongChieu) {
        ArrayList<DanhSachGhe> seatList = new ArrayList<>();
        sqlDB = helper.getReadableDatabase();

        String query = "SELECT ChoNgoi.maChoNgoi, hangGhe, soGhe, maPhongChieu, maSuatChieu, tinhTrang FROM ChoNgoi LEFT OUTER JOIN (SELECT *FROM ChiTietGheDaDat WHERE ChiTietGheDaDat.maSuatChieu = ?) AS Q ON ChoNgoi.maChoNgoi = Q.maChoNgoi WHERE ChoNgoi.maPhongChieu = ?";

        // Thêm tham số vào câu lệnh truy vấn
        Cursor c = sqlDB.rawQuery(query, new String[]{maSuatChieu, maPhongChieu});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            DanhSachGhe dsg = new DanhSachGhe();
            dsg.setMaChoNgoi(c.getString(0));
            dsg.setHangGhe(c.getString(1));
            dsg.setSoGhe(c.getInt(2));
            dsg.setMaPhongChieu(c.getString(3));
            dsg.setMaSuatChieu(c.getString(4));
            if(c.isNull(5)){
                dsg.setTinhTrang(0);
            }
            else{
                dsg.setTinhTrang(c.getInt(5));
            }
            seatList.add(dsg);
            c.moveToNext();
        }
        // Đóng Cursor sau khi sử dụng
        c.close();
        return seatList;
    }

//    public boolean insertChoNgoi(ChoNgoi choNgoi){
//        ContentValues values = new ContentValues();
//        values.put("maChoNgoi", choNgoi.getMaChoNgoi());
//        values.put("hangGhe", choNgoi.getHangGhe());
//        values.put("soGhe", choNgoi.getSoGhe());
//        values.put(:, choNgoi.getTinhTrang());
//        values.put(SQLHelper.TABLE_CHO_NGOI_MAPHONGCHIEU, choNgoi.getMaPhongChieu());
//        long res = sqlDB.insert(SQLHelper.TABLE_CHO_NGOI, null, values);
//        if(res == -1) return false;
//        else return true;
//    }
//
//    public boolean updateChoNgoi(ChoNgoi choNgoi){
//        ContentValues values = new ContentValues();
//        values.put(SQLHelper.TABLE_CHO_NGOI_HANGGHE, choNgoi.getHangGhe());
//        values.put(SQLHelper.TABLE_CHO_NGOI_SOGHE, choNgoi.getSoGhe());
//        values.put(SQLHelper.TABLE_CHO_NGOI_TINHTRANG, choNgoi.getTinhTrang());
//        values.put(SQLHelper.TABLE_CHO_NGOI_MAPHONGCHIEU, choNgoi.getMaPhongChieu());
//
//        long res = sqlDB.update(SQLHelper.TABLE_CHO_NGOI, values, SQLHelper.TABLE_CHO_NGOI_MACHONGOI + " =?", new String[]{choNgoi.getMaChoNgoi()});
//        if(res == 1) return false;
//        else return true;
//    }
//
//    public boolean deleteChoNgoi(String maChoNgoi){
//        long res = sqlDB.delete(SQLHelper.TABLE_CHO_NGOI, SQLHelper.TABLE_CHO_NGOI + " =?", new String[]{maChoNgoi});
//        if(res == -1) return false;
//        else return true;
//    }


}
