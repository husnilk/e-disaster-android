package com.example.pelaporanbencana.model.EvacueeShowall;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DataItem{

    @SerializedName("nik")
    private String nik;

    @SerializedName("birthdate")
    private Date birthdate;

    @SerializedName("gender")
    private String gender;

    @SerializedName("name")
    private String name;

    public String getNik(){
        return nik;
    }

    public Date getBirthdate(){
        return birthdate;
    }

    public String getGender(){
        return gender;
    }

    public String getName(){
        return name;
    }
}