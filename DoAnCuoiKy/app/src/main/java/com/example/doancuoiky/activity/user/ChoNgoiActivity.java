package com.example.doancuoiky.activity.user;

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
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.DanhSachGheAdapter;
import com.example.doancuoiky.dao.ChoNgoiDAO;
import com.example.doancuoiky.model.DanhSachGhe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChoNgoiActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DanhSachGheAdapter adapter;
    ArrayList<DanhSachGhe> arraySeats = new ArrayList<>();
    Button btn_continue;
    String maSuatChieu, maPhongChieu, gioChieu, ngayChieu;
    TextView tv_type, tv_name, tv_time, tv_date, tv_cate;

    ChoNgoiDAO danhSachGheDAO;
    TextView txt_tempTotal;

    double tongTien = 0;
    List<String> seatSelected = new ArrayList<>();

    String tenPhim, maPhim, theLoai;
    int gioiHanTuoi;
    byte[] poster;
    double giaVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cho_ngoi);
        danhSachGheDAO = new ChoNgoiDAO(this);
        txt_tempTotal = findViewById(R.id.txt_TempTotal);
        Intent intent = getIntent();
        maSuatChieu = intent.getStringExtra("maSuatChieu");
        maPhongChieu = intent.getStringExtra("maPhongChieu");
        gioChieu = intent.getStringExtra("gioChieu");
        ngayChieu = intent.getStringExtra("ngayChieu");
        maPhim = intent.getStringExtra("maPhim");
        tenPhim = intent.getStringExtra("tenPhim");
        theLoai = intent.getStringExtra("theLoai");
        gioiHanTuoi = intent.getIntExtra("gioiHanTuoi", 0);
        poster = intent.getByteArrayExtra("poster");
        giaVe = intent.getDoubleExtra("giaVe", 0);
        mappingControl();
        toolBarSeat();
        seatAdapter();
        setDataOnLayout();
        buttonContinue();
    }

    private void mappingControl(){
        tv_type = findViewById(R.id.txt_TypeFilm);
        tv_name= findViewById(R.id.txt_NameFilm);
        tv_time = findViewById(R.id.txt_TimeFilm);
        tv_date = findViewById(R.id.txt_DateFilm);
        tv_cate = findViewById(R.id.txt_CateFilm);
    }
    private void setDataOnLayout(){
        tv_type.setText(gioiHanTuoi + "+");
        tv_name.setText(tenPhim);
        tv_time.setText(gioChieu);
        tv_date.setText(ngayChieu);
        tv_cate.setText(theLoai);
    }
    private List<DanhSachGhe> getListSeat() {
        ArrayList<DanhSachGhe> list = new ArrayList<>();
        list = danhSachGheDAO.getSeatBySuatChieu(maSuatChieu, maPhongChieu);
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
        toolbar.setTitle(tenPhim);
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
                String hangGheVaSoGhe = seat.getHangGhe() + seat.getSoGhe();
                if (seat.isSelected()) {
                    // Nếu ghế đã được chọn trước đó, giảm tiền đi
                    tongTien += giaVe;
                    seatSelected.add(hangGheVaSoGhe);
                } else {
                    // Nếu ghế chưa được chọn trước đó, tăng tiền lên
                    tongTien -= giaVe;
                    seatSelected.remove(hangGheVaSoGhe);
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
                if(tongTien <= 0){
                    Toast.makeText(ChoNgoiActivity.this, "Vui lòng chọn ghế!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(ChoNgoiActivity.this, ComboBapNuocActivity.class);
                // Gửi danh sách ghế đã chọn qua Intent
                Toast.makeText(ChoNgoiActivity.this, "a" + gioChieu + ngayChieu, Toast.LENGTH_SHORT).show();

                intent.putStringArrayListExtra("selectedSeats", (ArrayList<String>) seatSelected);
                intent.putExtra("totalPrice", tongTien);
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