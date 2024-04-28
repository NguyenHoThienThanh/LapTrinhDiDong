package com.example.doancuoiky.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.ComboBapNuoc;

import java.util.ArrayList;
import java.util.List;

public class AdminPopcornDrinkAdapter extends ArrayAdapter<ComboBapNuoc> {
    Activity context;
    int layout_id;
    ArrayList<ComboBapNuoc> arrayComboBapNuoc;

    public AdminPopcornDrinkAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<ComboBapNuoc> objects) {
        super(context,resource, objects);
        this.context = context;
        this.layout_id = resource;
        this.arrayComboBapNuoc = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(layout_id, null);

        TextView tv_fandb_name = convertView.findViewById(R.id.txt_fandb_name);
        TextView tv_fandb_detail = convertView.findViewById(R.id.txt_fandb_detail);
        TextView tv_fandb_money = convertView.findViewById(R.id.txt_fandb_money);
        TextView tv_numberItems = convertView.findViewById(R.id.tv_numberItems);
        ImageView fandb_image = convertView.findViewById(R.id.fandb_image);

        ComboBapNuoc comboBapNuoc = arrayComboBapNuoc.get(position);
        tv_fandb_name.setText(comboBapNuoc.getTenCombo());
        tv_fandb_detail.setText(comboBapNuoc.getMoTa());
        tv_fandb_money.setText(String.valueOf(comboBapNuoc.getGia()));

        if (comboBapNuoc != null) {
            int soLuong = comboBapNuoc.getSoLuong();
            tv_numberItems.setText(String.valueOf(soLuong)); // Chỉ gán giá trị nếu comboBapNuoc không bị null
        } else {
            Log.e("AdapterError", "comboBapNuoc là null ở vị trí " + position);
        }
        byte[] comboBapNuocHinhAnh = comboBapNuoc.getHinhAnh();

        Bitmap bitmap = null;
        if (comboBapNuocHinhAnh == null || comboBapNuocHinhAnh.length == 0) {
            // Nếu không có dữ liệu hình ảnh, hiển thị hình ảnh mặc định hoặc thông báo lỗi
            Log.e("ImageError", "Không có dữ liệu hình ảnh");
            fandb_image.setImageResource(R.drawable.corn); // Sử dụng hình ảnh mặc định
        } else {
            try {
                // Cố gắng tạo Bitmap từ mảng byte
                bitmap = BitmapFactory.decodeByteArray(comboBapNuocHinhAnh, 0, comboBapNuocHinhAnh.length);

                if (bitmap == null) {
                    Log.e("ImageError", "Không thể tạo Bitmap từ dữ liệu byte");
                    fandb_image.setImageResource(R.drawable.corn); // Sử dụng hình ảnh mặc định
                } else {
                    fandb_image.setImageBitmap(bitmap); // Gán Bitmap vào ImageView
                }
            } catch (Exception e) {
                // Nếu có lỗi khi tạo Bitmap, xử lý ngoại lệ
                Log.e("BitmapError", "Lỗi khi tạo Bitmap từ dữ liệu byte", e);
                fandb_image.setImageResource(R.drawable.corn); // Sử dụng hình ảnh mặc định
            }
        }

        return convertView;
    }

}
