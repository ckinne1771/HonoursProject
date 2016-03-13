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
import java.util.Locale;

/**
 * Created by ckinn on 23/02/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DB_NAME = "CardDatabase1.s3db";
    private static final String DB_PATH = "/data/data/com.example.ckinn.honoursproject/databases/";

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

    private final Context appContext;

    public DBHandler (Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version)
    {
        super(context, DB_NAME, factory, DATABASE_VERSION);
        this.appContext= context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
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
        db.execSQL(CREATE_CARDS_TABLE);
        db.execSQL(CREATE_CARDSOWNED_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        onCreate(db);
    }

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

    private Boolean dbCheck()
    {
        SQLiteDatabase db = null;
        try{
            String dbPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setVersion(3);
        }
        catch(SQLiteException e)
        {
            Log.e("SQLHelper", "Database not Found!");
        }
        if(db != null)
        {
            db.close();
        }

        return  db != null ? true : false;
    }

    private void copyDBFromAssets () throws IOException{
        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DB_PATH + DB_NAME;

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
        catch (IOException e)
        {
            throw new Error ("Problems copying DB!");
        }
    }

    public void addCard(CardClass card) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, card.getName());
        values.put(COLUMN_TYPE, card.getType());
        values.put(COLUMN_ATTACK, card.getAttack());
        values.put(COLUMN_CARDATTRIBUTE, card.getCardAttribute());
        values.put(COLUMN_CARDLEVEL, card.getCardLevel());
        values.put(COLUMN_DEFENCE, card.getDefence());
        values.put(COLUMN_CARDTEXT, card.getCardText());
        values.put(COLUMN_SET, card.getSet());
        values.put(COLUMN_CARDTYPE, card.getCardType());
//COmplete
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CARDS, null, values);
        db.close();
    }

    public void addCardOwned(CardClass card) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, card.getName());
        values.put(COLUMN_TYPE, card.getType());
        values.put(COLUMN_ATTACK, card.getAttack());
        values.put(COLUMN_CARDATTRIBUTE, card.getCardAttribute());
        values.put(COLUMN_CARDLEVEL, card.getCardLevel());
        values.put(COLUMN_DEFENCE, card.getDefence());
        values.put(COLUMN_CARDTEXT, card.getCardText());
        values.put(COLUMN_SET, card.getSet());
        values.put(COLUMN_CARDTYPE, card.getCardType());
//COmplete
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CARDSOWNED, null, values);
        db.close();
    }

    public CardClass findCard (String cardname)
    {

        String query = "Select * FROM " + TABLE_CARDS + " WHERE "
                + COLUMN_NAME + " =  \"" + cardname + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        CardClass card = new CardClass();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            card.setName(cursor.getString(0));
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

    public CardClass findCardOwned (String cardname)
    {

        String query = "Select * FROM " + TABLE_CARDSOWNED + " WHERE "
                + COLUMN_NAME + " =  \"" + cardname + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        CardClass card = new CardClass();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            card.setName(cursor.getString(0));
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
    public boolean deleteCard(String cardname) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_CARDS + " WHERE " + COLUMN_NAME + " =  \"" +
                cardname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        CardClass card = new CardClass();

        if (cursor.moveToFirst()) {
            card.setName(cursor.getString(0));
            db.delete(TABLE_CARDS, COLUMN_NAME + " = ?",
                    new String[] { String.valueOf(card.getName()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean deleteCardOwned(String cardname) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_CARDSOWNED + " WHERE " + COLUMN_NAME + " =  \"" +
                cardname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        CardClass card = new CardClass();

        if (cursor.moveToFirst()) {
            card.setName(cursor.getString(0));
            db.delete(TABLE_CARDS, COLUMN_NAME + " = ?",
                    new String[] { String.valueOf(card.getName()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}


