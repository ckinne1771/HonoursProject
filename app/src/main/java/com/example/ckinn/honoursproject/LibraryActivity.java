package com.example.ckinn.honoursproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by ckinn on 18/03/2016.
 */
public class LibraryActivity extends AppCompatActivity {

    TextView cardInfo;
    TextView Instruction;
    Spinner cards;
    ImageView cardPicture;
    private ArrayAdapter<String> listAdapter;

@Override
    protected void onCreate(Bundle savedInstanceState)
{

    super.onCreate(savedInstanceState);
    setContentView(R.layout.librarylayout);
    cardInfo = (TextView) findViewById(R.id.cardInfo);
    cards = (Spinner) findViewById(R.id.cardspinner); //the spinner which shows cards the user can select
    cardPicture = (ImageView) findViewById(R.id.cardImage);
    Instruction = (TextView) findViewById(R.id.Instructions);
    final DBHandler dbHandler = new DBHandler(LibraryActivity.this, "CardDatabase1.s3db", null, 3); //The database needs to be accessed.
    String[] spinnerLists = dbHandler.getSpinnerContent();// an array of strings is created with it's content matching the card names of the netries in the "CardsOwned" table.

    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(LibraryActivity.this, android.R.layout.simple_spinner_item, spinnerLists);//Adapt the array so that it can be used in a spinner.

    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//The spinner layout will be a simple drop down menu
    cards.setAdapter(spinnerAdapter);//attach the array adapter to the spinner.
    cards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      protected Adapter initializedAdapter = null;
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            String selected = parent.getItemAtPosition(position).toString();
            if(selected.equalsIgnoreCase("Dark Magician"))
            {
                cardInfo.setText(dbHandler.findCardOwned("Dark Magician").toString());
                cardPicture.setImageResource(R.drawable.darkmagician);
            }

            if(selected.equalsIgnoreCase("Blue Eyes White Dragon"))
            {
                cardInfo.setText(dbHandler.findCardOwned("Blue Eyes White Dragon").toString());
                cardPicture.setImageResource(R.drawable.blueeyeswhitedragon);
            }
            if(selected.equalsIgnoreCase("Harpie Lady"))
            {
                cardInfo.setText(dbHandler.findCardOwned("Harpie Lady").toString());
                cardPicture.setImageResource(R.drawable.harpielady);
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    });
}
}

