package com.example.doancuoiky.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.KhachHangDAO;
import com.example.doancuoiky.dao.TaiKhoanDAO;
import com.example.doancuoiky.model.KhachHang;
import com.example.doancuoiky.model.TaiKhoan;

public class ChiTietKhachHangActivity extends AppCompatActivity {

    EditText edt_userName, edt_password, edt_hoTen, edt_ngaySinh, edt_diaChi, edt_email;
    TextView txt_userName, txt_email;
    Toolbar toolbar;

    String userName, password, email, hoTen, diaChi, ngaySinh, soDienThoai, maKhachHang;

    Button btn_saveProfile;
    KhachHangDAO khachHangDAO;
    TaiKhoanDAO taiKhoanDAO;
    KhachHang khachHang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        khachHangDAO = new KhachHangDAO(this);
        taiKhoanDAO = new TaiKhoanDAO(this);
        toolBar();
        mappingControl();
        editProfile();


        saveProfile();
    }

    private void mappingControl() {
        edt_userName = findViewById(R.id.edt_userName);
        edt_password = findViewById(R.id.edt_password);
        edt_email = findViewById(R.id.edt_email);
        edt_hoTen = findViewById(R.id.edt_hoTen);
        edt_diaChi = findViewById(R.id.edt_diaChi);
        edt_ngaySinh = findViewById(R.id.edt_ngaySinh);
        txt_userName = findViewById(R.id.txt_userName);
        txt_email = findViewById(R.id.txt_email);
    }

    public void toolBar() {
        toolbar = findViewById(R.id.toolbar_editProfile);

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

    public void saveProfile() {
        btn_saveProfile = findViewById(R.id.btn_saveProfile);

        btn_saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile();


            }
        });
    }

    private void updateProfile() {
        khachHang = khachHangDAO.findOneById(maKhachHang);
        TaiKhoan taiKhoan = taiKhoanDAO.findOneByTaiKhoan(khachHang.getSoDienThoai().trim());
        taiKhoan.setMatKhau(edt_password.getText().toString().trim());
        boolean rs = taiKhoanDAO.update(taiKhoan);
        if(rs){
            Toast.makeText(this, "Update password success", Toast.LENGTH_SHORT).show();
        }

        khachHang.setEmail(edt_email.getText().toString().trim());
        khachHang.setHoTen(edt_hoTen.getText().toString());
        khachHang.setDiaChi(edt_diaChi.getText().toString());
        khachHang.setNgaySinh(edt_ngaySinh.getText().toString().trim());
        khachHang.setUserName(edt_userName.getText().toString().trim());
        boolean result = khachHangDAO.update(khachHang);
        if ( result) {
            Toast.makeText(ChiTietKhachHangActivity.this, "Update success", Toast.LENGTH_SHORT).show();
            userName = khachHang.getUserName();
            email = khachHang.getEmail();
            hoTen = khachHang.getHoTen();
            diaChi = khachHang.getDiaChi();
            ngaySinh = khachHang.getNgaySinh();
        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra("email", email);
        returnIntent.putExtra("hoTen", hoTen);
        returnIntent.putExtra("userName", userName);
        returnIntent.putExtra("diaChi", diaChi);
        returnIntent.putExtra("ngaySinh", ngaySinh);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void editProfile(){
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        password = intent.getStringExtra("password");
        email = intent.getStringExtra("email");
        hoTen = intent.getStringExtra("hoTen");
        diaChi = intent.getStringExtra("diaChi");
        ngaySinh = intent.getStringExtra("ngaySinh");
        soDienThoai = intent.getStringExtra("soDienThoai");
        maKhachHang = intent.getStringExtra("maKhachHang");

        edt_hoTen.setText(hoTen);
        edt_ngaySinh.setText(ngaySinh);
        edt_email.setText(email);
        edt_diaChi.setText(diaChi);
        edt_userName.setText(userName);
    }
}
