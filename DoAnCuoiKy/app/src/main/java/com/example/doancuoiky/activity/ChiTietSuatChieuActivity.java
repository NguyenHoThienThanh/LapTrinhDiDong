package com.example.doancuoiky.activity;

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
//        Toast.makeText(this, "a"+danhSachNgayChieuAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
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
        ChiTietSuatChieu chiTietSuatChieu = listCtsc.get(0);
        ArrayList<ChiTietSuatChieu> list = new ArrayList<>();
        list =suatChieuDao.getTimeBySuatChieu(chiTietSuatChieu.getMaPhim(),chiTietSuatChieu.getNgayChieu());
        tv_rapdexuat.setText(list.size());
        danhSachNgayChieuAdapter.setData(listCtsc, new DanhSachNgayChieuAdapter.IOnDateClickListener() {



            @Override
            public void onDateClick(int position) {

//                Toast.makeText(ChiTietSuatChieuActivity.this, ""+listCtsc.toString(), Toast.LENGTH_LONG).show();
//                ArrayList<ChiTietSuatChieu> list = new ArrayList<>();
//                list = suatChieuDao.getTimeBySuatChieu(chiTietSuatChieu.getMaPhim(), chiTietSuatChieu.getNgayChieu());
//                rcv_gio = findViewById(R.id.rcv_gio);

                //Toast.makeText(ChiTietSuatChieuActivity.this, "a"+chiTietSuatChieu.getNgayChieu(), Toast.LENGTH_SHORT).show();
//                danhSachGioChieuAdapter.setData(list);
//                    @Override
//                    public void onTimeClick(int position) {
//
//                    }
//                });
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