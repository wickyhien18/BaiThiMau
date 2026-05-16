package com.example.baithimau;


import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;


public class InputForm extends AppCompatActivity {

    private Button add, back;
    private EditText id, name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.input_form);

        add = findViewById(R.id.buttonFormAdd);
        back = findViewById(R.id.buttonBack);
        id = findViewById(R.id.editTextId);
        name =  findViewById(R.id.editTextName);
        phone = findViewById(R.id.editTextPhone);

        add.setOnClickListener(v -> {
            String contractName = name.getText().toString().trim();
            String contractPhone = phone.getText().toString().trim();
            String contractId = id.getText().toString().trim();

            if (contractName.isEmpty() || contractPhone.isEmpty() || contractId.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                Contract newContract = new Contract(Integer.parseInt(contractId), contractName, contractPhone);
                Intent intent = new Intent();
                intent.putExtra("newContract", newContract);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        back.setOnClickListener(v -> {
            finish();
        });
    }
}