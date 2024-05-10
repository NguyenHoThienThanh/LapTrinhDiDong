package com.example.doancuoiky.activity.admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.AdminStaffAdapter;
import com.example.doancuoiky.dao.NhanVienDAO;
import com.example.doancuoiky.model.NhanVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class AdminStaffActivity extends AppCompatActivity {
    ArrayList<NhanVien> listNhanVien = new ArrayList<>();
    NhanVienDAO nhanVienDAO;
    Button btn_sua_nv, btn_them_nv, btn_huy, btn_them_nv_toggle ;
    LinearLayout layout_themnv;
    AdminStaffAdapter adminStaffAdapter;
    ListView lv_danh_sach_nv;
    public NhanVien nhanVienSelected;
    int pos =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_staff);
        nhanVienDAO = new NhanVienDAO(this);
        lv_danh_sach_nv = findViewById(R.id.lv_danh_sach_nv);
        toolBar();
        staffAdapter();
        EditText edt_ngay_sinh = findViewById(R.id.edt_ngay_sinh);
        edt_ngay_sinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                // Tạo DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AdminStaffActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, month, day);
                                edt_ngay_sinh.setText(sdf.format(selectedDate.getTime()));
                            }
                        }, year, month, day
                );
                datePickerDialog.show();
            }
        });
        btn_them_nv = findViewById(R.id.btn_them_nv);
        btn_them_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNull()&&isValidPhoneNumber()&&isValidEmail()&&isValidBirthdate()){
                    insert();
                    clear();
                }
            }
        });
        lv_danh_sach_nv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                nhanVienSelected = listNhanVien.get(position);
                pos = position;
                return false;
            }
        });
        btn_huy = findViewById(R.id.btn_huy);
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                // Khi nút hủy được nhấn, đặt lại trạng thái của nút sửa và thêm
                Button btn_sua_nv = findViewById(R.id.btn_sua_nv);
                Button btn_them_nv = findViewById(R.id.btn_them_nv);

                btn_sua_nv.setVisibility(View.INVISIBLE); // Ẩn nút sửa
                btn_them_nv.setVisibility(View.VISIBLE); // Hiển thị nút thêm

                // Đặt lại và ẩn layout thêm nhân viên nếu đang hiển thị
                if (layout_themnv.getVisibility() == View.VISIBLE) {
                    layout_themnv.setVisibility(View.GONE);
                }
            }
        });
        btn_sua_nv = findViewById(R.id.btn_sua_nv);
        btn_sua_nv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AdminStaffActivity.this);
                builder.setTitle("Xác nhận sửa");
                builder.setMessage("Bạn có chắc chắn muốn sửa thông tin của nhân viên này?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        update();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_them_nv_toggle = findViewById(R.id.btn_them_nv_toggle);
        layout_themnv = findViewById(R.id.layout_themnv);
        btn_them_nv_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đổi giữa VISIBLE và GONE
                if (layout_themnv.getVisibility() == View.GONE) {
                    layout_themnv.setVisibility(View.VISIBLE);
                } else {
                    layout_themnv.setVisibility(View.GONE);
                }
            }
        });
    }
    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_nv);
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

    private void staffAdapter(){
        listNhanVien = nhanVienDAO.findAllNV();
        adminStaffAdapter = new AdminStaffAdapter(this, R.layout.item_nhanvien, listNhanVien);
        lv_danh_sach_nv.setAdapter(adminStaffAdapter);
        registerForContextMenu(lv_danh_sach_nv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_nhanvien, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_delete) {
            delete();
            return true;
        }else if(itemId == R.id.action_update){
            showUpdateDialog();
            return true;
        }else {
            return super.onContextItemSelected(item);
        }
    }
    public boolean checkNull() {
        EditText edt_ho_ten = findViewById(R.id.edt_ho_ten);
        EditText edt_dia_chi = findViewById(R.id.edt_dia_chi);
        EditText edt_email = findViewById(R.id.edt_email);
        EditText edt_so_dt = findViewById(R.id.edt_so_dt);
        EditText edt_ngay_sinh = findViewById(R.id.edt_ngay_sinh);
        RadioGroup rg_gender = findViewById(R.id.radio_gender);


        String hoTen = edt_ho_ten.getText().toString().trim();
        String diaChi = edt_dia_chi.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String soDt = edt_so_dt.getText().toString().trim();
        String ngaySinh = edt_ngay_sinh.getText().toString().trim();

        if (hoTen.isEmpty()) {
            Toast.makeText(this, "Họ tên không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (diaChi.isEmpty()) {
            Toast.makeText(this, "Địa chỉ không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Email không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (soDt.isEmpty()) {
            Toast.makeText(this, "Số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ngaySinh.isEmpty()) {
            Toast.makeText(this, "Ngày sinh không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Kiểm tra giới tính có được chọn hay không
        int selectedGenderId = rg_gender.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isValidPhoneNumber() {
        EditText edt_so_dt = findViewById(R.id.edt_so_dt);
        String soDt = edt_so_dt.getText().toString().trim();

        if (!soDt.matches("\\d{10}")) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isValidEmail() {
        EditText edt_email = findViewById(R.id.edt_email);
        String email = edt_email.getText().toString().trim();

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) { // Kiểm tra email có đúng định dạng
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isValidBirthdate() {
        EditText edt_ngay_sinh = findViewById(R.id.edt_ngay_sinh);
        String ngaySinhStr = edt_ngay_sinh.getText().toString().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date ngaySinh;
        try {
            ngaySinh = sdf.parse(ngaySinhStr);

            Calendar calNow = Calendar.getInstance();
            Calendar calBirthdate = Calendar.getInstance();
            calBirthdate.setTime(ngaySinh);

            int age = calNow.get(Calendar.YEAR) - calBirthdate.get(Calendar.YEAR);
            if (calNow.get(Calendar.DAY_OF_YEAR) < calBirthdate.get(Calendar.DAY_OF_YEAR)) {
                age--; // Giảm tuổi nếu chưa đến ngày sinh
            }

            if (age < 18) {
                Toast.makeText(this, "Nhân viên phải đủ 18 tuổi", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Định dạng ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminStaffActivity.this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên này?");

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nhanVienDAO.delete(nhanVienSelected.getMaNhanVien());
                listNhanVien.clear();
                listNhanVien.addAll(nhanVienDAO.findAllNV());
                adminStaffAdapter.notifyDataSetChanged();
                nhanVienSelected = null;
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showUpdateDialog() {
        if (nhanVienSelected != null) {
            Button btn_sua_nv = findViewById(R.id.btn_sua_nv);
            Button btn_them_nv = findViewById(R.id.btn_them_nv);

            btn_sua_nv.setVisibility(View.VISIBLE);
            btn_them_nv.setVisibility(View.GONE); // Ẩn nút thêm nhân viên khi đang sửa thông tin
            layout_themnv.setVisibility(View.VISIBLE);

            EditText edt_ho_ten = findViewById(R.id.edt_ho_ten);
            EditText edt_dia_chi = findViewById(R.id.edt_dia_chi);
            EditText edt_email = findViewById(R.id.edt_email);
            EditText edt_so_dt = findViewById(R.id.edt_so_dt);
            EditText edt_ngay_sinh = findViewById(R.id.edt_ngay_sinh);
            RadioGroup rg_gender = findViewById(R.id.radio_gender);
            EditText edt_ma_nv = findViewById(R.id.edt_ma_nv);

            edt_ma_nv.setText(nhanVienSelected.getMaNhanVien());
            edt_ho_ten.setText(nhanVienSelected.getHoTen());
            edt_dia_chi.setText(nhanVienSelected.getDiaChi());
            edt_email.setText(nhanVienSelected.getEmail());
            edt_so_dt.setText(nhanVienSelected.getSoDienThoai());

            if (nhanVienSelected.isGioiTinh()==true) {
                rg_gender.check(R.id.rb_nam);
            } else {
                rg_gender.check(R.id.rb_nu);
            }
            rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // Kiểm tra nút radio nào được chọn và cập nhật giới tính của nhanVienSelected
                    if (checkedId == R.id.rb_nam) {
                        nhanVienSelected.setGioiTinh(true); // true cho nam
                    } else if (checkedId == R.id.rb_nu) {
                        nhanVienSelected.setGioiTinh(false); // false cho nữ
                    }
                }
            });
            edt_ngay_sinh.setText(nhanVienSelected.getNgaySinh());
            btn_sua_nv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Định dạng ngày sinh với SimpleDateFormat
                    String ngaySinhRaw = edt_ngay_sinh.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date ngaySinhDate = sdf.parse(ngaySinhRaw);  // Chuyển đổi chuỗi ngày sinh sang đối tượng Date
                        String ngaySinhFormatted = sdf.format(ngaySinhDate);  // Định dạng lại để chắc chắn đúng "dd/MM/yyyy"
                        nhanVienSelected.setNgaySinh(ngaySinhFormatted);

                        if (checkNull() && isValidPhoneNumber() && isValidEmail() && isValidBirthdate()) {
                            boolean isUpdated = nhanVienDAO.update(nhanVienSelected);
                            if (isUpdated) {
                                Toast.makeText(AdminStaffActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                staffAdapter();
                            } else {
                                Toast.makeText(AdminStaffActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (ParseException e) {
                        Toast.makeText(AdminStaffActivity.this, "Định dạng ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Button btn_sua_nv = findViewById(R.id.btn_sua_nv);
            btn_sua_nv.setVisibility(View.INVISIBLE);
        }
    }
    private void insert() {
        EditText edt_ho_ten = findViewById(R.id.edt_ho_ten);
        EditText edt_dia_chi = findViewById(R.id.edt_dia_chi);
        EditText edt_email = findViewById(R.id.edt_email);
        EditText edt_so_dt = findViewById(R.id.edt_so_dt);
        EditText edt_ngay_sinh = findViewById(R.id.edt_ngay_sinh);

        RadioGroup radio_gender = findViewById(R.id.radio_gender);
        int selectedGenderId = radio_gender.getCheckedRadioButtonId();

        String hoTen = edt_ho_ten.getText().toString();
        String diaChi = edt_dia_chi.getText().toString();
        String email = edt_email.getText().toString();
        String soDienThoai = edt_so_dt.getText().toString();
        boolean gioiTinh = selectedGenderId == R.id.rb_nam;

        String ngaySinh = edt_ngay_sinh.getText().toString();
        // Tạo đối tượng NhanVien mới
        NhanVien nhanVien = new NhanVien(hoTen, diaChi, email, soDienThoai, gioiTinh, ngaySinh);

        // Thêm vào cơ sở dữ liệu
        boolean isInserted = nhanVienDAO.insert(nhanVien);

        // Kiểm tra kết quả chèn
        if (isInserted) {
            Toast.makeText(AdminStaffActivity.this, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
            listNhanVien.clear();
            listNhanVien.addAll(nhanVienDAO.findAllNV());
            adminStaffAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(AdminStaffActivity.this, "Thêm nhân viên không thành công", Toast.LENGTH_SHORT).show();
        }
        staffAdapter(); // Cập nhật danh sách nhân viên
    }

    private void update() {
        EditText edt_ho_ten = findViewById(R.id.edt_ho_ten);
        EditText edt_dia_chi = findViewById(R.id.edt_dia_chi);
        EditText edt_email = findViewById(R.id.edt_email);
        EditText edt_so_dt = findViewById(R.id.edt_so_dt);
        EditText edt_ngay_sinh = findViewById(R.id.edt_ngay_sinh);
        RadioGroup radio_gender = findViewById(R.id.radio_gender);

        // Cập nhật thông tin sau khi chỉnh sửa
        nhanVienSelected.setHoTen(edt_ho_ten.getText().toString());
        nhanVienSelected.setDiaChi(edt_dia_chi.getText().toString());
        nhanVienSelected.setEmail(edt_email.getText().toString());
        nhanVienSelected.setSoDienThoai(edt_so_dt.getText().toString());
        nhanVienSelected.setNgaySinh(edt_ngay_sinh.getText().toString());

        // Xử lý giới tính
        int selectedGenderId = radio_gender.getCheckedRadioButtonId();
        boolean gioiTinh = selectedGenderId == R.id.rb_nam;
        nhanVienSelected.setGioiTinh(gioiTinh);

        // Cập nhật vào cơ sở dữ liệu
        boolean isUpdated = nhanVienDAO.update(nhanVienSelected);

        // Hiển thị kết quả
        if (isUpdated) {
            Toast.makeText(AdminStaffActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            staffAdapter();
        } else {
            Toast.makeText(AdminStaffActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        EditText edt_ma_nv = findViewById(R.id.edt_ma_nv);
        EditText edt_ho_ten = findViewById(R.id.edt_ho_ten);
        EditText edt_dia_chi = findViewById(R.id.edt_dia_chi);
        EditText edt_email = findViewById(R.id.edt_email);
        EditText edt_so_dt = findViewById(R.id.edt_so_dt);
        EditText edt_ngay_sinh = findViewById(R.id.edt_ngay_sinh);
        RadioGroup rg_gender = findViewById(R.id.radio_gender);

        edt_ho_ten.setText("");
        edt_dia_chi.setText("");
        edt_email.setText("");
        edt_so_dt.setText("");
        edt_ngay_sinh.setText("");
        rg_gender.clearCheck();
    }

}