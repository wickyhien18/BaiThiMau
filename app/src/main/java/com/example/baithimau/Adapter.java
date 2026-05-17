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
        // Reuse view if possible for better performance
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        Contract contract = getItem(position);

        if (contract != null) {
            // Bind data to the correct IDs from item.xml
            TextView textViewId = convertView.findViewById(R.id.textViewId);
            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewPhone = convertView.findViewById(R.id.textViewPhone);

            if (textViewId != null) {
                textViewId.setText(String.valueOf(contract.getId()));
            }
            if (textViewName != null) {
                textViewName.setText(contract.getName());
            }
            if (textViewPhone != null) {
                textViewPhone.setText(contract.getPhoneNumber());
            }
        }
        return convertView;
    }
}
