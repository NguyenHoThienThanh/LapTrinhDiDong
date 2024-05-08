package com.example.doancuoiky.activity.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.LichSuDatVeAdapter;
import com.example.doancuoiky.adapter.SearchViewAdapter;
import com.example.doancuoiky.dao.HoaDonDAO;
import com.example.doancuoiky.dao.KhachHangDAO;
import com.example.doancuoiky.model.HoaDon;

import java.util.ArrayList;

public class LichSuDatVeActivity extends AppCompatActivity {

    RecyclerView rcv;
    ArrayList<HoaDon> arrHoaDon;
    HoaDonDAO hdDao;
    KhachHangDAO khDao;
    LichSuDatVeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_dat_ve);
        hdDao = new HoaDonDAO(this);
        khDao = new KhachHangDAO(this);
        toolBarLichSuDatVe();
        mappingConTrol();
        adapter();
    }

    public void toolBarLichSuDatVe() {
        Toolbar toolbar = findViewById(R.id.toolbar_lichsudatve);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Kết thúc activity hiện tại khi nút đi được nhấn
            }
        });
    }

    public void mappingConTrol(){
        rcv = findViewById(R.id.rcv_lichSuDatVe);
    }

    public void adapter(){
        adapter = new LichSuDatVeAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(linearLayoutManager);


        arrHoaDon = hdDao.findHoaDonByMaKhachHang((khDao.findOneBySoDienThoai(LoginActivity.getTaiKhoan().getTaiKhoan())).getMaKhachHang());
        String maKhachHang = (khDao.findOneBySoDienThoai(LoginActivity.getTaiKhoan().getTaiKhoan())).getMaKhachHang();
        adapter.setData(arrHoaDon);
        rcv.setAdapter(adapter);
    }
}