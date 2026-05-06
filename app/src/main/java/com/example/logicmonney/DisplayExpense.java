package com.example.logicmonney;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DisplayExpense extends AppCompatActivity
        implements ExpenseAdapter.OnItemLongClick {

    RecyclerView rvExpense;
    DatabaseHelper db;
    ExpenseAdapter adapter;
    ArrayList<ExpenseModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_expense);

        // Back action
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        rvExpense = findViewById(R.id.rvExpense);
        db = new DatabaseHelper(this);

        rvExpense.setLayoutManager(new LinearLayoutManager(this));
        loadExpenses();
    }

    private void loadExpenses() {
        list = new ArrayList<>();

        // Read from expense table
        list = db.getAllExpenses();
        adapter = new ExpenseAdapter(list,this);
        rvExpense.setAdapter(adapter);
    }

    @Override
    public void onItemLongClicked(int expenseId) {

        boolean deleted = db.deleteExpense(expenseId);
        if (deleted) {
            Toast.makeText(this,
                    "Deleted id=" + expenseId,
                    Toast.LENGTH_SHORT).show();
            loadExpenses(); // refresh
        } else {
            Toast.makeText(this,
                    "Delete failed id=" + expenseId,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
