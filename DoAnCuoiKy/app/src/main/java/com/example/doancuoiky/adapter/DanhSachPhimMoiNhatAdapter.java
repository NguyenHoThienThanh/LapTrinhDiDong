package com.example.doancuoiky.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.user.ChiTietPhimActivity;
import com.example.doancuoiky.model.Phim;

import java.util.List;

public class DanhSachPhimMoiNhatAdapter extends RecyclerView.Adapter<DanhSachPhimMoiNhatAdapter.ViewHolder>{
    List<Phim> items;
    Context context;

    public DanhSachPhimMoiNhatAdapter(List items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DanhSachPhimMoiNhatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachPhimMoiNhatAdapter.ViewHolder holder, int position) {
        holder.txtTitle.setText(items.get(position).getTenPhim());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));

        Glide.with(context)
                .load(items.get(position).getMoTaPhim())
                .apply(requestOptions)
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ChiTietPhimActivity.class);
            intent.putExtra("id", items.get(position).getMaPhim());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            pic = itemView.findViewById(R.id.pic);
        }
    }

}
