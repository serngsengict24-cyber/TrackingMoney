package com.example.logicmonney;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Show splash for 2 seconds
        new Handler().postDelayed(() -> {
            startActivity(new Intent(splash.this,
                    LoginActivity.class));
            finish();
        }, 2000);
    }
}
