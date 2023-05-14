package com.example.exs1;
//import static com.example.exs1.MenuActivity.sensorsChecked;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exs1.Interfaces.StepCallback;
import com.example.exs1.Logic.GameManager;
import com.example.exs1.Utilities.SignalGen;
import com.example.exs1.Utilities.StepDetector;
import com.google.android.material.textview.MaterialTextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private int col=5;
    private int row=8;
    private ImageButton leftButton, rightButton;
    private ImageView myCar, myCarLeft,myCarLeft2,myCarRight,myCarRight2,heart1,heart2,heart3;

    private MaterialTextView scoreText;
    private  ImageView[][] myMatrix = new ImageView[row][col];
    private  ImageView[][] myCoinsMatrix = new ImageView[7][col];
    private int lifeCounter = 3;
    private String crashed="no! you crashed into rock! :-(";

    private String collect="yes! you got a coin!  :-)";
    private GridLayout myGrid,myGrid2;

    private Timer timer = new Timer();
    Random rand = new Random();
    private static  final int BONUS =150;
    private GameManager gameManager;
    private MediaPlayer mediaPlayer;

    private StepDetector stepDetector;
    public static final String KEY_SENSOR = "KEY_SENSOR";
    public static final String KEY_SPEED = "KEY_SPEED";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_SCORE = "KEY_SCORE";
    private Boolean sensorsChecked,speedChecked;
    private enum Speed {FAST,NORMAL};
    private long speed;
    private String name="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent previousIntent = getIntent();
        sensorsChecked=previousIntent.getBooleanExtra(KEY_SENSOR,false);
        speedChecked=previousIntent.getBooleanExtra(KEY_SPEED,false);
        findViews();
        gameManager=new GameManager(3);
        name= previousIntent.getStringExtra(KEY_NAME);
        switch (chosenSpeed(speedChecked)){
            case FAST:
                speed=700;
                break;
            case NORMAL:
                speed=1000;
        }

        // initiate buttons
        leftButton = findViewById(R.id.myLeftButton);
        rightButton = findViewById(R.id.myRightButton);
        mediaPlayer = MediaPlayer.create(this,R.raw.crash_sound);
        mediaPlayer.setVolume(1.0f,1.0f);
        initStepDetector();

        if(sensorsChecked)
            stepDetector.start();

        // initiate grid with objects, set all of them invisible  by default
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++)
            {
                int view_object_id=myGrid.getChildAt((i*col)+j).getId();
                myMatrix[i][j] = findViewById(view_object_id);
                myMatrix[i][j].setVisibility(View.INVISIBLE);
                }
            }
        for(int i=0;i<row-1;i++)
        {
            for(int j=0;j<col;j++){
                int view_object_id=myGrid2.getChildAt((i*col)+j).getId();
                myCoinsMatrix[i][j] = findViewById(view_object_id);
                myCoinsMatrix[i][j].setVisibility(View.INVISIBLE);
            }
        }

        myCar.setVisibility(View.VISIBLE);

        //initiate starting playground in random places
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<5;j++)
            {
                int randomNum = rand.nextInt(100);
                if(randomNum%7==0) {
                    myMatrix[i][j].setVisibility(View.VISIBLE);
                }
            }
        }


        //-------------------movement of the car---------------------

        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            movingLeft();
            }

        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            movingRight();
            }
        });

        ///updating view details every frame
        startTimer();
    }

    private Speed chosenSpeed(Boolean speedChecked) {
        Speed myVar=Speed.NORMAL;
        if(speedChecked)
            myVar=Speed.FAST;
        return myVar;
    }


    private void findViews() {
        myCar=findViewById(R.id.myCar);
        myCarLeft =findViewById(R.id.myCarleft);
        myCarLeft2=findViewById(R.id.myCarleft2);
        myCarRight=findViewById(R.id.myCarRight);
        myCarRight2=findViewById(R.id.myCarRight2);
        heart1=findViewById(R.id.heart1);
        heart2=findViewById(R.id.heart2);
        heart3=findViewById(R.id.heart3);
        myGrid=findViewById(R.id.background_grid);
        myGrid2=findViewById((R.id.coins_grid));
        scoreText=findViewById(R.id.scoreText);
    }







    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
        if(sensorsChecked)
            stepDetector.start();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPause() {
        super.onPause();
        gameManager.setScore(gameManager.getScore());
        scoreText.setText(""+gameManager.getScore());
        if(sensorsChecked) {
            stepDetector.stop();
        }
        timer.cancel();
        
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(sensorsChecked) {
            stepDetector.stop();
        }
        timer.cancel();
    }

    //---------------work every frame-----------------------
    private void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                runOnUiThread(() -> {
                            if (gameManager.getLife() <= 0) {
                                this.cancel(); // stopping the game after 3 collisions
                                openHighScoreScreen();
                            }
                            else {
                                for (int i = 6; i >= 0; i--) {
                                    for (int j = 0; j < 5; j++) {
                                        if (myMatrix[i][j].getVisibility() == View.VISIBLE) {
                                            myMatrix[i][j].setVisibility(View.INVISIBLE);
                                            if (i != 6)
                                                myMatrix[i + 1][j].setVisibility(View.VISIBLE);
                                        }
                                        if (myCoinsMatrix[i][j].getVisibility() == View.VISIBLE) {
                                            myCoinsMatrix[i][j].setVisibility(View.INVISIBLE);
                                            if (i != 6)
                                                myCoinsMatrix[i + 1][j].setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                                for (int i = 0; i < 5; i++) {
                                    int randomNum = rand.nextInt(150);
                                    if (randomNum % 6 == 0) {
                                        myMatrix[0][i].setVisibility(View.VISIBLE);
                                    }

                                    if (myMatrix[0][i].getVisibility() == View.INVISIBLE) {
                                        randomNum = rand.nextInt(2000);
                                        if (randomNum % 23 == 0) {
                                            myCoinsMatrix[0][i].setVisibility(View.VISIBLE);
                                        }
                                    }


                                }


                                // -------checks if there is a collision--------

                                // case 1: if car is in the middle
                                if (myCar.getVisibility() == View.VISIBLE && myMatrix[6][2].getVisibility() == View.VISIBLE) {
                                    SignalGen.getInstance().toast(crashed, crashed.length());
                                    crashed(6, 2);
                                    lifeCounter--;
                                    gameManager.setLife(lifeCounter);
                                }
                                // case 2: if car is in the first left lane
                                if (myCarLeft.getVisibility() == View.VISIBLE && myMatrix[6][1].getVisibility() == View.VISIBLE) {
                                    SignalGen.getInstance().toast(crashed, crashed.length());
                                    crashed(6, 1);
                                    lifeCounter--;
                                    gameManager.setLife(lifeCounter);
                                }
                                // case 3: if car is in the most left lane
                                if (myCarLeft2.getVisibility() == View.VISIBLE && myMatrix[6][0].getVisibility() == View.VISIBLE) {
                                    SignalGen.getInstance().toast(crashed, crashed.length());
                                    crashed(6, 0);
                                    lifeCounter--;
                                    gameManager.setLife(lifeCounter);
                                }
                                // case 4: if car is in the right lane
                                if (myCarRight.getVisibility() == View.VISIBLE && myMatrix[6][3].getVisibility() == View.VISIBLE) {
                                    SignalGen.getInstance().toast(crashed, crashed.length());
                                    crashed(6, 3);
                                    lifeCounter--;
                                    gameManager.setLife(lifeCounter);
                                }
                                // case 5: if car is in the most right lane
                                if (myCarRight2.getVisibility() == View.VISIBLE && myMatrix[6][4].getVisibility() == View.VISIBLE) {
                                    SignalGen.getInstance().toast(crashed, crashed.length());
                                    crashed(6, 4);
                                    lifeCounter--;
                                    gameManager.setLife(lifeCounter);
                                }

                                // ----------checks if there is a coins collect-------------

                                // case 1: if car is in the middle
                                if (myCar.getVisibility() == View.VISIBLE && myCoinsMatrix[6][2].getVisibility() == View.VISIBLE) {
                                    SignalGen.getInstance().toast(collect, collect.length());
                                    gameManager.setScore(gameManager.getScore() + BONUS);
                                    scoreText.setText("" + gameManager.getScore());
                                }
                                //  case 2: if car is in the first left lane
                                if (myCarLeft.getVisibility() == View.VISIBLE && myCoinsMatrix[6][1].getVisibility() == View.VISIBLE) {
                                    SignalGen.getInstance().toast(collect, collect.length());
                                    gameManager.setScore(gameManager.getScore() + BONUS);
                                    scoreText.setText("" + gameManager.getScore());
                                }
                                // case 3: if car is in the most left lane
                                if (myCarLeft2.getVisibility() == View.VISIBLE && myCoinsMatrix[6][0].getVisibility() == View.VISIBLE) {
                                    SignalGen.getInstance().toast(collect, collect.length());
                                    gameManager.setScore(gameManager.getScore() + BONUS);
                                    scoreText.setText("" + gameManager.getScore());
                                }
                                // case 4: if car is in the right lane
                                if (myCarRight.getVisibility() == View.VISIBLE && myCoinsMatrix[6][3].getVisibility() == View.VISIBLE) {
                                    SignalGen.getInstance().toast(collect, collect.length());
                                    gameManager.setScore(gameManager.getScore() + BONUS);
                                    scoreText.setText("" + gameManager.getScore());
                                }

                                // case 5: if car is in the most right lane
                                if (myCarRight2.getVisibility() == View.VISIBLE && myCoinsMatrix[6][4].getVisibility() == View.VISIBLE) {
                                    SignalGen.getInstance().toast(collect, collect.length());
                                    gameManager.setScore(gameManager.getScore() + BONUS);
                                    scoreText.setText("" + gameManager.getScore());
                                }

                                gameManager.setScore(gameManager.getScore() + 50);
                                scoreText.setText("" + gameManager.getScore());
                            }
                        }
                );


            }}, 0, speed); // 16 milliseconds = 60 fps



}

    private void openHighScoreScreen() {
        Intent nextIntent = new Intent(this, HighscoreActivity.class);
        nextIntent.putExtra(MainActivity.KEY_NAME,name);
        nextIntent.putExtra(MainActivity.KEY_SCORE,gameManager.getScore());
        startActivity(nextIntent);

    }

    @SuppressLint("SetTextI18n")
    private void crashed(int i , int j)
    {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gameManager.crash(MainActivity.this,v);
        gameManager.setScore(gameManager.getScore());
        scoreText.setText(""+gameManager.getScore());
        mediaPlayer.start();


        if (heart1.getVisibility() == View.VISIBLE) {
            heart1.setVisibility(View.INVISIBLE);
            myMatrix[i][j].setVisibility(View.INVISIBLE);
        }
        if (heart2.getVisibility() == View.VISIBLE && heart1.getVisibility() == View.INVISIBLE && myMatrix[i][j].getVisibility() == View.VISIBLE) {
            heart2.setVisibility(View.INVISIBLE);
            myMatrix[i][j].setVisibility(View.INVISIBLE);
        }
        if (heart3.getVisibility() == View.VISIBLE && heart2.getVisibility() == View.INVISIBLE && myMatrix[i][j].getVisibility() == View.VISIBLE) {
            heart3.setVisibility(View.INVISIBLE);
            myMatrix[i][j].setVisibility(View.INVISIBLE);
        }

    }


    public void movingLeft(){
        //case when car is in the first left lane
        if(myCarLeft2.getVisibility()==View.INVISIBLE&& myCarLeft.getVisibility()==View.VISIBLE) {
            myCarLeft.setVisibility(View.INVISIBLE);
            myCarLeft2.setVisibility(View.VISIBLE);
        }
        //case when car is in the middle lane
        if(myCarLeft.getVisibility()==View.INVISIBLE&&myCar.getVisibility()==View.VISIBLE) {
            myCar.setVisibility(View.INVISIBLE);
            myCarLeft.setVisibility(View.VISIBLE);
        }
        //case when car is in the first right lane
        if(myCar.getVisibility()==View.INVISIBLE&&myCarRight.getVisibility()==View.VISIBLE) {
            myCarRight.setVisibility(View.INVISIBLE);
            myCar.setVisibility(View.VISIBLE);
        }
        //case when car is in the second right lane
        if(myCarRight.getVisibility()==View.INVISIBLE&&myCarRight2.getVisibility()==View.VISIBLE) {
            myCarRight2.setVisibility(View.INVISIBLE);
            myCarRight.setVisibility(View.VISIBLE);
        }
    }


    public void movingRight(){
        //case when car is in the first right lane
        if(myCarRight2.getVisibility()==View.INVISIBLE&&myCarRight.getVisibility()==View.VISIBLE) {
            myCarRight.setVisibility(View.INVISIBLE);
            myCarRight2.setVisibility(View.VISIBLE);
        }
        //case when car is in the middle lane
        if(myCarRight.getVisibility()==View.INVISIBLE&&myCar.getVisibility()==View.VISIBLE) {
            myCar.setVisibility(View.INVISIBLE);
            myCarRight.setVisibility(View.VISIBLE);
        }
        //case when car is in the first left lane
        if(myCar.getVisibility()==View.INVISIBLE&& myCarLeft.getVisibility()==View.VISIBLE) {
            myCarLeft.setVisibility(View.INVISIBLE);
            myCar.setVisibility(View.VISIBLE);
        }
        //case when car is in the most left lane
        if(myCarLeft.getVisibility()==View.INVISIBLE&&myCarLeft2.getVisibility()==View.VISIBLE) {
            myCarLeft2.setVisibility(View.INVISIBLE);
            myCarLeft.setVisibility(View.VISIBLE);
        }

    }






    private void initStepDetector() {
        stepDetector = new StepDetector(this, new StepCallback() {
            @Override
            public void stepX() {
                if(stepDetector.getStepsX()>1.0)
                     movingLeft();
                if(stepDetector.getStepsX()<=-1.0)
                     movingRight();
            }

            @Override
            public void stepY() {
            }

            @Override
            public void stepZ() {
                // Pass
            }
        });
    }

}