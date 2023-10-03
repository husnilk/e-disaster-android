package com.example.pelaporanbencana.model.VictimShowAllAsId;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

public class DataItem{

    @SerializedName("nik")
    private String nik;

    @SerializedName("victims_status")
    private String victimsStatus;

    @SerializedName("birthdate")
    private Date birthdate;

    @SerializedName("gender")
    private String gender;

    @SerializedName("name")
    private String name;

    @SerializedName("heir")
    private String heir;

    @SerializedName("address")
    private String address;

    public String getNik(){
        return nik;
    }

    public String getVictimsStatus(){
        return victimsStatus;
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

    public String getHeir() { return heir;}

    public String getAddress() { return address;}
}