package com.example.doancuoiky.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doancuoiky.R;

public class ChonRapActivity extends AppCompatActivity {
    private RecyclerView rcvDate;
    private RecyclerView rcvTime;
    private RecyclerView rcvTheather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_rap);
    }
}