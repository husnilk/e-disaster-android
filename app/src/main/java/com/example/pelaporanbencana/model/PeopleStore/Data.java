package com.example.pelaporanbencana.model.PeopleStore;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("nik")
    private String nik;

    @SerializedName("address")
    private String address;

    @SerializedName("birthdate")
    private String birthdate;

    @SerializedName("gender")
    private String gender;

    @SerializedName("heir")
    private String heir;

    @SerializedName("name")
    private String name;

    @SerializedName("picture")
    private String picture;

    public String getNik(){
        return nik;
    }

    public String getAddress(){
        return address;
    }

    public String getBirthdate(){
        return birthdate;
    }

    public String getGender(){
        return gender;
    }

    public String getHeir(){
        return heir;
    }

    public String getName(){
        return name;
    }

    public String getPicture(){
        return picture;
    }
}