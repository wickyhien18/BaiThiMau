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
                    
                    updateContractList();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Init();
        Listen();
        db = new SQLite(MainActivity.this,"CONTRACT_2326",null, 1);
    }

    private void Init() {
        add = findViewById(R.id.buttonAdd);
        search = findViewById(R.id.searchText);
        listView = findViewById(R.id.contractList);
        db = new SQLite(this);
        contractDB = db.getAll();
        updateContractList();
    }

    private void updateContractList() {
        contractList.clear();
        for (Contract contract : contractDB) {
            if (contract != null)
                contractList.add(contract.toString());
        }

        contractList.sort((s1,s2) -> {
            String[] arr1 = s1.split(" - ")[1].split(" ");
            String[] arr2 = s2.split(" - ")[1].split(" ");
            return arr1[arr1.length - 1].compareTo(arr2[arr2.length - 1]);
        });
    }

    private void Listen() {
        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, InputForm.class);
            contractPicker.launch(intent);
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contractList);
        listView.setAdapter(adapter);
    }
}