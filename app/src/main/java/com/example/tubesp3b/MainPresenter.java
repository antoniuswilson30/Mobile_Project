package com.example.tubesp3b;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.provider.Telephony;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {
    private List<Doctor> doctors;
    private List<Pertemuan> pertemuans;
    private IMainActivity ui;
    private FeedReaderDbHelper dbHelper;
    private FeedReaderDbHelperPertemuan dbHelperPertemuan;
    private PenyimpanFilter filter;

    public MainPresenter(IMainActivity ui, FeedReaderDbHelper dbHelper, FeedReaderDbHelperPertemuan dbHelperPertemuan, PenyimpanFilter filter){
        this.dbHelper = dbHelper;
        this.dbHelperPertemuan = dbHelperPertemuan;
        this.filter = filter;
        doctors = new ArrayList<>();
        pertemuans = new ArrayList<>();
        this.ui = ui;
    }

    public String getFilter(){
        return this.filter.getKeyFilter();
    }

    public void setFilter(String filter){
        this.filter.saveFilter(filter);
        SQLiteDatabase db = dbHelperPertemuan.getReadableDatabase();
        this.pertemuans.clear();
        this.pertemuans.addAll(dbHelperPertemuan.getPertemuans(db, getFilter()));
        ui.updatePertemuans(this.pertemuans);
    }

    public void updateDoctors(String nama, String spesialis, String nomor){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.insertDoctor(nama,spesialis,nomor,db);
        db = dbHelper.getReadableDatabase();
        this.doctors.clear();
        this.doctors.addAll(dbHelper.getDoctor(db));
        ui.updateDoctor(this.doctors);
    }

    public void updatePertemuans(int idDokter, String nama, String keluhan, String waktu, String tanggal, String status){
        SQLiteDatabase db = dbHelperPertemuan.getWritableDatabase();
        dbHelperPertemuan.insertPertemuan(idDokter,nama, waktu, tanggal, keluhan, status, db);
        db = dbHelperPertemuan.getReadableDatabase();
        this.pertemuans.clear();
        this.pertemuans.addAll(dbHelperPertemuan.getPertemuans(db, getFilter()));
        ui.updatePertemuans(this.pertemuans);
    }

    public int getIdDoctor(String nama){
        int temp = 0;
        for(int i =0; i < this.doctors.size(); i++){
            if(this.doctors.get(i).nama.equals(nama)){
                temp = this.doctors.get(i).id;
            }
        }
        return  temp;
    }

    public void loadData(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        dbHelper.onUpgrade(db, 0,1);
        this.doctors.clear();
        this.doctors.addAll(dbHelper.getDoctor(db));
        ui.loadDoctor(this.doctors);
    }

    public void loadDataPertemuan(){
        SQLiteDatabase db = dbHelperPertemuan.getReadableDatabase();
//        dbHelperPertemuan.onUpgrade(db, 0, 1);
        this.pertemuans.clear();
        this.pertemuans.addAll(dbHelperPertemuan.getPertemuans(db, getFilter()));
        ui.loadPertemuans(this.pertemuans);
    }

    public void doneStatus(int i){
        SQLiteDatabase db = dbHelperPertemuan.getWritableDatabase();
        dbHelperPertemuan.doneStatus(db, i);
        this.pertemuans.clear();
        this.pertemuans.addAll(dbHelperPertemuan.getPertemuans(db, getFilter()));
        ui.updatePertemuans(this.pertemuans);
    }

    public void undoneStatus(int i){
        SQLiteDatabase db = dbHelperPertemuan.getWritableDatabase();
        dbHelperPertemuan.undoneStats(db, i);
        this.pertemuans.clear();
        this.pertemuans.addAll(dbHelperPertemuan.getPertemuans(db, getFilter()));
        ui.updatePertemuans(this.pertemuans);
    }

    public void editDoctor(String nama, String spesialis, String nomor, int i){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.editDoctor(nama, spesialis,nomor,db, i);
        SQLiteDatabase dbPertemuan = dbHelperPertemuan.getWritableDatabase();
        dbHelperPertemuan.updateNama(dbPertemuan, i, nama);
        this.doctors.clear();
        this.doctors.addAll(dbHelper.getDoctor(db));
        this.pertemuans.clear();
        this.pertemuans.addAll(dbHelperPertemuan.getPertemuans(dbPertemuan, getFilter()));
        ui.updateDoctor(this.doctors);
        this.filter.saveFilter("-");
    }

    public void deleteDoctor(int i){
        String selection = FeedReaderContract.FeedEntry._ID+ " LIKE ? ";
        String[] selectionArgs = { String.valueOf(doctors.get(i).id) };
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = db.delete(FeedReaderContract.FeedEntry.namaTable, selection,selectionArgs);
        doctors.remove(i);
        ui.updateDoctor(this.doctors);
    }

    public void deletePertemuan(int i){
        String selection = FeedReaderContractPertemuan.FeedEntryPertemuan._ID+ " LIKE ? ";
        String[] selectionArgs = { String.valueOf(pertemuans.get(i).id) };
        SQLiteDatabase db = dbHelperPertemuan.getWritableDatabase();
        int deletedRows = db.delete(FeedReaderContractPertemuan.FeedEntryPertemuan.namaTable, selection,selectionArgs);
        pertemuans.remove(i);
        ui.updatePertemuans(this.pertemuans);
    }

    public List<Doctor> getDoctors(){
        return this.doctors;
    }

    public List<Pertemuan> getPertemuans() {
        return this.pertemuans;
    }

    public String[] getNamaDoctor(){
        String[] ans = new String[this.doctors.size()];
        for(int i =0; i < this.doctors.size();i++){
            ans[i] = this.doctors.get(i).nama;
        }
        return ans;
    }

    public String[] getNamaSearch(){
        String[] ans = new String[this.doctors.size()+1];
        ans[0] = "-";
        for(int i =0; i < this.doctors.size();i++){
            ans[i+1] = this.doctors.get(i).nama;
        }
        return ans;
    }
    public void changePage(String title){
        ui.changePage(title);
    }
    public void closeDialog(){
        ui.closeDialog();
    }
    public void closeDialogPertemuan(){
        ui.closeDialogPertemuan();
    }
    public void closeDialogEdit(){
        ui.closeDialogEdit();
    }
    public void closeApp(){
        ui.closeApp();
    }
    public void dialogDoktor(){
        ui.dialogDoktor();
    }
    public void dialogDoktorEdit(int id){
        Doctor temp = this.doctors.get(id);
        ui.dialogDoktorEdit(temp);
    }
    public void dialogPertemuan(int i){
        ui.dialogPertemuan(this.pertemuans.get(i));
    }


}
