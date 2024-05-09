package com.example.doancuoiky.activity.user;

import android.app.AlertDialog;
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

    String hoTen, userName, password, email, ngaySinh, diaChi, soDienThoai, maKhachHang;

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
        if(edt_password.getText().toString().trim().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lỗi")
                    .setMessage("Password không được để trống")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }
        else if(!checkPassword(edt_password.getText().toString().trim())){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lỗi")
                    .setMessage("Password chỉ được tối đa 10 kí tự và chỉ gồm chữ cái và số")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }
        else{
            taiKhoan.setMatKhau(edt_password.getText().toString().trim());
            taiKhoanDAO.update(taiKhoan);
        }

        if (!isValidUsername(edt_userName.getText().toString())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lỗi")
                    .setMessage("Username không được có ký tự đặc biệt, không được quá 20 ký tự và không chứa khoảng trống.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        if (!isValidFullName(edt_hoTen.getText().toString())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lỗi")
                    .setMessage("Họ tên không được chứa ký tự đặc biệt và không quá 50 ký tự.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        if (!isValidEmail(edt_email.getText().toString())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lỗi")
                    .setMessage("Email không hợp lệ.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        if (!isValidDateFormat(edt_ngaySinh.getText().toString())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lỗi")
                    .setMessage("Ngày sinh phải có định dạng dd-MM-yyyy và là ngày hợp lệ.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
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

            hoTen = khachHang.getHoTen();
            email = khachHang.getEmail();
            ngaySinh = khachHang.getNgaySinh();
            userName = khachHang.getUserName();
            diaChi = khachHang.getDiaChi();
            soDienThoai = khachHang.getSoDienThoai();
            maKhachHang = khachHang.getMaKhachHang();

            Intent intent = new Intent();
            intent.putExtra("userName", userName);
            intent.putExtra("password", password);
            intent.putExtra("hoTen", hoTen);
            intent.putExtra("ngaySinh", ngaySinh);
            intent.putExtra("diaChi", diaChi);
            intent.putExtra("soDienThoai", soDienThoai);
            intent.putExtra("email", email);
            intent.putExtra("maKhachHang", maKhachHang);


            if (img_khachhang.getDrawable() != null) {
                BitmapDrawable drawable = (BitmapDrawable) img_khachhang.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                byte[] avatarByteArray = bitmapToByteArray(bitmap);
                intent.putExtra("imageInfo", avatarByteArray);
            }
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    public boolean checkPassword(String password) {
        // Kiểm tra xem chuỗi mật khẩu có ít nhất 10 kí tự hoặc số không
        if (password == null || password.length() < 10) {
            return false;
        }
        // Biến đếm số kí tự hoặc số
        int count = 0;
        // Lặp qua từng kí tự trong chuỗi mật khẩu
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            // Kiểm tra xem kí tự có phải là kí tự hoặc số không
            if (Character.isLetterOrDigit(ch)) {
                count++;
            }
        }
        // Trả về true nếu có ít nhất 10 kí tự hoặc số, ngược lại trả về false
        return count <= 15;
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

    public static boolean isValidFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return false;
        }
        fullName = fullName.trim();
        if (fullName.length() > 50) {
            return false;
        }
        char[] chars = fullName.toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                if (c != ' ' && c != '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w\\.-]+@([\\w\\.-]+\\.)+[\\w\\.-]{2,3}$";
        return email.matches(regex);
    }

    public static boolean isValidDateFormat(String dob) {

        if (dob == null || dob.isEmpty()) {
            return false;
        }

        String[] parts = dob.split("-");

        if (parts.length != 3) {
            return false;
        }

        for (String part : parts) {
            if (!isNumeric(part)) {
                return false;
            }
        }

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1000 || year > 9999) {
            return false;
        }

        // Kiểm tra xem tháng 2 có hợp lệ hay không (kiểm tra năm nhuận)
        if (month == 2 && !isLeapYear(year) && day > 28) {
            return false;
        }

        // Ngày sinh hợp lệ
        return true;
    }

    private static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        }
        if (year % 100 == 0 && year % 400 != 0) {
            return false;
        }
        return true;
    }

    public static boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        username = username.trim();
        if (username.length() > 20) {
            return false;
        }
        String regex = "[^a-zA-Z0-9_]";

        if (username.matches(regex)) {
            return false;
        }
        char[] chars = username.toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                if (c == ' ') {
                    return false;
                }
            }
        }
        return true;
    }




}
