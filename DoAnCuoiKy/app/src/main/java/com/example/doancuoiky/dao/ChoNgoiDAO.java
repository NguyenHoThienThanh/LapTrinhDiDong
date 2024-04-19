package com.example.doancuoiky.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doancuoiky.model.ChoNgoi;

import java.util.ArrayList;

public class ChoNgoiDAO {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;

    public ChoNgoiDAO(Context context) {
        helper = new SQLHelper(context);
        sqlDB = helper.openDatabase();
    }

    public ArrayList<ChoNgoi> findAllChoNgoi() {
        ArrayList<ChoNgoi> choNgoiArrayList = new ArrayList<>();
        Cursor c = sqlDB.query("ChoNgoi", null, null,
                null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            ChoNgoi cn = new ChoNgoi();
            cn.setMaChoNgoi(c.getString(0));
            cn.setHangGhe(c.getString(1));
            cn.setSoGhe(c.getInt(2));
            cn.setMaPhongChieu(c.getString(3));
            choNgoiArrayList.add(cn);
            c.moveToNext();
        }
        // Đóng Cursor sau khi sử dụng
        c.close();
        return choNgoiArrayList;
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
