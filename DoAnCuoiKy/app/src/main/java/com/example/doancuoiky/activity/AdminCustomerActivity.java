package com.example.doancuoiky.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.AdminCustomerAdapter;
import com.example.doancuoiky.dao.KhachHangDAO;
import com.example.doancuoiky.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class AdminCustomerActivity extends AppCompatActivity {
    RecyclerView rcv_customer;
    AdminCustomerAdapter adminCustomerAdapter;
    ArrayList<KhachHang> listKhachHang = new ArrayList<>();
    KhachHangDAO khachHangDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_customer);
        khachHangDAO = new KhachHangDAO(this);
        toolBar();
        customerAdapter();
        //getList();
    }

    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_customer);
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

    private void customerAdapter(){
        rcv_customer = findViewById(R.id.rcv_customer);
        adminCustomerAdapter = new AdminCustomerAdapter(this);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_customer.setLayoutManager(linearLayout);
        listKhachHang = khachHangDAO.findAllKhachHang();

        adminCustomerAdapter.setData(listKhachHang);
        rcv_customer.setAdapter(adminCustomerAdapter);

    }

    public List<KhachHang> getList(){
        listKhachHang = khachHangDAO.findAllKhachHang();
        return  listKhachHang;
    }

}
