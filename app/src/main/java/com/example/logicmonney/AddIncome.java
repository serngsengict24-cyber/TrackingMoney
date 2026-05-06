package com.example.logicmonney;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddIncome extends AppCompatActivity {

    EditText idEntry, incomeEntry, dateEntry, commentEntry;
    Button btnSave, btnUpdate, btnDelete;
    ImageView folderDisplay;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Database
        db = new DatabaseHelper(this);

        // Bind views
        idEntry = findViewById(R.id.idEntry);
        incomeEntry = findViewById(R.id.incomeEntry);
        dateEntry = findViewById(R.id.dateEntry);
        commentEntry = findViewById(R.id.commentEntry);

        btnSave = findViewById(R.id.btnSave);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        folderDisplay = findViewById(R.id.folderDisplay);
        folderDisplay.setOnClickListener(v ->
                startActivity(new Intent(AddIncome.this, displayIncome.class))
        );

        // ===== SAVE =====
        btnSave.setOnClickListener(v -> {
            String amount = incomeEntry.getText().toString().trim();
            String date = dateEntry.getText().toString().trim();
            String note = commentEntry.getText().toString().trim();

            if (amount.isEmpty() || date.isEmpty()) {
                Toast.makeText(this,
                        "Amount and Date are required",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = db.insertIncome(amount, date, note);

            if (success) {
                Toast.makeText(this,
                        "Income saved successfully",
                        Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this,
                        "Failed to save income",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(v -> {
            String idStr = idEntry.getText().toString().trim();
            String income = incomeEntry.getText().toString().trim();
            String date = dateEntry.getText().toString().trim();
            String note = commentEntry.getText().toString().trim();

            // Validate ID
            if (idStr.isEmpty()) {
                idEntry.setError("Enter ID to update");
                return;
            }

            // Validate other fields
            if (income.isEmpty()) {
                incomeEntry.setError("Enter income amount");
                return;
            }

            if (date.isEmpty()) {
                dateEntry.setError("Enter date");
                return;
            }

            if (note.isEmpty()) {
                commentEntry.setError("Enter comment");
                return;
            }

            // Convert ID
            int id = Integer.parseInt(idStr);

            // Update database
            db.updateIncome(id, income, date, note);

            // Clear inputs
            idEntry.setText("");
            incomeEntry.setText("");
            dateEntry.setText("");
            commentEntry.setText("");
        });


        // ===== DELETE =====
        btnDelete.setOnClickListener(v -> {
            String idStr = idEntry.getText().toString().trim();

            // Validate ID
            if (idStr.isEmpty()) {
                idEntry.setError("Enter ID to delete");
                return;
            }

            // Convert ID
            int id = Integer.parseInt(idStr);

            // Delete from database
            db.deleteIncome(id);

            // Clear inputs
            idEntry.setText("");
            incomeEntry.setText("");
            dateEntry.setText("");
            commentEntry.setText("");
        });

    }

    private void clearFields() {
        idEntry.setText("");
        incomeEntry.setText("");
        dateEntry.setText("");
        commentEntry.setText("");
    }
}
