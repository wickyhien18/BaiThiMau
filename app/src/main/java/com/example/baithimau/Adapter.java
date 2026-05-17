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

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        Contract contract = getItem(position);

        if (contract != null) {
            TextView textViewLayout = convertView.findViewById(R.id.textViewLayout);
            textViewLayout.setText(contract.toString());
        }
        return convertView;
    }
}
