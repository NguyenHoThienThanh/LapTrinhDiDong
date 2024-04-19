package com.example.doancuoiky.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.ComboBapNuoc;

import java.text.DecimalFormat;
import java.util.List;

public class ComboBapNuocAdapter extends RecyclerView.Adapter<ComboBapNuocAdapter.FAndBViewHolder> {
    Context context;
    List<ComboBapNuoc> foodAndBeverageList;

    public ComboBapNuocAdapter(Context context) {
        this.context = context;
    }

    private IOnItemClickListener iOnItemClickListener;

    // Interface định nghĩa hành động khi nhấn nút plus hoặc minus
    public interface IOnItemClickListener {
        void onPlusClick(int position);

        void onMinusClick(int position);
    }

    public void setData(List<ComboBapNuoc> list, IOnItemClickListener iOnItemClickListener) {
        this.foodAndBeverageList = list;
        this.iOnItemClickListener = iOnItemClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FAndBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fandb, parent, false);
        return new FAndBViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAndBViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ComboBapNuoc foodAndBeverage = foodAndBeverageList.get(position);
        if (foodAndBeverage == null)
            return;
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        String formattedMoney = decimalFormat.format(foodAndBeverage.getGia());
        holder.tv_name.setText(foodAndBeverage.getTenCombo());
        holder.tv_money.setText(formattedMoney + "đ");
        holder.img_fandb.setImageResource(foodAndBeverage.getHinhAnh());
        holder.tv_number.setText(String.valueOf(foodAndBeverage.getSoLuongDat()));

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi hàm onMinusClick của callback mListener
                if (iOnItemClickListener != null) {
                    iOnItemClickListener.onMinusClick(position);
                }
            }
        });

        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi hàm onPlusClick của callback mListener
                if (iOnItemClickListener != null) {
                    iOnItemClickListener.onPlusClick(position);
                }
            }
        });

    }

    public void updateQuantityTextView(int position, int quantity) {
        // Cập nhật số lượng đặt hàng trên TextView tương ứng với vị trí position
        foodAndBeverageList.get(position).setSoLuongDat(quantity);
        notifyItemChanged(position, quantity); // Thông báo cho RecyclerView cập nhật item tại vị trí position
    }
    @Override
    public int getItemCount() {
        if (foodAndBeverageList != null) {
            return foodAndBeverageList.size();
        }
        return 0;
    }

    public class FAndBViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_money, btn_minus, btn_plus, tv_number;
        private ImageView img_fandb;

        public FAndBViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.txt_fandb_name);
            tv_money = itemView.findViewById(R.id.txt_fandb_money);
            btn_minus = itemView.findViewById(R.id.tv_minus);
            btn_plus = itemView.findViewById(R.id.tv_plus);
            tv_number = itemView.findViewById(R.id.tv_numberItems);
            img_fandb = itemView.findViewById(R.id.fandb_image);
        }
    }
}
