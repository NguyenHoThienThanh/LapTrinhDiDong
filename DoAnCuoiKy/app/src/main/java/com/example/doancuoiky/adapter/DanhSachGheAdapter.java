package com.example.doancuoiky.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.DanhSachGhe;

import java.util.List;

public class DanhSachGheAdapter extends RecyclerView.Adapter<DanhSachGheAdapter.SeatViewHolder> {

    private Context context;
    private List<DanhSachGhe> seatList;

    public DanhSachGheAdapter(Context context) {
        this.context = context;
    }

    private IOnSeatClickListener iOnSeatClickListener;
    public interface IOnSeatClickListener {
        void onSeatClick(int position);
    }
    public void setData(List<DanhSachGhe> list, IOnSeatClickListener iOnSeatClickListener){
        this.seatList = list;
        this.iOnSeatClickListener = iOnSeatClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seats, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DanhSachGhe seat = seatList.get(position);
        if(seat == null)
            return;
        holder.btn_seatNumber.setText(seat.getHangGhe() + seat.getSoGhe());

        if (seat.isSelected()) {
            // Ghế đã được chọn bởi bạn
            holder.btn_seatNumber.setBackgroundResource(R.drawable.seat_background_click); // Màu khi ghế bạn đang chọn
        } else if (seat.getTinhTrang() == 1) {
            // Ghế đã được chọn bởi người khác
            holder.btn_seatNumber.setBackgroundResource(R.drawable.seat_background_selected); // Màu khi ghế đã được chọn bởi người khác
            holder.btn_seatNumber.setEnabled(false); // Vô hiệu hóa nút
        } else {
            // Ghế chưa được chọn
            holder.btn_seatNumber.setBackgroundResource(R.drawable.seat_background); // Màu khi ghế chưa được chọn
            holder.btn_seatNumber.setEnabled(true); // Kích hoạt nút
        }

        // Sự kiện click cho Button
        holder.btn_seatNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra trạng thái của ghế
                if (seat.getTinhTrang() == 1) {
                    // Nếu ghế đã được chọn bởi người khác, không làm gì cả
                    return;
                }
                // Đảo ngược trạng thái isSelected của ghế
                seat.setSelected(!seat.isSelected());
                // Cập nhật giao diện tương ứng với trạng thái mới của ghế
                if (seat.isSelected()) {
                    holder.btn_seatNumber.setBackgroundResource(R.drawable.seat_background_click);
//                    Toast.makeText(context, seat.getHangGhe() + seat.getSoGhe() + seat.getTinhTrang() + seat.getMaSuatChieu(), Toast.LENGTH_SHORT).show();// Màu khi ghế bạn đang chọn
                } else {
                    holder.btn_seatNumber.setBackgroundResource(R.drawable.seat_background); // Màu khi ghế không được chọn
                }
                // Gọi callback nếu có
                if(iOnSeatClickListener != null){
                    iOnSeatClickListener.onSeatClick(position);
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
