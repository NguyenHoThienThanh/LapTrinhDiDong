package com.example.doancuoiky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.DanhSachGheAdapter;
import com.example.doancuoiky.dao.ChoNgoiDAO;
import com.example.doancuoiky.model.ChoNgoi;
import com.example.doancuoiky.model.DanhSachGhe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChoNgoiActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DanhSachGheAdapter adapter;
    ArrayList<DanhSachGhe> arraySeats = new ArrayList<>();
    Button btn_continue;

    ChoNgoiDAO danhSachGheDAO;
    TextView txt_tempTotal;

    double tongTien = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cho_ngoi);
        danhSachGheDAO = new ChoNgoiDAO(this);
        txt_tempTotal = findViewById(R.id.txt_TempTotal);
        toolBarSeat();
        seatAdapter();
        buttonContinue();
    }

    private List<DanhSachGhe> getListSeat() {
        ArrayList<DanhSachGhe> list = new ArrayList<>();
        list = danhSachGheDAO.getSeatBySuatChieu("MSC002", "PC02");
        return list;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Kiểm tra nếu home button được chọn
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toolBarSeat() {
        Toolbar toolbar = findViewById(R.id.toolbar_seat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void seatAdapter() {
        recyclerView = findViewById(R.id.rcv_seat);
        // Khởi tạo adapter và gán vào recyclerView
        adapter = new DanhSachGheAdapter(this);

        // Tạo một đối tượng SpanSizeLookup
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 8);
        recyclerView.setLayoutManager(gridLayoutManager);

        arraySeats = (ArrayList<DanhSachGhe>) getListSeat();
        adapter.setData(arraySeats, new DanhSachGheAdapter.IOnSeatClickListener() {
            @Override
            public void onSeatClick(int position) {
                DanhSachGhe seat = arraySeats.get(position);
                double giaGhe = 55000; // Đơn vị tiền
                if (seat.isSelected()) {
                    // Nếu ghế đã được chọn trước đó, giảm tiền đi
                    tongTien += giaGhe;
                } else {
                    // Nếu ghế chưa được chọn trước đó, tăng tiền lên
                    tongTien -= giaGhe;
                }
                // Cập nhật giao diện hiển thị tổng tiền
                updateTotalPrice(tongTien);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void updateTotalPrice(double totalPrice) {
        // Đây là nơi để bạn cập nhật giao diện hiển thị tổng tiền, có thể là TextView hoặc bất kỳ phần tử giao diện nào khác
        // Ví dụ:
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        String formattedMoney = decimalFormat.format(totalPrice);
        txt_tempTotal.setText(String.valueOf(formattedMoney + "đ"));
    }

    public void buttonContinue() {
        btn_continue = findViewById(R.id.btn_Continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoNgoiActivity.this, ComboBapNuocActivity.class);
                startActivity(intent);
            }
        });
    }
}