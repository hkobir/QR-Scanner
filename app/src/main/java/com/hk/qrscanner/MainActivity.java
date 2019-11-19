package com.hk.qrscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.hk.qrscanner.databinding.ActivityMainBinding;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;
private Bitmap bitmap;
private QRGEncoder encoder;
private String TAG = "generateQRCode";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.textET.getText().toString().equals("")&&binding.textET.getText().toString().length()>0){

                    generateCode(binding.textET.getText().toString());

                }
                else{
                    binding.generateButton.setError("Enter some text");
                    binding.generateButton.requestFocus();
                }
            }
        });

    }

    private void generateCode(String text) {

        //get screen dimention that are small
        WindowManager windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smmallerDimension = width>height?width:height;
        smmallerDimension*=3/4;

        //encoding
        encoder = new QRGEncoder(text,null, QRGContents.Type.TEXT,smmallerDimension);

        try {
            bitmap = encoder.encodeAsBitmap();
            binding.qrImage.setImageBitmap(bitmap);
        }
        catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
    }
}
