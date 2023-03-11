package com.example.tubesp3b;

import android.content.Context;
import android.content.SharedPreferences;

public class PenyimpanFilter {
    protected SharedPreferences sharedPref;
    protected final static String NAMA_SHARED_PREF="sp_filter";
    protected final static String KEY_FILTER="FILTER";

    public PenyimpanFilter(Context context){
        this.sharedPref = context.getSharedPreferences(NAMA_SHARED_PREF, 0);
    }

    public void saveFilter(String filter){
        SharedPreferences.Editor editor =this.sharedPref.edit();
        editor.putString(KEY_FILTER,filter);
        editor.commit();
    }

    public String getKeyFilter(){
        return sharedPref.getString(KEY_FILTER, "-");
    }
}
