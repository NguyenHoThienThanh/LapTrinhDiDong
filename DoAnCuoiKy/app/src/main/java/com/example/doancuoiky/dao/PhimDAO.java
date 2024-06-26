package com.example.doancuoiky.dao;

import android.content.ContentValues;
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
                phimArrayList.add(phim);
            }while(c.moveToNext());
        }
        if(c != null){
            c.close();
        }
        return phimArrayList;
    }

    public ArrayList<String> findAllMaPhim(){
        ArrayList<String> list = new ArrayList<>();
        Cursor c =sqlDB.query("Phim", null, null, null, null, null,null );
        if(c != null && c.moveToFirst()){
            do{
                list.add(c.getString(0));
            }while(c.moveToNext());
        }
        if(c != null){
            c.close();
        }
        return list;
    }

    public boolean insert(Phim phim){
        try {
            sqlDB = helper.getReadableDatabase();
            sqlDB = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("maPhim", phim.getMaPhim());
            values.put("tenPhim", phim.getTenPhim());
            values.put("moTaPhim", phim.getMoTaPhim());
            values.put("dienVien", phim.getDienVien());
            values.put("quocGia", phim.getQuocGia());
            values.put("theLoai", phim.getTheLoai());
            values.put("thoiLuong", phim.getThoiLuong());
            values.put("gioiHanDoTuoi", phim.getGioiHanDoTuoi());
            values.put("giaVe", phim.getGiaVe());
            values.put("trailer", phim.getTrailer());
            // thực thi insert
            long result = sqlDB.insert("Phim", null, values);
            return result != -1;
        }
        catch (Exception e){

        }
        return false;

    }
    public boolean update(Phim phim){
        try {
            sqlDB = helper.getReadableDatabase();
            sqlDB = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("maPhim", phim.getMaPhim());
            values.put("tenPhim", phim.getTenPhim());
            values.put("moTaPhim", phim.getMoTaPhim());
            values.put("dienVien", phim.getDienVien());
            values.put("quocGia", phim.getQuocGia());
            values.put("theLoai", phim.getTheLoai());
            values.put("thoiLuong", phim.getThoiLuong());
            values.put("gioiHanDoTuoi", phim.getGioiHanDoTuoi());
            values.put("giaVe", phim.getGiaVe());
            values.put("trailer", phim.getTrailer());
            // thực thi insert
            long result = sqlDB.update("Phim", values, "maPhim = ?", new String[]{phim.getMaPhim()});
            return result != -1;
        }
        catch (Exception e){

        }
        return false;
    }
    public boolean delete(String maPhim){
        sqlDB = helper.getReadableDatabase();
        sqlDB = helper.getWritableDatabase();
        long res = sqlDB.delete("Phim", "maPhim=?", new String[]{maPhim});
        if(res == -1) return false;
        else return true;
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
        sqlDB = helper.getReadableDatabase();
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
                phimArrayList.add(phim);
            }while(c.moveToNext());
        }
        if(c != null){
            c.close();
        }
        return phimArrayList;
    }
    public ArrayList<Phim> findUpComingPhim(){
        sqlDB = helper.getReadableDatabase();
        ArrayList<Phim> phimArrayList = new ArrayList<>();
        String query = "SELECT * FROM Phim LIMIT 100 OFFSET 5;";
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
                phimArrayList.add(phim);
            }while(c.moveToNext());
        }
        if(c != null){
            c.close();
        }
        return phimArrayList;
    }

}
