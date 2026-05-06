package com.example.logicmonney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddExpense extends AppCompatActivity {

    EditText idEntry, expenseEntry, dateEntry, commentEntry;
    Button btnSave, btnUpdate, btnDelete;
    ImageView folderDisplay;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Initialize Database
        db = new DatabaseHelper(this);

        // Bind Views
        idEntry = findViewById(R.id.idEntry);
        expenseEntry = findViewById(R.id.expenseEntry);
        dateEntry = findViewById(R.id.dateEntry);
        commentEntry = findViewById(R.id.commentEntry);

        btnSave = findViewById(R.id.btnSave);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        folderDisplay = findViewById(R.id.folderDisplay);

        // Open DisplayExpense Activity
        folderDisplay.setOnClickListener(v ->
                startActivity(new Intent(AddExpense.this, DisplayExpense.class))
        );

        // Save Income
        btnSave.setOnClickListener(v -> {
            String amount = expenseEntry.getText().toString().trim();
            String date = dateEntry.getText().toString().trim();
            String note = commentEntry.getText().toString().trim();

            if (amount.isEmpty() || date.isEmpty()) {
                Toast.makeText(
                        AddExpense.this,
                        "Amount and Date are required",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            // ID is auto-increment (no need to input)
            boolean success = db.insertIncome(amount, date, note);

            if (success) {
                Toast.makeText(
                        AddExpense.this,
                        "Income saved successfully",
                        Toast.LENGTH_SHORT
                ).show();
                clearFields();
            } else {
                Toast.makeText(
                        AddExpense.this,
                        "Failed to save income",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        /// delete

        btnDelete.setOnClickListener(v -> {
            String idStr = idEntry.getText().toString().trim();

            // Validate ID
            if (idStr.isEmpty()) {
                idEntry.setError("Enter ID to delete");
                return;
            }

            // Convert ID
            int id = Integer.parseInt(idStr);

            // Delete EXPENSE from database
            db.deleteExpense(id);

            // Clear inputs
            idEntry.setText("");
            expenseEntry.setText("");
            dateEntry.setText("");
            commentEntry.setText("");
        });


        /// update

        btnUpdate.setOnClickListener(v -> {
            String idStr = idEntry.getText().toString().trim();
            String income = expenseEntry.getText().toString().trim();
            String date = dateEntry.getText().toString().trim();
            String note = commentEntry.getText().toString().trim();

            // Validate ID
            if (idStr.isEmpty()) {
                idEntry.setError("Enter ID to update");
                return;
            }

            // Validate other fields
            if (income.isEmpty()) {
                expenseEntry.setError("Enter income amount");
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
            expenseEntry.setText("");
            dateEntry.setText("");
            commentEntry.setText("");
        });

        // Delete (not implemented yet)
        btnDelete.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "Delete feature not implemented",
                        Toast.LENGTH_SHORT).show()
        );
    }
    private void clearFields() {
        idEntry.setText("");
        expenseEntry.setText("");
        dateEntry.setText("");
        commentEntry.setText("");
    }
}
