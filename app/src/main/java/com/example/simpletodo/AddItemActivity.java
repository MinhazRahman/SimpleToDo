package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class AddItemActivity extends AppCompatActivity {

    EditText txtAddItem;
    FloatingActionButton btnDone;
    FloatingActionButton btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // get the elements
        txtAddItem = findViewById(R.id.txtAddItem);
        btnDone = findViewById(R.id.btnAddItemDone);
        btnCancel = findViewById(R.id.btnAddItemCancel);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Item");

        // After entering the item on the text field, we click on the done button
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from the text field
                String item = txtAddItem.getText().toString().trim();

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

    }
}