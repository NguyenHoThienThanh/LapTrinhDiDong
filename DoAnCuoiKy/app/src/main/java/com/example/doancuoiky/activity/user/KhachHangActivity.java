package com.example.doancuoiky.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.KhachHangDAO;
import com.example.doancuoiky.dao.TaiKhoanDAO;
import com.example.doancuoiky.model.KhachHang;

public class KhachHangActivity extends AppCompatActivity {

    TextView txt_hoTen, txt_gioiTinh, txt_soDienThoai, txt_email, txt_ngaySinh, txt_userName, txt_diaChi;

    String hoTen, userName, password, email, ngaySinh, diaChi, soDienThoai, maKhachHang;
    Button btn_editProfile;
    KhachHangDAO khachHangDAO;
    TaiKhoanDAO taiKhoanDAO;
    KhachHang khachHang;
    private static final int REQUEST_CODE_EDIT = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        khachHangDAO = new KhachHangDAO(this);
        taiKhoanDAO = new TaiKhoanDAO(this);


        mappingControl();

//        Intent intent = getIntent();
//        userName = intent.getStringExtra("userName");
//        email = intent.getStringExtra("email");
//        hoTen = intent.getStringExtra("hoTen");
//        diaChi = intent.getStringExtra("diaChi");
//        ngaySinh = intent.getStringExtra("ngaySinh");
        getProfile();
        editProfile();



    }

    public void mappingControl(){
        txt_email = findViewById(R.id.txt_email);
        txt_gioiTinh = findViewById(R.id.txt_gioiTinh);
        txt_hoTen = findViewById(R.id.txt_hoTen);
        txt_soDienThoai = findViewById(R.id.txt_soDienThoai);
        txt_ngaySinh = findViewById(R.id.txt_ngaySinh);
        txt_userName = findViewById(R.id.txt_userName);
        txt_diaChi = findViewById(R.id.txt_diaChi);
    }

    public void getProfile(){
        khachHang = new KhachHang();
        khachHang = khachHangDAO.findOneById("KH002");

        txt_userName.setText(khachHang.getUserName());
        txt_email.setText(khachHang.getEmail());
        txt_ngaySinh.setText( "Ngày sinh: "+khachHang.getNgaySinh());
        txt_hoTen.setText( "Họ và tên: "+khachHang.getHoTen());
        if(khachHang.getGioiTinh() == 0 ){
            txt_gioiTinh.setText("Giới tính: Nam");
        }else {
            txt_gioiTinh.setText("Giới tính: Nữ");
        }
        txt_diaChi.setText("Địa chỉ: " + khachHang.getDiaChi());
        txt_soDienThoai.setText("Số điện thoại: " + khachHang.getSoDienThoai());

        hoTen = khachHang.getHoTen();
        email = khachHang.getEmail();
        ngaySinh = khachHang.getNgaySinh();
        userName = khachHang.getUserName();
        diaChi = khachHang.getDiaChi();
        soDienThoai = khachHang.getSoDienThoai();
        maKhachHang = khachHang.getMaKhachHang();

    }

    public void editProfile(){
        btn_editProfile = findViewById(R.id.btn_editProfile);

        btn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KhachHangActivity.this, ChiTietKhachHangActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("password", password);
                intent.putExtra("hoTen", hoTen);
                intent.putExtra("ngaySinh", ngaySinh);
                intent.putExtra("diaChi", diaChi);
                intent.putExtra("soDienThoai", soDienThoai);
                intent.putExtra("email", email);
                intent.putExtra("maKhachHang", maKhachHang);
                startActivityForResult(intent, REQUEST_CODE_EDIT);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_EDIT &&  resultCode == RESULT_OK && data != null ) {
            userName = data.getStringExtra("userName");
            email = data.getStringExtra("email");
            hoTen = data.getStringExtra("hoTen");
            diaChi = data.getStringExtra("diaChi");
            ngaySinh = data.getStringExtra("ngaySinh");
            Toast.makeText(this, userName, Toast.LENGTH_SHORT).show();
            txt_email.setText(email);
            txt_userName.setText(userName);
            txt_hoTen.setText("Họ và tên: "+hoTen);
            txt_ngaySinh.setText("Ngày sinh: "+ngaySinh);
            txt_diaChi.setText("Địa chỉ: " + diaChi);
        }
    }



}
