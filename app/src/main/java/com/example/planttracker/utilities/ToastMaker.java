package com.example.planttracker.utilities;

import android.content.Context;
import android.widget.Toast;

public class ToastMaker {
    public static void makeToast(Context applicationContext, String message) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show();
    }
}
