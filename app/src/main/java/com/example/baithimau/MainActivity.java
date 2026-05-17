package com.example.baithimau;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
                    addContract();
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
        addContract();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.delete_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;

        Contract contract = contractDB.get(index);

        if (item.getItemId() == R.id.itemDelete) {
            ConfirmDialog.show(
                    this,
                    "Confirm",
                    "Are you sure you want to delete this contract?",
                    () -> {
                        db.delete(contract);
                        addContract();
                    }
            );
            return true;
        }

        else if (item.getItemId() == R.id.itemEdit) {
            Intent intent = new Intent(this, InputForm.class);
            intent.putExtra("editContract", contract);
            contractPicker.launch(intent);
            return true;
        }

        return super.onContextItemSelected(item);

    }

    private void Init() {
        add = findViewById(R.id.buttonAdd);
        search = findViewById(R.id.searchText);
        contractDB = new ArrayList<>();
        adapter = new Adapter(this, R.layout.item, contractDB);
        listView = findViewById(R.id.contractList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }


    private void Listen() {
        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, InputForm.class);
            contractPicker.launch(intent);
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                db.OpenDB();
                contractDB.clear();
                contractDB.addAll(db.search(s.toString()));
                adapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, contractDB.get(position).toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void addContract() {
        contractDB.clear();
        contractDB.addAll(db.getAll());
        adapter.notifyDataSetChanged();
    }
}