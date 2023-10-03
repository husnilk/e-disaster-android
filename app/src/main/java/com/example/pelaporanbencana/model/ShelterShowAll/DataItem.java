package com.example.pelaporanbencana.model.ShelterShowAll;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("id_shelter")
    private String idShelter;

    @SerializedName("address")
    private String address;

    @SerializedName("hunian_types")
    private String hunianTypes;

    @SerializedName("location")
    private String location;

    @SerializedName("capacity")
    private int capacity;

    public String getIdShelter(){
        return idShelter;
    }

    public String getAddress(){
        return address;
    }

    public String getHunianTypes(){
        return hunianTypes;
    }

    public String getLocation(){
        return location;
    }

    public int getCapacity(){
        return capacity;
    }
}