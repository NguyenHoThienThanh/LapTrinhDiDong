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
import com.example.doancuoiky.model.KhachHang;
import com.example.doancuoiky.model.Phim;

import java.util.ArrayList;
import java.util.List;

public class AdminFilmAdapter extends ArrayAdapter<Phim> {

    Activity context;
    int layout_id;
    ArrayList<Phim> arrPhim;


    public AdminFilmAdapter(@NonNull Activity context, int layout_id, @NonNull ArrayList<Phim> objects) {
        super(context, layout_id, objects);
        this.context = context;
        this.layout_id = layout_id;
        this.arrPhim = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(layout_id, null);
        Phim phim = arrPhim.get(position);
        //Mapping
        TextView txtMaPhim =convertView.findViewById(R.id.txtMaPhim);
        TextView txtTenPhim =convertView.findViewById(R.id.txtTenPhim);
        TextView txtThoiLuong =convertView.findViewById(R.id.txtThoiLuong);
        TextView txtDoTuoi =convertView.findViewById(R.id.txtDoTuoi);
        TextView txtMoTa =convertView.findViewById(R.id.txtMoTa);
        TextView txtDienVien =convertView.findViewById(R.id.txtDienVien);
        TextView txtGiaVe =convertView.findViewById(R.id.txtGiaVe);
        TextView txtTheLoai =convertView.findViewById(R.id.txtTheLoai);
        TextView txtQuocGia =convertView.findViewById(R.id.txtQuocGia);

        txtMaPhim.setText(phim.getMaPhim());
        txtTenPhim.setText(phim.getTenPhim());
        txtThoiLuong.setText(String.valueOf(phim.getThoiLuong()));
        txtDoTuoi.setText(String.valueOf(phim.getGioiHanDoTuoi()));
        txtMoTa.setText(phim.getMoTaPhim());
        txtDienVien.setText(phim.getDienVien());
        txtGiaVe.setText(String.valueOf(phim.getGiaVe()));
        txtTheLoai.setText(phim.getTheLoai());
        txtQuocGia.setText(phim.getQuocGia());

        return convertView;
    }
}
