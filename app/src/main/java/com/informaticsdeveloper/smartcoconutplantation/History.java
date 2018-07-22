package com.informaticsdeveloper.smartcoconutplantation;

import java.util.ArrayList;

public class History {

    public History(String lokasi, String tanggal, String waktu) {
        Lokasi = lokasi;
        Tanggal = tanggal;
        Waktu = waktu;
    }

    private static String Lokasi;
    private static String Tanggal;

    public static String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public static String getLokasi() {
        return Lokasi;
    }

    public void setLokasi(String lokasi) {
        Lokasi = lokasi;
    }

    public static String getWaktu() {
        return Waktu;
    }

    public void setWaktu(String waktu) {
        Waktu = waktu;
    }

    private static String Waktu;
}
