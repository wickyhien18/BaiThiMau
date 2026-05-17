package com.example.baithimau;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;

public class ConfirmDialog {
    public interface OnConfirmListener {
        void onConfirm();
    }

    public static void show(
            Context context,
            String title,
            String message,
            OnConfirmListener listener
    ) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    if (listener != null) {
                        listener.onConfirm();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

    }
}
