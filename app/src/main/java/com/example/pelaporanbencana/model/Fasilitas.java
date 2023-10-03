package com.example.pelaporanbencana.model;

public class Fasilitas {
    int id_facilities;
    String id_disasters, facilities_category, description;

    public Fasilitas(int id_facilities, String id_disasters, String facilities_category, String description) {
        this.id_facilities = id_facilities;
        this.id_disasters = id_disasters;
        this.facilities_category = facilities_category;
        this.description = description;
    }

    public int getId_facilities() {
        return id_facilities;
    }

    public void setId_facilities(int id_facilities) {
        this.id_facilities = id_facilities;
    }

    public String getId_disasters() {
        return id_disasters;
    }

    public void setId_disasters(String id_disasters) {
        this.id_disasters = id_disasters;
    }

    public String getFacilities_category() {
        return facilities_category;
    }

    public void setFacilities_category(String facilities_category) {
        this.facilities_category = facilities_category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
