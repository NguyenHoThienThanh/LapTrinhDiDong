package com.example.doancuoiky.activity.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.LichSuDatVeAdapter;
import com.example.doancuoiky.dao.HoaDonDAO;
import com.example.doancuoiky.dao.KhachHangDAO;
import com.example.doancuoiky.model.HoaDon;

import java.util.ArrayList;

public class LichSuDatVeActivity extends AppCompatActivity {

    ListView lvLichSuDatVe;
    ArrayList<HoaDon> arrHoaDon;
    HoaDonDAO hdDao;
    KhachHangDAO khDao;
    Context context;
    LichSuDatVeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_dat_ve);

        context = this;
        hdDao = new HoaDonDAO(context);
        khDao = new KhachHangDAO(context);
        lvLichSuDatVe = findViewById(R.id.lvLichSuDatVe);

        String maKH = khDao.findOneBySoDienThoai(LoginActivity.getTaiKhoan().getTaiKhoan()).getMaKhachHang();
        arrHoaDon = hdDao.findHoaDonByMaKhachHang(maKH);
        adapter = new LichSuDatVeAdapter(LichSuDatVeActivity.this, R.layout.item_lich_su_dat_ve, arrHoaDon);
        lvLichSuDatVe.setAdapter(adapter);
    }
}