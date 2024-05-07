package com.example.doancuoiky.activity.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.user.LoginActivity;

public class HomeAdminActivity extends AppCompatActivity {
    CardView cv_customer, cv_bill, cv_movie, cv_ticket, cv_logout, cv_popcorn, cv_filmroom, cv_staff, cv_thongke;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        mappingControl();

        cv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        cv_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer();
            }
        });
        cv_popcorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popCornDrink();
            }
        });
        cv_filmroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmRoom();
            }
        });
        cv_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staff();
            }
        });
        cv_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie();
            }
        });
        cv_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticket();
            }
        });
        cv_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bill();
            }
        });
        cv_thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statistics();
            }
        });

    }

    public void mappingControl(){
        cv_customer = findViewById(R.id.cv_customer);
        cv_ticket = findViewById(R.id.cv_ticket);
        cv_bill = findViewById(R.id.cv_bill);
        cv_movie  = findViewById(R.id.cv_movie);
        cv_logout = findViewById(R.id.cv_logout);
        cv_popcorn = findViewById(R.id.cv_popcorn);
        cv_filmroom = findViewById(R.id.cv_filmroom);
        cv_staff = findViewById(R.id.cv_staff);
        cv_thongke = findViewById(R.id.cv_thongke);
    }

    public void logOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đăng xuất");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");

        // Thiết lập nút tích cực (OK)
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Thực hiện đăng xuất khi người dùng nhấn nút "Đăng xuất"
                Intent intent = new Intent(HomeAdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Hiển thị hộp thoại cảnh báo
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void customer(){
        Intent loginIntent = new Intent(this, AdminCustomerActivity.class);
        startActivity(loginIntent);
    }
    public void popCornDrink(){
        Intent loginIntent = new Intent(this, AdminPopcornDrinkActivity.class);
        startActivity(loginIntent);
    }
    public void filmRoom(){
        Intent loginIntent = new Intent(this, AdminPhongChieuActivity.class);
        startActivity(loginIntent);
    }
    public void staff(){
        Intent loginIntent = new Intent(this, AdminStaffActivity.class);
        startActivity(loginIntent);
    }
    public void movie(){
        Intent loginIntent = new Intent(this, ActivityAdminFilm.class);
        startActivity(loginIntent);
    }
    public void ticket(){
        Intent loginIntent = new Intent(this, AdminSuatChieuActivity.class);
        startActivity(loginIntent);
    }
    public void bill(){
        Intent loginIntent = new Intent(this, AdminBillActivity.class);
        startActivity(loginIntent);
    }
    public void statistics(){
        Intent intent = new Intent(this, AdminChartActivity.class);
        startActivity(intent);
    }
}
