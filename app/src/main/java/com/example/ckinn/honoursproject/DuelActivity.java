package com.example.ckinn.honoursproject;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Random;

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
    int cardImage1Chosen = 0;
    int cardImage2Chosen = 0;
    int cardImage3Chosen = 0;
    ArrayList<Integer> cardResources = new ArrayList<>();
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
        final DBHandler dbHandler = new DBHandler(DuelActivity.this, "CardDatabase1.s3db", null, 3);
        String[] cardList = dbHandler.getSpinnerContent();

        cardResources.add(R.drawable.blueeyeswhitedragon);
        cardResources.add(R.drawable.darkmagician);
        cardResources.add(R.drawable.harpielady);

        Random rand = new Random();
        int n = rand.nextInt(cardList.length);
        if (n == 0)
        {
            card1.setImageResource(R.drawable.blueeyeswhitedragon);
            cardImage1Chosen = 0;

        }
        else if (n == 1)
        {
            card1.setImageResource(R.drawable.darkmagician);
            cardImage1Chosen = 1;

        }
        else if (n == 2)
        {
            card1.setImageResource(R.drawable.harpielady);
            cardImage1Chosen = 2;

        }
        n = rand.nextInt(cardList.length);
        if (n == 0)
        {
            card2.setImageResource(R.drawable.blueeyeswhitedragon);
            cardImage2Chosen  =0;

        }
        else if (n == 1)
        {
            card2.setImageResource(R.drawable.darkmagician);
            cardImage2Chosen = 1;

        }
        else if (n == 2)
        {
            card2.setImageResource(R.drawable.harpielady);
            cardImage2Chosen = 2;

        }
        n = rand.nextInt(cardList.length);
        if (n == 0)
        {
            card3.setImageResource(R.drawable.blueeyeswhitedragon);
            cardImage3Chosen = 0;

        }
        else if (n == 1)
        {
            card3.setImageResource(R.drawable.darkmagician);
            cardImage3Chosen =1;

        }
        else if (n == 2)
        {
            card3.setImageResource(R.drawable.harpielady);
            cardImage3Chosen = 2;

        }

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
            public void onClick(View v) {//the first zone on the field
                if (card1Selected)//if the first card is selected
                {
                    //cardImage1Chosen, cardImage2Chose, and cardImage3Chosen refer to the integer value representing the image of a particular card.
                    duelBox1.setImageResource(cardResources.get(cardImage1Chosen));//set the image of the zone to the image associated with first card.
                }
                else if(card2Selected)//if the second card is selected
                {
                    duelBox1.setImageResource(cardResources.get(cardImage2Chosen));//set the image of the zone to the image associated with second card.
                }
                else if (card3Selected)//if the third card is selected
                {
                    duelBox1.setImageResource(cardResources.get(cardImage3Chosen));//set the image of the zone to the image associated with third card.
                }
            }
        });
        duelBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox2.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox2.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox2.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox3.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox3.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox3.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox4.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox4.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox4.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox5.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox5.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox5.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox6.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox6.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox6.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox7.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox7.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox7.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox8.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox8.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox8.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox9.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox9.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox9.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox10.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox10.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox10.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox11.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox11.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox11.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox12.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox12.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox12.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox13.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox13.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox13.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox14.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox14.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox14.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox15.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox15.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox15.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox16.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox16.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox16.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox17.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox17.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox17.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox18.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox18.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox18.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox19.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox19.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox19.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });
        duelBox20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card1Selected)
                {
                    duelBox20.setImageResource(cardResources.get(cardImage1Chosen));
                }
                else if(card2Selected)
                {
                    duelBox20.setImageResource(cardResources.get(cardImage2Chosen));
                }
                else if (card3Selected)
                {
                    duelBox20.setImageResource(cardResources.get(cardImage3Chosen));
                }
            }
        });

    }
    }



