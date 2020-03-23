package com.example.privatechat.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.privatechat.R;

public class PictureViewerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");


        Bitmap bitmap = BitmapFactory.decodeFile(path);

        ImageView imageView = (ImageView) findViewById(R.id.picture_imageview);
        imageView.setImageBitmap(bitmap);

    }
}
