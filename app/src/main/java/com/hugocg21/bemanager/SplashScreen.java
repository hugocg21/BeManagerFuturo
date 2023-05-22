package com.hugocg21.bemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hugocg21.bemanager.Login.Login;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("datos", MODE_PRIVATE);
        boolean recordar = sharedPreferences.getBoolean("recordar", false);
        Log.i("Estado de sesion", String.valueOf(recordar));

        ImageView splashImage = findViewById(R.id.imageViewlogoSplashScreen);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_animation);
        splashImage.startAnimation(animation);

        if (recordar) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    finish();
                }
            }, 4000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
            }, 4000);
        }
    }
}