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
    private Context context;

    public ChoNgoiDAO(Context context) {
        this.context = context;
        helper = new SQLHelper(context);
        sqlDB = helper.getWritableDatabase();
    }

    public ArrayList<ChoNgoi> findAllChoNgoi(){
        ArrayList<ChoNgoi> choNgoiArrayList = new ArrayList<>();
        Cursor c = sqlDB.query(SQLHelper.TABLE_CHO_NGOI, null,null,
                null,null,null,null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            ChoNgoi cn = new ChoNgoi();
            cn.setMaChoNgoi(c.getString(0));
            cn.setHangGhe(c.getString(1));
            cn.setSoGhe(c.getInt(2));
            cn.setTinhTrang(c.getInt(3));
            cn.setMaPhongChieu(c.getString(4));
            choNgoiArrayList.add(cn);

            c.moveToNext();
        }
        c.close();
        return choNgoiArrayList;
    }

    public boolean insertChoNgoi(ChoNgoi choNgoi){
        ContentValues values = new ContentValues();
        values.put(SQLHelper.TABLE_CHO_NGOI_MACHONGOI, choNgoi.getMaChoNgoi());
        values.put(SQLHelper.TABLE_CHO_NGOI_HANGGHE, choNgoi.getHangGhe());
        values.put(SQLHelper.TABLE_CHO_NGOI_SOGHE, choNgoi.getSoGhe());
        values.put(SQLHelper.TABLE_CHO_NGOI_TINHTRANG, choNgoi.getTinhTrang());
        values.put(SQLHelper.TABLE_CHO_NGOI_MAPHONGCHIEU, choNgoi.getMaPhongChieu());
        long res = sqlDB.insert(SQLHelper.TABLE_CHO_NGOI, null, values);
        if(res == -1) return false;
        else return true;
    }

    public boolean updateChoNgoi(ChoNgoi choNgoi){
        ContentValues values = new ContentValues();
        values.put(SQLHelper.TABLE_CHO_NGOI_HANGGHE, choNgoi.getHangGhe());
        values.put(SQLHelper.TABLE_CHO_NGOI_SOGHE, choNgoi.getSoGhe());
        values.put(SQLHelper.TABLE_CHO_NGOI_TINHTRANG, choNgoi.getTinhTrang());
        values.put(SQLHelper.TABLE_CHO_NGOI_MAPHONGCHIEU, choNgoi.getMaPhongChieu());

        long res = sqlDB.update(SQLHelper.TABLE_CHO_NGOI, values, SQLHelper.TABLE_CHO_NGOI_MACHONGOI + " =?", new String[]{choNgoi.getMaChoNgoi()});
        if(res == 1) return false;
        else return true;
    }

    public boolean deleteChoNgoi(String maChoNgoi){
        long res = sqlDB.delete(SQLHelper.TABLE_CHO_NGOI, SQLHelper.TABLE_CHO_NGOI + " =?", new String[]{maChoNgoi});
        if(res == -1) return false;
        else return true;
    }


}
