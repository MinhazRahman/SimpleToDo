package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;
    Button btnAdd;
    EditText editText;
    RecyclerView recyclerView;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        editText = findViewById(R.id.editItem);
        recyclerView = findViewById(R.id.rvItems);


        // create some mock data
        items = new ArrayList<>();
        items.add("Solve Leetcode problems");
        items.add("Exercise for 30minutes");
        items.add("Go to work");

        // Implement ItemsAdapter.OnItemLongClickListener interface
        ItemsAdapter.OnItemLongClickListener onLongClickListener = new ItemsAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // Remove the item from the data model
                items.remove(position);
                // Notify the adapter at which position the item was deleted
                itemsAdapter.notifyItemRemoved(position);
                // Notify the user
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();

            }
        };

        // Construct the adapter for the items
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        // Set the Adapter for the RecyclerView
        recyclerView.setAdapter(itemsAdapter);
        // Set the LayoutManager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add logic on button press
        // Add item to the RecyclerView when Add button is clicked
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get item from the text field
                String newItem = editText.getText().toString();
                // Add item to the data model
                items.add(newItem);
                // Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size()-1); // item is inserted at the last position
                // Clear the edit text
                editText.setText("");
                // Notify user that an item was added
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}