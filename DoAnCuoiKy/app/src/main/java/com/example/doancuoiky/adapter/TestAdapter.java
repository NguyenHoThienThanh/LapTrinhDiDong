package com.example.doancuoiky.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.HoaDon;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends ArrayAdapter<HoaDon> {

    Activity context;
    int layoutId;
    List<HoaDon> listHD;

    public TestAdapter(Activity context, int layoutId, List<HoaDon> listHD) {
        super(context, layoutId, listHD);
        this.context = context;
        this.layoutId = layoutId;
        this.listHD = listHD;
    }

    // Gọi hàm getView để tiến hành sắp xếp dữ liệu

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Tạo inflact để chứa Layout
        LayoutInflater myInflactor = context.getLayoutInflater();
        // Đặt idLayout lên để tạo thành View
        convertView = myInflactor.inflate(layoutId, null);
        // Lấy 1 phần tử trong mảng SV
        HoaDon hd = listHD.get(position);

        // Tham chiếu sv này lên View
        // Tham chiếu và set lần lượt các thuộc tính của SV lên
        TextView tv_mahoadon = convertView.findViewById(R.id.tv_mahoadon);
        TextView tv_masuatchieu = convertView.findViewById(R.id.tv_masuatchieu);
        TextView tv_makhachhang = convertView.findViewById(R.id.tv_makhachhang);
        TextView tv_macombo = convertView.findViewById(R.id.tv_macombo);
        TextView tv_tongtien = convertView.findViewById(R.id.tv_tongtien);
        // Set giá trị
        tv_mahoadon.setText(hd.getMaHoaDon());
        tv_masuatchieu.setText(hd.getMaSuatChieu());
        tv_makhachhang.setText(hd.getMaKhachHang());
        tv_macombo.setText(hd.getMaCombo());
        tv_tongtien.setText(String.valueOf(hd.getTongTien()));
        return convertView;
    }
}
