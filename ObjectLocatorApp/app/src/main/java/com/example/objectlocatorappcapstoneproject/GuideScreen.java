package com.example.objectlocatorappcapstoneproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GuideScreen extends AppCompatActivity {
AppCompatButton getStar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guide_screen);
        getStar = findViewById(R.id.getStar);

        getStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent beg = new Intent(GuideScreen.this,MainActivity.class);
                startActivity(beg);
            }
        });
    }
}