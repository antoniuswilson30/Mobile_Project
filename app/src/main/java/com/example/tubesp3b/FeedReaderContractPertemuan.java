package com.example.tubesp3b;

import android.provider.BaseColumns;

public class FeedReaderContractPertemuan {
    private FeedReaderContractPertemuan(){
    }

    public static class FeedEntryPertemuan implements BaseColumns {
        public static final String namaTable ="Pertemuan";
        public static final String kolomNama = "Nama";
        public static final String kolomSpesialis = "Spesialis";
        public static final String kolomTanggal = "Tanggal";
        public static final String kolomWaktu = "Waktu";
        public static final String kolomKeluhan = "Keluhan";
        public static final String kolomStatus = "Status";
        public static final String kolomIdDokter = "IdDokter";
    }
}
