package com.example.doancuoiky.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.HoaDonDAO;
import com.example.doancuoiky.model.ThongKeTheoPhim;
import com.example.doancuoiky.model.ThongKeTheoThang;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminChartActivity extends AppCompatActivity {

    LinearLayout piechart_layout, barchart_layout;
    Spinner spinner;
    PieChart pieChart;
    BarChart barChart;
    HoaDonDAO hoaDonDAO;
    ArrayList<ThongKeTheoThang> thongKeTheoThang = new ArrayList<>();
    ArrayList<ThongKeTheoPhim> thongKeTheoPhim= new ArrayList<>();
    String selectedYear, selectedThang;
    TextView tv_tongtientheonam, tv_tongsovedaban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chart);
        toolBarThongKe();
        hoaDonDAO = new HoaDonDAO(this);
        pieChart = findViewById(R.id.piechart);
        barChart = findViewById(R.id.barchart);
        tv_tongtientheonam = findViewById(R.id.tv_tongtientheonam);
        tv_tongsovedaban =  findViewById(R.id.tv_tongsovedaban);
        piechart_layout = findViewById(R.id.piechar_layout);
        barchart_layout = findViewById(R.id.barchar_layout);
        settingBarChart();
        spinnerAdapterYear();
        spinnerAdapterMonth();
    }

    public void spinnerAdapterYear(){
        spinner = findViewById(R.id.spinner_year);
        String arr[] = {"2023", "2024"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyProcessYearEvent());

    }

    public void spinnerAdapterMonth(){
        spinner = findViewById(R.id.spinner_month);
        ArrayList<ThongKeTheoPhim> thongKeTheoPhimArrayList = hoaDonDAO.getListMonth();
        ArrayList<String> arr = new ArrayList<>();
        selectedThang = thongKeTheoPhimArrayList.get(0).getThang();
        for(ThongKeTheoPhim thongKeTheoPhim : thongKeTheoPhimArrayList){
            arr.add(thongKeTheoPhim.getThang());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyProcessMonthEvent());
    }

    private class MyProcessYearEvent implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedYear = String.valueOf(parent.getItemAtPosition(position));
            getTkThang(selectedYear);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class MyProcessMonthEvent implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedThang = String.valueOf(parent.getItemAtPosition(position));
            getTkPhim(selectedThang);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
    public void settingBarChart(){
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setAxisMinimum(0);
        yAxisRight.setAxisMaximum(50000000);
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setAxisMaximum(50000000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_thongke, menu);
        return true;
    }

    public void toolBarThongKe() {
        Toolbar toolbar = findViewById(R.id.toolbar_thongke);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.tkthang) {
            if (selectedYear != null) {
                getTkThang(selectedYear);
            } else {
                Toast.makeText(this, "Vui lòng chọn năm trước khi thực hiện thống kê theo tháng", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == R.id.tkphim) {
            if (selectedThang != null) {
                getTkPhim(selectedThang);
            } else {
                Toast.makeText(this, "Vui lòng chọn tháng trước khi thực hiện thống kê theo phim", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void getTkThang(String selectedYear) {
        long tongTienTheoNam = 0;
        piechart_layout.setVisibility(View.GONE);
        barchart_layout.setVisibility(View.VISIBLE);
        // Tạo dữ liệu cho biểu đồ BarChart
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        thongKeTheoThang = hoaDonDAO.getListRevenue(selectedYear);
        for (ThongKeTheoThang tktt : thongKeTheoThang) {
            String thang = tktt.getThang();
            double revenue = tktt.getTongTienThang();
            tongTienTheoNam += tktt.getTongTienThang();
            barEntries.add(new BarEntry(Integer.parseInt(thang), (float) revenue));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Thống kê doanh thu theo tháng");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextSize(14f);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        Legend legend=barChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER); // Đặt vị trí ngang của chú thích
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); // Đặt vị trí dọc của chú thích
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL); // Đặt hướng của chú thích
        legend.setDrawInside(false); // Vẽ chú thích bên ngoài biểu đồ
        legend.setTextSize(12f);

        tv_tongtientheonam.setText(String.valueOf(tongTienTheoNam) + "đ");
        barChart.animateXY(1000,1000);
        barChart.invalidate(); // Cập nhật biểu đồ
    }

    private void getTkPhim(String selectedMonth) {
        int tongSoVe = 0;
        barchart_layout.setVisibility(View.GONE);
        piechart_layout.setVisibility(View.VISIBLE);
        // Tạo dữ liệu cho biểu đồ PieChart
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        thongKeTheoPhim = hoaDonDAO.getListViewMovie(selectedMonth);
        for (ThongKeTheoPhim tktp : thongKeTheoPhim) {
            int soVe = tktp.getSoLuongVe();
            String tenPhim = tktp.getTenPhim();
            tongSoVe += tktp.getSoLuongVe();
            pieEntries.add(new PieEntry(soVe, tenPhim));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setSliceSpace(2);
        dataSet.setValueTextSize(12f);
        dataSet.setValueFormatter(new PercentFormatter(pieChart));

        PieData data = new PieData(dataSet);
        data.setValueTextSize(12f); // Có thể đặt kích thước chữ cho nhãn ở đây nếu cần


        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT); // Đặt vị trí ngang của chú thích
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); // Đặt vị trí dọc của chú thích
        legend.setOrientation(Legend.LegendOrientation.VERTICAL); // Đặt hướng của chú thích
        legend.setDrawInside(false); // Vẽ chú thích bên ngoài biểu đồ


        tv_tongsovedaban.setText(String.valueOf(tongSoVe));
        pieChart.setData(data);
        pieChart.animateXY(1000,1000);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Phim xem nhiều nhất");
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();
    }
}