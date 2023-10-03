package com.example.pelaporanbencana.model.ShelterStore;

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

    public void setIdShelter(String idShelter){
        this.idShelter = idShelter;
    }

    public String getIdShelter(){
        return idShelter;
    }

    public void setLongLoc(String longLoc){
        this.longLoc = longLoc;
    }

    public String getLongLoc(){
        return longLoc;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setLatLoc(String latLoc){
        this.latLoc = latLoc;
    }

    public String getLatLoc(){
        return latLoc;
    }

    public void setHunianTypes(String hunianTypes){
        this.hunianTypes = hunianTypes;
    }

    public String getHunianTypes(){
        return hunianTypes;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public int getCapacity(){
        return capacity;
    }
}