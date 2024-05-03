package com.example.doancuoiky.activity.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.user.MainActivity;
import com.example.doancuoiky.adapter.AdminFilmAdapter;
import com.example.doancuoiky.adapter.AdminSuatChieuAdapter;
import com.example.doancuoiky.dao.PhimDAO;
import com.example.doancuoiky.dao.PhongChieuPhimDAO;
import com.example.doancuoiky.dao.SuatChieuDao;
import com.example.doancuoiky.model.ChiTietSuatChieu;

import java.util.ArrayList;
import java.util.List;

public class AdminSuatChieuActivity extends AppCompatActivity {

    EditText edtMaSuatChieu, edtNgayChieu, edtGioChieu;
    Spinner spnMaPhongChieu, spnMaPhim;
    Button btnThem, btnXoa, btnSua, btnXem;
    ListView lvSuatChieu;
    ArrayList<ChiTietSuatChieu> arrSuatChieu;
    AdminSuatChieuAdapter adapter;
    SuatChieuDao scDao;
    PhimDAO phimDao;
    PhongChieuPhimDAO pcpDao;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_suat_chieu);
        context = this;
        scDao = new SuatChieuDao(context);
        phimDao = new PhimDAO(context);
        pcpDao = new PhongChieuPhimDAO(context);
        mapping();
        loadDataSpinner();
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
                String maPhongChieu = spnMaPhongChieu.getSelectedItem().toString();
                String maPhim = spnMaPhim.getSelectedItem().toString();
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
                String maPhongChieu = spnMaPhongChieu.getSelectedItem().toString();
                String maPhim = spnMaPhim.getSelectedItem().toString();
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

    private void loadDataSpinner(){
        ArrayList<String> listMaPhim = phimDao.findAllMaPhim();
        ArrayAdapter<String> adapterMaPhim = new ArrayAdapter<>(AdminSuatChieuActivity.this, android.R.layout.simple_spinner_item, listMaPhim);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMaPhim.setAdapter(adapterMaPhim);

        ArrayList<String> listMaPhongChieu = pcpDao.findAllMaPhongChieu();
        ArrayAdapter<String> adapterMaPC= new ArrayAdapter<>(AdminSuatChieuActivity.this, android.R.layout.simple_spinner_item,listMaPhongChieu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMaPhongChieu.setAdapter(adapterMaPC);
    }

    private void mapping() {
        edtMaSuatChieu = findViewById(R.id.edtMaSuatChieu);
        spnMaPhongChieu = findViewById(R.id.spnMaPhongChieu);
        spnMaPhim = findViewById(R.id.spnMaPhim);
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