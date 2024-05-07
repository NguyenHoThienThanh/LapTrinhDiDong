package com.example.doancuoiky.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.KhachHangDAO;
import com.example.doancuoiky.dao.TaiKhoanDAO;
import com.example.doancuoiky.model.KhachHang;

public class KhachHangActivity extends AppCompatActivity {

    TextView txt_hoTen, txt_gioiTinh, txt_soDienThoai, txt_email, txt_ngaySinh, txt_userName, txt_diaChi;

    String hoTen, userName, password, email, ngaySinh, diaChi, soDienThoai, maKhachHang;
    ImageView img_avt;
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

        toolBar();
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
        img_avt = findViewById(R.id.img_avatar_profile);
    }

    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_profile);

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

    public void getProfile(){
        khachHang = new KhachHang();
        khachHang = khachHangDAO.findOneBySoDienThoai(LoginActivity.getTaiKhoan().getTaiKhoan());
        Toast.makeText(this, ""+khachHang.getAvatar(), Toast.LENGTH_SHORT).show();
        Bitmap bitmap = BitmapFactory.decodeByteArray(khachHang.getAvatar(), 0, khachHang.getAvatar().length);
        img_avt.setImageBitmap(bitmap);
        txt_userName.setText(khachHang.getUserName());
        txt_email.setText(khachHang.getEmail());
        txt_ngaySinh.setText(khachHang.getNgaySinh());
        txt_hoTen.setText(khachHang.getHoTen());
        if(khachHang.getGioiTinh() == 0 ){
            txt_gioiTinh.setText("Nam");
        }else if(khachHang.getGioiTinh() == 1) {
            txt_gioiTinh.setText("Nữ");

        }
        txt_diaChi.setText(khachHang.getDiaChi());
        txt_soDienThoai.setText(khachHang.getSoDienThoai());

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
            byte[] avatarByteArray = getIntent().getByteArrayExtra("avatarByteArray");
            txt_email.setText(email);
            txt_userName.setText(userName);
            txt_hoTen.setText(hoTen);
            txt_ngaySinh.setText(ngaySinh);
            txt_diaChi.setText(diaChi);
            if (avatarByteArray != null) {
                setImageUsingGlide(avatarByteArray);
            }
        }
    }

    private void setImageUsingGlide(byte[] avatarByteArray) {
        Glide.with(this)
                .load(avatarByteArray)
                .placeholder(R.drawable.bhd_icon) // Placeholder image if loading fails
                .into(img_avt); // ImageView to set the image
    }

    // Example method for setting image using manual decoding (optional)
    private void setImageUsingManualDecoding(byte[] avatarByteArray) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;  // Only decode bounds for efficiency
        BitmapFactory.decodeByteArray(avatarByteArray, 0, avatarByteArray.length, options);

        // Calculate inSampleSize to scale down the image if necessary
        int inSampleSize = calculateInSampleSize(options, img_avt.getWidth(), img_avt.getHeight());
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(avatarByteArray, 0, avatarByteArray.length, options);
        img_avt.setImageBitmap(decodedBitmap);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratio of height and width to requested height and width
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            // Keep reducing inSampleSize until the image dimensions are smaller or equal to the requested dimensions
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}
