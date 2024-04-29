package com.example.doancuoiky.activity.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doancuoiky.R;
import com.example.doancuoiky.activity.user.ChiTietKhachHangActivity;
import com.example.doancuoiky.activity.user.KhachHangActivity;
import com.example.doancuoiky.adapter.AdminPopcornDrinkAdapter;
import com.example.doancuoiky.adapter.ComboBapNuocAdapter;
import com.example.doancuoiky.dao.ComboBapNuocDAO;
import com.example.doancuoiky.model.ComboBapNuoc;
import com.example.doancuoiky.model.KhachHang;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminPopcornDrinkActivity extends AppCompatActivity {
    ListView lv_comboBapNuoc;
    Button btn_them, btn_sua, btn_huy;

    AdminPopcornDrinkAdapter adminComboBapNuocAdapter;
    ArrayList<ComboBapNuoc> listCombo = new ArrayList<>();
    ComboBapNuocDAO comboBapNuocDAO;
    ComboBapNuoc comboBapNuoc;
    public ComboBapNuoc comboBapNuocSelected;
    int pos =-1;

    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_popcorn_drink);
        comboBapNuocDAO = new ComboBapNuocDAO(this);
        lv_comboBapNuoc = findViewById(R.id.lv_combo);
        adminComboBapNuocAdapter();

        TextView tv_numberItems = findViewById(R.id.tv_numberItems);
        final int[] currentQuantity = {0}; // Bắt đầu từ 0

        TextView tv_plus = findViewById(R.id.tv_plus);
        tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuantity[0]++;
                tv_numberItems.setText(String.valueOf(currentQuantity[0])); // Cập nhật số lượng
            }
        });

        TextView tv_minus = findViewById(R.id.tv_minus);
        tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuantity[0] > 0) {
                    currentQuantity[0]--;
                    tv_numberItems.setText(String.valueOf(currentQuantity[0]));
                }
            }
        });
        ImageView fandb_image = findViewById(R.id.fandb_image);
        fandb_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        btn_them = findViewById(R.id.btn_them);
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt_name = findViewById(R.id.edt_fandb_name);
                EditText edt_detail = findViewById(R.id.edt_fandb_detail);
                EditText edt_money = findViewById(R.id.edt_fandb_money);
                TextView tv_numberItems = findViewById(R.id.tv_numberItems);
                checkNull();
                ComboBapNuoc newCombo = new ComboBapNuoc();
                newCombo.setTenCombo(edt_name.getText().toString());
                newCombo.setMoTa(edt_detail.getText().toString());

                int soLuong = Integer.parseInt(tv_numberItems.getText().toString());
                newCombo.setSoLuong(soLuong);

                float gia;
                try {
                    gia = Float.parseFloat(edt_money.getText().toString().replace("đ", "").trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(AdminPopcornDrinkActivity.this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                newCombo.setGia(gia);
                // Cập nhật hình ảnh từ ImageView
                ImageView img_picture = findViewById(R.id.fandb_image);
                Drawable drawable = img_picture.getDrawable(); // Lấy Drawable từ ImageView
                if (drawable != null && drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    newCombo.setHinhAnh(bitmapToByteArray(bitmap)); // Chuyển Bitmap thành mảng byte
                } else {
                    Toast.makeText(AdminPopcornDrinkActivity.this, "Chưa có hình ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean isInserted = comboBapNuocDAO.insert(newCombo);

                if (isInserted) {
                    listCombo.clear();
                    listCombo.addAll(comboBapNuocDAO.findAllCombo());
                    adminComboBapNuocAdapter.notifyDataSetChanged();
                    Toast.makeText(AdminPopcornDrinkActivity.this, "Thêm combo thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminPopcornDrinkActivity.this, "Thêm combo không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lv_comboBapNuoc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                comboBapNuocSelected = listCombo.get(position);
                pos = position;
                return false;
            }
        });
        btn_huy = findViewById(R.id.btn_huy);
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt_name = findViewById(R.id.edt_fandb_name);
                EditText edt_detail = findViewById(R.id.edt_fandb_detail);
                EditText edt_money = findViewById(R.id.edt_fandb_money);
                TextView tv_numberItems = findViewById(R.id.tv_numberItems);
                ImageView img_picture = findViewById(R.id.fandb_image);

                edt_name.setText("");
                edt_detail.setText("");
                edt_money.setText("");
                tv_numberItems.setText("0");
                // Đặt lại biến currentQuantity về 0
                currentQuantity[0] = 0;
                TextView tv_plus = findViewById(R.id.tv_plus);
                tv_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentQuantity[0]++;
                        tv_numberItems.setText(String.valueOf(currentQuantity[0])); // Cập nhật số lượng
                    }
                });

                TextView tv_minus = findViewById(R.id.tv_minus);
                tv_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentQuantity[0] > 0) {
                            currentQuantity[0]--;
                            tv_numberItems.setText(String.valueOf(currentQuantity[0]));
                        }
                    }
                });
                img_picture.setImageResource(R.drawable.corn);

                // Ẩn nút sửa
                Button btn_sua = findViewById(R.id.btn_sua);
                btn_sua.setVisibility(View.INVISIBLE);
            }
        });
        btn_sua = findViewById(R.id.btn_sua);
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPopcornDrinkActivity.this);
                builder.setTitle("Xác nhận sửa");
                builder.setMessage("Bạn có chắc chắn muốn sửa combo này?");

                // Thiết lập hành động cho nút "Đồng ý"
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Gọi hàm cập nhật dữ liệu
                        update(); // Hoặc hàm logic khác cho việc sửa combo
                    }
                });

                // Thiết lập hành động cho nút "Hủy"
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Đóng hộp thoại nếu người dùng chọn Hủy
                    }
                });

                AlertDialog alertDialog = builder.create(); // Tạo hộp thoại
                alertDialog.show(); // Hiển thị hộp thoại
            }
        });


    }

    private void adminComboBapNuocAdapter() {
        listCombo = comboBapNuocDAO.findAllCombo();
        adminComboBapNuocAdapter = new AdminPopcornDrinkAdapter(this, R.layout.item_fandb_admin, listCombo);
        lv_comboBapNuoc.setAdapter(adminComboBapNuocAdapter);
        registerForContextMenu(lv_comboBapNuoc);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_combobapnuoc, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_delete) {
            delete();
            return true;
        }else if(itemId == R.id.action_update){
            showUpdateDialog();
            return true;
        }
        else {
            return super.onContextItemSelected(item);
        }
    }
    private void checkNull(){
        EditText edt_name = findViewById(R.id.edt_fandb_name);
        EditText edt_detail = findViewById(R.id.edt_fandb_detail);
        EditText edt_money = findViewById(R.id.edt_fandb_money);
        TextView tv_numberItems = findViewById(R.id.tv_numberItems);
        // Kiểm tra các trường có rỗng không
        if (edt_name.getText().toString().trim().isEmpty()) {
            Toast.makeText(AdminPopcornDrinkActivity.this, "Tên combo không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edt_detail.getText().toString().trim().isEmpty()) {
            Toast.makeText(AdminPopcornDrinkActivity.this, "Mô tả combo không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edt_money.getText().toString().trim().isEmpty()) {
            Toast.makeText(AdminPopcornDrinkActivity.this, "Giá combo không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tv_numberItems.getText().toString().trim().isEmpty()) {
            Toast.makeText(AdminPopcornDrinkActivity.this, "Số lượng combo không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private void delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminPopcornDrinkActivity.this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa combo này?");

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comboBapNuocDAO.delete(comboBapNuocSelected.getMaCombo());
                listCombo.clear();
                listCombo.addAll(comboBapNuocDAO.findAllCombo());
                adminComboBapNuocAdapter.notifyDataSetChanged();
                comboBapNuocSelected = null;
                adminComboBapNuocAdapter();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void update() {
        EditText edt_name = findViewById(R.id.edt_fandb_name);
        EditText edt_detail = findViewById(R.id.edt_fandb_detail);
        EditText edt_money = findViewById(R.id.edt_fandb_money);
        TextView tv_numberItems = findViewById(R.id.tv_numberItems);
        ImageView img_picture = findViewById(R.id.fandb_image);

        // Cập nhật thông tin sau khi người dùng chỉnh sửa
        comboBapNuocSelected.setTenCombo(edt_name.getText().toString());
        comboBapNuocSelected.setMoTa(edt_detail.getText().toString());
        comboBapNuocSelected.setGia(Float.parseFloat(edt_money.getText().toString().replace("đ", "").trim()));
        comboBapNuocSelected.setSoLuong(Integer.parseInt(tv_numberItems.getText().toString()));

        if (img_picture.getDrawable() != null) {
            BitmapDrawable drawable = (BitmapDrawable) img_picture.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            comboBapNuocSelected.setHinhAnh(bitmapToByteArray(bitmap));
        }
        // Gọi hàm update để cập nhật thông tin
        boolean isUpdated = comboBapNuocDAO.update(comboBapNuocSelected);
        adminComboBapNuocAdapter();
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                Uri imageUri = data.getData(); // Lấy URI của ảnh đã chọn
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                // Cập nhật ImageView
                ImageView fandb_image = findViewById(R.id.fandb_image);
                fandb_image.setImageBitmap(bitmap); // Cập nhật ảnh

                if (comboBapNuoc != null) {
                    comboBapNuoc.setHinhAnh(bitmapToByteArray(bitmap)); // Chuyển Bitmap sang mảng byte
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Chuyển Bitmap thành mảng byte
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        return baos.toByteArray();
    }

    // Hàm gọi để chọn ảnh từ thư viện
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST); // Mở thư viện ảnh
    }
    private void showUpdateDialog() {
        if (comboBapNuocSelected != null) {
            // Hiển thị nút sửa khi comboBapNuocSelected không phải là null
            btn_sua = findViewById(R.id.btn_sua);
            btn_sua.setVisibility(View.VISIBLE); // Hiển thị nút sửa
            TextView tv_minus = findViewById(R.id.tv_minus);
            TextView tv_plus = findViewById(R.id.tv_plus);
            TextView tv_numberItems = findViewById(R.id.tv_numberItems);
            // Lấy số lượng hiện tại từ comboBapNuocSelected
            final int[] currentQuantity = {comboBapNuocSelected.getSoLuong()};

            // Khi người dùng nhấn nút tăng
            tv_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentQuantity[0]++; // Tăng số lượng
                    tv_numberItems.setText(String.valueOf(currentQuantity[0])); // Cập nhật TextView
                }
            });

            // Khi người dùng nhấn nút giảm
            tv_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentQuantity[0] > 0) {
                        currentQuantity[0]--; // Giảm số lượng
                        tv_numberItems.setText(String.valueOf(currentQuantity[0])); // Cập nhật TextView
                    }
                }
            });

            EditText edt_name = findViewById(R.id.edt_fandb_name);
            EditText edt_detail = findViewById(R.id.edt_fandb_detail);
            EditText edt_money = findViewById(R.id.edt_fandb_money);
//            TextView tv_numberItems = findViewById(R.id.tv_numberItems);
            ImageView img_picture = findViewById(R.id.fandb_image);

            edt_name.setText(comboBapNuocSelected.getTenCombo());
            edt_detail.setText(comboBapNuocSelected.getMoTa());
            edt_money.setText(String.valueOf(comboBapNuocSelected.getGia()));
            tv_numberItems.setText(String.valueOf(comboBapNuocSelected.getSoLuong()));

            if (comboBapNuocSelected.getHinhAnh() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(comboBapNuocSelected.getHinhAnh(), 0, comboBapNuocSelected.getHinhAnh().length);
                img_picture.setImageBitmap(bitmap);
            } else {
                img_picture.setImageResource(R.drawable.corn); // Hình ảnh mặc định
            }
        } else {
            // Ẩn nút sửa nếu comboBapNuocSelected là null
            btn_sua.setVisibility(View.INVISIBLE);
        }
    }

}
