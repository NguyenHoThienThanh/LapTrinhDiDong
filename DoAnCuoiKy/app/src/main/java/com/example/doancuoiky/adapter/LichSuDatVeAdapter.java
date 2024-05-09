package com.example.doancuoiky.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.user.ChiTietPhimActivity;
import com.example.doancuoiky.activity.user.DetailTicket;
import com.example.doancuoiky.activity.user.LichSuDatVeActivity;
import com.example.doancuoiky.model.HoaDon;
import com.example.doancuoiky.model.Phim;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LichSuDatVeAdapter extends RecyclerView.Adapter<LichSuDatVeAdapter.LishSuDatVeViewHolder> {


    Context context;
    ArrayList<HoaDon> hoaDonList;

    public LichSuDatVeAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<HoaDon> list) {
        this.hoaDonList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LishSuDatVeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_su_dat_ve, parent, false);
        return new LishSuDatVeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LishSuDatVeViewHolder holder, int position) {
        HoaDon hoaDon = hoaDonList.get(position);
        holder.maHoaDon.setText(hoaDon.getMaHoaDon());
        holder.maSuatChieu.setText(hoaDon.getMaSuatChieu());
        holder.maCombo.setText(hoaDon.getMaCombo());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String formattedTongTien = decimalFormat.format(hoaDon.getTongTien());
        holder.tongTien.setText(formattedTongTien + "đ");
        holder.ngayDatVe.setText(hoaDon.getNgayLapHoaDon());

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.light_blue));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.light_blue_2));
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailTicket.class);
            intent.putExtra("maHoaDon", hoaDonList.get(position).getMaHoaDon());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        {
            return hoaDonList != null ? hoaDonList.size() : 0;
        }
    }

    public class LishSuDatVeViewHolder extends RecyclerView.ViewHolder {
        private TextView maHoaDon, maSuatChieu, maCombo, tongTien, ngayDatVe;

        public LishSuDatVeViewHolder(@NonNull View itemView) {
            super(itemView);
            maHoaDon = itemView.findViewById(R.id.txtMaHoaDon);
            maSuatChieu = itemView.findViewById(R.id.txtMaSuatChieu);
            maCombo = itemView.findViewById(R.id.txtMaCombo);
            tongTien = itemView.findViewById(R.id.txtTongTien);
            ngayDatVe = itemView.findViewById(R.id.txtNgayDat);
        }
    }
}
