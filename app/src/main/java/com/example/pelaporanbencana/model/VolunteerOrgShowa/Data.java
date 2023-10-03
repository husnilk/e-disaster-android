package com.example.pelaporanbencana.model.VolunteerOrgShowa;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("volunteer_org_address")
    private String volunteerOrgAddress;

    @SerializedName("volunteer_org_status")
    private String volunteerOrgStatus;

    @SerializedName("id_volunteer_org")
    private String idVolunteerOrg;

    @SerializedName("volunteer_org_name")
    private String volunteerOrgName;

    public String getVolunteerOrgAddress(){
        return volunteerOrgAddress;
    }

    public String getVolunteerOrgStatus(){
        return volunteerOrgStatus;
    }

    public String getIdVolunteerOrg(){
        return idVolunteerOrg;
    }

    public String getVolunteerOrgName(){
        return volunteerOrgName;
    }
}