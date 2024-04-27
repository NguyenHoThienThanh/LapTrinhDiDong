package com.example.doancuoiky.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.KhachHang;

import java.util.List;

public class AdminCustomerAdapter extends RecyclerView.Adapter<AdminCustomerAdapter.FAndBViewHolder> {
    Context context;
    List<KhachHang> listKhachHang;
    private ComboBapNuocAdapter.IOnItemClickListener iOnItemClickListener;
    public AdminCustomerAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public FAndBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new FAndBViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAndBViewHolder holder, int position) {
        KhachHang khachHang = listKhachHang.get(position);
        if(khachHang == null){
            return;
        }
        holder.txt_ngaySinh.setText(khachHang.getNgaySinh());
        holder.txt_diaChi.setText(khachHang.getDiaChi());
        holder.txt_hoTen.setText(khachHang.getHoTen());
        holder.txt_maKhachHang.setText(khachHang.getMaKhachHang());
        Bitmap bitmap = BitmapFactory.decodeByteArray(khachHang.getAvatar(), 0 , khachHang.getAvatar().length);
        holder.img_avatar.setImageBitmap(bitmap);

    }
    public void setData(List<KhachHang> list){
        this.listKhachHang = list;
    }
    @Override
    public int getItemCount() {
        if(listKhachHang != null){
            return listKhachHang.size();
        }
        return 0;
    }

    public class FAndBViewHolder extends  RecyclerView.ViewHolder{
        public TextView txt_hoTen, txt_maKhachHang, txt_ngaySinh, txt_diaChi;
        public ImageView img_avatar;
        public FAndBViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_diaChi = itemView.findViewById(R.id.txt_diaChi);
            txt_hoTen = itemView.findViewById(R.id.txt_hoTen);
            txt_maKhachHang = itemView.findViewById(R.id.txt_maKhachHang);
            txt_ngaySinh = itemView.findViewById(R.id.txt_ngaySinh);
            img_avatar = itemView.findViewById(R.id.img_avatar);

        }
    }
}

