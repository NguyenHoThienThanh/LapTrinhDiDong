package com.example.doancuoiky.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doancuoiky.model.ChoNgoi;
import com.example.doancuoiky.model.ComboBapNuoc;

import java.util.ArrayList;

public class ComboBapNuocDAO {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;
    private Context context;

    public ComboBapNuocDAO(Context context) {
        this.context = context;
        helper = new SQLHelper(context);
        sqlDB = helper.getWritableDatabase();
    }

    public ArrayList<ComboBapNuoc> findAllCombo(){
        ArrayList<ComboBapNuoc> comboBapNuocArrayList = new ArrayList<>();
        Cursor c = sqlDB.query(SQLHelper.TABLE_COMBO_BAP_NUOC, null,null,
                null,null,null,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            ComboBapNuoc cb = new ComboBapNuoc();
            cb.setHinhAnh(c.getInt(0));
            cb.setMaCombo(c.getString(1));
            cb.setTenCombo(c.getString(2));
            cb.setSoLuong(c.getInt(3));
            cb.setGia(c.getFloat(4));
            comboBapNuocArrayList.add(cb);

            c.moveToNext();
        }
        c.close();
        return comboBapNuocArrayList;
    }

    public boolean insertCombo(ComboBapNuoc comboBapNuoc){
        ContentValues values = new ContentValues();
        values.put(SQLHelper.TABLE_COMBO_BAP_NUOC_HINHANH, comboBapNuoc.getHinhAnh());
        values.put(SQLHelper.TABLE_COMBO_BAP_NUOC_MACOMBO, comboBapNuoc.getMaCombo());
        values.put(SQLHelper.TABLE_COMBO_BAP_NUOC_TENCOMBO, comboBapNuoc.getTenCombo());
        values.put(SQLHelper.TABLE_COMBO_BAP_NUOC_SOLUONG, comboBapNuoc.getSoLuong());
        values.put(SQLHelper.TABLE_COMBO_BAP_NUOC_GIA, comboBapNuoc.getGia());

        long res = sqlDB.insert(SQLHelper.TABLE_COMBO_BAP_NUOC, null, values);
        if(res == -1) return false;
        else return true;
    }

    public boolean updateCombo(ComboBapNuoc comboBapNuoc){
        ContentValues values = new ContentValues();
        values.put(SQLHelper.TABLE_COMBO_BAP_NUOC_HINHANH, comboBapNuoc.getHinhAnh());
        values.put(SQLHelper.TABLE_COMBO_BAP_NUOC_TENCOMBO, comboBapNuoc.getTenCombo());
        values.put(SQLHelper.TABLE_COMBO_BAP_NUOC_GIA, comboBapNuoc.getGia());
        values.put(SQLHelper.TABLE_COMBO_BAP_NUOC_SOLUONG, comboBapNuoc.getSoLuong());

        long res = sqlDB.update(SQLHelper.TABLE_COMBO_BAP_NUOC, values, SQLHelper.TABLE_COMBO_BAP_NUOC_MACOMBO + " =?", new String[]{comboBapNuoc.getMaCombo()});
        if(res == 1) return false;
        else return true;
    }

    public boolean deleteCombo(String maCombo){
        long res = sqlDB.delete(SQLHelper.TABLE_COMBO_BAP_NUOC, SQLHelper.TABLE_COMBO_BAP_NUOC_MACOMBO + " =?", new String[]{maCombo});
        if(res == -1) return false;
        else return true;
    }
}
