package com.example.tubesp3b;


import android.provider.BaseColumns;

public  final class FeedReaderContract{
    private FeedReaderContract(){
    }

    public static class FeedEntry implements BaseColumns {
        public static final String namaTable ="Doctor";
        public static final String kolomNama = "Nama";
        public static final String kolomSpesialis = "Spesialis";
        public static final String kolomNomor = "Nomor";
    }
}
