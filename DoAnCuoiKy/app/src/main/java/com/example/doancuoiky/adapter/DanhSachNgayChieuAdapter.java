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
import com.example.doancuoiky.model.ChiTietSuatChieu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DanhSachNgayChieuAdapter extends RecyclerView.Adapter<DanhSachNgayChieuAdapter.DateViewHolder>{
    private Context context;
    private List<ChiTietSuatChieu> dateList;
    private int selectedPosition = 0;

    public DanhSachNgayChieuAdapter(Context context) {
        this.context = context;
    }
    private DanhSachNgayChieuAdapter.IOnDateClickListener iOnDateClickListener;
    public interface IOnDateClickListener {
        void onDateClick(int position);
    }
    public void setData(List<ChiTietSuatChieu> list, IOnDateClickListener iOnDateClickListener){
        this.dateList = list;
        this.iOnDateClickListener = iOnDateClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ChiTietSuatChieu ctsc = dateList.get(position);
        if(ctsc==null) return;
        // Định dạng để lấy chỉ ngày
        String formattedDate = getOnlyDay(ctsc.getNgayChieu());
        holder.item_date.setText(formattedDate);
        // Kiểm tra trạng thái của phần tử
        if (position == selectedPosition) {
            holder.item_date.setTextColor(context.getResources().getColor(R.color.white)); // Hoặc màu khác
            holder.item_date.setBackgroundResource(R.drawable.date_background_click); // Màu cho phần tử được chọn
        } else {
            holder.item_date.setTextColor(context.getResources().getColor(R.color.pink)); // Hoặc màu khác
            holder.item_date.setBackgroundResource(R.drawable.date_background); // Màu mặc định
        }
        holder.item_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position; // Cập nhật vị trí được chọn
                notifyDataSetChanged(); // Yêu cầu cập nhật giao diện
                // Gọi callback nếu có
                if(iOnDateClickListener != null){
                    iOnDateClickListener.onDateClick(position);
                }
            }
        });
    }
    private String getOnlyDay(String fullDate) {
        try {
            // Định dạng gốc (dd-MM-yyyy)
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = originalFormat.parse(fullDate);

            // Định dạng mới để lấy chỉ ngày
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            return dayFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return ""; // Trả về chuỗi rỗng nếu có lỗi
        }
    }
    @Override
    public int getItemCount() {
        if(dateList != null){
            return dateList.size();
        }
        return 0;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder{
        private Button item_date;
        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            item_date = itemView.findViewById(R.id.btn_date);
        }
    }
}
