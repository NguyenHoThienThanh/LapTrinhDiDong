package com.example.doancuoiky.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.doancuoiky.model.ComboBapNuoc;
import com.example.doancuoiky.model.NhanVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NhanVienDAO {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;
    private Context context;

    public NhanVienDAO(Context context) {
        helper = new SQLHelper(context);
        helper.processCopy();
        sqlDB = helper.getWritableDatabase();
    }
    public ArrayList<NhanVien> findAllNV() {
        ArrayList<NhanVien> nhanVienArrayList = new ArrayList<>();
        Cursor c = sqlDB.query("NhanVien", null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(c.getString(0));
                nv.setHoTen(c.getString(1));
                nv.setNgaySinh(c.getString(2));
                nv.setGioiTinh(c.getInt(3) == 1); // 1 là Nam, 0 là Nữ
                nv.setDiaChi(c.getString(4));
                nv.setEmail(c.getString(5));
                nv.setSoDienThoai(c.getString(6));

                nhanVienArrayList.add(nv);

                c.moveToNext();
            } while (!c.isAfterLast());
        }
        if (c != null) {
            c.close();
        }

        return nhanVienArrayList;
    }

    public boolean update(NhanVien nhanVien) {
        if (nhanVien == null || nhanVien.getMaNhanVien() == null) {
            return false; // Kiểm tra nhân viên có hợp lệ không
        }

        ContentValues values = new ContentValues();
        values.put("hoTen", nhanVien.getHoTen());
        values.put("diaChi", nhanVien.getDiaChi());
        values.put("email", nhanVien.getEmail());
        values.put("soDienThoai", nhanVien.getSoDienThoai());
        values.put("gioiTinh", nhanVien.isGioiTinh() ? 1 : 0);
        values.put("ngaySinh", nhanVien.getNgaySinh()); // Lưu ngày sinh dưới dạng chuỗi định dạng "dd/MM/yyyy"
        try {
            sqlDB = helper.getWritableDatabase();
            int result = sqlDB.update("NhanVien", values, "maNhanVien = ?", new String[]{nhanVien.getMaNhanVien()});

            return result > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }



    public boolean delete(String maNhanVien){
        sqlDB = helper.getReadableDatabase();
        sqlDB = helper.getWritableDatabase();
        long res = sqlDB.delete("NhanVien", "maNhanVien=?", new String[]{maNhanVien});
        if(res == -1) return false;
        else return true;
    }
    public boolean insert(NhanVien nhanVien) {
        try {
            sqlDB = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("hoTen", nhanVien.getHoTen());
            values.put("diaChi", nhanVien.getDiaChi());
            values.put("email", nhanVien.getEmail());
            values.put("soDienThoai", nhanVien.getSoDienThoai());
            values.put("gioiTinh", nhanVien.isGioiTinh() ? 1 : 0);
            values.put("ngaySinh", nhanVien.getNgaySinh().toString());

            long result = sqlDB.insert("NhanVien", null, values);

            return result != -1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public NhanVien findOne(String maNhanVien) {
        NhanVien nhanVien = null;

        String[] columns = {"maNhanVien", "hoTen", "diaChi", "email", "soDienThoai", "gioiTinh", "ngaySinh"};
        String selection = "maNhanVien = ?";
        String[] selectionArgs = {maNhanVien};

        Cursor c = sqlDB.query("NhanVien", columns, selection, selectionArgs, null, null, null);

        if (c != null && c.moveToFirst()) {
            nhanVien = new NhanVien();
            nhanVien.setMaNhanVien(c.getString(0));
            nhanVien.setHoTen(c.getString(1));
            nhanVien.setDiaChi(c.getString(2));
            nhanVien.setEmail(c.getString(3));
            nhanVien.setSoDienThoai(c.getString(4));
            nhanVien.setGioiTinh(c.getInt(5) == 1);
            nhanVien.setNgaySinh(c.getString(6));
        }
        if (c != null) {
            c.close();
        }

        return nhanVien;
    }

}
