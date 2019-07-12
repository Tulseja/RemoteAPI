package com.remoteapi.nikhilkumar.remoteapi.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Invoice;
import com.remoteapi.nikhilkumar.remoteapi.responsePOJO.Product;

import java.util.ArrayList;
import java.util.List;

import static com.remoteapi.nikhilkumar.remoteapi.utils.DBConstantsKt.INVOICE_DATA;
import static com.remoteapi.nikhilkumar.remoteapi.utils.DBConstantsKt.INVOICE_ID;
import static com.remoteapi.nikhilkumar.remoteapi.utils.DBConstantsKt.INVOICE_TABLE;


public class InvoiceDBHelper extends BaseDBHelper {
    private static InvoiceDBHelper mHelper;

    private InvoiceDBHelper(Context context) {
        super(context);
    }

    public static InvoiceDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new InvoiceDBHelper(context.getApplicationContext());
        }
        return mHelper;
    }

    public List<Invoice> getInvoiceList(String tableName) {
        ArrayList<Invoice> invoiceList = new ArrayList<>();
        Cursor cursor = null;
        try {
            SQLiteDatabase db = getDB();
            cursor = db.query(tableName, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(INVOICE_ID);
                int jsonIndex = cursor.getColumnIndex(INVOICE_DATA);
                while (!cursor.isAfterLast()) {
                    String jsonString = cursor.getString(jsonIndex);
                    int id = cursor.getInt(index);
                    Invoice i = new Invoice();
                    i.setInvoiceId(id);
                    i.setProductListJson(jsonString);
                    invoiceList.add(i);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return invoiceList;
    }

    public String getProductListJson(int invoiceId){
        Cursor cursor = null;
        String jsonString  = "";
        try {
            SQLiteDatabase db = getDB();
            cursor = db.query(INVOICE_TABLE, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(INVOICE_ID);
                int jsonIndex = cursor.getColumnIndex(INVOICE_DATA);
                while (!cursor.isAfterLast()) {
                    int id = cursor.getInt(index);
                    if(id == invoiceId){
                       jsonString = cursor.getString(jsonIndex);
                    }

                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return jsonString ;
    }

    @NonNull
    public boolean insertInvoiceList(ArrayList<Product> productArrayList, String tableName) {
        ContentValues contentValues;
        Gson gson = new Gson();

        JsonElement element = gson.toJsonTree(productArrayList, new TypeToken<List<Product>>() {}.getType());

        if (! element.isJsonArray()) {
                // fail appropriately
            throw new IllegalArgumentException("Cant Populate JSON ");
        }

        JsonArray jsonArray = element.getAsJsonArray();
        contentValues = new ContentValues();
        contentValues.put(INVOICE_DATA, jsonArray.toString());
        long insertedId = getDB().insert(tableName, null, contentValues);
        Log.d(TAG, "Inserted/updated invoice id: " + insertedId);

        return true;
    }
}