package com.example.doancuoiky.activity.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.KhachHangDAO;
import com.example.doancuoiky.dao.TaiKhoanDAO;
import com.example.doancuoiky.model.KhachHang;
import com.example.doancuoiky.model.TaiKhoan;

public class SignUpActivity extends AppCompatActivity {

    EditText edtSoDienThoai, edtEmail, edtHoTen, edtMatKhau;
    Button btnSignUp;
    KhachHangDAO khDao;
    TaiKhoanDAO tkDao;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = this;
        khDao = new KhachHangDAO(context);
        tkDao = new TaiKhoanDAO(context);
        mapping();
        events();
    }

    private void events() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = edtSoDienThoai.getText().toString();
                String email = edtEmail.getText().toString();
                String hoTen = edtHoTen.getText().toString();
                String matkhau = edtMatKhau.getText().toString();

                if (checkEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage("Vui lòng nhập đầy đủ thông tin!")
                            .setTitle("Lỗi đăng ký")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    if (!checkLegalNumberPhone(sdt)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        builder.setMessage("Số điện thoại không đúng định dạng!")
                                .setTitle("Lỗi đăng ký")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Xử lý sự kiện khi người dùng nhấn nút OK
                                        dialog.dismiss(); // Đóng hộp thoại
                                        return;
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else{
                        KhachHang kh = new KhachHang();
                        kh.setHoTen(hoTen);
                        kh.setSoDienThoai(sdt);
                        kh.setEmail(email);

                        TaiKhoan tk = new TaiKhoan();
                        tk.setTaiKhoan(sdt);
                        tk.setMatKhau(matkhau);
                        tk.setRoleId(1);

                        boolean res_kh = khDao.insert(kh);
                        boolean res_tk = tkDao.register(tk);
                        if (res_tk && res_kh){
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setMessage("Đăng ký thành công!")
                                    .setTitle("Success")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Xử lý sự kiện khi người dùng nhấn nút OK
                                            dialog.dismiss(); // Đóng hộp thoại
                                            return;
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                }

            }
        });
    }

    private void mapping() {
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtEmail = findViewById(R.id.edtEmail);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private boolean checkLegalNumberPhone(String phone){
        if (phone.length() != 10){
            return false;
        }
        try {
            long i = (long) Double.parseDouble(phone);
            return i > 0;
        } catch (Exception e) {
        }
        return false;
    }

    private boolean checkEmpty(){
        String sdt = edtSoDienThoai.getText().toString();
        String email = edtEmail.getText().toString();
        String hoTen = edtHoTen.getText().toString();
        String matkhau = edtMatKhau.getText().toString();

        if (sdt.equals("") || email.equals("") || hoTen.equals("") || matkhau.equals(""))
            return true;
        return false;
    }
}