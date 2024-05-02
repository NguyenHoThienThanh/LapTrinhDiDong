package com.example.doancuoiky.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.dao.ChiTietGheDaDatDAO;
import com.example.doancuoiky.dao.ChoNgoiDAO;
import com.example.doancuoiky.dao.SuatChieuDao;
import com.example.doancuoiky.model.ChiTietGheDaDat;
import com.example.doancuoiky.model.ChiTietSuatChieu;
import com.example.doancuoiky.model.DanhSachGhe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DanhSachGioChieuAdapter extends RecyclerView.Adapter<DanhSachGioChieuAdapter.TimeViewHolder>{
    private Context context;
    private List<ChiTietSuatChieu> timeList;
    ChiTietGheDaDatDAO chiTietGheDaDatDAO;
    ArrayList<ChiTietGheDaDat> chiTietGheDaDat = new ArrayList<>();
    private IOnTimeClickListener iOnTimeClickListener;
    public interface IOnTimeClickListener {
        void onTimeClick(int position);
    }

    public DanhSachGioChieuAdapter(Context context) {
        this.context = context;
        chiTietGheDaDatDAO = new ChiTietGheDaDatDAO(context);
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
        chiTietGheDaDat = chiTietGheDaDatDAO.getSeatBySuatChieu(ctsc.getMaSuatChieu());
        holder.seats_remaining.setText(64-chiTietGheDaDat.size()+"/"+"64");
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
        private TextView seats_remaining;
        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            item_time = itemView.findViewById(R.id.btn_time);
            seats_remaining = itemView.findViewById(R.id.seats_remaining);
        }
    }
}
