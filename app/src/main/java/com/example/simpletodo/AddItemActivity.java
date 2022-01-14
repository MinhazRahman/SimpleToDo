package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Objects;

public class AddItemActivity extends AppCompatActivity {

    EditText editTextAddItem;
    EditText editTextDate;
    EditText chooseTime;
    FloatingActionButton btnDone;
    FloatingActionButton btnCancel;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // get the elements
        editTextAddItem = findViewById(R.id.txtAddItem);
        btnDone = findViewById(R.id.btnAddItemDone);
        btnCancel = findViewById(R.id.btnAddItemCancel);
        editTextDate = findViewById(R.id.editTxtDate);
        chooseTime = findViewById(R.id.editTxtChooseTime);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Item");

        // Pop up date picker dialog when clicked on editTxtDate text field
        // Enter the selected date to the text field
        editTextDate.setInputType(InputType.TYPE_NULL);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append((monthOfYear + 1)).append("/").append(dayOfMonth).append("/").append(year);
                        editTextDate.setText(stringBuilder);
                    }
                };

                // DatePicker dialog
               datePickerDialog = new DatePickerDialog(AddItemActivity.this,dateSetListener, year, month, day);
               datePickerDialog.show();
            }
        });

        // Pop up the timer when clicked on the Choose time text field
        // and user can use the time from the timer
        chooseTime.setOnClickListener(new View.OnClickListener() {
            // define Android Time Picker inside onClick method
            @Override
            public void onClick(View view) {

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        // Show selected time as am-pm inside EditText field
                        String amPm;
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        StringBuilder selectedTime = new StringBuilder();
                        selectedTime.append(hourOfDay).append(":").append(minutes).append(amPm);
                        chooseTime.setText(selectedTime);
                    }
                };

                // Create the TimePickerDialog
                timePickerDialog = new TimePickerDialog(AddItemActivity.this, timeSetListener, 0, 0, false);
                timePickerDialog.show();

            }
        });

        // After entering the item on the text field, we click on the done button
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from the text field
                String item = editTextAddItem.getText().toString().trim();

                // Create an Intent
                Intent intent = new Intent();

                // Check if txtAddItem field is empty
                if (TextUtils.isEmpty(item)){
                    Toast.makeText(getApplicationContext(), "Empty field not allowed!",
                            Toast.LENGTH_SHORT).show();
                }else {

                    // Pass relevant data back as a result
                    intent.putExtra(MainActivity.KEY_ITEM_TEXT, item);

                    // Activity finished ok, return the data
                    setResult(RESULT_OK, intent); // set result code and bundle data for response

                    // closes the activity, pass data to parent
                    finish();
                }
            }
        });

        // Cancel adding item and go back to the MainActivity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // closes the activity
            }
        });

        // Focus on Add Item EditText field
        editTextAddItem.setFocusableInTouchMode(true);
        editTextAddItem.requestFocus();

    }
}