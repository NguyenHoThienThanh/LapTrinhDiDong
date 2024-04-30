package com.example.doancuoiky.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.NhanVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class AdminStaffAdapter extends ArrayAdapter<NhanVien> {
    Activity context;
    int layout_id;
    ArrayList<NhanVien> arrayNhanVien;

    public AdminStaffAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<NhanVien> objects) {
        super(context,resource, objects);
        this.context = context;
        this.layout_id = resource;
        this.arrayNhanVien = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(layout_id, null);
        EditText tv_ma_nv = convertView.findViewById(R.id.edt_ma_nv);
        EditText tv_ho_ten = convertView.findViewById(R.id.edt_ho_ten);
        EditText tv_dia_chi = convertView.findViewById(R.id.edt_dia_chi);
        EditText tv_email = convertView.findViewById(R.id.edt_email);
        EditText tv_so_dt = convertView.findViewById(R.id.edt_so_dt);
        EditText tv_ngay_sinh = convertView.findViewById(R.id.edt_ngay_sinh);
        RadioGroup rg_gioi_tinh = convertView.findViewById(R.id.radio_gender);

        NhanVien nhanVien = arrayNhanVien.get(position);

        tv_ma_nv.setText(nhanVien.getMaNhanVien());
        tv_ho_ten.setText(nhanVien.getHoTen());
        tv_dia_chi.setText(nhanVien.getDiaChi());
        tv_email.setText(nhanVien.getEmail());
        tv_so_dt.setText(nhanVien.getSoDienThoai());
        tv_ngay_sinh.setText(nhanVien.getNgaySinh());

        if (nhanVien.isGioiTinh()) {
            rg_gioi_tinh.check(R.id.rb_nam);
        } else {
            rg_gioi_tinh.check(R.id.rb_nu);
        }
        return convertView;
    }
}
