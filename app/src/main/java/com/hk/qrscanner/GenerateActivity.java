package com.hk.qrscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import android.view.View;
import android.widget.Toast;


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

        binding.saveToGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    // Save image to gallery
                    String savedImageURL = MediaStore.Images.Media.insertImage(  //for save obviously need write external storage permission
                            getContentResolver(),
                            bitmap,
                            "My Personal QRCode",
                            "My medical information"
                    );
                    // Parse the gallery image url to uri
                    Uri savedImageURI = Uri.parse(savedImageURL);

                    Toast.makeText(GenerateActivity.this, "save image to gallery: "+savedImageURI, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(GenerateActivity.this, "Generate the QR image first.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void generateCode(String text) {

        MultiFormatWriter formatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = formatWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            binding.qrImage.setImageBitmap(bitmap);
            binding.saveToGallery.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }
}
