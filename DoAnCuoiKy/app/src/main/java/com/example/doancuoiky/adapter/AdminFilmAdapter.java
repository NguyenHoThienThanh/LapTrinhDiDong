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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.Phim;

import java.util.ArrayList;

public class AdminFilmAdapter extends ArrayAdapter<Phim> {

    Activity context;
    int layout_id;
    ArrayList<Phim> arrPhim;


    public AdminFilmAdapter(@NonNull Activity context, int layout_id, @NonNull ArrayList<Phim> objects) {
        super(context, layout_id, objects);
        this.context = context;
        this.layout_id = layout_id;
        this.arrPhim = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(layout_id, null);
        Phim phim = arrPhim.get(position);
        //Mapping
        TextView txtMaPhim =convertView.findViewById(R.id.txtMaHoaDon);
        TextView txtTenPhim =convertView.findViewById(R.id.txtMaSuatChieu);
        TextView txtThoiLuong =convertView.findViewById(R.id.txtMaKhachHang);
        TextView txtDoTuoi =convertView.findViewById(R.id.txtMaCombo);
        TextView txtMoTa =convertView.findViewById(R.id.txtTongTien);
        TextView txtDienVien =convertView.findViewById(R.id.txtDienVien);
        TextView txtGiaVe =convertView.findViewById(R.id.txtGiaVe);
        TextView txtTheLoai =convertView.findViewById(R.id.txtTheLoai);
        TextView txtQuocGia =convertView.findViewById(R.id.txtQuocGia);
        ImageView imgaTrailer = convertView.findViewById(R.id.imgTrailer);

        txtMaPhim.setText(phim.getMaPhim());
        txtTenPhim.setText(phim.getTenPhim());
        txtThoiLuong.setText(String.valueOf(phim.getThoiLuong()));
        txtDoTuoi.setText(String.valueOf(phim.getGioiHanDoTuoi()));
        txtMoTa.setText(phim.getMoTaPhim());
        txtDienVien.setText(phim.getDienVien());
        txtGiaVe.setText(String.valueOf(phim.getGiaVe()));
        txtTheLoai.setText(phim.getTheLoai());
        txtQuocGia.setText(phim.getQuocGia());
        byte[] trailer = phim.getTrailer();

        Bitmap bitmap = null;
        if (trailer == null || trailer.length == 0) {
            // Nếu không có dữ liệu hình ảnh, hiển thị hình ảnh mặc định hoặc thông báo lỗi
            Log.e("ImageError", "Không có dữ liệu hình ảnh");
            imgaTrailer.setImageResource(R.drawable.corn); // Sử dụng hình ảnh mặc định
        } else {
            try {
                // Cố gắng tạo Bitmap từ mảng byte
                bitmap = BitmapFactory.decodeByteArray(trailer, 0, trailer.length);

                if (bitmap == null) {
                    Log.e("ImageError", "Không thể tạo Bitmap từ dữ liệu byte");
                    imgaTrailer.setImageResource(R.drawable.corn); // Sử dụng hình ảnh mặc định
                } else {
                    imgaTrailer.setImageBitmap(bitmap); // Gán Bitmap vào ImageView
                }
            } catch (Exception e) {
                // Nếu có lỗi khi tạo Bitmap, xử lý ngoại lệ
                Log.e("BitmapError", "Lỗi khi tạo Bitmap từ dữ liệu byte", e);
                imgaTrailer.setImageResource(R.drawable.corn); // Sử dụng hình ảnh mặc định
            }
        }

        return convertView;
    }
}
