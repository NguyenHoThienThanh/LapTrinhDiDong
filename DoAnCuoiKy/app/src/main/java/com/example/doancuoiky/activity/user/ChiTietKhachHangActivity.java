package com.example.doancuoiky.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ChiTietKhachHangActivity extends AppCompatActivity {

    EditText edt_userName, edt_password, edt_hoTen, edt_ngaySinh, edt_diaChi, edt_email;
    TextView txt_userName, txt_email;
    Toolbar toolbar;

    String  maKhachHang;

    Button btn_saveProfile;
    KhachHangDAO khachHangDAO;
    TaiKhoanDAO taiKhoanDAO;
    KhachHang khachHang;
    ImageView img_khachhang;
    private static final int PICK_IMAGE_REQUEST = 1;

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
        img_khachhang = findViewById(R.id.img_khachhang);
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                Uri imageUri = data.getData(); // Lấy URI của ảnh đã chọn
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                // Cập nhật ImageView
                img_khachhang.setImageBitmap(bitmap); // Cập nhật ảnh

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Chuyển Bitmap thành mảng byte
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        return baos.toByteArray();
    }
    // Hàm gọi để chọn ảnh từ thư viện
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST); // Mở thư viện ảnh
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
        img_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btn_saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });


    }

    private void updateProfile() {
        khachHang = khachHangDAO.findOneBySoDienThoai(LoginActivity.getTaiKhoan().getTaiKhoan());
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
        if (img_khachhang.getDrawable() != null) {
            BitmapDrawable drawable = (BitmapDrawable) img_khachhang.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            khachHang.setAvatar(bitmapToByteArray(bitmap));
        }
        boolean result = khachHangDAO.update(khachHang);
        if (result) {
            Toast.makeText(ChiTietKhachHangActivity.this, "Update success", Toast.LENGTH_SHORT).show();
            edt_email.setText(khachHang.getEmail());
            edt_password.setText(taiKhoan.getMatKhau());
            edt_diaChi.setText(khachHang.getDiaChi());
            edt_hoTen.setText(khachHang.getHoTen());
            edt_ngaySinh.setText(khachHang.getNgaySinh());
            edt_userName.setText(khachHang.getUserName());
            txt_email.setText(khachHang.getEmail());
            txt_userName.setText(khachHang.getUserName());

        }
    }

    private void editProfile(){
        KhachHang kh = khachHangDAO.findOneBySoDienThoai(LoginActivity.getTaiKhoan().getTaiKhoan());
        TaiKhoan tk = taiKhoanDAO.findOneByTaiKhoan(kh.getSoDienThoai());
        txt_userName.setText(kh.getUserName());
        txt_email.setText(kh.getEmail());
        edt_password.setText(tk.getMatKhau());
        edt_hoTen.setText(kh.getHoTen());
        edt_ngaySinh.setText(kh.getNgaySinh());
        edt_email.setText(kh.getEmail());
        edt_diaChi.setText(kh.getDiaChi());
        edt_userName.setText(kh.getUserName());
        Bitmap bitmap = BitmapFactory.decodeByteArray(kh.getAvatar(), 0, kh.getAvatar().length);
        img_khachhang.setImageBitmap(bitmap);
    }
}
