package com.example.cs480_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseTrackerDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "expense_tracker.db";
    private static final int DATABASE_VERSION = 1;

    // Expense Table
    private static final String TABLE_EXPENSE = "expense";
    private static final String EXPENSE_ID = "id";
    private static final String EXPENSE_AMOUNT = "amount";
    private static final String EXPENSE_CATEGORY = "category";
    private static final String EXPENSE_DATE = "date";
    private static final String EXPENSE_DESCRIPTION = "description";
    private static final String EXPENSE_RECEIPT = "receipt_image";
    private static final String EXPENSE_BUDGET_ID = "budget_id";

    // Budget Table
    private static final String TABLE_BUDGET = "budget";
    private static final String BUDGET_ID = "id";
    private static final String BUDGET_AMOUNT = "amount";
    private static final String BUDGET_CATEGORY = "category";
    private static final String BUDGET_START_DATE = "start_date";
    private static final String BUDGET_END_DATE = "end_date";

    // for queries
    private Cursor cursor;

    // create tables
    public static final String CREATE_TABLE_BUDGET =
            "CREATE TABLE " + TABLE_BUDGET + "("
                    + BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + BUDGET_AMOUNT + " REAL,"
                    + BUDGET_CATEGORY + " TEXT,"
                    + BUDGET_START_DATE + " DATE,"
                    + BUDGET_END_DATE + " DATE"
                    + ")";

    public static final String CREATE_TABLE_EXPENSE =
            "CREATE TABLE " + TABLE_EXPENSE + "("
                    + EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + EXPENSE_AMOUNT + " REAL,"
                    + EXPENSE_CATEGORY + " TEXT,"
                    + EXPENSE_DATE + " DATE,"
                    + EXPENSE_DESCRIPTION + " TEXT,"
                    + EXPENSE_RECEIPT + " BLOB,"
                    + EXPENSE_BUDGET_ID + " INTEGER,"
                    + "FOREIGN KEY(" + EXPENSE_BUDGET_ID + ") REFERENCES " + TABLE_BUDGET + "(" + BUDGET_ID + ")"
                    + ")";

    public ExpenseTrackerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BUDGET);
        db.execSQL(CREATE_TABLE_EXPENSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i >= i1) return;

        Log.d("SQLiteDemo", "onUpgrade: Version = " + i1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        onCreate(db);
    }

    public void dropTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS" + tableName);
        onCreate(db);
    }

    // ADD, UPDATE, DELETE BUDGET
    public long addBudget(@NonNull Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BUDGET_AMOUNT, budget.getAmount());
        values.put(BUDGET_CATEGORY, budget.getCategory());
        values.put(BUDGET_START_DATE, budget.getStartDate().getTime());
        values.put(BUDGET_END_DATE, budget.getEndDate().getTime());
        long id = db.insert(TABLE_BUDGET, null, values);
        db.close();
        return id;
    }

    public void updateBudget(int budgetId, double amount, String category, String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BUDGET_AMOUNT, amount);
        values.put(BUDGET_CATEGORY, category);
        values.put(BUDGET_START_DATE, startDate);
        values.put(BUDGET_END_DATE, endDate);
        db.update(TABLE_BUDGET, values, BUDGET_ID + " = ?", new String[] {String.valueOf(budgetId)});
        db.close();
    }

    public void deleteBudget(int budgetId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUDGET, BUDGET_ID + " = ?", new String[] {String.valueOf(budgetId)});
        db.close();
    }

    // ADD, UPDATE, DELETE EXPENSE
    public long addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXPENSE_AMOUNT, expense.getAmount());
        values.put(EXPENSE_CATEGORY, expense.getCategory());
        values.put(EXPENSE_DATE, expense.getDate());
        values.put(EXPENSE_DESCRIPTION, expense.getDescription());
        values.put(EXPENSE_RECEIPT, expense.getReceiptImage());
        values.put(EXPENSE_BUDGET_ID, expense.getBudgetId());
        long id = db.insert(TABLE_EXPENSE, null, values);
        db.close();
        return id;
    }

    public void updateExpense(int expenseId, String category, double amount, String date, String description, byte[] receiptImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXPENSE_CATEGORY, category);
        values.put(EXPENSE_AMOUNT, amount);
        values.put(EXPENSE_DATE, date);
        values.put(EXPENSE_DESCRIPTION, description);
        values.put(EXPENSE_RECEIPT, receiptImage);
        db.update(TABLE_EXPENSE, values, EXPENSE_ID + " = ?", new String[] {String.valueOf(expenseId)});
        db.close();
    }

    public void deleteExpense(int expenseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSE, EXPENSE_ID + " = ?", new String[] {String.valueOf(expenseId)});
        db.close();
    }

    // FUNCTIONS FOR MAIN ACTIVITY
    public List<Expense> selectExpenses() {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {EXPENSE_ID, EXPENSE_AMOUNT, EXPENSE_CATEGORY, EXPENSE_DATE, EXPENSE_DESCRIPTION, EXPENSE_RECEIPT, EXPENSE_BUDGET_ID};
        Cursor cursor = db.query(TABLE_EXPENSE, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(EXPENSE_ID));
            double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(EXPENSE_AMOUNT));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_CATEGORY));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_DATE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(EXPENSE_DESCRIPTION));
            byte[] receiptImage = cursor.getBlob(cursor.getColumnIndexOrThrow(EXPENSE_RECEIPT));
            int budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(EXPENSE_BUDGET_ID));
            expenses.add(new Expense(id, amount, category, date, description, receiptImage, budgetId));
        }
        cursor.close();
        db.close();
        return expenses;
    }


    public List<Expense> getExpensesByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Expense> expensesByCategory = new ArrayList<>();

        String[] columns = {"id", "amount", "category", "date", "description", "receipt_image", "budget_id"};
        String selection = "category = ?";
        String[] selectionArgs = {category};

        Cursor cursor = db.query("expenses", columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
            String categoryStr = cursor.getString(cursor.getColumnIndexOrThrow("category"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            byte[] receiptImage = cursor.getBlob(cursor.getColumnIndexOrThrow("receipt_image"));
            int budgetId = cursor.getInt(cursor.getColumnIndexOrThrow("budget_id"));

            Expense expense = new Expense(id, amount, categoryStr, date, description, receiptImage, budgetId);
            expensesByCategory.add(expense);
        }
        cursor.close();

        return expensesByCategory;
    }

    public Map<String, Double> getCategoryExpensePercentage() {
        SQLiteDatabase db = this.getReadableDatabase();

        // get total expense amount
        cursor = db.rawQuery("SELECT SUM(" +EXPENSE_AMOUNT + ") FROM " + TABLE_EXPENSE, null);
        cursor.moveToFirst();
        double totalExpenseAmount = cursor.getDouble(0);
        cursor.close();

        // get expense amount for each category
        cursor = db.rawQuery("SELECT " + EXPENSE_CATEGORY + ", SUM(" + EXPENSE_AMOUNT + ") FROM "
                + TABLE_EXPENSE + " GROUP BY " + EXPENSE_CATEGORY, null);

        // calculate percentage and map
        Map<String, Double> categoryExpensePercentage = new HashMap<>();
        while (cursor.moveToNext()) {
            String category = cursor.getString(0);
            double expenseAmount = cursor.getDouble(1);
            double percentage = (expenseAmount / totalExpenseAmount) * 100;
            categoryExpensePercentage.put(category, percentage);
        }
        cursor.close();
        db.close();
        return categoryExpensePercentage;
    }

    // text to speech/notification of adding, updating, or deleting expense/budget
    // finish menu bar with functionality

    // MAIN ACTIVITY
    // spinner to choose from the current budgets created
    // total current expenses textBox, total current budget amount textBox
    // pie chart of percentage of all expenses, colored by type

    // button to display a total list of all recent expenses
    // filter by: category
    // order by: amount, date
    // learn how to display list of expenses given a sql statement

    // themes
}
