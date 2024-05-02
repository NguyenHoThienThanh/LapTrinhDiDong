package com.example.doancuoiky.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doancuoiky.R;
import com.example.doancuoiky.model.HoaDon;

import java.util.ArrayList;

public class LichSuDatVeAdapter extends ArrayAdapter<HoaDon> {
    Activity context;
    int layout_id;
    ArrayList<HoaDon> arrHoaDon;

    public LichSuDatVeAdapter(@NonNull Activity context, int layout_id, @NonNull ArrayList<HoaDon> arrHoaDon) {
        super(context, layout_id, arrHoaDon);
        this.context = context;
        this.layout_id = layout_id;
        this.arrHoaDon = arrHoaDon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(layout_id, null);
        HoaDon hd = arrHoaDon.get(position);

        TextView txtMaHoaDon = convertView.findViewById(R.id.txtMaHoaDon);
        TextView txtMaSuatChieu = convertView.findViewById(R.id.txtMaSuatChieu);
        TextView txtMaCombo = convertView.findViewById(R.id.txtMaCombo);
        TextView txtTongTien = convertView.findViewById(R.id.txtTongTien);

        txtMaHoaDon.setText(hd.getMaHoaDon());
        txtMaSuatChieu.setText(hd.getMaSuatChieu());
        txtMaCombo.setText(hd.getMaCombo());
        txtTongTien.setText(String.valueOf(hd.getTongTien()));

        return convertView;
    }
}
