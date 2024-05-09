package com.example.doancuoiky.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class AdminCustomerAdapter extends ArrayAdapter<KhachHang> {
    Activity context;
    int layout_id;
    ArrayList<KhachHang> arrayKhachHang;

    public AdminCustomerAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<KhachHang> objects) {
        super(context,resource, objects);
        this.context = context;
        this.layout_id = resource;
        this.arrayKhachHang = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(layout_id, null);
        TextView tv_name = convertView.findViewById(R.id.txt_hoTen);
        TextView tv_ngaySinh = convertView.findViewById(R.id.txt_ngaySinh);
        TextView tv_diaChi = convertView.findViewById(R.id.txt_diaChi);
        TextView tv_maKhachHang = convertView.findViewById(R.id.txt_maKhachHang);
        ImageView img = convertView.findViewById(R.id.img_avatar);

        KhachHang khachHang = arrayKhachHang.get(position);
        tv_name.setText(khachHang.getHoTen());
        tv_maKhachHang.setText(khachHang.getMaKhachHang());
        tv_diaChi.setText(khachHang.getDiaChi());
        tv_ngaySinh.setText(khachHang.getNgaySinh());
        byte[] avatarData = khachHang.getAvatar();
        Bitmap defaultBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bhd_icon); // default_avatar là tên của hình ảnh mặc định

        if (avatarData != null && avatarData.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatarData, 0, avatarData.length);
            img.setImageBitmap(bitmap);
        } else {
            img.setImageBitmap(defaultBitmap); // Thiết lập hình ảnh mặc định
        }

        return convertView;
    }

}

