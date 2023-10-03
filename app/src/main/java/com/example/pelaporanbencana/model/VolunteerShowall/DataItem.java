package com.example.pelaporanbencana.model.VolunteerShowall;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DataItem{

    @SerializedName("volunteers_name")
    private String volunteersName;

    @SerializedName("volunteers_gender")
    private String volunteersGender;

    @SerializedName("id_volunteers")
    private String idVolunteers;

    @SerializedName("volunteers_skill")
    private String volunteersSkill;

    @SerializedName("volunteers_birthdate")
    private Date volunteersBirthdate;

    public String getVolunteersName(){
        return volunteersName;
    }

    public String getVolunteersGender(){
        return volunteersGender;
    }

    public String getIdVolunteers(){
        return idVolunteers;
    }

    public String getVolunteersSkill(){
        return volunteersSkill;
    }

    public Date getVolunteersBirthdate(){
        return volunteersBirthdate;
    }
}