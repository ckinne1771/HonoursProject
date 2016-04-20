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
//The variables and Ui elements needed for this activity.

    //the fololwing are the sets of imagebuttons that are needed for duel activity.
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

    //these variables are used to aid in the randomisation of the opening hand and the placement of the image on an
    //open zone.
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
        //define the UI elements
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
        //get the list of availible cards.
        final DBHandler dbHandler = new DBHandler(DuelActivity.this, "CardDatabase1.s3db", null, 3);
        String[] cardList = dbHandler.getSpinnerContent();

        //add the intergers representing certain images to an array.
        cardResources.add(R.drawable.blueeyeswhitedragon);
        cardResources.add(R.drawable.darkmagician);
        cardResources.add(R.drawable.harpielady);

        //the folowing code generates the random hand. A random number will be generated for each card,
        //the result of which determines the card in the hand in a certain position.

        Random rand = new Random();//generate a random number.
        int n = rand.nextInt(cardList.length);
        if (n == 0)//if the generated number is 0.
        {
            card1.setImageResource(R.drawable.blueeyeswhitedragon);//set the first card to "blue eyes white dragon".
            cardImage1Chosen = 0;//Define that this card is "blue eyes white dragon.

        }
        else if (n == 1)//if the generated number is 1.
        {
            card1.setImageResource(R.drawable.darkmagician); //set the first card to "Dark Magician".
            cardImage1Chosen = 1; //Define that this card is "Dark Magician".

        }
        else if (n == 2)//if the generated number is 2.
        {
            card1.setImageResource(R.drawable.harpielady); //set the first card to "Harpie Lady".
            cardImage1Chosen = 2; //Define that this card is "Harpie Lady".

        }
        n = rand.nextInt(cardList.length);//generate a new number.
        if (n == 0)//if the generated number is 0
        {
            card2.setImageResource(R.drawable.blueeyeswhitedragon); //set the second card to "blue eyes white dragon".
            cardImage2Chosen  =0; //define that this card is "blue eyes white dragon".

        }
        else if (n == 1)//if the generated number is 1.
        {
            card2.setImageResource(R.drawable.darkmagician);//set the second card to "Dark Magician".
            cardImage2Chosen = 1;//define that this card is "Dark Magician".

        }
        else if (n == 2)//if the generated number is 2.
        {
            card2.setImageResource(R.drawable.harpielady); //set the second card to "Harpie Lady".
            cardImage2Chosen = 2; //define that this card is "harpie Lady".

        }
        n = rand.nextInt(cardList.length);//generate a new number.
        if (n == 0)//if the genreated number is 0.
        {
            card3.setImageResource(R.drawable.blueeyeswhitedragon);//set the third card to "blue eyes white dragon".
            cardImage3Chosen = 0;//define that this card is "blue eyes white dragon".

        }
        else if (n == 1)//if the generated number is 1.
        {
            card3.setImageResource(R.drawable.darkmagician); //set the third card to "dark magician"
            cardImage3Chosen =1;//define that this card is "dark magician".

        }
        else if (n == 2)//if the generated number is 2.
        {
            card3.setImageResource(R.drawable.harpielady);//set the third card to "Harpie Lady".
            cardImage3Chosen = 2;//define that this card is "harpie Lady".

        }
//the following are con click listeners that detect when a card has been selected and chance boolean values when pressed
        //to help indicate what card is activated.
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

        //The following are on click listeners for the zempty zones on the field. When a card is selected, pressing
        //on an open zone will cause the selected card's image to be placed on the zone.
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
        //the rest of the duel zones follow the smae logic as the first.
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



