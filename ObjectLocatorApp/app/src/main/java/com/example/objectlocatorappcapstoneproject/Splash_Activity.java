package com.example.objectlocatorappcapstoneproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash_Activity extends AppCompatActivity {
ImageView logo;
TextView title,team;
Animation a,b,c;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        title = findViewById(R.id.title);
        team = findViewById(R.id.team);

        a = AnimationUtils.loadAnimation(this,R.anim.splash);
        b = AnimationUtils.loadAnimation(this,R.anim.splash);
        c = AnimationUtils.loadAnimation(this,R.anim.splash);

        logo.setAnimation(a);
        title.setAnimation(b);
        team.setAnimation(c);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash_Activity.this,GuideScreen.class));
                finish();
            }
        },3000);
    }
}