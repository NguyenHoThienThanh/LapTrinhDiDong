package com.example.doancuoiky.activity.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.user.MainActivity;
import com.example.doancuoiky.adapter.AdminFilmAdapter;
import com.example.doancuoiky.dao.PhimDAO;
import com.example.doancuoiky.model.Phim;

import java.util.ArrayList;

public class ActivityAdminFilm extends AppCompatActivity {

    EditText edtMaPhim, edtTenPhim, edtThoiLuong, edtDoTuoi, edtMoTa,
            edtDienVien, edtGiaVe, edtTheLoai, edtQuocGia;
    Button btnThem, btnXoa, btnSua, btnXem;
    ListView lvPhim;
    ArrayList<Phim> arrPhim;
    AdminFilmAdapter adapter;
    Context context;
    PhimDAO phimDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_film);
        context = this;
        phimDao = new PhimDAO(context);
        mapping();
        events();
    }

    private void events() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maPhim = edtMaPhim.getText().toString();
                String tenPhim = edtTenPhim.getText().toString();
                String thoiLuong = edtThoiLuong.getText().toString();
                String doTuoi = edtDoTuoi.getText().toString();
                String moTa = edtMoTa.getText().toString();
                String dienVien = edtDienVien.getText().toString();
                String giaVe = edtGiaVe.getText().toString();
                String theLoai = edtTheLoai.getText().toString();
                String quocGia = edtQuocGia.getText().toString();

                Phim phim = new Phim();
                phim.setMaPhim(maPhim);
                phim.setTenPhim(tenPhim);
                phim.setThoiLuong(Integer.parseInt(thoiLuong));
                phim.setGioiHanDoTuoi(Integer.parseInt(doTuoi));
                phim.setMoTaPhim(moTa);
                phim.setDienVien(dienVien);
                phim.setGiaVe(Float.parseFloat(giaVe));
                phim.setTheLoai(theLoai);
                phim.setQuocGia(quocGia);

                boolean res = phimDao.insert(phim);

                if (res){
                    Toast.makeText(ActivityAdminFilm.this, "Insert thành công", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(ActivityAdminFilm.this, "Insert thất bại", Toast.LENGTH_SHORT).show();
                }
                clearText();
            }
        });

        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrPhim.clear();
                arrPhim = phimDao.findAllPhim();
                adapter = new AdminFilmAdapter(ActivityAdminFilm.this, R.layout.item_phim, arrPhim);
                lvPhim.setAdapter(adapter);
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maPhim = edtMaPhim.getText().toString();
                String tenPhim = edtTenPhim.getText().toString();
                String thoiLuong = edtThoiLuong.getText().toString();
                String doTuoi = edtDoTuoi.getText().toString();
                String moTa = edtMoTa.getText().toString();
                String dienVien = edtDienVien.getText().toString();
                String giaVe = edtGiaVe.getText().toString();
                String theLoai = edtTheLoai.getText().toString();
                String quocGia = edtQuocGia.getText().toString();

                Phim phim = new Phim();
                phim.setMaPhim(maPhim);
                phim.setTenPhim(tenPhim);
                phim.setThoiLuong(Integer.parseInt(thoiLuong));
                phim.setGioiHanDoTuoi(Integer.parseInt(doTuoi));
                phim.setMoTaPhim(moTa);
                phim.setDienVien(dienVien);
                phim.setGiaVe(Float.parseFloat(giaVe));
                phim.setTheLoai(theLoai);
                phim.setQuocGia(quocGia);

                boolean res = phimDao.update(phim);

                if (res){
                    Toast.makeText(ActivityAdminFilm.this, "Update thành công", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(ActivityAdminFilm.this, "Update thất bại", Toast.LENGTH_SHORT).show();
                }
                clearText();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maPhim = edtMaPhim.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc muốn xóa phim này không");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean res = phimDao.delete(maPhim);
                        if (res){
                            Toast.makeText(ActivityAdminFilm.this, "Delete thành công", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(ActivityAdminFilm.this, "Delete thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                clearText();
            }
        });
    }

    private void mapping() {
        edtMaPhim = findViewById(R.id.edtMaPhim);
        edtTenPhim = findViewById(R.id.edtTenPhim);
        edtThoiLuong = findViewById(R.id.edtThoiLuong);
        edtDoTuoi = findViewById(R.id.edtDoTuoi);
        edtMoTa = findViewById(R.id.edtMoTaPhim);
        edtDienVien = findViewById(R.id.edtDienVien);
        edtGiaVe = findViewById(R.id.edtGiaVe);
        edtTheLoai = findViewById(R.id.edtTheLoai);
        edtQuocGia = findViewById(R.id.edtQuocGia);
        btnThem = findViewById(R.id.btnThemPhim);
        btnXoa = findViewById(R.id.btnXoaPhim);
        btnSua = findViewById(R.id.btnSuaPhim);
        btnXem = findViewById(R.id.btnHienThiPhim);

        lvPhim = findViewById(R.id.lvPhim);
        arrPhim = phimDao.findAllPhim();
        adapter = new AdminFilmAdapter(ActivityAdminFilm.this, R.layout.item_phim, arrPhim);
        lvPhim.setAdapter(adapter);
    }

    private void clearText() {
        edtMaPhim.setText("");
        edtTenPhim.setText("");
        edtThoiLuong.setText("");
        edtDoTuoi.setText("");
        edtMoTa.setText("");
        edtDienVien.setText("");
        edtGiaVe.setText("");
        edtTheLoai.setText("");
        edtQuocGia.setText("");
    }
}