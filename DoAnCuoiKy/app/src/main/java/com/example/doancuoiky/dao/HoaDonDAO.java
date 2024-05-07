package com.example.doancuoiky.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.SQLHelper;
import com.example.doancuoiky.model.HoaDon;
import com.example.doancuoiky.model.ThongKeTheoPhim;
import com.example.doancuoiky.model.ThongKeTheoThang;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    private SQLiteDatabase sqlDB;
    private SQLHelper helper;

    public HoaDonDAO(Context context) {
        helper = new SQLHelper(context);
        helper.processCopy();
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

    public ArrayList<HoaDon> findHoaDonByMaKhachHang(String maKhachHang) {
        sqlDB = helper.getReadableDatabase();
        ArrayList<HoaDon> listHD = new ArrayList<>();
        String query = "SELECT * FROM HoaDon WHERE maKhachHang = ?";
        Cursor c = sqlDB.rawQuery(query, new String[]{maKhachHang});
        if (c.moveToFirst()) {
            do {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(c.getString(0));
                hd.setMaSuatChieu(c.getString(1));
                hd.setMaKhachHang(c.getString(2));
                hd.setMaCombo(c.getString(3));
                hd.setTongTien(c.getDouble(4));
                listHD.add(hd);
            } while (c.moveToNext());
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
        values.put("ngayLapHoaDon", hoaDon.getNgayLapHoaDon());
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
        values.put("ngayLapHoaDon", hoaDon.getNgayLapHoaDon());
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

    public ArrayList<ThongKeTheoThang> getListRevenue(String year){
        sqlDB = helper.getReadableDatabase();
        ArrayList<ThongKeTheoThang> listTK = new ArrayList<>();
        String query = "SELECT substr(ngayLapHoaDon, 7, 4) as nam, substr(ngayLapHoaDon, 4, 2) as thang, SUM(tongTien) as tongTienThang FROM HoaDon WHERE substr(ngayLapHoaDon, 7, 4) = ? GROUP BY nam, thang";
        Cursor c = sqlDB.rawQuery(query, new String[]{year});
        if (c.moveToFirst()) {
            do {
                ThongKeTheoThang tktt = new ThongKeTheoThang();
                tktt.setNam(c.getString(0));
                tktt.setThang(c.getString(1));
                tktt.setTongTienThang(c.getDouble(2));
                listTK.add(tktt);
            } while (c.moveToNext());
        }
        c.close();
        return listTK;
    }

    public ArrayList<ThongKeTheoPhim> getListViewMovie(String thang){
        sqlDB = helper.getReadableDatabase();
        ArrayList<ThongKeTheoPhim> listMovie = new ArrayList<>();
        String query = "select tenPhim, COUNT(*), substr(ngayChieu, 4, 7) as thang from ChiTietGheDaDat inner join (select maSuatChieu, Q.tenPhim, Q.maPhim, ngayChieu from SuatChieu inner join(select maPhim, tenPhim from Phim) as Q on Q.maPhim = SuatChieu.maPhim) as T on T.maSuatChieu = ChiTietGheDaDat.maSuatChieu where thang = ? GROUP BY tenPhim, thang";
        Cursor c = sqlDB.rawQuery(query, new String[]{thang});
        if (c.moveToFirst()) {
            do {
                ThongKeTheoPhim tktp = new ThongKeTheoPhim();
                tktp.setTenPhim(c.getString(0));
                tktp.setSoLuongVe(c.getInt(1));
                tktp.setThang(c.getString(2));
                listMovie.add(tktp);
            } while (c.moveToNext());
        }
        c.close();
        return listMovie;
    }

    public ArrayList<ThongKeTheoPhim> getListMonth(){
        sqlDB = helper.getReadableDatabase();
        ArrayList<ThongKeTheoPhim> listMonth = new ArrayList<>();
        String query = "select substr(ngayChieu, 4, 7) as thang from ChiTietGheDaDat inner join (select maSuatChieu, Q.tenPhim, Q.maPhim, ngayChieu from SuatChieu inner join(select maPhim, tenPhim from Phim) as Q on Q.maPhim = SuatChieu.maPhim) as T on T.maSuatChieu = ChiTietGheDaDat.maSuatChieu GROUP BY thang";
        Cursor c = sqlDB.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                ThongKeTheoPhim tktp = new ThongKeTheoPhim();
                tktp.setThang(c.getString(0));
                listMonth.add(tktp);
            } while (c.moveToNext());
        }
        c.close();
        return listMonth;
    }




}
