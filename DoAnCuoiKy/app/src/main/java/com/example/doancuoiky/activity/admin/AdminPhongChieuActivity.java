package com.example.doancuoiky.activity.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.AdminPhongChieuAdapter;
import com.example.doancuoiky.dao.PhongChieuPhimDAO;
import com.example.doancuoiky.model.PhongChieuPhim;

import java.util.ArrayList;

public class AdminPhongChieuActivity extends AppCompatActivity {
    EditText edt_room_capacity;
    Button btn_them, btn_huy;
    ListView lv_cinema_room;
    AdminPhongChieuAdapter AdminPhongChieuAdapter;
    ArrayList<PhongChieuPhim> listPhongChieuPhim = new ArrayList<>();
    PhongChieuPhimDAO phongChieuPhimDAO;

    public PhongChieuPhim phongChieuPhimSelected;
    int pos =-1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_phong_chieu);
        phongChieuPhimDAO = new PhongChieuPhimDAO(this);
        lv_cinema_room = findViewById(R.id.lv_cinema_rooms);
        toolBar();
        customerAdapter();
        btn_them = findViewById(R.id.btn_them_pc);
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkEmptyFields()) {
                    return;
                }
                TextView edt_room_capacity = findViewById(R.id.edt_room_capacity);

                int soChoNgoi;
                try {
                    soChoNgoi = Integer.parseInt(edt_room_capacity.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(AdminPhongChieuActivity.this, "Số chỗ ngồi không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                PhongChieuPhim newPhongChieu = new PhongChieuPhim(soChoNgoi);
                boolean isInserted = phongChieuPhimDAO.insertPhongChieuPhim(newPhongChieu);
                if (isInserted) {
                    Toast.makeText(AdminPhongChieuActivity.this, "Thêm phòng chiếu thành công", Toast.LENGTH_SHORT).show();
                    customerAdapter(); // Cập nhật danh sách phòng chiếu
                } else {
                    Toast.makeText(AdminPhongChieuActivity.this, "Thêm phòng chiếu không thành công", Toast.LENGTH_SHORT).show();
                }
                edt_room_capacity.setText("");
            }
            private boolean checkEmptyFields() {
                TextView edt_soChoNgoi = findViewById(R.id.tv_so_cho_ngoi);

                if (edt_soChoNgoi.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(this, "Số chỗ ngồi không được để trống", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        });
        edt_room_capacity = findViewById(R.id.edt_room_capacity);
        btn_huy = findViewById(R.id.btn_cancel);
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_room_capacity.setText("");
            }
        });
    }

    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_filmroom);
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

    private void customerAdapter(){
        listPhongChieuPhim = phongChieuPhimDAO.findAllPhongChieuPhim();
        AdminPhongChieuAdapter = new AdminPhongChieuAdapter(this, R.layout.item_phong_chieu, listPhongChieuPhim);
        lv_cinema_room.setAdapter(AdminPhongChieuAdapter);
        registerForContextMenu(lv_cinema_room);
    }
}