package com.example.exs1;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import android.widget.Toast;
import android.os.Vibrator;

import com.example.exs1.Utilities.SignalGen;
import com.example.exs1.Logic.GameManager;



public class MainActivity extends AppCompatActivity {
    private int col= 3;
    private int row=7;
    private ImageButton leftButton, rightButton;
    private ImageView myCar,myCarLeft,myCarRight,heart1,heart2,heart3;
    private  ImageView[][] myRockMatrix= new ImageView[row][col];
    private int lifeCounter = 3;
    private String crashed="no! you crashed into rock! :-(";
    private GridLayout myGrid;

    private Timer timer = new Timer();
    Random rand = new Random();

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        gameManager=new GameManager();
        // initiate buttons
        leftButton = findViewById(R.id.myLeftButton);
        rightButton = findViewById(R.id.myRightButton);



        // initiate grid with objects, set all of them invisible  by default
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++)
            {
                int view_object_id=myGrid.getChildAt((i*col)+j).getId();
                myRockMatrix[i][j]=findViewById(view_object_id);
                myRockMatrix[i][j].setVisibility(View.INVISIBLE);
            }
        }
        myCar.setVisibility(View.VISIBLE);

        //initiate starting playground in random places
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<col;j++)
            {
                int randomNum = rand.nextInt(30);
                if(randomNum%3==0) {
                    myRockMatrix[i][j].setVisibility(View.VISIBLE);
                }
            }
        }


        //-------------------movement of the car---------------------

        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //case when car is in the middle lane
                if(myCarLeft.getVisibility()==View.INVISIBLE&&myCar.getVisibility()==View.VISIBLE) {
                    myCar.setVisibility(View.INVISIBLE);
                    myCarLeft.setVisibility(View.VISIBLE);
                }

                //case when car is in the right lane
                if(myCar.getVisibility()==View.INVISIBLE&&myCarRight.getVisibility()==View.VISIBLE) {
                    myCarRight.setVisibility(View.INVISIBLE);
                    myCar.setVisibility(View.VISIBLE);
                }
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //case when car is in the middle lane
                if(myCarRight.getVisibility()==View.INVISIBLE&&myCar.getVisibility()==View.VISIBLE) {
                    myCar.setVisibility(View.INVISIBLE);
                    myCarRight.setVisibility(View.VISIBLE);
                }

                //case when car is in the left lane
                if(myCar.getVisibility()==View.INVISIBLE&&myCarLeft.getVisibility()==View.VISIBLE) {
                    myCarLeft.setVisibility(View.INVISIBLE);
                    myCar.setVisibility(View.VISIBLE);
                }
            }
        });

        // checks if life is good:
        if(heart3.getVisibility()==View.INVISIBLE)
        {
            heart1.setVisibility(View.VISIBLE);
            heart2.setVisibility(View.VISIBLE);
            heart3.setVisibility(View.VISIBLE);
        }
        ///updating view details every frame
        startTimer();
    }


    private void findViews() {
        myCar=findViewById(R.id.myCar);
        myCarLeft=findViewById(R.id.myCar2);
        myCarRight=findViewById(R.id.myCar3);
        heart1=findViewById(R.id.heart1);
        heart2=findViewById(R.id.heart2);
        heart3=findViewById(R.id.heart3);
        myGrid=findViewById(R.id.background_grid);
    }




    //---------------work every frame-----------------------


    private void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {

                    for (int i = row - 2; i >= 0; i--) {
                        for (int j = 0; j < col; j++) {
                            if (myRockMatrix[i][j].getVisibility() == View.VISIBLE) {
                                myRockMatrix[i][j].setVisibility(View.INVISIBLE);
                                if (i != row - 2)
                                    myRockMatrix[i + 1][j].setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    for (int i = 0; i < 3; i++) {
                        int randomNum = rand.nextInt(18);
                        if (randomNum % 3 == 0) {
                            myRockMatrix[0][i].setVisibility(View.VISIBLE);
                        }
                    }

                    // checks if there is life left
                    if(lifeCounter<=0)
                        this.cancel(); // stopping the game after 3 collisions
                    // checks if there is a collision

                    // case 1: if car is in the middle
                    if (myCar.getVisibility() == View.VISIBLE && myRockMatrix[5][1].getVisibility() == View.VISIBLE) {
                        SignalGen.getInstance().toast(crashed, crashed.length());
                        crashed();
                        lifeCounter--;
                        if (heart1.getVisibility() == View.VISIBLE) {
                            heart1.setVisibility(View.INVISIBLE);
                            myRockMatrix[5][1].setVisibility(View.INVISIBLE);

                        }
                        if (heart2.getVisibility() == View.VISIBLE && heart1.getVisibility() == View.INVISIBLE && myRockMatrix[5][1].getVisibility() == View.VISIBLE) {
                            heart2.setVisibility(View.INVISIBLE);
                            myRockMatrix[5][1].setVisibility(View.INVISIBLE);


                        }
                        if (heart3.getVisibility() == View.VISIBLE && heart2.getVisibility() == View.INVISIBLE && myRockMatrix[5][1].getVisibility() == View.VISIBLE) {
                            heart3.setVisibility(View.INVISIBLE);
                            myRockMatrix[5][1].setVisibility(View.INVISIBLE);

                        }
                    }


                    // case 2: if car is in the left
                    if (myCarLeft.getVisibility() == View.VISIBLE && myRockMatrix[5][0].getVisibility() == View.VISIBLE) {
                        SignalGen.getInstance().toast(crashed, crashed.length());
                        crashed();
                        lifeCounter--;
                        if (heart1.getVisibility() == View.VISIBLE) {
                            heart1.setVisibility(View.INVISIBLE);
                            myRockMatrix[5][0].setVisibility(View.INVISIBLE);

                        }
                        if (heart2.getVisibility() == View.VISIBLE && heart1.getVisibility() == View.INVISIBLE && myRockMatrix[5][0].getVisibility() == View.VISIBLE) {
                            heart2.setVisibility(View.INVISIBLE);
                            myRockMatrix[5][0].setVisibility(View.INVISIBLE);

                        }
                        if (heart3.getVisibility() == View.VISIBLE && heart2.getVisibility() == View.INVISIBLE && myRockMatrix[5][0].getVisibility() == View.VISIBLE) {
                            heart3.setVisibility(View.INVISIBLE);
                            myRockMatrix[5][0].setVisibility(View.INVISIBLE);

                        }
                    }
                    // case 3: if car is in the right
                    if (myCarRight.getVisibility() == View.VISIBLE && myRockMatrix[5][2].getVisibility() == View.VISIBLE) {
                        SignalGen.getInstance().toast(crashed, crashed.length());
                        crashed();
                        lifeCounter--;
                        if (heart1.getVisibility() == View.VISIBLE) {
                            heart1.setVisibility(View.INVISIBLE);
                            myRockMatrix[5][2].setVisibility(View.INVISIBLE);
                        }
                        if (heart2.getVisibility() == View.VISIBLE && heart1.getVisibility() == View.INVISIBLE && myRockMatrix[5][2].getVisibility() == View.VISIBLE) {
                            heart2.setVisibility(View.INVISIBLE);
                            myRockMatrix[5][2].setVisibility(View.INVISIBLE);
                        }
                        if (heart3.getVisibility() == View.VISIBLE && heart2.getVisibility() == View.INVISIBLE && myRockMatrix[5][2].getVisibility() == View.VISIBLE) {
                            heart3.setVisibility(View.INVISIBLE);
                            myRockMatrix[5][2].setVisibility(View.INVISIBLE);
                        }
                    }
                });


            }}, 0, 1000); // 16 milliseconds = 60 fps










}
    private void crashed()
    {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gameManager.crash(MainActivity.this,v);

    }

}