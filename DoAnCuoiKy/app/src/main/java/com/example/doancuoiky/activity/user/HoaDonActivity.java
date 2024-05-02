package com.example.doancuoiky.activity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.KhachHangDAO;
import com.example.doancuoiky.dao.PhimDAO;
import com.example.doancuoiky.dao.SuatChieuDao;
import com.example.doancuoiky.model.ChiTietSuatChieu;
import com.example.doancuoiky.model.ComboBapNuoc;
import com.example.doancuoiky.model.KhachHang;
import com.example.doancuoiky.model.Phim;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class HoaDonActivity extends AppCompatActivity {

    ArrayList<String> selectedSeats;
    ArrayList<ComboBapNuoc> selectedCombos;
    String maPhongChieu, maSuatChieu, maHoaDon, maPhim, maKhachHang;
    Double total, totalPrice;

    PhimDAO phimDAO;
    KhachHangDAO khachHangDAO;
    SuatChieuDao suatChieuDao;

    ImageView img_qr_code, img_avatar_film;
    TextView txt_typeFilm, tv_nameFilm, tv_maPhongChieu, tv_soLuongVe, tv_soGhe, tv_ngayvagio, tv_nguoidat, tv_sdt, tv_mahoadon, tv_combo, tv_tongtien;
    Button btn_gotomenu;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_ticket_online);
        phimDAO = new PhimDAO(this);
        khachHangDAO = new KhachHangDAO(this);
        suatChieuDao = new SuatChieuDao(this);

        Intent intent = getIntent();
        selectedSeats = intent.getStringArrayListExtra("selectedSeats");
        selectedCombos = (ArrayList<ComboBapNuoc>) intent.getSerializableExtra("selectedCombos");
        totalPrice = intent.getDoubleExtra("totalPrice", 0.0);
        total = intent.getDoubleExtra("total", 0.0);
        maKhachHang = (khachHangDAO.findOneBySoDienThoai(LoginActivity.getTaiKhoan().getTaiKhoan())).getMaKhachHang();
        maSuatChieu = intent.getStringExtra("maSuatChieu");
        maPhongChieu = intent.getStringExtra("maPhongChieu");
        maPhim = intent.getStringExtra("maPhim");
        maHoaDon = intent.getStringExtra("maHoaDon");

        mappingControl();
        getDataOnLayout();
        buttonBackToMenu();
    }

    private void buttonBackToMenu() {
        btn_gotomenu = findViewById(R.id.btn_gotomenu); // Thay R.id.backButton bằng ID của nút quay lại menu trong layout của bạn
        btn_gotomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonActivity.this);
                builder.setMessage("Bạn có muốn quay lại menu không?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(HoaDonActivity.this, HomeActivity.class); // Thay CurrentActivity và MenuActivity bằng tên Activity hiện tại và Activity của menu
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }


    private void mappingControl() {
        img_avatar_film = findViewById(R.id.img_avatar_film);
        img_qr_code = findViewById(R.id.qr_code);
        txt_typeFilm = findViewById(R.id.txt_typeFilm);
        tv_nameFilm = findViewById(R.id.tv_nameFilm);
        tv_maPhongChieu = findViewById(R.id.tv_maPhongChieu);
        tv_soLuongVe = findViewById(R.id.tv_soLuongVe);
        tv_soGhe = findViewById(R.id.tv_soGhe);
        tv_ngayvagio = findViewById(R.id.tv_ngayvagio);
        tv_nguoidat = findViewById(R.id.tv_nguoidat);
        tv_sdt = findViewById(R.id.tv_sdt);
        tv_mahoadon = findViewById(R.id.tv_mahoadon);
        tv_combo = findViewById(R.id.tv_combo);
        tv_tongtien = findViewById(R.id.tv_tongtien);
    }
    private void getDataOnLayout(){
        Phim phim = phimDAO.findOneById(maPhim);
        KhachHang khachHang = khachHangDAO.findOneById(maKhachHang);
        ChiTietSuatChieu chiTietSuatChieu = suatChieuDao.findOneByMaSuatChieu(maSuatChieu);

        Bitmap bitmap = BitmapFactory.decodeByteArray(phim.getTrailer(), 0, phim.getTrailer().length);
        img_avatar_film.setImageBitmap(bitmap);
        txt_typeFilm.setText(phim.getGioiHanDoTuoi() + "+");
        tv_nameFilm.setText(phim.getTenPhim());
        tv_maPhongChieu.setText(maPhongChieu);
        tv_soLuongVe.setText(String.valueOf(selectedSeats.size()));

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

        tv_soGhe.setText(seatString.toString());
        tv_ngayvagio.setText(chiTietSuatChieu.getNgayChieu() + " | " + chiTietSuatChieu.getGioChieu());
        tv_nguoidat.setText(khachHang.getUserName());
        tv_sdt.setText(khachHang.getSoDienThoai());
        tv_mahoadon.setText(maHoaDon);

        StringBuilder comboText = new StringBuilder();
        for(int i = 0; i < selectedCombos.size(); i++){
            ComboBapNuoc comboBapNuoc = selectedCombos.get(i);
            comboText.append("x").append(comboBapNuoc.getSoLuongDat()).append(" ").append(comboBapNuoc.getTenCombo());
            // Kiểm tra xem có phải là lần cuối cùng trong vòng lặp hay không
            if (i != selectedCombos.size() - 1) {
                // Nếu không phải lần cuối cùng, thêm dòng xuống hàng
                comboText.append("\n");
            }
        }
        tv_combo.setText(comboText.toString());

        MultiFormatWriter multiFormatWriter =  new MultiFormatWriter();
        try {
            BitMatrix bitMatrix =  multiFormatWriter.encode("MHD013", BarcodeFormat.QR_CODE,  200,  200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap =  barcodeEncoder.createBitmap(bitMatrix);
            img_qr_code.setImageBitmap(bitmap);
        }catch (WriterException e){
            throw new RuntimeException(e);
        }

        tv_tongtien.setText(String.valueOf(total + totalPrice));
    }
}
