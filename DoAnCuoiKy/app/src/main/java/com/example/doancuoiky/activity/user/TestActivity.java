package com.example.doancuoiky.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.TestAdapter;
import com.example.doancuoiky.dao.HoaDonDAO;
import com.example.doancuoiky.model.HoaDon;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    ListView lv;
    List<HoaDon> listHD;
    TestAdapter adapter;

    // Khai báo context
    HoaDonDAO hdDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        hdDao = new HoaDonDAO(this);
        lv = findViewById(R.id.lv);


        listHD = hdDao.getListHoaDon();
        adapter = new TestAdapter((Activity) TestActivity.this, R.layout. test_custom, listHD);
        lv.setAdapter(adapter);
        insert();
    }

    public void insert(){
        String maHoaDon = hdDao.getNextID();
        HoaDon hd = new HoaDon(maHoaDon, "MSC001", "MKH001", "CB02", 100000);
        boolean res = hdDao.insertHoaDon(hd);

        if (res){
            Toast.makeText(this, "Insert thành công", Toast.LENGTH_SHORT).show();
            listHD.clear(); // Xóa danh sách hiện tại
            listHD.addAll(hdDao.getListHoaDon()); // Lấy danh sách mới từ cơ sở dữ liệu
            adapter.notifyDataSetChanged(); // Cập nhật adapter
        }
        else{
            Toast.makeText(this, "Insert thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}