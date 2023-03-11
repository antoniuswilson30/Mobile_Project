package com.example.tubesp3b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Doctor.db";
    private static final String SQLCreate = "CREATE TABLE " + FeedReaderContract.FeedEntry.namaTable + " (" + FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY, " + FeedReaderContract.FeedEntry.kolomNama + " TEXT, " + FeedReaderContract.FeedEntry.kolomSpesialis +" TEXT, " + FeedReaderContract.FeedEntry.kolomNomor +" TEXT)";
    private static final String SQLDelete = "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.namaTable;
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLCreate);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQLDelete);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertDoctor(String nama, String spesialis,String nomor, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.kolomNama, nama);
        values.put(FeedReaderContract.FeedEntry.kolomSpesialis, spesialis);
        values.put(FeedReaderContract.FeedEntry.kolomNomor, nomor);
        long newRowId = db.insert(FeedReaderContract.FeedEntry.namaTable, null, values);
    }

    public void editDoctor(String nama, String spesialis,String nomor, SQLiteDatabase db, int id){
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.kolomNama,
                FeedReaderContract.FeedEntry.kolomSpesialis,
                FeedReaderContract.FeedEntry.kolomNomor
        };
        String selection = FeedReaderContract.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.namaTable,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.kolomNama, nama);
        values.put(FeedReaderContract.FeedEntry.kolomSpesialis, spesialis);
        values.put(FeedReaderContract.FeedEntry.kolomNomor, nomor);
        int count = db.update(
                FeedReaderContract.FeedEntry.namaTable,
                values,
                selection,
                selectionArgs);
    }

    public List<Doctor> getDoctor(SQLiteDatabase db){
        List<Doctor> temp = new ArrayList<>();
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.kolomNama,
                FeedReaderContract.FeedEntry.kolomSpesialis,
                FeedReaderContract.FeedEntry.kolomNomor
        };

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.namaTable,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            String namaDokter = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.kolomNama));
            String namaSpesialis = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.kolomSpesialis));
            String nomor = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.kolomNomor));
            temp.add(new Doctor(id, namaDokter,namaSpesialis, nomor));
        }
        cursor.close();
        return temp;
    }
}

