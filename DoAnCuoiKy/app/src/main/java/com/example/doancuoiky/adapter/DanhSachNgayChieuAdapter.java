package com.example.doancuoiky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.ChiTietSuatChieu;

import java.util.List;

public class DanhSachNgayChieuAdapter extends RecyclerView.Adapter<DanhSachNgayChieuAdapter.DateViewHolder>{
    private Context context;
    private List<ChiTietSuatChieu> dateList;

    public DanhSachNgayChieuAdapter(Context context) {
        this.context = context;
    }
    private DanhSachNgayChieuAdapter.IOnDateClickListener iOnDateClickListener;
    public interface IOnDateClickListener {
        void onSeatClick(int position);
    }
    public void setData(List<ChiTietSuatChieu> list, DanhSachNgayChieuAdapter.IOnDateClickListener iOnDateClickListener){
        this.dateList = list;
        this.iOnDateClickListener = iOnDateClickListener;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seats, parent, false);
        return new DanhSachNgayChieuAdapter.DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(dateList != null){
            return dateList.size();
        }
        return 0;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder{
        private TextView item_date;
        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView item_date = itemView.findViewById(R.id.item_date);
        }
    }
}
