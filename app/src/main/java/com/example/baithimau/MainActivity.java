package com.example.baithimau;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private Button add;
    private EditText search;
    private ListView listView;
    private ArrayList<Contract> contractDB;
    private Adapter adapter;
    private SQLite db;

    private ActivityResultLauncher<Intent> contractPicker = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == MainActivity.RESULT_OK && result.getData() != null) {
                Contract newContract = (Contract) result.getData().getSerializableExtra("newContract");
                if (newContract != null) {
                    db.insert(newContract);
                    contractDB.add(newContract);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    );


    @Override
    protected void onStart() {
        super.onStart();
        if (db != null) {
            db.OpenDB();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (db != null) {
            db.CloseDB();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Init();
        Listen();

        db = new SQLite(MainActivity.this,"CONTRACT_2326",null, 1);
        db.OpenDB();

        if (db.count() == 0) {
            db.insert(new Contract(1, "Wicky", "091"));
            db.insert(new Contract(2, "Nguyen Van A", "09175686"));
            db.insert(new Contract(3, "Nguyen Van B", "091867868"));
            db.insert(new Contract(4, "Nguyen Van C", "09112313"));
            db.insert(new Contract(5, "Nguyen Van DE", "091678678"));
            db.insert(new Contract(6, "Nguyen Van EE", "09135435"));
        }

        contractDB.clear();
        contractDB.addAll(db.getAll());
        adapter.notifyDataSetChanged();
    }

    private void Init() {
        add = findViewById(R.id.buttonAdd);
        search = findViewById(R.id.searchText);
        contractDB = new ArrayList<>();
        adapter = new Adapter(this, android.R.layout.simple_list_item_1, contractDB);
        listView = findViewById(R.id.contractList);
        listView.setAdapter(adapter);
    }


    private void Listen() {
        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, InputForm.class);
            contractPicker.launch(intent);
        });
    }
}