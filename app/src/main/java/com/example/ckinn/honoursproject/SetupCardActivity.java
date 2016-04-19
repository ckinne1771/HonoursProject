package com.example.ckinn.honoursproject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

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
            public void onClick(View v) {//logic for the "Write to Tag" button
                if(!writeCard)//if writeCard is set to false when the button is pressed
                {
                    writeCard = true;//enables tag writing
                }
                else if(writeCard)//if writecard is set to true when the button is pressed
                {
                    writeCard =false;//disables tag writing
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

    private void handleIntent(Intent intent)
    {
       if(writeCard) { //if write card is true when the smartphone is held over a tag.
        String action = intent.getAction();//gets the intent for the detection of NFC tags.
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))//if  an NDEF tag is discovered.
        {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);//get the tag
            NdefMessage ndefMessage = createNdefmessage(cardEntry.getText().toString()); //set the message to be written to the tag as the data
            //entered in the editable text view.

            writeNdefMessage(tag, ndefMessage);//write to the tag.
            writeCard = false;//turn off card writing.

        }
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action))
        {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            NdefMessage ndefMessage = createNdefmessage(cardEntry.getText().toString());

            writeNdefMessage(tag, ndefMessage);
            writeCard = false;

        }

    }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupForegroundDispatch(this,nfcAdapter);
    }

    @Override
    protected void onPause() {

        stopForegroundDispatch(this, nfcAdapter);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }



    /**
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        adapter.enableForegroundDispatch(activity, pendingIntent, null, null);
    }


    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    private void formatTag (Tag tag, NdefMessage ndefMessage)
    {
        try{
            NdefFormatable ndefFormatable = NdefFormatable.get(tag);
            if(ndefFormatable == null )
            {
                Toast.makeText(this, "Tag is not formattable", Toast.LENGTH_SHORT).show();;
                ndefFormatable.connect();
                ndefFormatable.format(ndefMessage);
                ndefFormatable.close();
            }

        }
        catch (Exception e)
        {
            Log.e("Format tag", e.getMessage());

        }
    }

    private void writeNdefMessage(Tag tag, NdefMessage ndefMessage)
    {
        try {
            if(tag==null)// if the tag is empty
            {
                Toast.makeText(this, "tagObject cannot be null", Toast.LENGTH_SHORT).show();
                return;
            }

            Ndef ndef = Ndef.get(tag); //gets the tag detected.

            if(ndef == null)
            {
                //format tag with the ndef format if it has not already been formatted and writes the message
                formatTag(tag, ndefMessage);
            }
            else{
                ndef.connect();//Connect to the tag.

                if(!ndef.isWritable())//if the tag is not writable
                {
                    Toast.makeText(this, "Tag is not writable", Toast.LENGTH_SHORT).show();//Display message to user
                    ndef.close();//disconnect from tag
                    return;
                }

                ndef.writeNdefMessage(ndefMessage);//Write data onto the tag
                ndef.close();//Disconnect from tag

                Toast.makeText(this, "Tag Written!", Toast.LENGTH_SHORT).show();// Display message to user.

            }
        }
        catch (Exception e) //Exception handling.
        {
            Log.e("writeNdefMessage", e.getMessage());
        }
    }

    private NdefRecord createTextRecord(String content)
    {
        try {
            byte[] language;
            language = Locale.getDefault().getLanguage().getBytes("UTF-8");

            final byte[] text = content.getBytes("UTF-8");
            final int languageSize = language.length;
            final int textLength = text.length;
            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + languageSize + textLength);

            payload.write((byte) (languageSize & 0x1F));
            payload.write(language, 0 , languageSize);
            payload.write(text, 0 , textLength);

            return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());

        }
        catch(UnsupportedEncodingException e)
        {
            Log.e("createTextRecord", e.getMessage());
        }
        return null;
    }

    private NdefMessage createNdefmessage(String content)
    {
        NdefRecord ndefRecord = createTextRecord(content);

        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ndefRecord});

        return ndefMessage;
    }
}
