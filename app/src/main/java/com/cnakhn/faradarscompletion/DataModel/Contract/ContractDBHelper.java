package com.cnakhn.faradarscompletion.DataModel.Contract;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContractDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "numberDb";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE = "CREATE TABLE " + Contract.TABLE_NAME +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," + Contract.INCOMING_NUMBER + " TEXT);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Contract.TABLE_NAME;

    public ContractDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void saveNumber(String number, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(Contract.INCOMING_NUMBER, number);
        database.insert(Contract.TABLE_NAME, null, values);
    }

    public Cursor readNumber(SQLiteDatabase database) {
        String[] projection = {"id", Contract.INCOMING_NUMBER};
        return (database.query(Contract.TABLE_NAME, projection, null, null, null, null, null));
    }
}
