package com.example.doancuoiky.activity.admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.user.ChiTietKhachHangActivity;
import com.example.doancuoiky.activity.user.MainActivity;
import com.example.doancuoiky.adapter.AdminFilmAdapter;
import com.example.doancuoiky.adapter.AdminSuatChieuAdapter;
import com.example.doancuoiky.dao.PhimDAO;
import com.example.doancuoiky.dao.PhongChieuPhimDAO;
import com.example.doancuoiky.dao.SuatChieuDao;
import com.example.doancuoiky.model.ChiTietSuatChieu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminSuatChieuActivity extends AppCompatActivity {

    EditText edtMaSuatChieu, edtNgayChieu, edtGioChieu;
    Spinner spnMaPhongChieu, spnMaPhim;
    Button btnThem, btnXoa, btnSua, btnXem;
    LinearLayout layout_SuatChieu;
    ListView lvSuatChieu;
    ArrayList<ChiTietSuatChieu> arrSuatChieu;
    AdminSuatChieuAdapter adapter;
    SuatChieuDao scDao;
    PhimDAO phimDao;
    PhongChieuPhimDAO pcpDao;
    Context context;
    int pos = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_suat_chieu);
        context = this;
        scDao = new SuatChieuDao(context);
        phimDao = new PhimDAO(context);
        pcpDao = new PhongChieuPhimDAO(context);
        mapping();
        toolBar();
        loadDataSpinner();
        events();
    }

    private void events() {
        arrSuatChieu = scDao.findAll();
        lvSuatChieu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChiTietSuatChieu ctsc = arrSuatChieu.get(position);
                pos = position;
                edtNgayChieu.setText(String.valueOf(ctsc.getNgayChieu()));
                edtGioChieu.setText(String.valueOf(ctsc.getGioChieu()));
                // Lấy mã phim từ ChiTietSuatChieu và tìm xem mã phim đó có trong Spinner không
                String maPhim = ctsc.getMaPhim();
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) spnMaPhim.getAdapter();
                int spinnerPosition = adapter.getPosition(maPhim);
                // Nếu tìm thấy mã phim trong Spinner, chọn mục đó
                if (spinnerPosition != -1) {
                    spnMaPhim.setSelection(spinnerPosition);
                } else {
                    // Xử lý trường hợp không tìm thấy mã phim trong Spinner (nếu cần)
                }

                // Lấy mã phòng chiếu từ ChiTietSuatChieu
                String maPhongChieu = ctsc.getMaPhongChieu();
                ArrayAdapter<String> adapter1 = (ArrayAdapter<String>) spnMaPhongChieu.getAdapter();
                int spinnerPosition1 = adapter1.getPosition(maPhongChieu);

                // Nếu tìm thấy mã phim trong Spinner, chọn mục đó
                if (spinnerPosition1 != -1) {
                    spnMaPhongChieu.setSelection(spinnerPosition1);
                } else {
                    // Xử lý trường hợp không tìm thấy mã phim trong Spinner (nếu cần)
                }
            }
        });
        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maPhongChieu = spnMaPhongChieu.getSelectedItem().toString();
                String maPhim = spnMaPhim.getSelectedItem().toString();
                String ngayChieu = edtNgayChieu.getText().toString();
                String gioChieu = edtGioChieu.getText().toString();

                if (edtGioChieu.getText().toString().isEmpty() || edtNgayChieu.getText().toString().isEmpty()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminSuatChieuActivity.this);
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
                else{
                    ChiTietSuatChieu ct = new ChiTietSuatChieu("", maPhongChieu, maPhim, ngayChieu, gioChieu);
                    boolean res = scDao.insert(ct);
                    if (res){
                        Toast.makeText(AdminSuatChieuActivity.this, "Insert thành công", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(AdminSuatChieuActivity.this, "Insert thất bại", Toast.LENGTH_SHORT).show();
                    }
                    arrSuatChieu = scDao.findAll();
                    adapter = new AdminSuatChieuAdapter(AdminSuatChieuActivity.this, R.layout.item_suat_chieu, arrSuatChieu);
                    lvSuatChieu.setAdapter(adapter);
                    clearText();
                }
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maPhongChieu = spnMaPhongChieu.getSelectedItem().toString();
                String maPhim = spnMaPhim.getSelectedItem().toString();
                String ngayChieu = edtNgayChieu.getText().toString();
                String gioChieu = edtGioChieu.getText().toString();
                if (edtGioChieu.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminSuatChieuActivity.this);
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
                else{
                    String maSuatChieu = arrSuatChieu.get(pos).getMaSuatChieu();
                    ChiTietSuatChieu ct = new ChiTietSuatChieu(maSuatChieu, maPhongChieu, maPhim, ngayChieu, gioChieu);
                    boolean res = scDao.update(ct);
                    if (res){
                        Toast.makeText(AdminSuatChieuActivity.this, "Update thành công", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(AdminSuatChieuActivity.this, "Update thất bại", Toast.LENGTH_SHORT).show();
                    }
                    arrSuatChieu = scDao.findAll();
                    adapter = new AdminSuatChieuAdapter(AdminSuatChieuActivity.this, R.layout.item_suat_chieu, arrSuatChieu);
                    lvSuatChieu.setAdapter(adapter);
                    clearText();
                }

            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos == -1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminSuatChieuActivity.this);
                    builder.setMessage("Vui lòng chọn suất chiếu để xóa!")
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminSuatChieuActivity.this);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc muốn xóa suất chiếu này không");
                String msc = arrSuatChieu.get(pos).getMaSuatChieu();
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean res = scDao.delete(msc);
                        if (res){
                            Toast.makeText(AdminSuatChieuActivity.this, "Delete thành công", Toast.LENGTH_SHORT).show();
                            arrSuatChieu = scDao.findAll();
                            adapter = new AdminSuatChieuAdapter(AdminSuatChieuActivity.this, R.layout.item_suat_chieu, arrSuatChieu);
                            lvSuatChieu.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            clearText();
                        }
                        else{
                            Toast.makeText(AdminSuatChieuActivity.this, "Delete thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                arrSuatChieu = scDao.findAll();
                adapter = new AdminSuatChieuAdapter(AdminSuatChieuActivity.this, R.layout.item_suat_chieu, arrSuatChieu);
                lvSuatChieu.setAdapter(adapter);
                clearText();
            }
        });
    }

    private void clearText() {
        edtGioChieu.setText("");
        edtNgayChieu.setText("");
    }

    private void loadDataSpinner(){
        ArrayList<String> listMaPhim = phimDao.findAllMaPhim();
        ArrayAdapter<String> adapterMaPhim = new ArrayAdapter<>(AdminSuatChieuActivity.this, android.R.layout.simple_spinner_item, listMaPhim);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMaPhim.setAdapter(adapterMaPhim);

        ArrayList<String> listMaPhongChieu = pcpDao.findAllMaPhongChieu();
        ArrayAdapter<String> adapterMaPC= new ArrayAdapter<>(AdminSuatChieuActivity.this, android.R.layout.simple_spinner_item,listMaPhongChieu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMaPhongChieu.setAdapter(adapterMaPC);
    }

    private void mapping() {
        spnMaPhongChieu = findViewById(R.id.spnMaPhongChieu);
        spnMaPhim = findViewById(R.id.spnMaPhim);
        layout_SuatChieu = findViewById(R.id.layout_Suat_Chieu);
        edtNgayChieu = findViewById(R.id.edtNgayChieu);
        edtNgayChieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                // Tạo DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AdminSuatChieuActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, month, day);
                                edtNgayChieu.setText(sdf.format(selectedDate.getTime()));
                            }
                        }, year, month, day
                );
                datePickerDialog.show();
            }
        });
        edtGioChieu = findViewById(R.id.edtGioChieu);
        btnThem = findViewById(R.id.btnThemSuatChieu);
        btnSua = findViewById(R.id.btnSuaSuatChieu);
        btnXoa = findViewById(R.id.btnXoaSuatChieu);
        btnXem = findViewById(R.id.btnHienThiSuatChieu);
        arrSuatChieu = scDao.findAll();
        lvSuatChieu = findViewById(R.id.lvSuatChieu);

        adapter = new AdminSuatChieuAdapter(AdminSuatChieuActivity.this, R.layout.item_suat_chieu, arrSuatChieu);
        lvSuatChieu.setAdapter(adapter);
    }

    private boolean checkEmptyInput(){
        int childCount = layout_SuatChieu.getChildCount();
        for (int i=0; i<childCount; i++){
            View view = layout_SuatChieu.getChildAt(i);
            if (view instanceof  EditText){
                EditText edt = (EditText) view;
                if (edt.getText().toString().equals("")){
                    return true;
                }
            }
        }
        return false;
    }

    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_SuatChieu);
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
}