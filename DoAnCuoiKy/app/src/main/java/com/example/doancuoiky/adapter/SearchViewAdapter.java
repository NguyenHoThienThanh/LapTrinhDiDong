package com.example.doancuoiky.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.doancuoiky.activity.user.ChiTietPhimActivity;
import com.example.doancuoiky.model.ComboBapNuoc;
import com.example.doancuoiky.model.Phim;

import java.util.List;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.SearchViewHolder> {

    Context context;
    List<Phim> phimList;

    public SearchViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Phim> list) {
        this.phimList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_phim, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Phim phim = phimList.get(position);
        holder.phim_name.setText(phim.getTenPhim());
        holder.phim_type.setText(phim.getTheLoai());
        Bitmap bitmap = BitmapFactory.decodeByteArray(phim.getTrailer(), 0, phim.getTrailer().length);
        holder.phim_img.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ChiTietPhimActivity.class);
            intent.putExtra("maPhim", phimList.get(position).getMaPhim());
            context.startActivity(intent);
        });
    }
    public void filterList(List<Phim> filteredList) {
        phimList = filteredList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return phimList != null ? phimList.size() : 0;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView phim_name, phim_type;
        private ImageView phim_img;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            phim_img = itemView.findViewById(R.id.phim_img);
            phim_name = itemView.findViewById(R.id.phim_name);
            phim_type = itemView.findViewById(R.id.phim_type);
        }
    }
}
