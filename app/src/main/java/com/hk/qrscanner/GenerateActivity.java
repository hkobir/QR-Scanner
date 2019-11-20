package com.hk.qrscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;

import android.os.Bundle;
import android.util.Log;

import android.view.View;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.hk.qrscanner.databinding.ActivityGenerateBinding;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class GenerateActivity extends AppCompatActivity {
    private ActivityGenerateBinding binding;
    private Bitmap bitmap;

    private String TAG = "generateQRCode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_generate);

        binding.generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.textET.getText().toString().equals("") && binding.textET.getText().toString().length() > 0) {

                    generateCode(binding.textET.getText().toString());

                } else {
                    binding.generateButton.setError("Enter some text");
                    binding.generateButton.requestFocus();
                }
            }
        });

    }

    private void generateCode(String text) {

        MultiFormatWriter formatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = formatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            binding.qrImage.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }
}
