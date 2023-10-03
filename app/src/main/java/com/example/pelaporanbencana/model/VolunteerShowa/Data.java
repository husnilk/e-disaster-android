package com.example.pelaporanbencana.model.VolunteerShowa;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Data{

    @SerializedName("volunteers_name")
    private String volunteersName;

    @SerializedName("volunteers_gender")
    private String volunteersGender;

    @SerializedName("id_volunteer_org")
    private String idVolunteerOrg;

    @SerializedName("id_volunteers")
    private String idVolunteers;

    @SerializedName("volunteers_skill")
    private String volunteersSkill;

    @SerializedName("volunteers_birthdate")
    private String volunteersBirthdate;

    public String getVolunteersName(){
        return volunteersName;
    }

    public String getVolunteersGender(){
        return volunteersGender;
    }

    public String getIdVolunteerOrg(){
        return idVolunteerOrg;
    }

    public String getIdVolunteers(){
        return idVolunteers;
    }

    public String getVolunteersSkill(){
        return volunteersSkill;
    }

    public String getVolunteersBirthdate(){
        return volunteersBirthdate;
    }
}