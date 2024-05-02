package com.example.doancuoiky.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.SQLHelper;
import com.example.doancuoiky.model.HoaDon;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    private SQLiteDatabase sqlDB;
    private SQLHelper helper;

    public HoaDonDAO(Context context) {
        helper = new SQLHelper(context);
        sqlDB = helper.getWritableDatabase();
    }

    public ArrayList<HoaDon> getListHoaDon() {
        sqlDB = helper.getReadableDatabase();
        ArrayList<HoaDon> listHD = new ArrayList<>();
        Cursor c = sqlDB.query("HoaDon", null, null,
                null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            HoaDon hd = new HoaDon();
            hd.setMaHoaDon(c.getString(0));
            hd.setMaSuatChieu(c.getString(1));
            hd.setMaKhachHang(c.getString(2));
            hd.setMaCombo(c.getString(3));
            hd.setTongTien(c.getDouble(4));
            listHD.add(hd);

            c.moveToNext();
        }
        c.close();
        return listHD;
    }

    public boolean insertHoaDon(HoaDon hoaDon) {
        sqlDB = helper.getReadableDatabase();
        sqlDB = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("maHoaDon", hoaDon.getMaHoaDon());
        values.put("maSuatChieu", hoaDon.getMaSuatChieu());
        values.put("maKhachHang", hoaDon.getMaKhachHang());
        values.put("maCombo", hoaDon.getMaCombo());
        values.put("tongTien", hoaDon.getTongTien());
        long res = sqlDB.insert("HoaDon", null, values);
        if (res == -1) return false;
        else return true;
    }

    public boolean createHoaDon(HoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", getNextID());
        values.put("maSuatChieu", hoaDon.getMaSuatChieu());
        values.put("maKhachHang", hoaDon.getMaKhachHang());
        values.put("maCombo", hoaDon.getMaCombo());
        values.put("tongTien", hoaDon.getTongTien());
        return true;
    }

    public String getNextID() {
        String nextID = "";
        int lastID = check();
        if (lastID < 999) {
            nextID = "MHD" + String.format("%03d", lastID + 1);
        } else {
            nextID = "MHD" + (lastID + 1);
        }
        return nextID;
    }

    public int check() {
        int check = 0;
        ArrayList<HoaDon> hoaDonList = (ArrayList<HoaDon>) getListHoaDon();
        for (int i = 0; i < hoaDonList.size(); i++) {
            HoaDon hoaDon = hoaDonList.get(i);
            check = Math.max(check, Integer.parseInt(hoaDon.getMaHoaDon().substring(3)));
        }
        return check;
    }

    public boolean delete(String maHoaDon){
        sqlDB = helper.getReadableDatabase();
        sqlDB = helper.getWritableDatabase();
        long res = sqlDB.delete("HoaDon", "maHoaDon=?", new String[]{maHoaDon});
        if(res == -1) return false;
        else return true;
    }
}
