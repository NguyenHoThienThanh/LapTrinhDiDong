package com.example.doancuoiky.activity.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.admin.HomeAdminActivity;
import com.example.doancuoiky.dao.TaiKhoanDAO;
import com.example.doancuoiky.model.TaiKhoan;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin;
    private static TaiKhoan taiKhoan;
    TaiKhoanDAO tkDao;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        tkDao = new TaiKhoanDAO(context);
        mapping();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Tài khoản hoặc mật khẩu rỗng. Vui lòng kiểm tra lại ")
                            .setTitle("Lỗi đăng nhập")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    if (tkDao.isTaiKhoanExists(username, password)) {
                        taiKhoan = tkDao.login(username, password);
                        if (taiKhoan != null) {
                            showSuccessDialog();
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Tài khoản hoặc mật khẩu sai, vui lòng đăng nhập lại")
                                .setTitle("Lỗi đăng nhập")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Xử lý sự kiện khi người dùng nhấn nút OK
                                        dialog.dismiss(); // Đóng hộp thoại
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }
            }
        });
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_success, null);
        builder.setView(dialogView);

        // Tạo AlertDialog
        final AlertDialog dialog = builder.create();
        dialog.show();

        // Ẩn AlertDialog sau 2 giây
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                if (taiKhoan.getRoleId() == 1) {
                    navigateToUserPage();
                } else if (taiKhoan.getRoleId() == 2) {
                    navigateToAdminPage();
                }
            }
        }, 3000);
    }

    private void navigateToUserPage() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("username", edtUsername.getText().toString());
        intent.putExtra("password", edtPassword.getText().toString());
        startActivity(intent);
    }

    private void navigateToAdminPage() {
        Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
        intent.putExtra("username", edtUsername.getText().toString());
        intent.putExtra("password", edtPassword.getText().toString());
        startActivity(intent);
    }

    private void mapping() {
        edtUsername = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassworrd);
        btnLogin = findViewById(R.id.btnLogin);
        edtUsername.setText("");
        edtPassword.setText("");
    }

    public static TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }
}