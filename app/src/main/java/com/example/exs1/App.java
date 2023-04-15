package com.example.exs1;
import android.app.Application;
import com.example.exs1.Utilities.SignalGen;




public class App extends Application {



        @Override
        public void onCreate() {
            super.onCreate();
            SignalGen.init(this);
        }
    }



