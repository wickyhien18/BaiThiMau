package com.example.baithimau;


import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;


public class InputForm extends AppCompatActivity {

    private Button add, back;
    private EditText id, name, phone;
    private boolean isUpdate = false;

    private Contract oldContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.input_form);

        add = findViewById(R.id.buttonFormAdd);
        back = findViewById(R.id.buttonBack);
        id = findViewById(R.id.editTextId);
        name =  findViewById(R.id.editTextName);
        phone = findViewById(R.id.editTextPhone);

        Intent intent = new Intent();
        oldContract = (Contract) intent.getSerializableExtra("editContract");
        if (oldContract != null) {
            id.setText(String.valueOf(oldContract.getId()));
            id.setEnabled(false);
            isUpdate = true;
            name.setText(oldContract.getName());
            phone.setText(oldContract.getPhoneNumber());
        }

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
                Intent intentResult = new Intent();
                intentResult.putExtra("newContract", newContract);
                intentResult.putExtra("isUpdate", isUpdate);
                setResult(RESULT_OK, intentResult);
                finish();
            }
        });

        back.setOnClickListener(v -> {
            finish();
        });
    }
}