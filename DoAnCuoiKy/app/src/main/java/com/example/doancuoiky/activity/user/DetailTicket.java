package com.example.doancuoiky.activity.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.HoaDonDAO;
import com.example.doancuoiky.model.ChiTietSuatChieu;
import com.example.doancuoiky.model.ChiTietVe;
import com.example.doancuoiky.model.ComboBapNuoc;
import com.example.doancuoiky.model.KhachHang;
import com.example.doancuoiky.model.Phim;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.DecimalFormat;

public class DetailTicket extends AppCompatActivity {

    String maHoaDon;
    ImageView img_avatar_film, img_qr_code;
    TextView txt_typeFilm, tv_nameFilm, tv_maPhongChieu, tv_soGhe, tv_ngayvagio, tv_nguoidat, tv_sdt,tv_mahoadon, tv_combo, tv_tongtien;

    HoaDonDAO hoaDonDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ticket);
        Intent intent = getIntent();
        maHoaDon = intent.getStringExtra("maHoaDon");
        toolBarChiTietLichSuDatVe();
        mappingControl();
        hoaDonDAO = new HoaDonDAO(this);
        getDataOnLayout();
    }

    public void toolBarChiTietLichSuDatVe() {
        Toolbar toolbar = findViewById(R.id.toolbar_chitietve);
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
    private void mappingControl() {
        img_avatar_film = findViewById(R.id.ls_img_avatar_film);
        img_qr_code = findViewById(R.id.ls_qr_code);
        txt_typeFilm = findViewById(R.id.ls_txt_typeFilm);
        tv_nameFilm = findViewById(R.id.ls_tv_nameFilm);
        tv_maPhongChieu = findViewById(R.id.ls_tv_maPhongChieu);
        tv_soGhe = findViewById(R.id.ls_tv_soGhe);
        tv_ngayvagio = findViewById(R.id.ls_tv_ngayvagio);
        tv_nguoidat = findViewById(R.id.ls_tv_nguoidat);
        tv_sdt = findViewById(R.id.ls_tv_sdt);
        tv_mahoadon = findViewById(R.id.ls_tv_mahoadon);
        tv_combo = findViewById(R.id.ls_tv_combo);
        tv_tongtien = findViewById(R.id.ls_tv_tongtien);
    }

    private void getDataOnLayout(){
        ChiTietVe chiTietVe = hoaDonDAO.findOneByMaHoaDon(maHoaDon);
        Bitmap bitmap = BitmapFactory.decodeByteArray(chiTietVe.getTrailer(), 0, chiTietVe.getTrailer().length);
        img_avatar_film.setImageBitmap(bitmap);
        txt_typeFilm.setText(chiTietVe.getGioiHanDoTuoi() + "+");
        tv_nameFilm.setText(chiTietVe.getTenPhim());
        tv_maPhongChieu.setText(chiTietVe.getMaPhongChieu());
        tv_soGhe.setText(chiTietVe.getMaGhe());
        tv_ngayvagio.setText(chiTietVe.getNgayChieu() + " | " + chiTietVe.getThoiGianChieu());
        tv_nguoidat.setText(chiTietVe.getHoTen());
        tv_sdt.setText(chiTietVe.getSoDienThoai());
        tv_mahoadon.setText(maHoaDon);
        tv_combo.setText(chiTietVe.getTenComBo());

        MultiFormatWriter multiFormatWriter =  new MultiFormatWriter();
        try {
            BitMatrix bitMatrix =  multiFormatWriter.encode(maHoaDon, BarcodeFormat.QR_CODE,  200,  200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap =  barcodeEncoder.createBitmap(bitMatrix);
            img_qr_code.setImageBitmap(bitmap);
        }catch (WriterException e){
            throw new RuntimeException(e);
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String formattedTongTien = decimalFormat.format(chiTietVe.getTongTien());
        tv_tongtien.setText(formattedTongTien + "đ");
    }
}