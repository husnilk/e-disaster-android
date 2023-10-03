package com.example.pelaporanbencana.model.ShelterShowallAsId;

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

    @SerializedName("jml_org")
    private int jmlOrg;

    @SerializedName("capacity")
    private int capacity;

    public void setIdShelter(String idShelter){
        this.idShelter = idShelter;
    }

    public String getIdShelter(){
        return idShelter;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
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

    public void setJmlOrg(int jmlOrg){
        this.jmlOrg = jmlOrg;
    }

    public int getJmlOrg(){
        return jmlOrg;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public int getCapacity(){
        return capacity;
    }
}