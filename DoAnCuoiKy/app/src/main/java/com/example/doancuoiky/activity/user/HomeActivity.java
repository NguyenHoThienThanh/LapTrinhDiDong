package com.example.doancuoiky.activity.user;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import com.example.doancuoiky.R;
import com.example.doancuoiky.adapter.ComboBapNuocAdapter;
import com.example.doancuoiky.adapter.DanhSachPhimMoiNhatAdapter;
import com.example.doancuoiky.adapter.SearchViewAdapter;
import com.example.doancuoiky.adapter.SliderAdapters;
import com.example.doancuoiky.dao.PhimDAO;
import com.example.doancuoiky.model.ComboBapNuoc;
import com.example.doancuoiky.model.Phim;
import com.example.doancuoiky.model.SliderItems;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    // ViewPage để hiển thị Slider
    RecyclerView rcv_search;
    SearchViewAdapter searchViewAdapter;
    SearchView searchView;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();

    //Hiển thị View lên RecycleView
    private DanhSachPhimMoiNhatAdapter adapterTop5FilmMovies;
    private DanhSachPhimMoiNhatAdapter adapterUpComingPhim;
    private RecyclerView recyclerViewBestMovies, recyclerViewUpComing, recyclerViewCategry;
    private ProgressBar loading1, loading2, loading3;
    ArrayList<Phim> phimList = new ArrayList<>();
    PhimDAO phimDao;

    // Bottom Navigation Bar
    ImageView imgLichSuGiaoDich, imgProfile, imgLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        phimDao = new PhimDAO(this);
        initView();
        banners();
        loadTop5PhimMovie();
        loadUpComingPhim();
        events();
        search();
    }

    private void loadUpComingPhim() {
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        adapterUpComingPhim = new DanhSachPhimMoiNhatAdapter(this);
        phimList = phimDao.findUpComingPhim();
        adapterUpComingPhim.setData(phimList);
        recyclerViewUpComing.setAdapter(adapterUpComingPhim);
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
                Intent intent = new Intent(HomeActivity.this, KhachHangActivity.class);
                startActivity(intent);
            }
        });
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đăng xuất");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");

        // Thiết lập nút tích cực (OK)
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Thực hiện đăng xuất khi người dùng nhấn nút "Đăng xuất"
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Hiển thị hộp thoại cảnh báo
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadTop5PhimMovie() {
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        adapterTop5FilmMovies = new DanhSachPhimMoiNhatAdapter(this);
        phimList = (ArrayList<Phim>) getTop5PhimList();
//        loading1.setVisibility(View.VISIBLE);

        adapterTop5FilmMovies.setData(phimList);
        recyclerViewBestMovies.setAdapter(adapterTop5FilmMovies);

    }

    private List<Phim> getTop5PhimList() {
        List<Phim> list = new ArrayList<>();
        list = phimDao.findTop5Phim();
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
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    private void initView() {
        viewPager2 = findViewById(R.id.viewpagerSlider);
        recyclerViewBestMovies = findViewById(R.id.view1);

        recyclerViewUpComing = findViewById(R.id.view3);
//        loading1 = findViewById(R.id.progressBar1);
//        loading2 = findViewById(R.id.progressBar3);
//        loading3 = findViewById(R.id.progressBar4);
        imgLichSuGiaoDich = findViewById(R.id.imgLichSuGiaoDich);
        imgProfile = findViewById(R.id.imgProfile);
        imgLogout = findViewById(R.id.img_logout);
        searchView = findViewById(R.id.search);
    }

    private void search() {
        rcv_search = findViewById(R.id.rcv_search);
        searchViewAdapter = new SearchViewAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_search.setLayoutManager(linearLayoutManager);

        phimList = phimDao.findAllPhim();
        searchViewAdapter.setData(phimList);
        rcv_search.setAdapter(searchViewAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.isEmpty()) { // Kiểm tra nếu chuỗi rỗng
                    rcv_search.setVisibility(View.GONE); // Ẩn rcv_search
                } else {
                    rcv_search.setVisibility(View.VISIBLE); // Hiện rcv_search
                    List<Phim> filteredList = new ArrayList<>();
                    // Lặp qua tất cả các phim trong phimList
                    for (Phim phim : phimList) {
                        // Nếu tên phim chứa newText, thêm phim vào filteredList
                        if (phim.getTenPhim().toLowerCase().contains(query.toLowerCase())) {
                            filteredList.add(phim);
                        }
                    }
                    // Cập nhật dữ liệu trong adapter với danh sách đã lọc
                    searchViewAdapter.filterList(filteredList);
                }
                return true;
            }
        });
    }
}