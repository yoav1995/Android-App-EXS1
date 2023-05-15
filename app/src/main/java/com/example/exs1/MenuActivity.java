package com.example.exs1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

public class MenuActivity extends AppCompatActivity {
    private MaterialTextView playTextBTN, highScoreTxtBTN;
    @SuppressLint({"UseSwitchCompatOrMaterialCode", "StaticFieldLeak"})
    public static Switch sensorsButton,speedButton;
    private EditText userNameTXT;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        findViews();
        playTextBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openGameScreen();
            }
        });

        highScoreTxtBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openHiScoreScreen();
            }
        });


        sensorsButton.setTextOn("On"); // displayed text of the Switch whenever it is in checked or on state
        sensorsButton.setTextOff("Off"); // displayed text of the Switch whenever it is in unchecked i.e. off stat
        sensorsButton.setTextColor(Color.BLACK);
        sensorsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sensorsButton.setChecked(!sensorsButton.isChecked());

            }

        });
        speedButton.setTextOn("On"); // displayed text of the Switch whenever it is in checked or on state
        speedButton.setTextOff("Off"); // displayed text of the Switch whenever it is in unchecked i.e. off stat
        speedButton.setTextColor(Color.BLACK);
        speedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                speedButton.setChecked(!speedButton.isChecked());

            }

        });


        userNameTXT.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    return true;
                }
                return false;
            }
        });

    }
    private void openGameScreen() {
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.putExtra(MainActivity.KEY_SENSOR,sensorsButton.isChecked());
        myIntent.putExtra(MainActivity.KEY_SPEED,speedButton.isChecked());
        myIntent.putExtra(MainActivity.KEY_NAME, userNameTXT.getText().toString());
        startActivity(myIntent);
    }
    private void openHiScoreScreen() {
        Intent myIntent = new Intent(this, HighscoreActivity.class);
        startActivity(myIntent);
    }

    public void findViews(){
        playTextBTN =findViewById(R.id.Play);
        highScoreTxtBTN =findViewById(R.id.Highscore);
        sensorsButton= findViewById((R.id.SensorsSwitch));
        speedButton=findViewById(R.id.SpeedSwitch);
        userNameTXT=findViewById(R.id.inputText_User_Name);
    }






}


