package com.example.pelaporanbencana.model.ShelterShowa;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("id_shelter")
    private String idShelter;

    @SerializedName("long_loc")
    private String longLoc;

    @SerializedName("address")
    private String address;

    @SerializedName("lat_loc")
    private String latLoc;

    @SerializedName("hunian_types")
    private String hunianTypes;

    @SerializedName("location")
    private String location;

    @SerializedName("capacity")
    private int capacity;

    public String getIdShelter(){
        return idShelter;
    }

    public String getLongLoc(){
        return longLoc;
    }

    public String getAddress(){
        return address;
    }

    public String getLatLoc(){
        return latLoc;
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