package com.example.simpletodo;


import static org.apache.commons.io.FileUtils.readLines;
import static org.apache.commons.io.FileUtils.writeLines;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_POSITION = "item_position";
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_REMINDER_DATE = "reminder_date";
    public static final String KEY_REMINDER_TIME = "reminder_time";
    public static final int EDIT_TEXT_CODE = 25;
    public static final int ADD_TEXT_CODE = 20;

    List<String> items;
    FloatingActionButton btnAdd;
    TextView emptyView;
    ImageView emptyViewIcon;
    RecyclerView recyclerView;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.rvItems);
        emptyView = findViewById(R.id.emptyViewText);
        emptyViewIcon = findViewById(R.id.emptyViewIcon);


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

               // Log.d("MainActivity", "Single click at position: " + position + " " + itemText);

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

        // Decorate the RecyclerView
        // This decorator displays dividers between each item within the list
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        // Clicking on the Add button launches AddItemActivity
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Construct the Intent
                // first parameter is the context, second is the class of the activity to launch
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);

                // Brings up the second activity
                startActivityForResult(intent, ADD_TEXT_CODE);

            }
        });
    }

    // Once the sub-activity finishes, the onActivityResult() method in the calling activity is invoked
    // Handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == EditActivity.RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            // Extract the updated data
            assert data != null;
            int itemPosition = data.getExtras().getInt(KEY_ITEM_POSITION);
            String editedItemText = data.getStringExtra(KEY_ITEM_TEXT);

            // Update the model with the edited item
            items.set(itemPosition, editedItemText);
            // Notify the adapter
            itemsAdapter.notifyItemChanged(itemPosition);
            saveItems();
            Toast.makeText(getApplicationContext(), "Item updated successfully", Toast.LENGTH_SHORT).show();
        }
        else if (resultCode == AddItemActivity.RESULT_OK && requestCode == ADD_TEXT_CODE){
            // Extract the data
            assert data != null;
            String newItem = data.getStringExtra(KEY_ITEM_TEXT);
            String dateString = data.getStringExtra(KEY_REMINDER_DATE);
            String timeString = data.getStringExtra(KEY_REMINDER_TIME);

            // Add item to the data model
            items.add(newItem);
            // Notify adapter that an item is inserted
            itemsAdapter.notifyItemInserted(items.size()-1); // item is inserted at the last position
            saveItems();
            // Notify user that an item was added
            Toast.makeText(getApplicationContext(), "Item was added",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    // Get data file
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    // Load data from the data file
    private void loadItems(){
        try {
            items = new ArrayList<>(readLines(getDataFile(), Charset.defaultCharset()));
           showMessageOnEmptyRecyclerView();

        }catch (IOException exception){
            Log.e("MainActivity", "Error while reading items", exception);
            items = new ArrayList<>();
        }
    }

    // Save data into the data file
    private void saveItems(){
        try {
            writeLines(getDataFile(), items);
            showMessageOnEmptyRecyclerView();
        } catch (IOException exception) {
            Log.e("MainActivity", "Error while writing items", exception);
        }
    }

    // Show a message to the user if the RecyclerView is empty
    private void showMessageOnEmptyRecyclerView(){
        if (items.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            emptyViewIcon.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.VISIBLE);

            emptyView.setText(String.valueOf("No todo items!!"));
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyViewIcon.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
        }
    }
}