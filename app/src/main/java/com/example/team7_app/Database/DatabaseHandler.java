package com.example.team7_app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_FILES = "files";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FILE = "file";
    private static final String KEY_PATH = "originalPath";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FILES + "("
                + KEY_FILE + " FILE," + KEY_PATH + " PATH" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILES);

        // Create tables again
        onCreate(db);
    }

    public void insertFile(File mFile) {
        SQLiteDatabase db = this.getWritableDatabase();
        String nullColumnHack = null; // Allow null value
        ContentValues values = new ContentValues();
        if(mFile != null) {
         values.put(KEY_FILE, mFile.getName());
         values.put(KEY_PATH, mFile.getAbsolutePath());

         db.insert(TABLE_FILES, nullColumnHack, values);
         }
        db.close();
    }

    public MyFile getPath(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] fields = {KEY_FILE, KEY_PATH};
        String criterials = KEY_FILE + "=?";
        String[] parameters = {String.valueOf(name)};
        String groupby = null;
        String having = null;
        String orderby = null;
        Cursor cursor = db.query(TABLE_FILES, fields, criterials,
                parameters, groupby, having, orderby);
        if (cursor != null)
            cursor.moveToFirst();
        MyFile myFile = new MyFile(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2));
        return myFile;
    }

}