package com.example.doancuoiky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.ChoNgoi;

import java.util.List;

public class ChoNgoiAdapter extends RecyclerView.Adapter<ChoNgoiAdapter.SeatViewHolder> {

    private Context context;
    private List<ChoNgoi> seatList;

    public ChoNgoiAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ChoNgoi> list){
        this.seatList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seats, parent, false);

        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {

        ChoNgoi seat = seatList.get(position);
        if(seat == null)
            return;
        holder.btn_seatNumber.setText(seat.getHangGhe() + seat.getSoGhe());

        // Sự kiện click cho Button
        holder.btn_seatNumber.setOnClickListener(new View.OnClickListener() {
            boolean check = false;
            @Override
            public void onClick(View v) {
                // Kiểm tra giá trị của biến check
                if(check == false) {
                    holder.btn_seatNumber.setBackgroundResource(R.drawable.seat_background);
                    check = true;
                } else {
                    holder.btn_seatNumber.setBackgroundResource(R.drawable.seat_background_click);
                    check = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(seatList != null){
            return seatList.size();
        }
        return 0;
    }

    public class SeatViewHolder extends RecyclerView.ViewHolder{
        private Button btn_seatNumber;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_seatNumber = itemView.findViewById(R.id.btn_seatNumber);
        }
    }


}
