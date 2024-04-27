package com.example.doancuoiky.activity.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
    ListView lv_customer;
    AdminCustomerAdapter adminCustomerAdapter;
    ArrayList<KhachHang> listKhachHang = new ArrayList<>();
    KhachHangDAO khachHangDAO;

    public KhachHang khachHangSelected;
    int pos =-1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_customer);
        khachHangDAO = new KhachHangDAO(this);
        lv_customer = findViewById(R.id.lv_customer);
        toolBar();
        customerAdapter();

        //getList();

        lv_customer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                khachHangSelected = listKhachHang.get(position);
                pos = position;
                return false;
            }
        });
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
        listKhachHang = khachHangDAO.findAllKhachHang();
        adminCustomerAdapter = new AdminCustomerAdapter(this, R.layout.item_customer, listKhachHang);
        lv_customer.setAdapter(adminCustomerAdapter);
        registerForContextMenu(lv_customer);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_khachhang, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_delete) {
            delete();
            return true;
        }else {
            return super.onContextItemSelected(item);
        }
    }

    private void delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminCustomerActivity.this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa khách hàng này?");

// Thiết lập nút tích cực (Đồng ý)
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                khachHangDAO.delete(khachHangSelected.getMaKhachHang());
                listKhachHang.clear(); // Xóa danh sách hiện tại
                listKhachHang.addAll(khachHangDAO.findAllKhachHang());
                adminCustomerAdapter.notifyDataSetChanged();
                khachHangSelected = null;


            }
        });

// Thiết lập nút tiêu cực (Hủy)
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Nếu người dùng chọn Hủy, đóng hộp thoại
                dialog.dismiss();
            }
        });

// Hiển thị hộp thoại
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
