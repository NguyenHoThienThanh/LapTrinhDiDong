package com.example.doancuoiky.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.DanhSachGioChieuAdapter;
import com.example.doancuoiky.adapter.DanhSachNgayChieuAdapter;
import com.example.doancuoiky.dao.SuatChieuDao;
import com.example.doancuoiky.model.ChiTietSuatChieu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChiTietSuatChieuActivity extends AppCompatActivity {
    RecyclerView rcv_ngay, rcv_gio;
    TextView tv_rapdexuat;
    DanhSachNgayChieuAdapter danhSachNgayChieuAdapter;
    DanhSachGioChieuAdapter danhSachGioChieuAdapter;
    ArrayList<ChiTietSuatChieu> listCtsc = new ArrayList<>();
    SuatChieuDao suatChieuDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_suat_chieu);
        suatChieuDao = new SuatChieuDao(this);
        mappingControl();
        dateAdapter();
    }

    private void mappingControl() {
        tv_rapdexuat = findViewById(R.id.tv_rapdexuat);
    }

    public void dateAdapter() {
        rcv_ngay = findViewById(R.id.rcv_ngay);
        danhSachNgayChieuAdapter = new DanhSachNgayChieuAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcv_ngay.setLayoutManager(layoutManager);
        listCtsc = (ArrayList<ChiTietSuatChieu>) getDateList();
        danhSachNgayChieuAdapter.setData(listCtsc, new DanhSachNgayChieuAdapter.IOnDateClickListener() {
            @Override
            public void onDateClick(int position) {
                rcv_gio = findViewById(R.id.rcv_gio);
                danhSachGioChieuAdapter = new DanhSachGioChieuAdapter(ChiTietSuatChieuActivity.this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ChiTietSuatChieuActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rcv_gio.setLayoutManager(layoutManager);
                ChiTietSuatChieu chiTietSuatChieu = listCtsc.get(position);
                ArrayList<ChiTietSuatChieu> list = new ArrayList<>();
                list = suatChieuDao.getTimeBySuatChieu(chiTietSuatChieu.getMaPhim(), chiTietSuatChieu.getNgayChieu());
                ArrayList<ChiTietSuatChieu> finalList = list;
                danhSachGioChieuAdapter.setData(list, new DanhSachGioChieuAdapter.IOnTimeClickListener() {
                    @Override
                    public void onTimeClick(int position) {
                        ChiTietSuatChieu chiTietSuatChieu1 = finalList.get(position);
                        Toast.makeText(ChiTietSuatChieuActivity.this, "" + chiTietSuatChieu1.getGioChieu() + chiTietSuatChieu1.getNgayChieu(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChiTietSuatChieuActivity.this, ChoNgoiActivity.class);
                        intent.putExtra("maSuatChieu", chiTietSuatChieu1.getMaSuatChieu());
                        intent.putExtra("maPhongChieu", chiTietSuatChieu1.getMaPhongChieu());
                        intent.putExtra("gioChieu", chiTietSuatChieu1.getGioChieu());
                        intent.putExtra("ngayChieu", chiTietSuatChieu1.getNgayChieu());
                        startActivity(intent);
                    }
                });
                rcv_gio.setAdapter(danhSachGioChieuAdapter);
            }
        });
        rcv_ngay.setAdapter(danhSachNgayChieuAdapter);
    }

    public List<ChiTietSuatChieu> getDateList() {
        ArrayList<ChiTietSuatChieu> list = new ArrayList<>();
        list = suatChieuDao.getDateBySuatChieu("MP001");
//        Toast.makeText(this, "a"+list.size(), Toast.LENGTH_SHORT).show();
        return list;
    }
}