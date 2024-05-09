package com.example.doancuoiky.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.ThanhToanAdapter;
import com.example.doancuoiky.dao.ChiTietGheDaDatDAO;
import com.example.doancuoiky.dao.ComboBapNuocDAO;
import com.example.doancuoiky.dao.HoaDonDAO;
import com.example.doancuoiky.dao.KhachHangDAO;
import com.example.doancuoiky.model.ChiTietGheDaDat;
import com.example.doancuoiky.model.ComboBapNuoc;
import com.example.doancuoiky.model.HoaDon;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;

public class ThanhToanActivity extends AppCompatActivity {
    ArrayList<ComboBapNuoc> selectedCombos;
    ArrayList<ComboBapNuoc> comboBapNuocArrayList;
    ArrayList<String> selectedSeats;
    ArrayList<String> maChoNgoiList;
    TextView tv_seatselecter, tv_totalmoney_thanhtoan, tv_totalmoneyseat_thanhtoan, tv_infofilm_thanhtoan, tv_movieroom_thanhtoan, tv_typefilm_thanhtoan, tv_filmname_thanhtoan ;
    RecyclerView rcv;
    ImageView film_img_thanhtoan;
    ThanhToanAdapter thanhToanAdapter;
    double totalPrice = 0;
    double total = 0;

    TextView tenKhachHang, thongTin;
    String maPhongChieu, gioChieu, ngayChieu, maSuatChieu, maCombo, maHoaDon, maKhachHang, ngayLapHoaDon, maGhe;
    String tenPhim, maPhim;
    int gioiHanTuoi;
    byte[] poster;
    double giaVe;
    Button btn_thanhToan;

    ChiTietGheDaDatDAO chiTietGheDaDatDAO;
    private
    HoaDonDAO hoaDonDAO;
    ComboBapNuocDAO comboBapNuocDAO;
    KhachHangDAO khachHangDAO;
    private double amount;
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "HoangNgoc";
    private String merchantCode = "MOMOC2IC20220510";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Đặt vé xem phim";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        hoaDonDAO = new HoaDonDAO(this);
        chiTietGheDaDatDAO = new ChiTietGheDaDatDAO(this);
        comboBapNuocDAO = new ComboBapNuocDAO(this);
        khachHangDAO = new KhachHangDAO(this);
        maKhachHang = (khachHangDAO.findOneBySoDienThoai(LoginActivity.getTaiKhoan().getTaiKhoan())).getMaKhachHang();
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        toolBarThanhToan();
        Intent intent = getIntent();
        if (intent != null) {
            selectedSeats = intent.getStringArrayListExtra("selectedSeats");
            maChoNgoiList = intent.getStringArrayListExtra("maChoNgoiList");
            selectedCombos = (ArrayList<ComboBapNuoc>) intent.getSerializableExtra("selectedCombos");
            totalPrice = intent.getDoubleExtra("totalPrice", 0.0);
            total = intent.getDoubleExtra("total", 0.0);
//            maKhachHang = intent.getStringExtra("maKhachHang");
            maSuatChieu = intent.getStringExtra("maSuatChieu");
            maPhongChieu = intent.getStringExtra("maPhongChieu");
            gioChieu = intent.getStringExtra("gioChieu");
            ngayChieu = intent.getStringExtra("ngayChieu");
            maPhim = intent.getStringExtra("maPhim");
            tenPhim = intent.getStringExtra("tenPhim");
            gioiHanTuoi = intent.getIntExtra("gioiHanTuoi", 0);
            poster = intent.getByteArrayExtra("poster");
            giaVe = intent.getDoubleExtra("giaVe", 0);
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

            Bitmap bitmap = BitmapFactory.decodeByteArray(poster, 0, poster.length);
            film_img_thanhtoan.setImageBitmap(bitmap);

            tv_typefilm_thanhtoan.setText(String.valueOf(gioiHanTuoi) + "+");

            tv_filmname_thanhtoan.setText(tenPhim);

            tenKhachHang.setText(khachHangDAO.findOneById(maKhachHang).getUserName());
            thongTin.setText(khachHangDAO.findOneById(maKhachHang).getSoDienThoai() + "-" + khachHangDAO.findOneById(maKhachHang).getEmail());

            tv_seatselecter.setText("Danh sách ghế đã chọn: " + seatString.toString());
            maGhe = seatString.toString();
            DecimalFormat decimalFormat2 = new DecimalFormat("#,###.###");
            String formattedMoney2 = decimalFormat2.format(totalPrice);
            tv_totalmoneyseat_thanhtoan.setText("Tổng tiền vé: " + formattedMoney2 + "đ");
            amount = total + totalPrice;

        }
        comBoAdapter();
        getControl();
    }

    //Get token through MoMo app
    private void requestPayment(String maDonHang) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
