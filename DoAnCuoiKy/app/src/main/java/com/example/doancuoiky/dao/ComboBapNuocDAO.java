package com.example.doancuoiky.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.doancuoiky.model.ChoNgoi;
import com.example.doancuoiky.model.ComboBapNuoc;
import com.example.doancuoiky.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
public class ComboBapNuocDAO {
    private SQLiteDatabase sqlDB;
    private SQLHelper helper;
    private Context context;

    public ComboBapNuocDAO(Context context) {
        helper = new SQLHelper(context);
        helper.processCopy();
        sqlDB = helper.getWritableDatabase();
    }

    public ArrayList<ComboBapNuoc> findAllCombo(){
        ArrayList<ComboBapNuoc> comboBapNuocArrayList = new ArrayList<>();
        Cursor c = sqlDB.query("ComboBapNuoc", null,null,
                null,null,null,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            ComboBapNuoc cb = new ComboBapNuoc();
            cb.setHinhAnh(c.getBlob(0));
            cb.setMaCombo(c.getString(1));
            cb.setTenCombo(c.getString(2));
            cb.setMoTa(c.getString(3));
            cb.setSoLuong(c.getInt(4));
            cb.setGia(c.getFloat(5));
            comboBapNuocArrayList.add(cb);

            c.moveToNext();
        }
        c.close();
        return comboBapNuocArrayList;
    }
    public ArrayList<ComboBapNuoc> findAllComboUser(){
        ArrayList<ComboBapNuoc> comboBapNuocArrayList = new ArrayList<>();
        // Thêm điều kiện "SoLuong > 0" vào câu truy vấn
        Cursor c = sqlDB.query("ComboBapNuoc", null, "SoLuong > ?",
                new String[]{"0"}, null, null, null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                ComboBapNuoc cb = new ComboBapNuoc();
                cb.setHinhAnh(c.getBlob(0));
                cb.setMaCombo(c.getString(1));
                cb.setTenCombo(c.getString(2));
                cb.setMoTa(c.getString(3));
                cb.setSoLuong(c.getInt(4));
                cb.setGia(c.getFloat(5));
                comboBapNuocArrayList.add(cb);

                c.moveToNext();
            }
        }
        c.close();
        return comboBapNuocArrayList;
    }

    public boolean update(ComboBapNuoc comboBapNuoc) {
        if (comboBapNuoc == null || comboBapNuoc.getMaCombo() == null) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("tenCombo", comboBapNuoc.getTenCombo());
        values.put("moTa", comboBapNuoc.getMoTa());
        values.put("soLuong", comboBapNuoc.getSoLuong());
        values.put("gia", comboBapNuoc.getGia());

        // Chỉ thêm dữ liệu hình ảnh nếu không phải là null
        if (comboBapNuoc.getHinhAnh() != null) {
            values.put("hinhAnh", comboBapNuoc.getHinhAnh());
        }

        try {
            sqlDB = helper.getWritableDatabase();
            int result = sqlDB.update("ComboBapNuoc", values, "maCombo = ?", new String[]{comboBapNuoc.getMaCombo()});

            return result > 0; // Trả về true nếu cập nhật thành công
        } catch (Exception ex) {
            ex.printStackTrace(); // Xử lý ngoại lệ
            return false; // Trả về false nếu có lỗi
        }
    }


    public boolean delete(String maCombo){
        sqlDB = helper.getReadableDatabase();
        sqlDB = helper.getWritableDatabase();
        long res = sqlDB.delete("ComboBapNuoc", "maCombo =?", new String[]{maCombo});
        if(res == -1) return false;
        else return true;
    }


    public boolean insert(ComboBapNuoc comboBapNuoc) {
        try {
            sqlDB = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("tenCombo", comboBapNuoc.getTenCombo());
            values.put("moTa", comboBapNuoc.getMoTa());
            values.put("soLuong", comboBapNuoc.getSoLuong());
            values.put("gia", comboBapNuoc.getGia());
            values.put("hinhAnh", comboBapNuoc.getHinhAnh());

            long result = sqlDB.insert("ComboBapNuoc", null, values);

            return result != -1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public ComboBapNuoc findOne(String maCombo) {
        ComboBapNuoc comboBapNuoc = null;

        String[] columns = {"hinhAnh", "maCombo", "tenCombo", "moTa", "soLuong", "gia"};
        String selection = "maCombo = ?";
        String[] selectionArgs = {maCombo};

        Cursor c = sqlDB.query("ComboBapNuoc", columns, selection, selectionArgs, null, null, null);

        if (c != null && c.moveToFirst()) {
            comboBapNuoc = new ComboBapNuoc();
            comboBapNuoc.setHinhAnh(c.getBlob(0));
            comboBapNuoc.setMaCombo(c.getString(1));
            comboBapNuoc.setTenCombo(c.getString(2));
            comboBapNuoc.setMoTa(c.getString(3));
            comboBapNuoc.setSoLuong(c.getInt(4));
            comboBapNuoc.setGia(c.getFloat(5));
        }
        if (c != null) {
            c.close();
        }

        return comboBapNuoc;
    }
    public boolean checkComboExists(String tenCombo) {
        List<ComboBapNuoc> list = findAllCombo(); // Giả sử bạn có hàm này để lấy tất cả combo
        for (ComboBapNuoc combo : list) {
            if (combo.getTenCombo().equalsIgnoreCase(tenCombo)) {
                return true; // Trả về true nếu tìm thấy combo với tên giống nhau
            }
        }
        return false; // Trả về false nếu không tìm thấy
    }
}
