package com.remoteapi.nikhilkumar.remoteapi.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static com.remoteapi.nikhilkumar.remoteapi.utils.DBConstantsKt.DB_NAME;
import static com.remoteapi.nikhilkumar.remoteapi.utils.DBConstantsKt.INVOICE_DATA;
import static com.remoteapi.nikhilkumar.remoteapi.utils.DBConstantsKt.INVOICE_ID;
import static com.remoteapi.nikhilkumar.remoteapi.utils.DBConstantsKt.INVOICE_TABLE;


public class BaseDBHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase mDB;
    String TAG = getClass().getSimpleName();

    protected BaseDBHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String invoiceQuery = "CREATE TABLE IF NOT EXISTS "
                + INVOICE_TABLE
                + " ("
                + INVOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + INVOICE_DATA + " TEXT NOT NULL DEFAULT '' "
                + ")";
        db.execSQL(invoiceQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    protected synchronized SQLiteDatabase getDB() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = this.getWritableDatabase();
        }
        return mDB;
    }

    public void closeDB() {
        if (mDB != null && mDB.isOpen()) {
            mDB.close();
        }
    }
}
