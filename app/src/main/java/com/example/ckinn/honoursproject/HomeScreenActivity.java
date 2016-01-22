package com.example.ckinn.honoursproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ckinn on 22/01/2016.
 */
public class HomeScreenActivity extends AppCompatActivity{

    TextView WelcomeText;
    Button ReadCardButton;
    Button SetupCardButton;
    Button ViewLibraryButton;
    Button DuelButton;
    Button ExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreenlayout);
        WelcomeText = (TextView) findViewById(R.id.WelcomeText);
        ReadCardButton = (Button) findViewById(R.id.ReadButton);
        SetupCardButton = (Button) findViewById(R.id.SetupButton);
        ViewLibraryButton = (Button) findViewById(R.id.LibraryButton);
        DuelButton = (Button) findViewById(R.id.DuelButton);
        ExitButton = (Button) findViewById(R.id.ExitButton);


        ReadCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        SetupCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ViewLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        DuelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
