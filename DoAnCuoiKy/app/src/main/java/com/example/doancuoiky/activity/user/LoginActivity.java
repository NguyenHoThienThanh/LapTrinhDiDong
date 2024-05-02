package com.example.doancuoiky.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.admin.HomeAdminActivity;
import com.example.doancuoiky.dao.TaiKhoanDAO;
import com.example.doancuoiky.model.TaiKhoan;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TaiKhoan taiKhoan;
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
                taiKhoan = tkDao.login(username, password);
                if (taiKhoan != null){
                    if (taiKhoan.getRoleId() == 1){
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        startActivity(intent);
                    }
                    else if (taiKhoan.getRoleId() == 2){
                        Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void mapping() {
        edtUsername = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassworrd);
        btnLogin = findViewById(R.id.btnLogin);
    }
}