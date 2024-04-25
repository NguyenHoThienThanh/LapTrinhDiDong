package com.example.doancuoiky.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.ChiTietSuatChieu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DanhSachGioChieuAdapter extends RecyclerView.Adapter<DanhSachGioChieuAdapter.TimeViewHolder>{
    private Context context;
    private List<ChiTietSuatChieu> timeList;

    private IOnTimeClickListener iOnTimeClickListener;
    public interface IOnTimeClickListener {
        void onTimeClick(int position);
    }

    public DanhSachGioChieuAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<ChiTietSuatChieu> list, IOnTimeClickListener iOnTimeClickListener){
        this.timeList = list;
        this.iOnTimeClickListener = iOnTimeClickListener;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
        return new DanhSachGioChieuAdapter.TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ChiTietSuatChieu ctsc = timeList.get(position);
        if(ctsc==null) return;
        holder.item_time.setText(ctsc.getGioChieu());
        holder.item_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi callback nếu có
                if(iOnTimeClickListener != null){
                    iOnTimeClickListener.onTimeClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(timeList != null){
            return timeList.size();
        }
        return 0;
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder{
        private Button item_time;
        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            item_time = itemView.findViewById(R.id.btn_time);
        }
    }
}
