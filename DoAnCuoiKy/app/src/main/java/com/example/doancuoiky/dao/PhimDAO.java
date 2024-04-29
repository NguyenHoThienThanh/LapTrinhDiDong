package com.example.doancuoiky.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.widget.Toast;

import com.example.doancuoiky.model.Phim;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PhimDAO {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;

    public PhimDAO(Context context){
        helper = new SQLHelper(context);
        helper.processCopy();
        sqlDB = helper.getWritableDatabase();
    }


    public ArrayList<Phim> findAllPhim(){
        ArrayList<Phim> phimArrayList = new ArrayList<>();
        Cursor c =sqlDB.query("Phim", null, null, null, null, null,null );
        if(c != null && c.moveToFirst()){
            do{
                Phim phim = new Phim();
                phim.setMaPhim(c.getString(0));
                phim.setTenPhim(c.getString(1));
                phim.setThoiLuong(c.getInt(2));
                phim.setGioiHanDoTuoi(c.getInt(3));
                phim.setMoTaPhim(c.getString(4));
                phim.setDienVien(c.getString(5));
                phim.setTrailer(c.getBlob(6));
                phim.setGiaVe(c.getFloat(7));
                phim.setTheLoai(c.getString(8));
                phim.setQuocGia(c.getString(9));
            }while(c.moveToNext());
        }
        if(c != null){
            c.close();
        }
        return phimArrayList;
    }

    public Phim findOneById(String maPhim){
        sqlDB = helper.getReadableDatabase();
        Phim phim = new Phim();
        String query = "SELECT * FROM Phim where maPhim = ?";
        Cursor c =sqlDB.rawQuery(query, new String[]{maPhim} );
        if(c != null && c.moveToFirst()){
            do{

                phim.setMaPhim(c.getString(0));
                phim.setTenPhim(c.getString(1));
                phim.setThoiLuong(c.getInt(2));
                phim.setGioiHanDoTuoi(c.getInt(3));
                phim.setMoTaPhim(c.getString(4));
                phim.setDienVien(c.getString(5));
                phim.setTrailer(c.getBlob(6));
                phim.setGiaVe(c.getFloat(7));
                phim.setTheLoai(c.getString(8));
                phim.setQuocGia(c.getString(9));
            }while(c.moveToNext());
        }
        if(c != null){
            c.close();
        }

        return phim;
    }

    public ArrayList<Phim> findTop5Phim(){
        ArrayList<Phim> phimArrayList = new ArrayList<>();
        String query = "SELECT * FROM Phim LIMIT 5";
        Cursor c =sqlDB.rawQuery(query, new String[]{} );
        if(c != null && c.moveToFirst()){
            do{
                Phim phim = new Phim();
                phim.setMaPhim(c.getString(0));
                phim.setTenPhim(c.getString(1));
                phim.setThoiLuong(c.getInt(2));
                phim.setGioiHanDoTuoi(c.getInt(3));
                phim.setMoTaPhim(c.getString(4));
                phim.setDienVien(c.getString(5));
                phim.setTrailer(c.getBlob(6));
                phim.setGiaVe(c.getFloat(7));
                phim.setTheLoai(c.getString(8));
                phim.setQuocGia(c.getString(9));
            }while(c.moveToNext());
        }
        if(c != null){
            c.close();
        }
        return phimArrayList;
    }

}
