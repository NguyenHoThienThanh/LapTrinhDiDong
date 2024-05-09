package com.example.doancuoiky.activity.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.KhachHangDAO;
import com.example.doancuoiky.dao.TaiKhoanDAO;
import com.example.doancuoiky.model.KhachHang;
import com.example.doancuoiky.model.TaiKhoan;

public class SignUpActivity extends AppCompatActivity {

    TextView txtLogin;
    EditText edtSoDienThoai, edtEmail, edtHoTen, edtMatKhau, edtNhapLaiMK;
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
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = edtSoDienThoai.getText().toString();
                String email = edtEmail.getText().toString();
                String hoTen = edtHoTen.getText().toString();
                String matkhau = edtMatKhau.getText().toString();
                String repeat = edtNhapLaiMK.getText().toString();

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
                    } else if (matkhau.length() < 5 || matkhau.length() > 15) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        builder.setMessage("Mật khẩu phải có từ 5 đến 10 ký tự!")
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
                    else if (khDao.findOneBySoDienThoai(sdt).getHoTen() != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        builder.setMessage("Số điện thoại này đã được sử dụng!")
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
                    else if (!isValidEmail(email)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        builder.setMessage("Email không đúng định dạng!")
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
                    else if(!matkhau.equals(repeat)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        builder.setMessage("Mật khẩu nhập lại không chính xác!")
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
                    else {
                        KhachHang kh = new KhachHang(hoTen, email, sdt);

                        TaiKhoan tk = new TaiKhoan();
                        tk.setTaiKhoan(sdt);
                        tk.setMatKhau(matkhau);
                        tk.setRoleId(1);


                        boolean res_tk = tkDao.register(tk);
                        boolean res_kh = khDao.insert(kh);

                        if (res_tk && res_kh) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setMessage("Đăng ký thành công!")
                                    .setTitle("Success")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Xử lý sự kiện khi người dùng nhấn nút OK
                                            dialog.dismiss(); // Đóng hộp thoại
                                            clearText();
                                            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                                            startActivity(i);
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
        edtNhapLaiMK = findViewById(R.id.edtNhapLaiMK);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtLogin = findViewById(R.id.txtLogin);
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

    private void clearText(){
        edtSoDienThoai.setText("");
        edtEmail.setText("");
        edtHoTen.setText("");
        edtMatKhau.setText("");
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[\\w\\.-]+@([\\w\\.-]+\\.)+[\\w\\.-]{2,3}$";
        return email.matches(regex);
    }
}