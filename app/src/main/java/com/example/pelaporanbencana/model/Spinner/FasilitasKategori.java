package com.example.pelaporanbencana.model.Spinner;

public class FasilitasKategori {
    int id_facilities;
    String facilities_category;

    public FasilitasKategori(int id_facilities, String facilities_category) {
        this.id_facilities = id_facilities;
        this.facilities_category = facilities_category;
    }

    public int getId_facilities() {
        return id_facilities;
    }

    public void setId_facilities(int id_facilities) {
        this.id_facilities = id_facilities;
    }

    public String getFacilities_category() {
        return facilities_category;
    }

    public void setFacilities_category(String facilities_category) {
        this.facilities_category = facilities_category;
    }

    @Override
    public String toString() {
        return facilities_category;
    }
}
