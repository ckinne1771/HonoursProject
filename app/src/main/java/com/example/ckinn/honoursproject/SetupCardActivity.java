package com.example.ckinn.honoursproject;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ckinn on 16/02/2016.
 */
public class SetupCardActivity extends AppCompatActivity {
    EditText cardEntry;
    TextView ExplanationText;
    Button  WriteButton;
    Button backButton;
    boolean writeCard = false;
    NfcAdapter nfcAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setupcardlayout);
        cardEntry = (EditText) findViewById(R.id.CardNameEntry);
        cardEntry.setText("Card Name", TextView.BufferType.EDITABLE);
        ExplanationText = (TextView) findViewById(R.id.ExplanationText);
        WriteButton = (Button) findViewById(R.id.WriteButton);
        backButton = (Button)findViewById(R.id.BackButton);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!writeCard)
                {
                    writeCard = true;
                }
                else if(writeCard)
                {
                    writeCard =false;
                }

            }
        });

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }


    }

  //  private void handleIntent()
}
