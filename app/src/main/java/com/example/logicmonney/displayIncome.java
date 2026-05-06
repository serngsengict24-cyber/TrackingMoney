package com.example.logicmonney;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class displayIncome extends AppCompatActivity {

    RecyclerView rvIncome;
    DatabaseHelper db;
    ArrayList<IncomeModel> incomeList;
    IncomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_income);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Database
        db = new DatabaseHelper(this);

        // Load Income Data
        incomeList = db.getAllIncome(); // Use DatabaseHelper method

        // RecyclerView
        rvIncome = findViewById(R.id.rvIncome);
        rvIncome.setLayoutManager(new LinearLayoutManager(this)); // Context only

        // Adapter
        adapter = new IncomeAdapter(incomeList);
        rvIncome.setAdapter(adapter);
    }
}
