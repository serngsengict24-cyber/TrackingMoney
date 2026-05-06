package com.example.logicmonney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.NullPointerException;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Button income, expenseBtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        income = findViewById(R.id.btnIncome);
        expenseBtn = findViewById(R.id.expenseBtn);

        income.setOnClickListener(v -> {
            Toast.makeText(this, "Income clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, AddIncome.class));
        });

        expenseBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddExpense.class))
        );

    }
}