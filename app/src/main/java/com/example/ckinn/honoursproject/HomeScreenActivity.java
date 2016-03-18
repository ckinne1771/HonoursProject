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
                if(readingCard==false)
                {
                    readingCard = true;
                    writingCard = false;
                    WelcomeText.setText("Card Reading Activated. Hold device over card to scan");
                }
               else if(readingCard==true)
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
                    /*
                    writingCard = true;
                    readingCard = false;
                    WelcomeText.setText("Card Writing Activated. Hold device over card to write");
                    */
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


    private void handleIntent(Intent intent) {

        if(readingCard) {
            String action = intent.getAction();
            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {


                String type = intent.getType();
                if (MIME_TEXT_PLAIN.equals(type)) {

                    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

                    new NdefReaderTask(this).execute(tag);

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



        else if(writingCard) {
            String action = intent.getAction();
            if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))
            {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                NdefMessage ndefMessage = createNdefmessage("Blue Eyes White Dragon");

                writeNdefMessage(tag, ndefMessage);
                WelcomeText.setText("Card Data Written");
                writingCard = false;

            }
            if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action))
            {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                NdefMessage ndefMessage = createNdefmessage("Blue Eyes White Dragon");

                writeNdefMessage(tag, ndefMessage);
                WelcomeText.setText("Card Data Written");
                writingCard = false;

            }

        }

    }



    @Override
    protected void onResume() {
        super.onResume();

        /**
         * It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.
         */
        setupForegroundDispatch(this,theNFCAdapter);
    }

    @Override
    protected void onPause() {
        /**
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         */
        stopForegroundDispatch(this, theNFCAdapter);

        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
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

        /*IntentFilter[] filters = new IntentFilter[2];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");

        }
        filters[1] = new IntentFilter();
        filters[1].addAction(NfcAdapter.ACTION_TECH_DISCOVERED);*/


        adapter.enableForegroundDispatch(activity, pendingIntent, null, null);
    }

    /**
     * @param activity The corresponding {@link BaseActivity} requesting to stop the foreground dispatch.
     * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
     */
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
            if(tag==null)
            {
                Toast.makeText(this, "tagObject cannot be null", Toast.LENGTH_SHORT).show();
                return;
            }

            Ndef ndef = Ndef.get(tag);

            if(ndef == null)
            {
                //format tag with the ndef format and writes the message
                formatTag(tag, ndefMessage);
            }
            else{
                ndef.connect();

                if(!ndef.isWritable())
                {
                    Toast.makeText(this, "Tag is not writable", Toast.LENGTH_SHORT).show();
                    ndef.close();
                    return;
                }

                ndef.writeNdefMessage(ndefMessage);
                ndef.close();

                Toast.makeText(this, "Tag Written!", Toast.LENGTH_SHORT).show();

            }
        }
        catch (Exception e)
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
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

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
            if (result != null) {
                readingCard = false;
                WelcomeText.setText("Read content: " + result);
                DBHandler dbHandler = new DBHandler(mContext, "CardDatabase1.s3db", null, 3);
                try {
                    dbHandler.dbCreate();
                }
                catch (IOException e)
                {

                }
                CardClass foundItem = dbHandler.findCard(result);
                if (foundItem.getName() != null)
                {
                    dbHandler.addCardOwned(foundItem);
                    Toast.makeText(mContext, "Card Added to Library", Toast.LENGTH_SHORT).show();
                    WelcomeText.setText(dbHandler.findCard(result).toString());
                }




            }
            else
            {
                readingCard = false;
                WelcomeText.setText("Tag was empty, but still read");
            }
        }
    }





}






