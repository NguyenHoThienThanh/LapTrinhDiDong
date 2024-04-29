package com.example.doancuoiky.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.KhachHang;
import com.example.doancuoiky.model.PhongChieuPhim;

import java.util.ArrayList;

public class AdminPhongChieuAdapter extends ArrayAdapter<PhongChieuPhim> {
    Activity context;
    int layout_id;
    ArrayList<PhongChieuPhim> arrayPhongChieu;

    public AdminPhongChieuAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<PhongChieuPhim> objects) {
        super(context,resource, objects);
        this.context = context;
        this.layout_id = resource;
        this.arrayPhongChieu = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(layout_id, null);
        TextView tv_ten_phong = convertView.findViewById(R.id.tv_ten_phong);
        TextView tv_so_cho_ngoi = convertView.findViewById(R.id.tv_so_cho_ngoi);

        PhongChieuPhim phongChieuPhim = arrayPhongChieu.get(position);
        tv_ten_phong.setText(phongChieuPhim.getMaPhongChieu());
        tv_so_cho_ngoi.setText(Integer.toString(phongChieuPhim.getSoChoNgoi()));
        return convertView;
    }
}
