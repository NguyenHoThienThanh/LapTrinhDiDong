package com.example.doancuoiky.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.PhimDAO;
import com.example.doancuoiky.model.Phim;

import java.text.DecimalFormat;

public class ChiTietPhimActivity extends AppCompatActivity {
    TextView txt_tenPhim, txt_moTaPhim, txt_dienVien, txt_thoiLuong, txt_gioiHanTuoi, txt_giaVe, txt_theLoai, txt_quocGia;
    ImageView img_trailer;
    Toolbar toolbar;
    Button btn_muaVe;
    PhimDAO phimDAO;

    Phim phim;

    String tenPhim, maPhim, theLoai;
    int thoiLuong, gioiHanTuoi;
    byte[] poster;
    double giaVe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mappingControl();
        toolBarFAndB();
        phimDAO = new PhimDAO(this);
        ChiTietPhimView();
        btnMuaVe();


    }

    public void mappingControl() {
        txt_tenPhim = findViewById(R.id.txt_filmName);
        txt_giaVe = findViewById(R.id.txt_giaVe);
        txt_thoiLuong = findViewById(R.id.txt_thoiLuong);
        txt_gioiHanTuoi = findViewById(R.id.txt_ageValiable);
        txt_moTaPhim = findViewById(R.id.txt_content);
        txt_dienVien = findViewById(R.id.txt_actor);
        txt_quocGia = findViewById(R.id.txt_quocGia);
        txt_theLoai = findViewById(R.id.txt_filmGenre);
        img_trailer = findViewById(R.id.img_filmPoster);
        toolbar = findViewById(R.id.toolbar_movie_detail);

    }

    public void toolBarFAndB() {
        Toolbar toolbar = findViewById(R.id.toolbar_movie_detail);

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

    public void btnMuaVe() {
        btn_muaVe = findViewById(R.id.btn_bookingMovie);


        btn_muaVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietPhimActivity.this, ChiTietSuatChieuActivity.class);
                intent.putExtra("tenPhim", tenPhim);
                intent.putExtra("maPhim", maPhim);
                intent.putExtra("gioiHanTuoi", gioiHanTuoi);
                intent.putExtra("giaVe", giaVe);
                intent.putExtra("theLoai", theLoai);
                intent.putExtra("poster", poster);
                startActivity(intent);
            }
        });
    }


    public void ChiTietPhimView() {
        phim = phimDAO.findOneById("MP008");
        tenPhim = phim.getTenPhim();
        maPhim = phim.getMaPhim();
        thoiLuong = phim.getThoiLuong();
        theLoai = phim.getTheLoai();
        poster = phim.getTrailer();
        giaVe = phim.getGiaVe();
        gioiHanTuoi = phim.getGioiHanDoTuoi();
        giaVe = phim.getGiaVe();
        DecimalFormat dc = new DecimalFormat();
        dc.applyPattern("#,### VND");
        txt_tenPhim.setText(phim.getTenPhim());
        txt_dienVien.setText(phim.getDienVien());
        txt_moTaPhim.setText(phim.getMoTaPhim());
        txt_thoiLuong.setText(phim.getThoiLuong() + " phút");
        txt_gioiHanTuoi.setText("Phim dành cho khán giả trên " + phim.getGioiHanDoTuoi() + " tuổi");
        txt_theLoai.setText(phim.getTheLoai());
        txt_quocGia.setText(phim.getQuocGia());
        txt_giaVe.setText(dc.format(giaVe));
        Bitmap bitmap = BitmapFactory.decodeByteArray(phim.getTrailer(), 0, phim.getTrailer().length);
        img_trailer.setImageBitmap(bitmap);
        toolbar.setTitle(phim.getTenPhim());

    }
}
