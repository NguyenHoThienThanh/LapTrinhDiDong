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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.AdminBillAdapter;
import com.example.doancuoiky.dao.HoaDonDAO;
import com.example.doancuoiky.model.HoaDon;

import java.util.ArrayList;

public class AdminBillActivity extends AppCompatActivity {

    EditText edtMaHoaDon, edtMaSuatChieu, edtMaKhachHang, edtMaCombo, edtTongTien;
    Button btnXoa, btnXem;
    ListView lvHoaDon;
    ArrayList<HoaDon> arrHoaDon;
    AdminBillAdapter adapter;
    Context context;
    HoaDonDAO hoaDonDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bill);
        context = this;
        hoaDonDao = new HoaDonDAO(context);
        mapping();
        events();
    }

    private void events() {
        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrHoaDon.clear();
                arrHoaDon = hoaDonDao.getListHoaDon();
                adapter = new AdminBillAdapter(AdminBillActivity.this, R.layout.item_bill_admin, arrHoaDon);
                lvHoaDon.setAdapter(adapter);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maHoaDon = edtMaHoaDon.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminBillActivity.this);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc muốn xóa phim này không");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean res = hoaDonDao.delete(maHoaDon);
                        if (res){
                            Toast.makeText(AdminBillActivity.this, "Delete thành công", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(AdminBillActivity.this, "Delete thất bại", Toast.LENGTH_SHORT).show();
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
        edtMaHoaDon = findViewById(R.id.edtMaHoaDon);
        edtMaSuatChieu = findViewById(R.id.edtMaSuatChieu);
        edtMaKhachHang = findViewById(R.id.edtMaKhachHang);
        edtMaCombo = findViewById(R.id.edtMaCombo);
        edtTongTien = findViewById(R.id.edtTongTien);
        btnXoa = findViewById(R.id.btnXoaHoaDon);
        btnXem = findViewById(R.id.btnHienThiHoaDon);
        lvHoaDon = findViewById(R.id.lvHoaDon);
        arrHoaDon = hoaDonDao.getListHoaDon();
        adapter = new AdminBillAdapter(AdminBillActivity.this, R.layout.item_bill_admin, arrHoaDon);
        lvHoaDon.setAdapter(adapter);
    }
}