//        if (edAmount.getText().toString() != null && edAmount.getText().toString().trim().length() != 0)
//            amount = edAmount.getText().toString().trim();

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", maDonHang); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", maDonHang); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", maPhongChieu);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", tenPhim);
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);

    }
    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("Thanh cong", data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response

                    maHoaDon = hoaDonDAO.getNextID();
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                    ngayLapHoaDon = dateFormat.format(currentDate);
                    HoaDon hd = new HoaDon(maHoaDon, maSuatChieu, maKhachHang, maCombo, total + totalPrice, ngayLapHoaDon, maGhe);
                    boolean res = hoaDonDAO.insertHoaDon(hd);

                    if (res){
                    }
                    else{
                        Toast.makeText(this, "Insert thất bại", Toast.LENGTH_SHORT).show();
                    }

                    for(int i = 0; i < maChoNgoiList.size(); i++){
                        ChiTietGheDaDat chiTietGheDaDat = new ChiTietGheDaDat(maSuatChieu, maChoNgoiList.get(i), 1);
                        boolean result = chiTietGheDaDatDAO.insertTicket(chiTietGheDaDat);
                        if (result){

                        }
                        else{

                        }
                    }
                    comboBapNuocArrayList = comboBapNuocDAO.findAllCombo();
                    for(int i = 0 ; i < comboBapNuocArrayList.size(); i++){
                        for(int j = 0; j<selectedCombos.size(); j++){
                            if(comboBapNuocArrayList.get(i).getMaCombo().equals(selectedCombos.get(j).getMaCombo())){
                                ComboBapNuoc cb = comboBapNuocArrayList.get(i);
                                cb.setSoLuong(comboBapNuocArrayList.get(i).getSoLuong() - selectedCombos.get(j).getSoLuongDat());
                                boolean isUpdated = comboBapNuocDAO.update(cb);
                            }
                        }
                    }

                    Intent intent = new Intent(getApplicationContext(), HoaDonActivity.class);
                    intent.putStringArrayListExtra("selectedSeats", selectedSeats);
                    intent.putExtra("selectedCombos", selectedCombos);
                    intent.putExtra("totalPrice", totalPrice);
                    intent.putExtra("total", total);

                    intent.putExtra("maSuatChieu", maSuatChieu);
                    intent.putExtra("maPhongChieu", maPhongChieu);
                    intent.putExtra("maPhim", maPhim);
                    intent.putExtra("poster", poster);
                    intent.putExtra("maHoaDon", maHoaDon);
                    startActivity(intent);
                    finish();

                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("Thanh cong", "Khong thanh cong");
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("Thanh cong", "Khong thanh cong");
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("Thanh cong", "Khong thanh cong");
                } else {
                    //TOKEN FAIL
                    Log.d("Thanh cong", "Khong thanh cong");
                }
            } else {
                Log.d("Thanh cong", "Khong thanh cong");
            }
        } else {
            Log.d("Thanh cong", "Khong thanh cong");
        }
    }
    public void mappingControl(){
        tv_seatselecter = findViewById(R.id.tv_seatselected_thanhtoan);
        tv_totalmoney_thanhtoan = findViewById(R.id.tv_totalmoney_thanhtoan);
        tv_totalmoneyseat_thanhtoan = findViewById(R.id.tv_totalmoneyseat_thanhtoan);
        tv_infofilm_thanhtoan = findViewById(R.id.tv_infofilm_thanhtoan);
        tv_movieroom_thanhtoan = findViewById(R.id.tv_movieroom_thanhtoan);
        film_img_thanhtoan = findViewById(R.id.film_img_thanhtoan);
        tv_typefilm_thanhtoan = findViewById(R.id.tv_typefilm_thanhtoan);
        tv_filmname_thanhtoan = findViewById(R.id.tv_filmname_thanhtoan);
        btn_thanhToan = findViewById(R.id.btn_thanhtoan);
        tenKhachHang = findViewById(R.id.customer_name);
        thongTin = findViewById(R.id.customer_phoneandemail);
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

    private void getControl(){
        btn_thanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder comBoString = new StringBuilder();
                // Lặp qua từng phần tử trong danh sách selectedSeats
                for (int i = 0; i < selectedCombos.size(); i++) {
                    // Thêm phần tử vào chuỗi
                    comBoString.append(selectedCombos.get(i).getMaCombo());

                    // Nếu không phải phần tử cuối cùng, thêm dấu phẩy vào chuỗi
                    if (i < selectedCombos.size() - 1) {
                        comBoString.append(", ");
                    }
                }
                maHoaDon = hoaDonDAO.getNextID();
                maCombo = String.valueOf(comBoString);
                HoaDon hoaDon = new HoaDon(maHoaDon, maSuatChieu, maKhachHang, maCombo, total + totalPrice, ngayLapHoaDon, maGhe);
                hoaDonDAO.createHoaDon(hoaDon);
                requestPayment(maHoaDon);
            }
        });
    }
}