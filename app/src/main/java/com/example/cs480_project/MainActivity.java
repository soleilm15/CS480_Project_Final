package com.example.cs480_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.AbstractCollection;

public class MainActivity extends AppCompatActivity {

    private Button addExpense;
    private Button addBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Uri uri;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addExpense:
                Intent intent = new Intent(MainActivity.this, AddingExpenses.class);
                startActivity(intent);
                return true;

            case R.id.addBudget:
                intent = new Intent(MainActivity.this, AddingBudget.class);
                startActivity(intent);
                return true;

            case R.id.viewExpenses:
                intent = new Intent(MainActivity.this, ViewExpenses.class);
                startActivity(intent);
                return true;

            case R.id.viewBudgets:
                intent = new Intent(MainActivity.this, ViewBudgets.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
