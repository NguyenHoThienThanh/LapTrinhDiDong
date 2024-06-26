package com.example.doancuoiky.activity.admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.AdminBillAdapter;
import com.example.doancuoiky.adapter.AdminFilmAdapter;
import com.example.doancuoiky.dao.HoaDonDAO;
import com.example.doancuoiky.model.HoaDon;

import java.util.ArrayList;

public class AdminBillActivity extends AppCompatActivity {


    ListView lvHoaDon;
    LinearLayout layout_HoaDon;
    ArrayList<HoaDon> arrHoaDon;
    AdminBillAdapter adapter;
    Context context;
    HoaDonDAO hoaDonDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bill);
        context = this;
        hoaDonDao = new HoaDonDAO(context);
        mapping();
        toolBar();
        events();
    }

    private void events() {
        lvHoaDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HoaDon hd = arrHoaDon.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminBillActivity.this);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc muốn xóa hóa đơn này không");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean res = hoaDonDao.delete(hd.getMaHoaDon());
                        if (res){
                            Toast.makeText(AdminBillActivity.this, "Delete thành công", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(AdminBillActivity.this, "Delete thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                arrHoaDon = hoaDonDao.getListHoaDon();
                adapter = new AdminBillAdapter(AdminBillActivity.this, R.layout.item_bill_admin, arrHoaDon);
                lvHoaDon.setAdapter(adapter);
                clearText();
                return true;
            }
        });

    }

    private void clearText() {
    }

    private void mapping() {
        layout_HoaDon = findViewById(R.id.layout_HoaDon);
        lvHoaDon = findViewById(R.id.lvHoaDon);
        arrHoaDon = hoaDonDao.getListHoaDon();
        adapter = new AdminBillAdapter(AdminBillActivity.this, R.layout.item_bill_admin, arrHoaDon);
        lvHoaDon.setAdapter(adapter);
    }

    private boolean checkEmptyInput(){
        int childCount = layout_HoaDon.getChildCount();
        for (int i=0; i<childCount; i++){
            View view = layout_HoaDon.getChildAt(i);
            if (view instanceof  EditText){
                EditText edt = (EditText) view;
                if (edt.getText().toString().equals("")){
                    return true;
                }
            }
        }
        return false;
    }

    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_HoaDon);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}