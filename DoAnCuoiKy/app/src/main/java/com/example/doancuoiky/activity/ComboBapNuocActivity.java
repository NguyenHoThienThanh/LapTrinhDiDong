package com.example.doancuoiky.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.ComboBapNuocAdapter;
import com.example.doancuoiky.dao.ChoNgoiDAO;
import com.example.doancuoiky.dao.ComboBapNuocDAO;
import com.example.doancuoiky.model.ChoNgoi;
import com.example.doancuoiky.model.ComboBapNuoc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class ComboBapNuocActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ComboBapNuocAdapter adapter;
    ArrayList<ComboBapNuoc> foodAndBeverageList = new ArrayList<>();
    Button btn_continue;
    TextView tv_1, tv_2, tv_labelTotal, tv_total, tv_number;
    ComboBapNuocDAO comboBapNuocDAO;
    double total = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_and_beverage);
        comboBapNuocDAO = new ComboBapNuocDAO(this);
        toolBarFAndB();
        getAddWidgets();
        foodAndBeverageAdapter();
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

    public void getAddWidgets(){
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
                int number = foodAndBeverage.getSoLuong();
                if (numberOrder < foodAndBeverage.getSoLuong()) {
                    numberOrder = numberOrder + 1;
                    foodAndBeverage.setSoLuongDat(numberOrder);
                    adapter.updateQuantityTextView(position, numberOrder);
                    total = total + (foodAndBeverage.getSoLuongDat() * foodAndBeverage.getGia());
                    calculateTotal();
                }
            }
            @Override
            public void onMinusClick(int position) {
                ComboBapNuoc foodAndBeverage = foodAndBeverageList.get(position);
                int numberOrder = foodAndBeverage.getSoLuongDat();
                int number = foodAndBeverage.getSoLuong();
                if (numberOrder > 0) {
                    numberOrder = numberOrder - 1;
                    foodAndBeverage.setSoLuongDat(numberOrder);
                    adapter.updateQuantityTextView(position, numberOrder);
                    total += (foodAndBeverage.getSoLuongDat() * foodAndBeverage.getGia());
                    calculateTotal();
                }
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
        List<ComboBapNuoc> foodAndBeverage = null;
        return foodAndBeverage;
    }

}