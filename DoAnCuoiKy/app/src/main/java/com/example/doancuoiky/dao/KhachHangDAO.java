package com.example.doancuoiky.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doancuoiky.model.KhachHang;
import com.example.doancuoiky.model.TaiKhoan;

import java.util.ArrayList;

public class KhachHangDAO {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;

    public KhachHangDAO(Context context){
        helper = new SQLHelper(context);
        helper.processCopy();
        sqlDB = helper.getWritableDatabase();
    }

    public ArrayList<KhachHang> findAllKhachHang(){
        ArrayList<KhachHang> khachHangArrayList = new ArrayList<>();
        Cursor c =sqlDB.query("KhachHang", null, null, null, null, null,null );
        c.moveToFirst();
        while(!c.isAfterLast()){
                KhachHang khachHang = new KhachHang();
                khachHang.setMaKhachHang(c.getString(0));
                khachHang.setHoTen(c.getString(1));
                khachHang.setNgaySinh(c.getString(2));
                khachHang.setGioiTinh(c.getInt(3));
                khachHang.setDiaChi(c.getString(4));
                khachHang.setEmail(c.getString(5));
                khachHang.setSoDienThoai(c.getString(6));
                khachHang.setUserName(c.getString(7));
                khachHang.setAvatar(c.getBlob(8));
                khachHangArrayList.add(khachHang);
                c.moveToNext();
        }

            c.close();

        return khachHangArrayList;
    }

    public KhachHang findOneById(String maKhachHang){
        sqlDB = helper.getReadableDatabase();
        KhachHang khachHang = new KhachHang();
        String query = "SELECT * FROM KhachHang where maKhachHang = ?";
        Cursor c =sqlDB.rawQuery(query, new String[]{maKhachHang} );
        if(c != null && c.moveToFirst()){
            do{
                khachHang.setMaKhachHang(c.getString(0));
                khachHang.setHoTen(c.getString(1));
                khachHang.setNgaySinh(c.getString(2));
                khachHang.setGioiTinh(c.getInt(3));
                khachHang.setDiaChi(c.getString(4));
                khachHang.setEmail(c.getString(5));
                khachHang.setSoDienThoai(c.getString(6));
                khachHang.setUserName(c.getString(7));
                khachHang.setAvatar(c.getBlob(8));
            }while(c.moveToNext());
        }
        if(c != null){
            c.close();
        }
        return khachHang;
    }

    public boolean update(KhachHang khachHang) {
        sqlDB = helper.getReadableDatabase();
        sqlDB = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maKhachHang", khachHang.getMaKhachHang());
        values.put("hoTen", khachHang.getHoTen());
        values.put("ngaySinh", khachHang.getNgaySinh());
        values.put("diaChi", khachHang.getDiaChi());
        values.put("email", khachHang.getEmail());
        values.put("userName", khachHang.getUserName());
        try {
            if (sqlDB.update("KhachHang", values, "maKhachHang = ?", new String[]{khachHang.getMaKhachHang()}) < 0) {
                return false;
            }
        } catch (Exception ex) {
        }
        return true;
    }

    public boolean delete(String maKhachHang){
        sqlDB = helper.getReadableDatabase();
        sqlDB = helper.getWritableDatabase();
        long res = sqlDB.delete("KhachHang", "maKhachHang =?", new String[]{maKhachHang});
        if(res == -1) return false;
        else return true;
    }


}
