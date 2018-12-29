package com.informaticsdeveloper.smartcoconutplantation.Model;

public class Plants {
    private String nama,ph,moisture,light,fertility;

    public Plants(String nama, String ph, String moisture, String light, String fertility) {
        this.nama = nama;
        this.ph = ph;
        this.moisture = moisture;
        this.light = light;
        this.fertility = fertility;
    }

    public Plants(String nama, String ph, String moisture, String light) {
        this.nama = nama;
        this.ph = ph;
        this.moisture = moisture;
        this.light = light;
    }

    public Plants(String nama, String ph, String moisture) {
        this.nama = nama;
        this.ph = ph;
        this.moisture = moisture;
    }

    public Plants(String nama, String ph) {
        this.nama = nama;
        this.ph = ph;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
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

    public String getFertility() {
        return fertility;
    }

    public void setFertility(String fertility) {
        this.fertility = fertility;
    }
}
