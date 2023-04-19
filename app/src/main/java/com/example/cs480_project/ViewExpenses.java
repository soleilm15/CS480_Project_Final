package com.example.cs480_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ViewExpenses extends AppCompatActivity {
    private ListView expensesListView;
    private List<Expense> expensesList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        expensesListView = findViewById(R.id.expensesListView);
        expensesList = new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        ExpenseTrackerDatabaseHelper dbHelper = new ExpenseTrackerDatabaseHelper(this);
        expensesList = dbHelper.selectExpenses();

        ArrayAdapter<Expense> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expensesList);
        expensesListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        String listItem;

        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(ViewExpenses.this, AddingExpenses.class);
                startActivity(intent);
                return true;
            case R.id.delete:
                // delete expense
                return true;
            case R.id.edit:
                // open addingExpenses activity with values of selected expense
                return true;
            case R.id.save:
                saveList();
                return true;
            case R.id.exit:
                saveList();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void saveList() {
        try {
            File file = new File(getFilesDir(), "list.txt");
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            for (Expense expense : expensesList) {
                osw.write(expense + "\n");
            }
            osw.close();
            fos.close();

            Toast.makeText(this, "List saved successfully!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: List saved unsuccessfully.", Toast.LENGTH_LONG).show();
        }
    }

}
