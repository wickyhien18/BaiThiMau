package com.example.baithimau;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private Button add;
    private EditText search;
    private ListView listView;
    private ArrayList<Contract> contractDB;
    private List<String> contractList;
    private ArrayAdapter<String> adapter;
    private SQLite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Init();
        Listen();
    }

    private void Init() {
        add = findViewById(R.id.buttonAdd);
        search = findViewById(R.id.searchText);
        listView = findViewById(R.id.contractList);
        db = new SQLite(this);
        contractDB = db.getAll();
        contractDB.sort(Comparator.comparing(Contract::getName));
        contractList = contractDB.stream().map(Contract::toString).collect(Collectors.toList());

    }
    private void Listen() {
        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, InputForm.class);
            startActivity(intent);
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contractList);
        listView.setAdapter(adapter);
    }
}