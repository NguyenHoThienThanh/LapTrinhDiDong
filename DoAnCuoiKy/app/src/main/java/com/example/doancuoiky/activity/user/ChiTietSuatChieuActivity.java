package com.example.doancuoiky.activity.user;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.DanhSachGioChieuAdapter;
import com.example.doancuoiky.adapter.DanhSachNgayChieuAdapter;
import com.example.doancuoiky.adapter.ImageAdapter;
import com.example.doancuoiky.dao.SuatChieuDao;
import com.example.doancuoiky.model.ChiTietSuatChieu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChiTietSuatChieuActivity extends AppCompatActivity {
    RecyclerView rcv_ngay, rcv_gio;
    TextView tv_rapdexuat;
    DanhSachNgayChieuAdapter danhSachNgayChieuAdapter;
    DanhSachGioChieuAdapter danhSachGioChieuAdapter;
    ArrayList<ChiTietSuatChieu> listCtsc = new ArrayList<>();
    List<ChiTietSuatChieu> listCtscFilter;
    SuatChieuDao suatChieuDao;

    private Carousel carousel;

    String tenPhim, maPhim, theLoai, ngayChieu;
    int  gioiHanTuoi;
    byte[] poster;
    double giaVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_suat_chieu);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("image0");
        arrayList.add("image1"); // Sử dụng tên của tài nguyên trong drawable
        arrayList.add("image2");

        ImageAdapter adapter = new ImageAdapter(ChiTietSuatChieuActivity.this, arrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                startActivity(new Intent(ChiTietSuatChieuActivity.this, ImageViewActivity.class).putExtra("image", path), ActivityOptions.makeSceneTransitionAnimation(ChiTietSuatChieuActivity.this, imageView, "image").toBundle());
            }
        });
        suatChieuDao = new SuatChieuDao(this);
        Intent intent = getIntent();
        maPhim = intent.getStringExtra("maPhim");
        tenPhim = intent.getStringExtra("tenPhim");
        theLoai = intent.getStringExtra("theLoai");
        gioiHanTuoi = intent.getIntExtra("gioiHanTuoi", 0);
        poster = intent.getByteArrayExtra("poster");
        giaVe = intent.getDoubleExtra("giaVe", 0);
        toolBarSuatChieu();
        mappingControl();
        dateAdapter();
    }

    public void toolBarSuatChieu() {
        Toolbar toolbar = findViewById(R.id.toolbar_film);
        toolbar.setTitle(tenPhim);
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
    private void mappingControl() {
        tv_rapdexuat = findViewById(R.id.tv_rapdexuat);
    }

    public void dateAdapter() {
        rcv_ngay = findViewById(R.id.rcv_ngay);
        danhSachNgayChieuAdapter = new DanhSachNgayChieuAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, GridLayoutManager.HORIZONTAL, false);
        rcv_ngay.setLayoutManager(layoutManager);
        listCtsc = (ArrayList<ChiTietSuatChieu>) getDateList(); // Lấy danh sách ngày
        danhSachNgayChieuAdapter.setData(listCtsc, new DanhSachNgayChieuAdapter.IOnDateClickListener() {
            @Override
            public void onDateClick(int position) {
                loadGioChieu(position); // Gọi hàm load giờ chiếu
            }
        });

        rcv_ngay.setAdapter(danhSachNgayChieuAdapter);

        // Hiển thị giờ chiếu của ngày đầu tiên
        if (!listCtsc.isEmpty()) {
            loadGioChieu(0); // Hiển thị giờ chiếu của ngày đầu tiên
        }
    }
    public ArrayList<ChiTietSuatChieu> filterDates(ArrayList<ChiTietSuatChieu> list) {
        ArrayList<ChiTietSuatChieu> filteredList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date currentDate = sdf.parse(sdf.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_YEAR, 15);
            Date maxDate = calendar.getTime();

            for (ChiTietSuatChieu ctsc : list) {
                Date date = sdf.parse(ctsc.getNgayChieu());

                if (date != null && !date.before(currentDate) && !date.after(maxDate)) {
                    ctsc.setNgayChieu(displayFormat.format(date));
                    filteredList.add(ctsc);
                }
            }

            Collections.sort(filteredList, (ctsc1, ctsc2) -> {
                try {
                    Date date1 = sdf.parse(ctsc1.getNgayChieu());
                    Date date2 = sdf.parse(ctsc2.getNgayChieu());
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            });

            TextView tv_no_shows = findViewById(R.id.chua_co_sc);
            RecyclerView rcv_gio = findViewById(R.id.rcv_gio);

            if (filteredList.isEmpty()) {
                tv_no_shows.setVisibility(View.VISIBLE);
                rcv_gio.setVisibility(View.GONE);
            } else {
                tv_no_shows.setVisibility(View.GONE);
                rcv_gio.setVisibility(View.VISIBLE);
            }

            return filteredList;
        } catch (ParseException e) {
            e.printStackTrace();
            return filteredList;
        }
    }
    private void loadGioChieu(int position) {
        rcv_gio = findViewById(R.id.rcv_gio);
        danhSachGioChieuAdapter = new DanhSachGioChieuAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rcv_gio.setLayoutManager(layoutManager);

        // Lấy danh sách giờ chiếu cho ngày cụ thể
        ChiTietSuatChieu chiTietSuatChieu = listCtsc.get(position);
        ArrayList<ChiTietSuatChieu> list = suatChieuDao.getTimeBySuatChieu(chiTietSuatChieu.getMaPhim(), chiTietSuatChieu.getNgayChieu());
        // Cập nhật số lượng giờ chiếu vào EditText
        EditText editSlRap = findViewById(R.id.edit_sl_rap);
        editSlRap.setText(String.valueOf(list.size())); // Đặt số lượng giờ chiếu

        danhSachGioChieuAdapter.setData(list, new DanhSachGioChieuAdapter.IOnTimeClickListener() {
            @Override
            public void onTimeClick(int position) {
                ChiTietSuatChieu chiTietSuatChieu1 = list.get(position);

                Intent intent = new Intent(ChiTietSuatChieuActivity.this, ChoNgoiActivity.class);
                intent.putExtra("maSuatChieu", chiTietSuatChieu1.getMaSuatChieu());
                intent.putExtra("maPhongChieu", chiTietSuatChieu1.getMaPhongChieu());
                intent.putExtra("gioChieu", chiTietSuatChieu1.getGioChieu());
                intent.putExtra("ngayChieu", chiTietSuatChieu1.getNgayChieu());
                intent.putExtra("tenPhim", tenPhim);
                intent.putExtra("theLoai", theLoai);
                intent.putExtra("maPhim", maPhim);
                intent.putExtra("gioiHanTuoi", gioiHanTuoi);
                intent.putExtra("giaVe", giaVe);
                intent.putExtra("poster", poster);
                startActivity(intent);
            }
        });

        rcv_gio.setAdapter(danhSachGioChieuAdapter); // Thiết lập adapter cho danh sách giờ chiếu
    }

    public List<ChiTietSuatChieu> getDateList() {
        ArrayList<ChiTietSuatChieu> list = new ArrayList<>();
        list = filterDates(suatChieuDao.getDateBySuatChieu(maPhim));
        return list;
    }
}