package com.example.cs480_project;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddingExpenses extends AppCompatActivity {

    private EditText editAmount, editDesc;
    private Spinner chooseType;
    private Button addReceipt, saveButton, cancelButton;
    private ImageButton chooseDate;
    private TextView dateTextView;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_expenses);

        ExpenseTrackerDatabaseHelper dbHelper = new ExpenseTrackerDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // get references to edit text views
        editAmount = (EditText) findViewById(R.id.editAmount);
        editDesc = (EditText) findViewById(R.id.editDesc);
        dateTextView = findViewById(R.id.dateDisplay);

        // references to spinners
        chooseType = (Spinner) findViewById(R.id.chooseType);

        // get references to buttons
        addReceipt = (Button) findViewById(R.id.addReceipt);
        saveButton = (Button) findViewById(R.id.save_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        chooseDate = (ImageButton) findViewById(R.id.chooseDateButton);

        //initialize calendar and date format
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        // Create a DatePickerDialog with the current date as the default
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddingExpenses.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String dateString = dateFormat.format(calendar.getTime());
                        chooseDate.setContentDescription(dateString);

                        dateTextView.setText(dateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Set up the spinner adapter for expense types
        ArrayAdapter<CharSequence> adapterTypes = ArrayAdapter.createFromResource(
                this, R.array.expense_types, android.R.layout.simple_spinner_item);
        adapterTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseType.setAdapter(adapterTypes);


        //set button to cancel activity
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //set button to save expense
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(editAmount.getText().toString());
                String category = chooseType.getSelectedItem().toString();
                String date = dateTextView.getText().toString();
                String description = editDesc.getText().toString();
                byte [] receiptImage = null;
                int budgetId = Integer.parseInt(null);

                Expense expense = new Expense(amount, amount, category, date, description, receiptImage, budgetId);

                dbHelper.addExpense(expense);
            }
        });

        //set button to allow user to upload their receipt
        addReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // complete later

            }
        });
    }
}

