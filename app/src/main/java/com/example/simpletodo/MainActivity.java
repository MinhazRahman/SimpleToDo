package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;
    Button btnAdd;
    EditText editText;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        editText = findViewById(R.id.editItem);
        recyclerView = findViewById(R.id.rvItems);

        editText.setText("Please, enter your item!");

        // create some mock data
        items = new ArrayList<>();
        items.add("Solve Leetcode problems");
        items.add("Exercise for 30m");
        items.add("Go to work");
    }
}