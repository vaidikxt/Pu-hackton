package com.example.yourheathhero;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 3 seconds

    private FirebaseAuth mAuth;
    private LottieAnimationView splashAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        splashAnimation = findViewById(R.id.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser() != null) {
                    // User is signed in, redirect to the home screen or main functionality
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));  // Replace HomeActivity with your target activity
                } else {
                    // No user is signed in, redirect to login screen
                    startActivity(new Intent(MainActivity.this, login.class));
                }
                finish();
            }
        }, SPLASH_DURATION);
    }
}
