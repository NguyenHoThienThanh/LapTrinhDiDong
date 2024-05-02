package com.example.doancuoiky.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.ChiTietSuatChieu;
import com.example.doancuoiky.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class AdminSuatChieuAdapter extends ArrayAdapter<ChiTietSuatChieu> {
    Activity context;
    int layout_id;
    ArrayList<ChiTietSuatChieu> arrSuatChieu;

    public AdminSuatChieuAdapter(@NonNull Activity context, int layout_id, @NonNull ArrayList<ChiTietSuatChieu> arrSuatChieu) {
        super(context, layout_id, arrSuatChieu);
        this.context = context;
        this.layout_id = layout_id;
        this.arrSuatChieu = arrSuatChieu;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(layout_id, null);
        TextView txtMaSuatChieu = convertView.findViewById(R.id.txtMaSuatChieu);
        TextView txtMaPhongChieu = convertView.findViewById(R.id.txtMaPhongChieu);
        TextView txtMaPhim = convertView.findViewById(R.id.txtMaPhim);
        TextView txtNgayChieu = convertView.findViewById(R.id.txtNgayChieu);
        TextView txtGioChieu = convertView.findViewById(R.id.txtGioChieu);

        ChiTietSuatChieu ct = arrSuatChieu.get(position);
        txtMaSuatChieu.setText(ct.getMaSuatChieu());
        txtMaPhongChieu.setText(ct.getMaPhongChieu());
        txtMaPhim.setText(ct.getMaPhim());
        txtNgayChieu.setText(ct.getNgayChieu());
        txtGioChieu.setText(ct.getGioChieu());
        return convertView;
    }
}
