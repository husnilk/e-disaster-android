package com.example.pelaporanbencana.model;

public class Kejadian {
    String id_disasters,
            id_urban_village,
            disasters_types,
            disasters_date,
            disasters_time,
            disasters_long,
            disasters_lat,
            disasters_village;

    public Kejadian() {

    }

    public Kejadian(String id_disasters, String id_urban_village, String disasters_types, String disasters_date, String disasters_time, String disasters_long, String disasters_lat, String disasters_village) {
        this.id_disasters = id_disasters;
        this.id_urban_village = id_urban_village;
        this.disasters_types = disasters_types;
        this.disasters_date = disasters_date;
        this.disasters_time = disasters_time;
        this.disasters_long = disasters_long;
        this.disasters_lat = disasters_lat;
        this.disasters_village = disasters_village;
    }

    public String getId_disasters() {
        return id_disasters;
    }

    public void setId_disasters(String id_disasters) {
        this.id_disasters = id_disasters;
    }

    public String getId_urban_village() {
        return id_urban_village;
    }

    public void setId_urban_village(String id_urban_village) {
        this.id_urban_village = id_urban_village;
    }

    public String getDisasters_types() {
        return disasters_types;
    }

    public void setDisasters_types(String disasters_types) {
        this.disasters_types = disasters_types;
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

    public String getDisasters_long() {
        return disasters_long;
    }

    public void setDisasters_long(String disasters_long) {
        this.disasters_long = disasters_long;
    }

    public String getDisasters_lat() {
        return disasters_lat;
    }

    public void setDisasters_lat(String disasters_lat) {
        this.disasters_lat = disasters_lat;
    }

    public String getDisasters_village() {
        return disasters_village;
    }

    public void setDisasters_village(String disasters_village) {
        this.disasters_village = disasters_village;
    }

}
