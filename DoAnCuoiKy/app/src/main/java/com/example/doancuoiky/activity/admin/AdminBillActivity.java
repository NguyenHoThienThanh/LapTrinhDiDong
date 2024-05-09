package com.example.doancuoiky.activity.admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    LinearLayout layout_HoaDon;
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
        toolBar();
        events();
    }

    private void events() {
        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HoaDon hd = arrHoaDon.get(position);
                edtMaHoaDon.setText(String.valueOf(hd.getMaHoaDon()));
                edtMaKhachHang.setText(String.valueOf(hd.getMaKhachHang()));
                edtMaSuatChieu.setText(String.valueOf(hd.getMaSuatChieu()));
                edtMaCombo.setText(String.valueOf(hd.getMaCombo()));
                edtTongTien.setText(String.valueOf(hd.getTongTien()));
            }
        });
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
                if (maHoaDon.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminBillActivity.this);
                    builder.setMessage("Vui lòng chọn hóa đơn để xóa!")
                            .setTitle("Fail")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminBillActivity.this);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc muốn xóa hóa đơn này không");
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
        layout_HoaDon = findViewById(R.id.layout_HoaDon);
        lvHoaDon = findViewById(R.id.lvHoaDon);
        arrHoaDon = hoaDonDao.getListHoaDon();
        adapter = new AdminBillAdapter(AdminBillActivity.this, R.layout.item_bill_admin, arrHoaDon);
        lvHoaDon.setAdapter(adapter);
    }

    private boolean checkEmptyInput(){
        int childCount = layout_HoaDon.getChildCount();
        for (int i=0; i<childCount; i++){
            View view = layout_HoaDon.getChildAt(i);
            if (view instanceof  EditText){
                EditText edt = (EditText) view;
                if (edt.getText().toString().equals("")){
                    return true;
                }
            }
        }
        return false;
    }

    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_Phim);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}