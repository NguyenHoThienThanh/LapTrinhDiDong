package com.example.doancuoiky.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DB_Movie.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    private final Context context;

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Không cần thực hiện gì trong phương thức này vì chúng ta đã có sẵn cơ sở dữ liệu.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Không cần thực hiện gì trong phương thức này vì chúng ta không cần cập nhật cơ sở dữ liệu.
    }

    public void processCopy() {
        File dbFile = context.getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            // Nếu file cơ sở dữ liệu không tồn tại, sao chép từ tệp assets
            try {
                copyDatabaseFromAssets();
            } catch (IOException e) {
                // Xử lý ngoại lệ nếu có lỗi khi sao chép
                Toast.makeText(context, "Error copying database: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            try {
                copyDatabaseFromAssets();
            } catch (IOException e) {
                // Xử lý ngoại lệ nếu có lỗi khi ghi đè
                Toast.makeText(context, "Error updating database: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void copyDatabaseFromAssets() throws IOException {
        try {
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outFileName = getDatabasePath();
            Path outputPath = Paths.get(outFileName);
            File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) {
                f.mkdir();
            }

            OutputStream myOutput = Files.newOutputStream(outputPath);
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DB_NAME;
    }
}



