package com.example.doancuoiky.activity.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class HoaDonActivity extends AppCompatActivity {

    ImageView img_qr_code;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_ticket_online);

        mappingControl();
        getDataOnLayout();

    }

    private void mappingControl() {
        img_qr_code = findViewById(R.id.qr_code);
    }
    private void getDataOnLayout(){
        MultiFormatWriter multiFormatWriter =  new MultiFormatWriter();
        try {
            BitMatrix bitMatrix =  multiFormatWriter.encode("MHD013", BarcodeFormat.QR_CODE,  200,  200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap =  barcodeEncoder.createBitmap(bitMatrix);
            img_qr_code.setImageBitmap(bitmap);
        }catch (WriterException e){
            throw new RuntimeException(e);
        }

    }
}
