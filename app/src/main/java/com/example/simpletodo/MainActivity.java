package com.example.simpletodo;


import static org.apache.commons.io.FileUtils.readLines;
import static org.apache.commons.io.FileUtils.writeLines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_POSITION = "item_position";
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final int EDIT_TEXT_CODE = 25;

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


        loadItems();

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
                saveItems();
            }
        };

        ItemsAdapter.OnItemClickListener onItemClickListener = new ItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                // Get the text of the item at the given position
                String itemText = items.get(position);

                Log.d("MainActivity", "Single click at position: " + position + " " + itemText);

                // Construct the Intent
                // first parameter is the context, second is the class of the activity to launch
                Intent intent = new Intent(MainActivity.this, EditActivity.class);

                // Pass the data being edited to EditActivity
                intent.putExtra(KEY_ITEM_POSITION, position);
                intent.putExtra(KEY_ITEM_TEXT, itemText);

                // Brings up the second activity
                startActivityForResult(intent, EDIT_TEXT_CODE);
            }
        };

        // Construct the adapter for the items
        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onItemClickListener);
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
                saveItems();
            }
        });
    }

    // Get data file
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    // Load data from the data file
    private void loadItems(){
        try {
            items = new ArrayList<>(readLines(getDataFile(), Charset.defaultCharset()));
        }catch (IOException exception){
            Log.e("MainActivity", "Error while reading items", exception);
            items = new ArrayList<>();
        }
    }

    // Save data into the data file
    private void saveItems(){
        try {
            writeLines(getDataFile(), items);
        } catch (IOException exception) {
            Log.e("MainActivity", "Error while writing items", exception);
        }
    }
}