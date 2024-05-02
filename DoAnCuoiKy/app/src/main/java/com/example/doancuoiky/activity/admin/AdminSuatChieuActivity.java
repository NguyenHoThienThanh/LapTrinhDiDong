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
import com.example.doancuoiky.adapter.AdminSuatChieuAdapter;
import com.example.doancuoiky.dao.SuatChieuDao;
import com.example.doancuoiky.model.ChiTietSuatChieu;

import java.util.ArrayList;
import java.util.List;

public class AdminSuatChieuActivity extends AppCompatActivity {

    EditText edtMaSuatChieu, edtMaPhongChieu, edtMaPhim, edtNgayChieu, edtGioChieu;
    Button btnThem, btnXoa, btnSua, btnXem;
    ListView lvSuatChieu;
    ArrayList<ChiTietSuatChieu> arrSuatChieu;
    AdminSuatChieuAdapter adapter;
    SuatChieuDao scDao;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_suat_chieu);
        context = this;
        scDao = new SuatChieuDao(context);
        mapping();
        events();
    }

    private void events() {
        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrSuatChieu.clear();
                arrSuatChieu = scDao.findAll();
                adapter = new AdminSuatChieuAdapter(AdminSuatChieuActivity.this, R.layout.item_phim, arrSuatChieu);
                lvSuatChieu.setAdapter(adapter);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maSuatChieu = edtMaSuatChieu.getText().toString();
                String maPhongChieu = edtMaPhongChieu.getText().toString();
                String maPhim = edtMaPhim.getText().toString();
                String ngayChieu = edtNgayChieu.getText().toString();
                String gioChieu = edtGioChieu.getText().toString();
                ChiTietSuatChieu ct = new ChiTietSuatChieu(maSuatChieu, maPhongChieu, maPhim, ngayChieu, gioChieu);
                boolean res = scDao.insert(ct);
                if (res){
                    Toast.makeText(AdminSuatChieuActivity.this, "Insert thành công", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(AdminSuatChieuActivity.this, "Insert thất bại", Toast.LENGTH_SHORT).show();
                }
                clearText();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maSuatChieu = edtMaSuatChieu.getText().toString();
                String maPhongChieu = edtMaPhongChieu.getText().toString();
                String maPhim = edtMaPhim.getText().toString();
                String ngayChieu = edtNgayChieu.getText().toString();
                String gioChieu = edtGioChieu.getText().toString();
                ChiTietSuatChieu ct = new ChiTietSuatChieu(maSuatChieu, maPhongChieu, maPhim, ngayChieu, gioChieu);
                boolean res = scDao.update(ct);
                if (res){
                    Toast.makeText(AdminSuatChieuActivity.this, "Update thành công", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(AdminSuatChieuActivity.this, "Update thất bại", Toast.LENGTH_SHORT).show();
                }
                clearText();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maSuatChieu = edtMaSuatChieu.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminSuatChieuActivity.this);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc muốn xóa phim này không");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean res = scDao.delete(maSuatChieu);
                        if (res){
                            Toast.makeText(AdminSuatChieuActivity.this, "Delete thành công", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(AdminSuatChieuActivity.this, "Delete thất bại", Toast.LENGTH_SHORT).show();
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

    private void clearText() {
    }

    private void mapping() {
        edtMaSuatChieu = findViewById(R.id.edtMaSuatChieu);
        edtMaPhongChieu = findViewById(R.id.edtMaPhongChieu);
        edtMaPhim = findViewById(R.id.edtMaPhim);
        edtNgayChieu = findViewById(R.id.edtNgayChieu);
        edtGioChieu = findViewById(R.id.edtGioChieu);
        btnThem = findViewById(R.id.btnThemSuatChieu);
        btnSua = findViewById(R.id.btnSuaSuatChieu);
        btnXoa = findViewById(R.id.btnXoaSuatChieu);
        btnXem = findViewById(R.id.btnHienThiSuatChieu);
        arrSuatChieu = scDao.findAll();
        lvSuatChieu = findViewById(R.id.lvSuatChieu);
        adapter = new AdminSuatChieuAdapter(AdminSuatChieuActivity.this, R.layout.item_suat_chieu, arrSuatChieu);
        lvSuatChieu.setAdapter(adapter);
    }
}