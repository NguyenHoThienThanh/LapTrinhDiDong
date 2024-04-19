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

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.ChoNgoiAdapter;
import com.example.doancuoiky.dao.ChoNgoiDAO;
import com.example.doancuoiky.model.ChoNgoi;

import java.util.ArrayList;
import java.util.List;

public class ChoNgoiActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ChoNgoiAdapter adapter;
    ArrayList<ChoNgoi> arraySeats = new ArrayList<>();
    Button btn_continue;

    ChoNgoiDAO choNgoiDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cho_ngoi);
        choNgoiDAO = new ChoNgoiDAO(this);
        toolBarSeat();
        fakeData();
        seatAdapter();
        buttonContinue();
    }

    private List<ChoNgoi> getListSeat() {
        List<ChoNgoi> list = new ArrayList<>();
        list = choNgoiDAO.findAllChoNgoi();
        return list;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Kiểm tra nếu home button được chọn
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void toolBarSeat(){
        Toolbar toolbar = findViewById(R.id.toolbar_seat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void seatAdapter(){
        recyclerView = findViewById(R.id.rcv_seat);
        // Khởi tạo adapter và gán vào recyclerView
        adapter = new ChoNgoiAdapter(this);

        // Tạo một đối tượng SpanSizeLookup
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 8);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter.setData(getListSeat());
        recyclerView.setAdapter(adapter);
    }
    public void buttonContinue(){
        btn_continue = findViewById(R.id.btn_Continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoNgoiActivity.this, ComboBapNuocActivity.class);
                startActivity(intent);
            }
        });
    }


    public void fakeData(){

        // Tạo dữ liệu giả mạo cho chỗ ngồi
        ArrayList<ChoNgoi> fakeChoNgoiList = new ArrayList<>();

        fakeChoNgoiList.add(new ChoNgoi("LVVP1-1", "A", 1, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-2", "A", 2, 1, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-3", "A", 3, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-4", "A", 4, 1, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-5", "A", 5, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-6", "A", 6, 1, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-7", "A", 7, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-8", "A", 8, 1, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-9", "B", 1, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-10", "B", 2, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-11", "B", 3, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-12", "B", 4, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-13", "B", 5, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-14", "B", 6, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-15", "B", 7, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-16", "B", 8, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-17", "C", 1, 1, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-18", "C", 2, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-19", "C", 3, 1, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-20", "C", 4, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-21", "C", 5, 1, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-22", "C", 6, 0, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-23", "C", 7, 1, "P1"));
        fakeChoNgoiList.add(new ChoNgoi("LVVP1-24", "C", 8, 0, "P1"));

        // Chèn dữ liệu vào cơ sở dữ liệu
        for (ChoNgoi choNgoi : fakeChoNgoiList) {
            choNgoiDAO.insertChoNgoi(choNgoi);
        }
    }
}