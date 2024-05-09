package com.example.doancuoiky.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.user.MainActivity;
import com.example.doancuoiky.activity.user.SignUpActivity;
import com.example.doancuoiky.adapter.AdminFilmAdapter;
import com.example.doancuoiky.dao.PhimDAO;
import com.example.doancuoiky.model.Phim;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityAdminFilm extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText edtMaPhim, edtTenPhim, edtThoiLuong, edtDoTuoi, edtMoTa,
            edtDienVien, edtGiaVe, edtTheLoai, edtQuocGia;
    Button btnThem, btnXoa, btnSua, btnXem, btn_them_phim_toggle;
    ListView lvPhim;

    LinearLayout layout_them_phim;
    ArrayList<Phim> arrPhim;
    AdminFilmAdapter adapter;
    Context context;
    PhimDAO phimDao;

    ImageView imgPhim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_film);
        context = this;
        phimDao = new PhimDAO(context);
        mapping();
        toolBar();
        events();
    }

    private void events() {
        lvPhim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Phim phim = arrPhim.get(position);
                edtMaPhim.setText(String.valueOf(phim.getMaPhim()));
                edtTenPhim.setText(String.valueOf(phim.getTenPhim()));
                edtThoiLuong.setText(String.valueOf(phim.getThoiLuong()));
                edtDoTuoi.setText(String.valueOf(phim.getGioiHanDoTuoi()));
                edtMoTa.setText(String.valueOf(phim.getMoTaPhim()));
                edtDienVien.setText(String.valueOf(phim.getDienVien()));
                edtGiaVe.setText(String.valueOf(phim.getGiaVe()));
                edtTheLoai.setText(String.valueOf(phim.getTheLoai()));
                edtQuocGia.setText(String.valueOf(phim.getQuocGia()));

                if(phim.getTrailer() == null){

                }
                else{
                    Bitmap bitmap = BitmapFactory.decodeByteArray(phim.getTrailer(), 0, phim.getTrailer().length);
                    imgPhim.setImageBitmap(bitmap);
                }
            }
        });

        btn_them_phim_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đổi giữa VISIBLE và GONE
                if (layout_them_phim.getVisibility() == View.GONE) {
                    layout_them_phim.setVisibility(View.VISIBLE);
                } else {
                    layout_them_phim.setVisibility(View.GONE);
                }
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maPhim = edtMaPhim.getText().toString();
                String tenPhim = edtTenPhim.getText().toString();
                String thoiLuong = edtThoiLuong.getText().toString();
                String doTuoi = edtDoTuoi.getText().toString();
                String moTa = edtMoTa.getText().toString();
                String dienVien = edtDienVien.getText().toString();
                String giaVe = edtGiaVe.getText().toString();
                String theLoai = edtTheLoai.getText().toString();
                String quocGia = edtQuocGia.getText().toString();

                if (checkEmptyInput()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                    builder.setMessage("Vui lòng nhập đầy đủ thông tin!")
                            .setTitle("Fail")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                    return;
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                try {
                    int i = Integer.parseInt(thoiLuong);
                    if (i <= 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                        builder.setMessage("Thời lượng phim phải là số nguyên dương!")
                                .setTitle("Fail")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Xử lý sự kiện khi người dùng nhấn nút OK
                                        dialog.dismiss(); // Đóng hộp thoại
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                }catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                    builder.setMessage("Thời lượng phim phải là số nguyên dương!")
                            .setTitle("Fail")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                try {
                    int i = Integer.parseInt(doTuoi);
                    if (i <= 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                        builder.setMessage("Độ tuổi phải là số nguyên dương!")
                                .setTitle("Fail")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Xử lý sự kiện khi người dùng nhấn nút OK
                                        dialog.dismiss(); // Đóng hộp thoại
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                }catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                    builder.setMessage("Độ tuổi phải là số nguyên dương!")
                            .setTitle("Fail")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                try {
                    float i = Float.parseFloat(giaVe);
                    if (i <= 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                        builder.setMessage("Giá vé phải là số dương!")
                                .setTitle("Fail")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Xử lý sự kiện khi người dùng nhấn nút OK
                                        dialog.dismiss(); // Đóng hộp thoại
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                }catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                    builder.setMessage("Giá vé phải là số dương!")
                            .setTitle("Fail")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                Phim phim = new Phim();
                phim.setMaPhim(maPhim);
                phim.setTenPhim(tenPhim);
                phim.setThoiLuong(Integer.parseInt(thoiLuong));
                phim.setGioiHanDoTuoi(Integer.parseInt(doTuoi));
                phim.setMoTaPhim(moTa);
                phim.setDienVien(dienVien);
                phim.setGiaVe(Float.parseFloat(giaVe));
                phim.setTheLoai(theLoai);
                phim.setQuocGia(quocGia);

                // Cập nhật hình ảnh từ ImageView
                ImageView img_picture = findViewById(R.id.fandb_image);
                if (img_picture.getDrawable() == null){
                    Toast.makeText(ActivityAdminFilm.this, "Chưa có hình ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }
                Drawable drawable = img_picture.getDrawable(); // Lấy Drawable từ ImageView
                if (drawable != null && drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    phim.setTrailer(bitmapToByteArray(bitmap)); // Chuyển Bitmap thành mảng byte
                } else {
                    Toast.makeText(ActivityAdminFilm.this, "Chưa có hình ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean res = phimDao.insert(phim);

                if (res){
                    Toast.makeText(ActivityAdminFilm.this, "Insert thành công", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(ActivityAdminFilm.this, "Insert thất bại", Toast.LENGTH_SHORT).show();
                }
                layout_them_phim = findViewById(R.id.layout_them_phim);
                arrPhim = phimDao.findAllPhim();
                adapter = new AdminFilmAdapter(ActivityAdminFilm.this, R.layout.item_phim, arrPhim);
                clearText();
            }
        });

        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maPhim = edtMaPhim.getText().toString();
                String tenPhim = edtTenPhim.getText().toString();
                String thoiLuong = edtThoiLuong.getText().toString();
                String doTuoi = edtDoTuoi.getText().toString();
                String moTa = edtMoTa.getText().toString();
                String dienVien = edtDienVien.getText().toString();
                String giaVe = edtGiaVe.getText().toString();
                String theLoai = edtTheLoai.getText().toString();
                String quocGia = edtQuocGia.getText().toString();

                if (checkEmptyInput()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                    builder.setMessage("Vui lòng nhập đầy đủ thông tin!")
                            .setTitle("Fail")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                    return;
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                try {
                    int i = Integer.parseInt(thoiLuong);
                    if (i <= 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                        builder.setMessage("Thời lượng phim phải là số nguyên dương!")
                                .setTitle("Fail")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Xử lý sự kiện khi người dùng nhấn nút OK
                                        dialog.dismiss(); // Đóng hộp thoại
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                }catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                    builder.setMessage("Thời lượng phim phải là số nguyên dương!")
                            .setTitle("Fail")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                try {
                    int i = Integer.parseInt(doTuoi);
                    if (i <= 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                        builder.setMessage("Độ tuổi phải là số nguyên dương!")
                                .setTitle("Fail")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Xử lý sự kiện khi người dùng nhấn nút OK
                                        dialog.dismiss(); // Đóng hộp thoại
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                }catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                    builder.setMessage("Độ tuổi phải là số nguyên dương!")
                            .setTitle("Fail")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                try {
                    float i = Float.parseFloat(giaVe);
                    if (i <= 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                        builder.setMessage("Giá vé phải là số dương!")
                                .setTitle("Fail")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Xử lý sự kiện khi người dùng nhấn nút OK
                                        dialog.dismiss(); // Đóng hộp thoại
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                }catch (Exception e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                    builder.setMessage("Giá vé phải là số dương!")
                            .setTitle("Fail")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                Phim phim = new Phim();
                phim.setMaPhim(maPhim);
                phim.setTenPhim(tenPhim);
                phim.setThoiLuong(Integer.parseInt(thoiLuong));
                phim.setGioiHanDoTuoi(Integer.parseInt(doTuoi));
                phim.setMoTaPhim(moTa);
                phim.setDienVien(dienVien);
                phim.setGiaVe(Float.parseFloat(giaVe));
                phim.setTheLoai(theLoai);
                phim.setQuocGia(quocGia);

                boolean res = phimDao.update(phim);

                if (res){
                    Toast.makeText(ActivityAdminFilm.this, "Update thành công", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(ActivityAdminFilm.this, "Update thất bại", Toast.LENGTH_SHORT).show();
                }
                layout_them_phim = findViewById(R.id.layout_them_phim);
                arrPhim = phimDao.findAllPhim();
                adapter = new AdminFilmAdapter(ActivityAdminFilm.this, R.layout.item_phim, arrPhim);
                layout_them_phim = findViewById(R.id.layout_them_phim);
                arrPhim = phimDao.findAllPhim();
                adapter = new AdminFilmAdapter(ActivityAdminFilm.this, R.layout.item_phim, arrPhim);
                clearText();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maPhim = edtMaPhim.getText().toString();
                if (maPhim.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                    builder.setMessage("Vui lòng chọn phim để xóa!")
                            .setTitle("Fail")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý sự kiện khi người dùng nhấn nút OK
                                    dialog.dismiss(); // Đóng hộp thoại
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdminFilm.this);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc muốn xóa phim này không");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean res = phimDao.delete(maPhim);
                        if (res){
                            Toast.makeText(ActivityAdminFilm.this, "Delete thành công", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(ActivityAdminFilm.this, "Delete thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                layout_them_phim = findViewById(R.id.layout_them_phim);
                arrPhim = phimDao.findAllPhim();
                adapter = new AdminFilmAdapter(ActivityAdminFilm.this, R.layout.item_phim, arrPhim);
                clearText();
            }
        });

        imgPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void mapping() {
        edtMaPhim = findViewById(R.id.edtMaPhim);
        edtTenPhim = findViewById(R.id.edtTenPhim);
        edtThoiLuong = findViewById(R.id.edtThoiLuong);
        edtDoTuoi = findViewById(R.id.edtDoTuoi);
        edtMoTa = findViewById(R.id.edtMoTaPhim);
        edtDienVien = findViewById(R.id.edtDienVien);
        edtGiaVe = findViewById(R.id.edtGiaVe);
        edtTheLoai = findViewById(R.id.edtTheLoai);
        edtQuocGia = findViewById(R.id.edtQuocGia);
        btn_them_phim_toggle = findViewById(R.id.btn_them_phim_toggle);
        btnThem = findViewById(R.id.btnThemPhim);
        btnXoa = findViewById(R.id.btnXoaPhim);
        btnSua = findViewById(R.id.btnSuaPhim);
        btnXem = findViewById(R.id.btnHienThiPhim);
        imgPhim = findViewById(R.id.imgPhim);
        lvPhim = findViewById(R.id.lvPhim);
        layout_them_phim = findViewById(R.id.layout_them_phim);
        arrPhim = phimDao.findAllPhim();
        adapter = new AdminFilmAdapter(ActivityAdminFilm.this, R.layout.item_phim, arrPhim);
        lvPhim.setAdapter(adapter);
    }

    private void clearText() {
        edtMaPhim.setText("");
        edtTenPhim.setText("");
        edtThoiLuong.setText("");
        edtDoTuoi.setText("");
        edtMoTa.setText("");
        edtDienVien.setText("");
        edtGiaVe.setText("");
        edtTheLoai.setText("");
        edtQuocGia.setText("");
    }

    // Hàm gọi để chọn ảnh từ thư viện
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST); // Mở thư viện ảnh
    }
    // Chuyển Bitmap thành mảng byte
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        return baos.toByteArray();
    }

    private Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                Uri imageUri = data.getData(); // Lấy URI của ảnh đã chọn
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                // Cập nhật ImageView
                ImageView imgPhim1 = findViewById(R.id.imgPhim);
                imgPhim1.setImageBitmap(bitmap); // Cập nhật ảnh

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_Phim);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean checkEmptyInput(){
        int childCount = layout_them_phim.getChildCount();
        for (int i=0; i<childCount; i++){
            View view = layout_them_phim.getChildAt(i);
            if (view instanceof  EditText){
                EditText edt = (EditText) view;
                if (edt.getText().toString().equals("")){
                    return true;
                }
            }
        }
        return false;
    }
}