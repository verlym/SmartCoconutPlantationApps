package com.informaticsdeveloper.smartcoconutplantation.HistoryMenu;

import java.util.ArrayList;

public class History {
    private String location;
    private String waktu;
    private String tanggal;
    private String ph;
    private String vertility;
    private String moisture;
    private String light;

    public History(String location, String waktu, String tanggal,String ph, String moisture) {
        this.location = location;
        this.waktu = waktu;
        this.tanggal = tanggal;
        this.ph = ph;
//        this.vertility = vertility;
        this.moisture = moisture;
//        this.light = light;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getVertility() {
        return vertility;
    }

    public void setVertility(String vertility) {
        this.vertility = vertility;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public void kalibrasi(String key, String value){
        double result = 0.0;
        double f = Double.valueOf(value);
        if (key=="pH"){
            if (f<650){
                result = (1023-f)/99.198;
            }else{
                result = f/99.198;
            }
        }else if(key=="Mois"){
            result = f/102.337;
        }else if (key=="Fertility"){
            result = (f/321.13)*1.428571424;
        }else{
            result = f/102.3;
        }
    }
}
