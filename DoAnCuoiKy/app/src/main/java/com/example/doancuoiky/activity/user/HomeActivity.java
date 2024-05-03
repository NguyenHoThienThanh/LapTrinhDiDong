package com.example.doancuoiky.activity.user;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.DanhSachPhimMoiNhatAdapter;
import com.example.doancuoiky.adapter.SliderAdapters;
import com.example.doancuoiky.dao.PhimDAO;
import com.example.doancuoiky.model.Phim;
import com.example.doancuoiky.model.SliderItems;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    // ViewPage để hiển thị Slider
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();

    //Hiển thị View lên RecycleView
    private DanhSachPhimMoiNhatAdapter adapterTop5FilmMovies;
    private RecyclerView recyclerViewBestMovies, recyclerViewUpComing, recyclerViewCategry;
    private ProgressBar loading1, loading2, loading3;
    List<Phim> phimList;
    PhimDAO phimDao;

    // Bottom Navigation Bar
    ImageView imgLichSuGiaoDich, imgProfile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        phimDao = new PhimDAO(this);

        initView();
        banners();
        loadTop5PhimMovie();
        events();
    }

    private void events() {
        imgLichSuGiaoDich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LichSuDatVeActivity.class);
                startActivity(intent);
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChiTietKhachHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadTop5PhimMovie(){
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        adapterTop5FilmMovies = new DanhSachPhimMoiNhatAdapter(this);
        phimList = getTop5PhimList();
//        loading1.setVisibility(View.VISIBLE);

        adapterTop5FilmMovies.setData(phimList);
        recyclerViewBestMovies.setAdapter(adapterTop5FilmMovies);

    }
    private List<Phim> getTop5PhimList(){
        List<Phim> list = new ArrayList<>();
        list = phimDao.findTop5Phim();
        Toast.makeText(this, "" + list.size(), Toast.LENGTH_SHORT).show();
        return list;
    }

    private void banners() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide));
        sliderItems.add(new SliderItems(R.drawable.wide1));
        sliderItems.add(new SliderItems(R.drawable.wide3));

        viewPager2.setAdapter(new SliderAdapters(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r*0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunable);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunable, 20000);
    }

    private Runnable sliderRunable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    private void initView() {
        viewPager2 = findViewById(R.id.viewpagerSlider);
        recyclerViewBestMovies = findViewById(R.id.view1);

        recyclerViewCategry = findViewById(R.id.view2);
        recyclerViewCategry.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpComing = findViewById(R.id.view3);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
//        loading1 = findViewById(R.id.progressBar1);
        loading2 = findViewById(R.id.progressBar3);
        loading3 = findViewById(R.id.progressBar4);
        imgLichSuGiaoDich = findViewById(R.id.imgLichSuGiaoDich);
        imgProfile = findViewById(R.id.imgProfile);

    }
}