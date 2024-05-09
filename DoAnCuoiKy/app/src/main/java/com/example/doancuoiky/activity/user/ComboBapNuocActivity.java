package com.example.doancuoiky.activity.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.ComboBapNuocAdapter;
import com.example.doancuoiky.dao.ComboBapNuocDAO;
import com.example.doancuoiky.model.ComboBapNuoc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class ComboBapNuocActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ComboBapNuocAdapter adapter;
    ArrayList<ComboBapNuoc> foodAndBeverageList = new ArrayList<>();
    ArrayList<ComboBapNuoc> fandBList = new ArrayList<>();
    Button btn_continue;
    TextView tv_1, tv_2, tv_labelTotal, tv_total, tv_number;
    ComboBapNuocDAO comboBapNuocDAO;

    String  maPhongChieu, gioChieu, ngayChieu, maSuatChieu;
    double total = 0;
    double totalPrice = 0;
    ArrayList<String> selectedSeats;
    ArrayList<String> maChoNgoiList;

    String tenPhim, maPhim;
    int gioiHanTuoi;
    byte[] poster;
    double giaVe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_and_beverage);
        comboBapNuocDAO = new ComboBapNuocDAO(this);
        Intent intent = getIntent();
        totalPrice = intent.getDoubleExtra("totalPrice", 0.0);
        selectedSeats = intent.getStringArrayListExtra("selectedSeats");
        maChoNgoiList = intent.getStringArrayListExtra("maChoNgoiList");
        maSuatChieu = intent.getStringExtra("maSuatChieu");
        maPhongChieu = intent.getStringExtra("maPhongChieu");
        gioChieu = intent.getStringExtra("gioChieu");
        ngayChieu = intent.getStringExtra("ngayChieu");
        maPhim = intent.getStringExtra("maPhim");
        tenPhim = intent.getStringExtra("tenPhim");
        gioiHanTuoi = intent.getIntExtra("gioiHanTuoi", 0);
        poster = intent.getByteArrayExtra("poster");
        giaVe = intent.getDoubleExtra("giaVe", 0);
        toolBarFAndB();
        mappingControl();
        foodAndBeverageAdapter();
        buttonContinue();
    }


    public void toolBarFAndB() {
        Toolbar toolbar = findViewById(R.id.toolbar_fandb);
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

    public void mappingControl(){
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_labelTotal = findViewById(R.id.txt_labeltotal);
        tv_total = findViewById(R.id.txt_total);
        btn_continue = findViewById(R.id.btn_continue);
        tv_number =findViewById(R.id.tv_numberItems);
    }

    private void foodAndBeverageAdapter() {
        recyclerView = findViewById(R.id.rcv_fandb);
        adapter = new ComboBapNuocAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        foodAndBeverageList = (ArrayList<ComboBapNuoc>) getListFoodAndBeverage();
        adapter.setData(foodAndBeverageList, new ComboBapNuocAdapter.IOnItemClickListener() {
            @Override
            public void onPlusClick(int position) {
                ComboBapNuoc foodAndBeverage = foodAndBeverageList.get(position);
                int numberOrder = foodAndBeverage.getSoLuongDat();
                if (numberOrder < foodAndBeverage.getSoLuong()) {
                    numberOrder = numberOrder + 1;
                    foodAndBeverage.setSoLuongDat(numberOrder);
                    adapter.updateQuantityTextView(position, numberOrder);
                    total = total + (foodAndBeverage.getSoLuongDat() * foodAndBeverage.getGia());
                    calculateTotal();
                }
                if(numberOrder > 0 && !fandBList.contains(foodAndBeverage)) fandBList.add(foodAndBeverage);
            }
            @Override
            public void onMinusClick(int position) {
                ComboBapNuoc foodAndBeverage = foodAndBeverageList.get(position);
                int numberOrder = foodAndBeverage.getSoLuongDat();
                if (numberOrder > 0) {
                    numberOrder = numberOrder - 1;
                    foodAndBeverage.setSoLuongDat(numberOrder);
                    adapter.updateQuantityTextView(position, numberOrder);
                    total += (foodAndBeverage.getSoLuongDat() * foodAndBeverage.getGia());
                    calculateTotal();
                }
                if(numberOrder == 0 && fandBList.contains(foodAndBeverage)) fandBList.remove(foodAndBeverage);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void calculateTotal() {
        double total = 0;
        for (ComboBapNuoc item : foodAndBeverageList) {
            total += item.getGia() * item.getSoLuongDat();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        String formattedMoney = decimalFormat.format(total);
        tv_total.setText(String.valueOf(formattedMoney + "đ"));
        this.total = total;
    }

    public List<ComboBapNuoc> getListFoodAndBeverage(){
        List<ComboBapNuoc> foodAndBeverage = new ArrayList<>();
        foodAndBeverage = comboBapNuocDAO.findAllComboUser();
        return foodAndBeverage;
    }
    public void buttonContinue() {
        btn_continue = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComboBapNuocActivity.this, ThanhToanActivity.class);
                // Gửi danh sách ghế đã chọn qua Intent
                intent.putStringArrayListExtra("selectedSeats", selectedSeats);
                intent.putStringArrayListExtra("maChoNgoiList", maChoNgoiList);
                intent.putExtra("selectedCombos", fandBList);
                intent.putExtra("totalPrice", totalPrice);
                intent.putExtra("total", total);

                intent.putExtra("maSuatChieu", maSuatChieu);
                intent.putExtra("maPhongChieu", maPhongChieu);
                intent.putExtra("gioChieu", gioChieu);
                intent.putExtra("ngayChieu", ngayChieu);
                intent.putExtra("tenPhim", tenPhim);
                intent.putExtra("maPhim", maPhim);
                intent.putExtra("gioiHanTuoi", gioiHanTuoi);
                intent.putExtra("giaVe", giaVe);
                intent.putExtra("poster", poster);
                // Khởi chạy TestActivity
                startActivity(intent);
            }
        });
    }

}