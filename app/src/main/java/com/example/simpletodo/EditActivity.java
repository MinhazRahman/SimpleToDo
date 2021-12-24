package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    EditText editText;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = findViewById(R.id.editText);
        btnSave = findViewById(R.id.btnSave);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Item");

        // Retrieve the data that have been passed by the MainActivity
        int itemPosition = getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION);
        String itemTextToBeEdited = getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT);

        // Set the text to be edited to the Text field
        editText.setText(itemTextToBeEdited);

        // After Editing the text, we click on the Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent
                Intent intent = new Intent();
                // Pass relevant data back as a result
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editText.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, itemPosition);
                // Activity finished ok, return the data
                setResult(RESULT_OK, intent); // set result code and bundle data for response
                finish(); // closes the activity, pass data to parent
            }
        });
    }
}