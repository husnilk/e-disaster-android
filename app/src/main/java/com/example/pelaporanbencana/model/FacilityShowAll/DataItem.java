package com.example.pelaporanbencana.model.FacilityShowAll;

import com.google.gson.annotations.SerializedName;

public class DataItem{

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