package com.example.logicmonney;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logicmonney.databinding.ActivitySignupBinding;

public class SignUp_activity extends AppCompatActivity {

     ActivitySignupBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // ✅ ViewBinding initialization
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        // ---------- SIGN UP ----------
        binding.btnSignup.setOnClickListener(v -> {

            String fullname = binding.FullName.getText().toString().trim();
            String email = binding.Email.getText().toString().trim();
            String password = binding.Password.getText().toString().trim();

            if (fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (databaseHelper.checkEmail(email)) {
                Toast.makeText(this, "Email already exists. Please login.", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = databaseHelper.addSignup(fullname, email, password);

            if (success) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });

        // ---------- LINK TO LOGIN ----------
        binding.LinkToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
