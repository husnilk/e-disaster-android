package com.example.pelaporanbencana.model.VolunteerOrgShowa;

import com.google.gson.annotations.SerializedName;

public class VolunteerOrgShowaResponse{

    @SerializedName("data")
    private Data data;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public Data getData(){
        return data;
    }

    public boolean isSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }
}