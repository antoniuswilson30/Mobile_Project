package com.example.tubesp3b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FeedReaderDbHelperPertemuan extends SQLiteOpenHelper  {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pertemuan.db";
    private static final String SQLCreate = "CREATE TABLE " + FeedReaderContractPertemuan.FeedEntryPertemuan.namaTable + " (" + FeedReaderContractPertemuan.FeedEntryPertemuan._ID + " INTEGER PRIMARY KEY, " + FeedReaderContractPertemuan.FeedEntryPertemuan.kolomIdDokter + " TEXT, "+ FeedReaderContractPertemuan.FeedEntryPertemuan.kolomNama + " TEXT, " + FeedReaderContractPertemuan.FeedEntryPertemuan.kolomWaktu + " TEXT, "+ FeedReaderContractPertemuan.FeedEntryPertemuan.kolomTanggal + " TEXT, "+ FeedReaderContractPertemuan.FeedEntryPertemuan.kolomKeluhan + " TEXT, "+ FeedReaderContractPertemuan.FeedEntryPertemuan.kolomStatus+" TEXT)";
    private static final String SQLDelete = "DROP TABLE IF EXISTS " + FeedReaderContractPertemuan.FeedEntryPertemuan.namaTable;
    public FeedReaderDbHelperPertemuan(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLDelete);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertPertemuan(int idDokter, String nama, String waktu,String tanggal, String keluhan, String status, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomIdDokter, String.valueOf(idDokter));
        values.put(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomNama, nama);
        values.put(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomWaktu, waktu);
        values.put(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomTanggal, tanggal);
        values.put(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomKeluhan, keluhan);
        values.put(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomStatus, status);
        long newRowId = db.insert(FeedReaderContractPertemuan.FeedEntryPertemuan.namaTable, null, values);
    }

    public List<Pertemuan> getPertemuans(SQLiteDatabase db, String filter){
        List<Pertemuan> temp = new ArrayList<>();
        String[] projection = {
                FeedReaderContractPertemuan.FeedEntryPertemuan._ID,
                FeedReaderContractPertemuan.FeedEntryPertemuan.kolomIdDokter,
                FeedReaderContractPertemuan.FeedEntryPertemuan.kolomNama,
                FeedReaderContractPertemuan.FeedEntryPertemuan.kolomWaktu,
                FeedReaderContractPertemuan.FeedEntryPertemuan.kolomTanggal,
                FeedReaderContractPertemuan.FeedEntryPertemuan.kolomKeluhan,
                FeedReaderContractPertemuan.FeedEntryPertemuan.kolomStatus
        };
        Cursor cursor;
        if(filter.equals("-")){
            cursor = db.query(
                    FeedReaderContractPertemuan.FeedEntryPertemuan.namaTable,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null              // The sort order
            );
        }else{
            String selection = FeedReaderContractPertemuan.FeedEntryPertemuan.kolomNama + " LIKE ?";
            String[] selectionArgs = {filter};
            cursor = db.query(
                    FeedReaderContractPertemuan.FeedEntryPertemuan.namaTable,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null              // The sort order
            );
        }
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContractPertemuan.FeedEntryPertemuan._ID));
            String namaDokter = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomNama));
            String waktu = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomWaktu));
            String tanggal = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomTanggal));
            String keluhan = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomKeluhan));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomStatus));
            String idDokter = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomIdDokter));
            temp.add(new Pertemuan(id, idDokter,namaDokter, keluhan, tanggal, waktu, status));
        }
        cursor.close();
        return temp;
    }

    public void doneStatus(SQLiteDatabase db, int id){
        String temp = "Sudah Konsultasi";
        ContentValues values = new ContentValues();
        values.put(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomStatus, temp);
        String selection = FeedReaderContractPertemuan.FeedEntryPertemuan._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        int count = db.update(
                FeedReaderContractPertemuan.FeedEntryPertemuan.namaTable,
                values,
                selection,
                selectionArgs);
    }

    public void undoneStats(SQLiteDatabase db, int id){
        String temp = "Belum Konsultasi";
        ContentValues values = new ContentValues();
        values.put(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomStatus, temp);
        String selection = FeedReaderContractPertemuan.FeedEntryPertemuan._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        int count = db.update(
                FeedReaderContractPertemuan.FeedEntryPertemuan.namaTable,
                values,
                selection,
                selectionArgs);
    }

    public void updateNama(SQLiteDatabase db, int idDokter, String namaBaru){
        ContentValues values = new ContentValues();
        values.put(FeedReaderContractPertemuan.FeedEntryPertemuan.kolomNama, namaBaru);
        String selection = FeedReaderContractPertemuan.FeedEntryPertemuan.kolomIdDokter + " LIKE ?";
        String[] selectionArgs = {String.valueOf(idDokter)};
        int count = db.update(
                FeedReaderContractPertemuan.FeedEntryPertemuan.namaTable,
                values,
                selection,
                selectionArgs);
    }
}
