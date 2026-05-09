package com.example.baithimau;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button add;
    private EditText search;
    private ListView listView;
    private ArrayList<Contract> contracts;
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
        contracts = db.getAll();
    }
    private void Listen() {
        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, InputForm.class);
            startActivity(intent);
        });
    }
}