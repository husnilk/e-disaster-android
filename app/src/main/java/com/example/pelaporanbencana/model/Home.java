package com.example.pelaporanbencana.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Home {
    private String id_disasters;
    private String disasters_types;
    private String disasters_village;
    private String disasters_date;
    private String disasters_time;
    private String urban_village_name;
    private String sub_district_name;
    private String district_name;
    private String disasters_types_name;

    public Home(String id_disasters, String disasters_types, String disasters_village, String disasters_date, String disasters_time, String urban_village_name, String sub_district_name, String district_name, String disasters_types_name) {
        this.id_disasters = id_disasters;
        this.disasters_types = disasters_types;
        this.disasters_village = disasters_village;
        this.disasters_date = disasters_date;
        this.disasters_time = disasters_time;
        this.urban_village_name = urban_village_name;
        this.sub_district_name = sub_district_name;
        this.district_name = district_name;
        this.disasters_types_name = disasters_types_name;
    }

    public String getDisasters_types_name() {
        return disasters_types_name;
    }

    public void setDisasters_types_name(String disasters_types_name) {
        this.disasters_types_name = disasters_types_name;
    }

    public String getId_disasters() {
        return id_disasters;
    }

    public void setId_disasters(String id_disasters) {
        this.id_disasters = id_disasters;
    }

    public String getDisasters_types() {
        return disasters_types;
    }

    public void setDisasters_types(String disasters_types) {
        this.disasters_types = disasters_types;
    }

    public String getDisasters_village() {
        return disasters_village;
    }

    public void setDisasters_village(String disasters_village) {
        this.disasters_village = disasters_village;
    }

    public String getDisasters_date() {
        return disasters_date;
    }

    public void setDisasters_date(String disasters_date) {
        this.disasters_date = disasters_date;
    }

    public String getDisasters_time() {
        return disasters_time;
    }

    public void setDisasters_time(String disasters_time) {
        this.disasters_time = disasters_time;
    }

    public String getUrban_village_name() {
        return urban_village_name;
    }

    public void setUrban_village_name(String urban_village_name) {
        this.urban_village_name = urban_village_name;
    }

    public String getSub_district_name() {
        return sub_district_name;
    }

    public void setSub_district_name(String sub_district_name) {
        this.sub_district_name = sub_district_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    //    public Home(String kodeKejadian, String namakejadian, String lokasiKejadian, String tglKejadian) {
//        this.kodeKejadian = kodeKejadian;
//        this.namakejadian = namakejadian;
//        this.lokasiKejadian = lokasiKejadian;
//        this.tglKejadian = tglKejadian;
//    }

//    public String getKodeKejadian() {
//        return kodeKejadian;
//    }
//
//    public void setKodeKejadian(String kodeKejadian) {
//        this.kodeKejadian = kodeKejadian;
//    }
//
//    public String getNamakejadian() {
//        return namakejadian;
//    }
//
//    public void setNamakejadian(String namakejadian) {
//        this.namakejadian = namakejadian;
//    }
//
//    public String getLokasiKejadian() {
//        return lokasiKejadian;
//    }
//
//    public void setLokasiKejadian(String lokasiKejadian) {
//        this.lokasiKejadian = lokasiKejadian;
//    }
//
//    public String getTglKejadian() {
//        return tglKejadian;
//    }
//
//    public void setTglKejadian(String tglKejadian) {
//        this.tglKejadian = tglKejadian;
//    }
//
//    @Override
//    public String toString() {
//        return "Home{" +
//                "namakejadian='" + namakejadian + '\'' +
//                ", lokasiKejadian='" + lokasiKejadian + '\'' +
//                ", tglKejadian=" + tglKejadian +
//                '}';
//    }
}
