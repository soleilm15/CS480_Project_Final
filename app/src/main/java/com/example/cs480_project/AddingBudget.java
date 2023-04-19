package com.example.cs480_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddingBudget extends AppCompatActivity {

    private EditText editBudget;
    private Spinner chooseBudgetType, chooseDuration, chooseOnce;
    private Button closeButton;
    private ImageButton chooseBudgetDate;
    private TextView dateTextView, onceTextView;
    private CheckBox repeated, reminder;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_budget);

        // get references to text / edit text views
        editBudget = (EditText) findViewById(R.id.total_budget);
        onceTextView = (TextView) findViewById(R.id.onceText);
        dateTextView = findViewById(R.id.dateBudgetDisplay);

        // get references to spinners
        chooseBudgetType = (Spinner) findViewById(R.id.category_spinner);
        chooseDuration = (Spinner) findViewById(R.id.duration_spinner);
        chooseOnce = (Spinner) findViewById(R.id.once_spinner);

        // get references to buttons
        chooseBudgetDate = (ImageButton) findViewById(R.id.chooseBudgetDateButton);
        closeButton = (Button) findViewById(R.id.close_button);

        // get references to checkboxes
        repeated = (CheckBox) findViewById(R.id.repeated_checkbox);
        reminder = (CheckBox) findViewById(R.id.reminder_checkbox);

        //initialize calendar and date format
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        // create a DatePickerDialog with the current date as the default
        chooseBudgetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddingBudget.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String dateString = dateFormat.format(calendar.getTime());
                        chooseBudgetDate.setContentDescription(dateString);

                        dateTextView.setText(dateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Set up the spinner adapter for budget types
        ArrayAdapter<CharSequence> adapterBudgetTypes = ArrayAdapter.createFromResource(
                this, R.array.budget_types, android.R.layout.simple_spinner_item);
        adapterBudgetTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseBudgetType.setAdapter(adapterBudgetTypes);

        // Set up the spinner adapter for budget duration
        ArrayAdapter<CharSequence> adapterDuration = ArrayAdapter.createFromResource(
                this, R.array.budget_duration, android.R.layout.simple_spinner_item);
        adapterDuration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseDuration.setAdapter(adapterDuration);

        // Set up the spinner adapter for the "once" dropdown
        ArrayAdapter<CharSequence> adapterOnce = ArrayAdapter.createFromResource(
                this, R.array.once_dropdown, android.R.layout.simple_spinner_item);
        adapterOnce.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseOnce.setAdapter(adapterOnce);

        //set button to close activity
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //sets visibility to gone so that these don't appear when the reminder is not checked
        chooseOnce.setVisibility(View.GONE);
        onceTextView.setVisibility(View.GONE);

        //sets the checkbox so that the choose once dropdown appears if its checked
        reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chooseOnce.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                onceTextView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
    }
}