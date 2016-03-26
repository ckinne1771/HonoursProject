package com.example.ckinn.honoursproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by ckinn on 20/03/2016.
 */
public class DuelActivity extends AppCompatActivity{

    ImageButton card1;
    ImageButton card2;
    ImageButton card3;
    ImageButton duelBox1;
    ImageButton duelBox2;
    ImageButton duelBox3;
    ImageButton duelBox4;
    ImageButton duelBox5;
    ImageButton duelBox6;
    ImageButton duelBox7;
    ImageButton duelBox8;
    ImageButton duelBox9;
    ImageButton duelBox10;
    ImageButton duelBox11;
    ImageButton duelBox12;
    ImageButton duelBox13;
    ImageButton duelBox14;
    ImageButton duelBox15;
    ImageButton duelBox16;
    ImageButton duelBox17;
    ImageButton duelBox18;
    ImageButton duelBox19;
    ImageButton duelBox20;

    private boolean card1Selected = false;
    private boolean card2Selected = false;
    private boolean card3Selected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duellayout);
        card1 = (ImageButton) findViewById(R.id.card1);
        card2 = (ImageButton) findViewById(R.id.card2);
        card3 = (ImageButton) findViewById(R.id.card3);
        duelBox1  = (ImageButton) findViewById(R.id.DuelBox);
        duelBox2  = (ImageButton) findViewById(R.id.DuelBox2);
        duelBox3  = (ImageButton) findViewById(R.id.DuelBox3);
        duelBox4  = (ImageButton) findViewById(R.id.DuelBox4);
        duelBox5  = (ImageButton) findViewById(R.id.DuelBox5);
        duelBox6  = (ImageButton) findViewById(R.id.DuelBox6);
        duelBox7  = (ImageButton) findViewById(R.id.DuelBox7);
        duelBox8  = (ImageButton) findViewById(R.id.DuelBox8);
        duelBox9  = (ImageButton) findViewById(R.id.DuelBox9);
        duelBox10  = (ImageButton) findViewById(R.id.DuelBox10);
        duelBox11  = (ImageButton) findViewById(R.id.DuelBox11);
        duelBox12  = (ImageButton) findViewById(R.id.DuelBox12);
        duelBox13  = (ImageButton) findViewById(R.id.DuelBox13);
        duelBox14  = (ImageButton) findViewById(R.id.DuelBox14);
        duelBox15  = (ImageButton) findViewById(R.id.DuelBox15);
        duelBox16  = (ImageButton) findViewById(R.id.DuelBox16);
        duelBox17  = (ImageButton) findViewById(R.id.DuelBox17);
        duelBox18  = (ImageButton) findViewById(R.id.DuelBox18);
        duelBox19  = (ImageButton) findViewById(R.id.DuelBox19);
        duelBox20  = (ImageButton) findViewById(R.id.DuelBox20);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!card1Selected)
                {
                    card1Selected = true;
                    card2Selected = false;
                    card3Selected = false;
                }
                else if (card1Selected)
                {
                    card1Selected = false;
                }

            }
        });

        card2.setOnClickListener(new View.OnClickListener(){
            @Override
        public void  onClick(View v) {
                if(!card2Selected)
                {
                    card2Selected = true;
                    card1Selected = false;
                    card3Selected = false;
                }
                else if (card2Selected)
                {
                    card2Selected = false;
                }
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!card3Selected)
                {
                    card3Selected = true;
                    card1Selected = false;
                    card2Selected = false;
                }
                else if(card3Selected)
                {
                    card3Selected = false;
                }
            }
        });

        duelBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox1.setImageResource(R.drawable.darkmagician);
                }
                else if(card2Selected)
                {
                    duelBox1.setImageResource(R.drawable.blueeyeswhitedragon);
                }
                else if (card3Selected)
                {
                    duelBox1.setImageResource(R.drawable.harpielady);
                }
            }
        });

    }
    }



