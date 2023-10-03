package com.example.pelaporanbencana.model.FacilityShowa;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("id_facilities")
    private int idFacilities;

    @SerializedName("id_disasters")
    private String idDisasters;

    @SerializedName("description")
    private String description;

    @SerializedName("facilities_category")
    private String facilitiesCategory;

    public int getIdFacilities(){
        return idFacilities;
    }

    public String getIdDisasters(){
        return idDisasters;
    }

    public String getDescription(){
        return description;
    }

    public String getFacilitiesCategory(){
        return facilitiesCategory;
    }
}