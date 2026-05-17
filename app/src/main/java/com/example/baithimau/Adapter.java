package com.example.baithimau;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Contract> {
    public Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Contract> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        Contract contract = getItem(position);

        if (contract != null) {
            TextView textViewId = view.findViewById(R.id.textViewId);
            TextView textViewName = view.findViewById(R.id.textViewName);
            TextView textViewPhone = view.findViewById(R.id.textViewPhone);
            textViewId.setText(String.valueOf(contract.getId()));
            textViewName.setText(contract.getName());
            textViewPhone.setText(contract.getPhoneNumber());
        }
        return view;
    }
}
