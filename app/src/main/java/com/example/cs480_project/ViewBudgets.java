package com.example.cs480_project;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ViewBudgets extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_budgets);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        // LIST VIEW OF ALL CURRENT BUDGETS
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
                Intent intent = new Intent(ViewBudgets.this, AddingBudget.class);
                startActivity(intent);
                return true;
            case R.id.delete:
                // delete budget
                return true;
            case R.id.edit:
                // open addingBudget activity with values of selected budget
                return true;
            case R.id.save:
                // saveList();
                return true;
            case R.id.exit:
                // saveList();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    private void saveList() {
        try {
            File file = new File(getFilesDir(), "list.txt");
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            for (String item : items) {
                osw.write(item + "\n");
            }
            osw.close();
            fos.close();

            Toast.makeText(this, "List saved successfully!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: List saved unsuccessfully.", Toast.LENGTH_LONG).show();
        }
    }
     */
}