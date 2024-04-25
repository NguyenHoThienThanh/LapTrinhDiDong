package com.example.doancuoiky.adapter;

import android.content.Context;
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
import com.example.doancuoiky.model.ComboBapNuoc;

import java.text.DecimalFormat;
import java.util.List;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.TestViewHolder>{

    Context context;
    List<ComboBapNuoc> foodAndBeverageList;


    public void setData(List<ComboBapNuoc> list) {
        this.foodAndBeverageList = list;
        notifyDataSetChanged();
    }
    public ThanhToanAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fandb_thanhtoan, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        ComboBapNuoc foodAndBeverage = foodAndBeverageList.get(position);
        if (foodAndBeverage == null)
            return;
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        String formattedMoney = decimalFormat.format(foodAndBeverage.getGia());
        holder.tv_name.setText(foodAndBeverage.getTenCombo());
        holder.tv_money.setText(formattedMoney + "đ");
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodAndBeverage.getHinhAnh(), 0, foodAndBeverage.getHinhAnh().length);
        holder.img_fandb.setImageBitmap(bitmap);
        holder.tv_number.setText("x" + String.valueOf(foodAndBeverage.getSoLuongDat()));
    }

    @Override
    public int getItemCount() {
        if (foodAndBeverageList != null) {
            return foodAndBeverageList.size();
        }
        return 0;
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_money, tv_number;
        private ImageView img_fandb;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.txt_fandb_name_thanhtoan);
            tv_money = itemView.findViewById(R.id.txt_fandb_money_thanhtoan);
            tv_number = itemView.findViewById(R.id.txt_fandb_number_thanhtoan);
            img_fandb = itemView.findViewById(R.id.fandb_image_thanhtoan);
        }
    }
}
