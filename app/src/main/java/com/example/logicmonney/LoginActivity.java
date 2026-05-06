package com.example.logicmonney;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.logicmonney.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.Email.getText().toString();
                String password = binding.Password.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "All fields are mandatory",
                            Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkCredentials =
                            databaseHelper.checkEmailPassword(email, password);

                    if (checkCredentials) {
                        Toast.makeText(LoginActivity.this,
                                "Login Successfully!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent =
                                new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Invalid Email or Password",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.LinkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUp_activity.class));
            }
        });
    }
}
