package com.example.ckinn.honoursproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ckinn on 23/02/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    //The variables used for the class.

    //These variables are used to define the path and name of the database, as well as its version
    private static final int DATABASE_VERSION = 3;
    private static final String DB_NAME = "CardDatabase1.s3db";
    private static final String DB_PATH = "/data/data/com.example.ckinn.honoursproject/databases/";

    //These variables are mostly used to aid in the creation of database tables.
    private static final String TABLE_CARDS = "Cards";
    private static final String TABLE_CARDSOWNED = "CardsOwned";

    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_CARDLEVEL = "CardLevel";
    public static final String COLUMN_CARDATTRIBUTE = "CardAttribute";
    public static final String COLUMN_CARDTYPE = "CardType";
    public static final String COLUMN_CARDTEXT = "CardText";
    public static final String COLUMN_ATTACK = "Attack";
    public static final String COLUMN_DEFENCE = "Defence";
    public static final String COLUMN_SET = "SetName";

    //this variable is used when the context of the application should be defined.
    private final Context appContext;

    //The constructor for this class. This takes in a context, the name of the database, the cursor
    //factory and the version of the database.
    public DBHandler (Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version)
    {
        super(context, DB_NAME, factory, DATABASE_VERSION);
        this.appContext= context;
    }



    //this method creates the two tables which are needed for this application.
    //These tables are the "cards" table and the "cardsOwned" table. The "cards" table holds data
    //on all of the available cards that the application can scan, while the "cardsOwned" table holds data
    //on the cards scanned by the user.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //strings a first created which form the SQL quesry that creates the tables.
        String CREATE_CARDS_TABLE = "CREATE TABLE " +
                TABLE_CARDS + "("
                + COLUMN_NAME + " STRING NOT NULL PRIMARY KEY," + COLUMN_TYPE
                + " STRING," + COLUMN_CARDLEVEL + " STRING," +
                COLUMN_CARDATTRIBUTE + " STRING," + COLUMN_CARDTYPE + " STRING," +
                COLUMN_CARDTEXT + " STRING," + COLUMN_ATTACK + " STRING," +
                COLUMN_DEFENCE + " STRING," + COLUMN_SET + " STRING" + ")";
        String CREATE_CARDSOWNED_TABLE = "CREATE TABLE " +
                TABLE_CARDSOWNED + "("
                + COLUMN_NAME + " STRING NOT NULL PRIMARY KEY," + COLUMN_TYPE
                + " STRING," + COLUMN_CARDLEVEL + " STRING," +
                COLUMN_CARDATTRIBUTE + " STRING," + COLUMN_CARDTYPE + " STRING," +
                COLUMN_CARDTEXT + " STRING," + COLUMN_ATTACK + " STRING," +
                COLUMN_DEFENCE + " STRING," + COLUMN_SET + " STRING" + ")";
        //This is were the SQL queries are executed.
        db.execSQL(CREATE_CARDS_TABLE);
        db.execSQL(CREATE_CARDSOWNED_TABLE);

    }

    //This method is called when the version of hte database changes. It will recreate the "cards" table.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        onCreate(db);
    }

    //This method is called to create the database by copying it from assets if it does not currently exist.
    public void dbCreate() throws IOException
    {
        boolean dbExist = dbCheck();
        if(!dbExist)
        {
            this.getReadableDatabase();
            try {
                copyDBFromAssets();
            }
            catch (IOException e)
            {
                throw  new Error("Error copying Database!");
            }
        }
    }

    //this method attempts to open the database to see if it exists. If no database is found, an exception is coaught and degigging information
    //is provided.
    private Boolean dbCheck()
    {
        SQLiteDatabase db = null;
        //the application attempts to open the database in the specified path.
        try{
            String dbPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setVersion(3);
        }
        //if no database is found.
        catch(SQLiteException e)
        {
            Log.e("SQLHelper", "Database not Found!");
        }
        //if the database is found.
        if(db != null)
        {
            db.close();
        }

        //returns true if the database is found, false if the database has not been found.
        return  db != null ? true : false;
    }

    //This method is used to copy the data iside the external database into the created database table
    private void copyDBFromAssets () throws IOException{
        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DB_PATH + DB_NAME;

        //attempts to copy database
        try {
            dbInput = appContext.getAssets().open(DB_NAME);
            dbOutput = new FileOutputStream(dbFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0)

            {
                dbOutput.write(buffer, 0, length);
            }
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
        }
        //if the database cannot be copied.
        catch (IOException e)
        {
            throw new Error ("Problems copying DB!");
        }
    }

    //this method is used to add a card to the "cardsOwned" table. Used when the NFC reading functionality reads a tag
    //containing correct data.
    public void addCardOwned(CardClass card) {

        //defines a new set of values for the database table..
        ContentValues values = new ContentValues();
        //The folowing adds values to each of the columns in the database table.
        values.put(COLUMN_NAME, card.getName());
        values.put(COLUMN_TYPE, card.getType());
        values.put(COLUMN_ATTACK, card.getAttack());
        values.put(COLUMN_CARDATTRIBUTE, card.getCardAttribute());
        values.put(COLUMN_CARDLEVEL, card.getCardLevel());
        values.put(COLUMN_DEFENCE, card.getDefence());
        values.put(COLUMN_CARDTEXT, card.getCardText());
        values.put(COLUMN_SET, card.getSet());
        values.put(COLUMN_CARDTYPE, card.getCardType());

        //get the database.
        SQLiteDatabase db = this.getWritableDatabase();

        //Inset the set of values as a new entry into the "cardsOwned" table.
        db.insert(TABLE_CARDSOWNED, null, values);
        //close the database.
        db.close();
    }

    //this method is called when an NFC tag is scanned, with "cardname" being the data held in the tag.
    public CardClass findCard (String cardname)
    {
        //The SQL querey which  searches the table for card names which match the data read.
        String query = "Select * FROM " + TABLE_CARDS + " WHERE "
                + COLUMN_NAME + " =  \"" + cardname + "\"";
        SQLiteDatabase db = this.getWritableDatabase();//Access the SQLite Database.

        Cursor cursor = db.rawQuery(query, null);//executes the query.


        CardClass card = new CardClass();//an instance of the card class is created so it can be put into the "CardsOwned" table

        if (cursor.moveToFirst()) { //Entered if a match is found.
            cursor.moveToFirst();
            card.setName(cursor.getString(0));//The following "sets" set the attributes of the instance of the card class to the mathed entry's attributes.
            card.setType(cursor.getString(1));
            card.setCardLevel(cursor.getString(2));
            card.setCardAttribute(cursor.getString(3));
            card.setCardType(cursor.getString(4));
            card.setCardText(cursor.getString(5));
            card.setAttack(cursor.getString(6));
            card.setDefence(cursor.getString(7));
            card.setSet(cursor.getString(8));

            cursor.close();
        } else {
            card = null;
        }
        db.close();
        return card;

    }

    //This method is used by the library class in order to find data stored in the "cardsOwned" table
    //This allows the data on the cards scanned by the user to be viewed.
    public CardClass findCardOwned (String cardname)
    {

        //The SQL querey which  searches the table for card names which match the data supplied.
        String query = "Select * FROM " + TABLE_CARDSOWNED + " WHERE "
                + COLUMN_NAME + " =  \"" + cardname + "\"";
        SQLiteDatabase db = this.getWritableDatabase(); //Access the SQLite Database

        Cursor cursor = db.rawQuery(query, null); //executes the query.


        CardClass card = new CardClass();//an instance of the card class is created in order to hold viewable data.

        if (cursor.moveToFirst()) { //Entered if a match is found.
            cursor.moveToFirst();
            card.setName(cursor.getString(0));//The following "sets" set the attributes of the instance of the card class to the mathed entry's attributes.
            card.setType(cursor.getString(1));
            card.setCardLevel(cursor.getString(2));
            card.setCardAttribute(cursor.getString(3));
            card.setCardType(cursor.getString(4));
            card.setCardText(cursor.getString(5));
            card.setAttack(cursor.getString(6));
            card.setDefence(cursor.getString(7));
            card.setSet(cursor.getString(8));

            cursor.close();
        } else {
            card = null;
        }
        db.close();
        return card;

    }


    //this method is used by the library class to pupulate the spinner with possible options.
    public String[] getSpinnerContent()
    {
        String query = "Select * FROM " + TABLE_CARDSOWNED; // the SQL query to search the "cardsOwned" table for card names.
        SQLiteDatabase db = this.getWritableDatabase();//gets the database
        Cursor cursor = db.rawQuery(query, null);//executes the query.
        ArrayList<String> spinnerContent = new ArrayList<String>(); //defines an array list of strings that will hold the data found in the query.
        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndexOrThrow("Name"));//sets the value of a string to the entry found in the name column
                spinnerContent.add(word); //adds the string to the array list.
            }while(cursor.moveToNext());
        }
        cursor.close();

        //Adds the arraylsit to an array of strings and returns this array.
        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);

        return allSpinner;
    }

}


