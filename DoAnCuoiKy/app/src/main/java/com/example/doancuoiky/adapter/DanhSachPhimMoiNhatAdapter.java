package com.example.doancuoiky.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.user.ChiTietPhimActivity;
import com.example.doancuoiky.activity.user.LoginActivity;
import com.example.doancuoiky.model.ComboBapNuoc;
import com.example.doancuoiky.model.Phim;

import java.util.List;

public class DanhSachPhimMoiNhatAdapter extends RecyclerView.Adapter<DanhSachPhimMoiNhatAdapter.Top5FilmViewHolder>{

    private Context context;
    List<Phim> items;

    public DanhSachPhimMoiNhatAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Top5FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film, parent, false);
        return new Top5FilmViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachPhimMoiNhatAdapter.Top5FilmViewHolder holder, int position) {
        Phim phim = items.get(position);

        holder.txtTitle.setText(phim.getTenPhim());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));

        Bitmap bitmap = BitmapFactory.decodeByteArray(phim.getTrailer(), 0, phim.getTrailer().length);
        holder.pic.setImageBitmap(bitmap);

        Glide.with(context)
                .asBitmap()
                .load(items.get(position).getMoTaPhim())
                .apply(requestOptions)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.pic.setImageBitmap(resource);
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ChiTietPhimActivity.class);
            intent.putExtra("maPhim", items.get(position).getMaPhim());
            context.startActivity(intent);
        });
    }

    public void setData(List<Phim> phimList){
        this.items = phimList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Top5FilmViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        ImageView pic;
        public Top5FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            pic = itemView.findViewById(R.id.pic);
        }
    }

}
