package com.example.exs1.Utilities;
import android.content.Context;
import android.widget.Toast;


public class SignalGen {
    public static SignalGen instance;
    private Context context;

    private SignalGen(Context context) {
        this.context = context;
    }
    public static void init(Context context) {
        if (instance == null) {
            instance = new SignalGen(context);
        }
    }

    public static SignalGen getInstance() {
        return instance;
    }

    public void toast(String text, int length) {
        Toast
                .makeText(context, text, length)
                .show();
    }




}
