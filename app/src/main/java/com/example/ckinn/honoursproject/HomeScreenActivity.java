package com.example.ckinn.honoursproject;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import  android.nfc.tech.NdefFormatable;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URLEncoder;
import java.util.Locale;

/**
 * Created by ckinn on 22/01/2016.
 */
public class HomeScreenActivity extends AppCompatActivity{


    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    TextView WelcomeText;
    Button ReadCardButton;
    Button SetupCardButton;
    Button ViewLibraryButton;
    Button DuelButton;
    Button ExitButton;
    NfcAdapter theNFCAdapter;
    public static boolean readingCard = false;
    private boolean writingCard = false;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                if(readingCard==false)//if the button is pressed whilst readingCard is set to false
                {
                    readingCard = true; //This value helps stop accidental tag reading
                    writingCard = false;
                    WelcomeText.setText("Card Reading Activated. Hold device over card to scan");
                }
               else if(readingCard==true)//if the button is pressed whilst readingCard is set to false
                {
                    readingCard = false;
                    WelcomeText.setText("Card Reading Deactivated.");
                }
            }
        });
        SetupCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(writingCard==false)
                {
                    Intent intent = new Intent(HomeScreenActivity.this, SetupCardActivity.class);
                    startActivity(intent);

                }
                else if(writingCard==true)
                {
                    writingCard = false;
                    WelcomeText.setText("Card Writing Deactivated.");
                }

            }
        });
        ViewLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(HomeScreenActivity.this, LibraryActivity.class);
                    startActivity(intent);
            }
        });
        DuelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenActivity.this, DuelActivity.class);
                startActivity(intent);
            }
        });
        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        theNFCAdapter = NfcAdapter.getDefaultAdapter(this);
        if (theNFCAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }

        if (!theNFCAdapter.isEnabled()) {
            WelcomeText.setText("NFC is disabled.");
        } else {
            WelcomeText.setText("NFC is enabled");
        }

        if(readingCard) {
            handleIntent(getIntent());
        }
    }


    private void handleIntent(Intent intent) {//this method is used to handle NFC discovery.

        if(readingCard) {
            String action = intent.getAction();
            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {//If an NFC tag is discovered


                String type = intent.getType();
                if (MIME_TEXT_PLAIN.equals(type)) {

                    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);//Gets the tag so it can be used in code.

                    new NdefReaderTask(this).execute(tag);//Calls a class to read the data on the class

                } else {
                    Log.d(TAG, "Wrong mime type: " + type);
                }
            } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

                // In case we would still use the Tech Discovered Intent
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                String[] techList = tag.getTechList();
                String searchedTech = Ndef.class.getName();

                for (String tech : techList) {
                    if (searchedTech.equals(tech)) {
                        new NdefReaderTask(this).execute(tag);
                        break;
                    }
                }
            } else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                String[] techList = tag.getTechList();
                String searchedTech = Ndef.class.getName();

                for (String tech : techList) {
                    if (searchedTech.equals(tech)) {
                        new NdefReaderTask(this).execute(tag);
                        break;
                    }
                }
            }
        }
    }



    @Override
    protected void onResume() { //Whist the application is running
        super.onResume();
        setupForegroundDispatch(this, theNFCAdapter); // set up the foreground dispatch system.
    }

    @Override
    protected void onPause() {

        stopForegroundDispatch(this, theNFCAdapter); //stop the foreground dispatch. This MUST be done before
        //the application is paused outright to avoid errors.

        super.onPause();//pause application. This can be run when the user minimises the application.
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }



    /**
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) { //declaration of the foreground dispatch. Takes in an activity and an NFC adapter.
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());//set the intent to the current activity and class.
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//give prioroty to this intent.

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);//get intent of an application which may handle an NFC intent.


        adapter.enableForegroundDispatch(activity, pendingIntent, null, null);//Starts foreground dispatch and therefore no other activity can handle NFC intents.
    }

    /**
     * @param activity The corresponding {@link BaseActivity} requesting to stop the foreground dispatch.
     * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }


    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        private Context mContext;
        public NdefReaderTask (Context context){
            mContext = context;
        }
        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            try {
                NdefRecord[] records = ndefMessage.getRecords();


                for (NdefRecord ndefRecord : records) {
                    if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                        try {
                            return readText(ndefRecord);
                        } catch (UnsupportedEncodingException e) {
                            Log.e(TAG, "Unsupported Encoding", e);
                        }
                    }
                }
            }
            catch (NullPointerException e )
            {
                Log.e(TAG, "Empty list");
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {

            byte[] payload = record.getPayload();

            // Get the Text Encoding
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }



        @Override
        protected void onPostExecute(String result) {
            if (result != null) {//if the tag is not null
                if(result.equals("Dark Magician") || result.equals("Blue Eyes White Dragon") || result.equals("Harpie Lady")) {//if the data is valid
                    readingCard = false;
                    //The following sets up the database in order to search it in a query
                    DBHandler dbHandler = new DBHandler(mContext, "CardDatabase1.s3db", null, 3);
                    try {
                        dbHandler.dbCreate();
                    } catch (IOException e) {

                    }
                    //Seaches the table containing all card data for an entry with a card name matching the data read from a tag.
                    CardClass foundItem = dbHandler.findCard(result);
                    if (foundItem.getName() != null) {  //if the database table containing all card data contains an entry with the card name matching the read data.
                        dbHandler.addCardOwned(foundItem);//Add the entry found to the database table containing the cards owned by the user
                        Toast.makeText(mContext, "Card Added to Library", Toast.LENGTH_SHORT).show();//Make a message pop up to let the user know a tag was written.
                        WelcomeText.setText("Card " + result + " Added to library. Please select an option.");//change the text view to inform the user of whats happened and what they can do.
                    }
                }
                else //if the data read is not recognised in the database table.
                {
                    readingCard = false;
                    WelcomeText.setText("Invalid Card Name. Please rewrite card and try again.");
                }

            }
            else // if the tag read is null
            {
                readingCard = false;
                WelcomeText.setText("The Read tag is empty. Please write data to it.");
            }
        }
    }





}






