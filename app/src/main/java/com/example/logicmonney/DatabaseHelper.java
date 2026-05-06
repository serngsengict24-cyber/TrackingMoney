package com.example.logicmonney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LogicMoneyDB.db";
    private static final int DATABASE_VERSION = 3;

    // ===== SIGNUP TABLE =====
    private static final String TABLE_SIGNUP = "signup";
    private static final String COL_SIGNUP_ID = "id";
    private static final String COL_FULLNAME = "fullname";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";

    // ===== EXPENSE TABLE =====
    private static final String TABLE_EXPENSE = "expense";
    private static final String COL_EXP_ID = "id";
    private static final String COL_EXP_AMOUNT = "amount";
    private static final String COL_EXP_DATE = "date";
    private static final String COL_EXP_NOTE = "note";

    // ===== INCOME TABLE =====
    private static final String TABLE_INCOME = "income";
    private static final String COL_INC_ID = "id";
    private static final String COL_INC_AMOUNT = "amount";
    private static final String COL_INC_DATE = "date";
    private static final String COL_INC_NOTE = "note";

    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // ===== CREATE TABLES =====
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Signup
        db.execSQL("CREATE TABLE " + TABLE_SIGNUP + " (" +
                COL_SIGNUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FULLNAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT)");

        // Expense
        db.execSQL("CREATE TABLE " + TABLE_EXPENSE + " (" +
                COL_EXP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EXP_AMOUNT + " INTEGER, " +
                COL_EXP_DATE + " TEXT, " +
                COL_EXP_NOTE + " TEXT)");

        // Income
        db.execSQL("CREATE TABLE " + TABLE_INCOME + " (" +
                COL_INC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_INC_AMOUNT + " INTEGER, " +
                COL_INC_DATE + " TEXT, " +
                COL_INC_NOTE + " TEXT)");
    }

    // ===== UPGRADE =====
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGNUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        onCreate(db);
    }

    // ================= SIGNUP =========================
    public boolean addSignup(String fullName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_FULLNAME, fullName);
        cv.put(COL_EMAIL, email);
        cv.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_SIGNUP, null, cv);

        if (result != -1) {
            Toast.makeText(context, "Signup successful", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Signup failed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_SIGNUP + " WHERE " + COL_EMAIL + " = ?",
                new String[]{email}
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_SIGNUP +
                        " WHERE " + COL_EMAIL + " = ? AND " + COL_PASSWORD + " = ?",
                new String[]{email, password}
        );
        boolean success = cursor.getCount() > 0;
        cursor.close();
        return success;
    }

    // ================= EXPENSE ========================
    public boolean insertExpense(String amount, String date, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_EXP_AMOUNT, amount);
        cv.put(COL_EXP_DATE, date);
        cv.put(COL_EXP_NOTE, note);

        return db.insert(TABLE_EXPENSE, null, cv) != -1;
    }

    public boolean updateIncome(int id, String amount, String date,String note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_EXP_AMOUNT, amount);
        cv.put(COL_EXP_DATE, date);
        cv.put(COL_EXP_NOTE, note);

        int rows = db.update(TABLE_INCOME, cv, COL_INC_ID + "=?", new
                String[]{String.valueOf(id)});
        if (rows > 0) {
            Toast.makeText(context, "Successfully Updated",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Update Failed (id=" + id + " not found)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public boolean deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EXPENSE, COL_EXP_ID + " = ?",
                new String[]{String.valueOf(id)}) > 0;
    }

    public ArrayList<ExpenseModel> getAllExpenses() {
        ArrayList<ExpenseModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_EXPENSE + " ORDER BY " + COL_EXP_ID + " DESC", null
        );

        if (cursor.moveToFirst()) {
            do {
                ExpenseModel model = new ExpenseModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                list.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // ================= INCOME =========================
    public boolean insertIncome(String amount, String date, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_INC_AMOUNT, amount);
        cv.put(COL_INC_DATE, date);
        cv.put(COL_INC_NOTE, note);

        return db.insert(TABLE_INCOME, null, cv) != -1;
    }


    public boolean updateExpense(int id, String amount, String date,String note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_EXP_AMOUNT, amount);
        cv.put(COL_EXP_DATE, date);
        cv.put(COL_EXP_NOTE, note);

        int rows = db.update(TABLE_EXPENSE, cv, COL_EXP_ID + "=?", new
                String[]{String.valueOf(id)});
        if (rows > 0) {
            Toast.makeText(context, "Successfully Updated",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Update Failed (id=" + id + " not found)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean deleteIncome(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_INCOME, COL_INC_ID + " = ?",
                new String[]{String.valueOf(id)}) > 0;
    }

    public ArrayList<IncomeModel> getAllIncome() {
        ArrayList<IncomeModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_INCOME + " ORDER BY " + COL_INC_ID + " DESC", null
        );

        if (cursor.moveToFirst()) {
            do {
                IncomeModel model = new IncomeModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                list.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
