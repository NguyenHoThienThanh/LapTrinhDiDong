package com.example.doancuoiky.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.ThanhToanAdapter;
import com.example.doancuoiky.model.ComboBapNuoc;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThanhToanActivity extends AppCompatActivity {
    ArrayList<ComboBapNuoc> selectedCombos;
    TextView tv_seatselecter, tv_totalmoney_thanhtoan, tv_totalmoneyseat_thanhtoan, tv_infofilm_thanhtoan, tv_movieroom_thanhtoan;
    RecyclerView rcv;

    ThanhToanAdapter thanhToanAdapter;
    double totalPrice = 0;
    double total = 0;

    String maPhongChieu, gioChieu, ngayChieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        toolBarThanhToan();
        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> selectedSeats = intent.getStringArrayListExtra("selectedSeats");
            selectedCombos = (ArrayList<ComboBapNuoc>) intent.getSerializableExtra("selectedCombos");
            totalPrice = intent.getDoubleExtra("totalPrice", 0.0);
            total = intent.getDoubleExtra("total", 0.0);
            maPhongChieu = intent.getStringExtra("maPhongChieu");
            gioChieu = intent.getStringExtra("gioChieu");
            ngayChieu = intent.getStringExtra("ngayChieu");
            mappingControl();
            StringBuilder seatString = new StringBuilder();
            // Lặp qua từng phần tử trong danh sách selectedSeats
            for (int i = 0; i < selectedSeats.size(); i++) {
                // Thêm phần tử vào chuỗi
                seatString.append(selectedSeats.get(i));

                // Nếu không phải phần tử cuối cùng, thêm dấu phẩy vào chuỗi
                if (i < selectedSeats.size() - 1) {
                    seatString.append(", ");
                }
            }
            DecimalFormat decimalFormat1 = new DecimalFormat("#,###.###");
            String formattedMoney1 = decimalFormat1.format(total + totalPrice);
            tv_totalmoney_thanhtoan.setText(formattedMoney1 + "đ");

            tv_infofilm_thanhtoan.setText(ngayChieu + " | " + gioChieu + " | ");
            tv_movieroom_thanhtoan.setText("Phòng chiếu: " + maPhongChieu);

            tv_seatselecter.setText("Danh sách ghế đã chọn: " + seatString.toString());

            DecimalFormat decimalFormat2 = new DecimalFormat("#,###.###");
            String formattedMoney2 = decimalFormat2.format(totalPrice);
            tv_totalmoneyseat_thanhtoan.setText("Tổng tiền vé: " + formattedMoney2 + "đ");

        }
        comBoAdapter();
    }
    public void mappingControl(){
        tv_seatselecter = findViewById(R.id.tv_seatselected_thanhtoan);
        tv_totalmoney_thanhtoan = findViewById(R.id.tv_totalmoney_thanhtoan);
        tv_totalmoneyseat_thanhtoan = findViewById(R.id.tv_totalmoneyseat_thanhtoan);
        tv_infofilm_thanhtoan = findViewById(R.id.tv_infofilm_thanhtoan);
        tv_movieroom_thanhtoan = findViewById(R.id.tv_movieroom_thanhtoan);
    }
    public void toolBarThanhToan() {
        Toolbar toolbar = findViewById(R.id.toolbar_thanhtoan);
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
    private void comBoAdapter() {
        rcv = findViewById(R.id.rcv_fandb_thanhtoan);
        thanhToanAdapter = new ThanhToanAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(linearLayoutManager);

        if (selectedCombos.isEmpty()) {
            TextView textView = findViewById(R.id.tv_empty_message);
            textView.setVisibility(View.VISIBLE); // Hiển thị TextView
            rcv.setVisibility(View.GONE); // Ẩn RecyclerView
        } else {
            // Nếu có combo đã chọn, hiển thị RecyclerView và thiết lập dữ liệu cho adapter
            thanhToanAdapter.setData(selectedCombos);
            rcv.setAdapter(thanhToanAdapter);
        }
    }
}