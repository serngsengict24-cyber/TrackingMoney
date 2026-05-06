package com.example.logicmonney;

public class ExpenseModel {

    int id;
    String amount;
    String date;
    String note;

    public ExpenseModel(int id, String amount, String date, String note) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }

    public int getId() { return id; }
    public String getAmount() { return amount; }
    public String getDate() { return date; }
    public String getNote() { return note; }
}
