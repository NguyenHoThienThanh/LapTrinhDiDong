package com.example.doancuoiky.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.user.LoginActivity;

public class HomeAdminActivity extends AppCompatActivity {
    CardView cv_customer, cv_bill, cv_movie, cv_ticket, cv_logout, cv_popcorn;

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
    }

    public void mappingControl(){
        cv_customer = findViewById(R.id.cv_customer);
        cv_ticket = findViewById(R.id.cv_ticket);
        cv_bill = findViewById(R.id.cv_bill);
        cv_movie  = findViewById(R.id.cv_movie);
        cv_logout = findViewById(R.id.cv_logout);
        cv_popcorn = findViewById(R.id.cv_popcorn);
    }

    public void logOut(){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    public void customer(){
        Intent loginIntent = new Intent(this, AdminCustomerActivity.class);
        startActivity(loginIntent);
    }
    public void popCornDrink(){
        Intent loginIntent = new Intent(this, AdminPopcornDrinkActivity.class);
        startActivity(loginIntent);
    }
}
