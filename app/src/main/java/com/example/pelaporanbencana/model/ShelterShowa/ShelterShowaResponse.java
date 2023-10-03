package com.example.pelaporanbencana.model.ShelterShowa;

import com.google.gson.annotations.SerializedName;

public class ShelterShowaResponse{

    @SerializedName("data")
    private Data data;

    @SerializedName("success")
    private boolean success;

    public Data getData(){
        return data;
    }

    public boolean isSuccess(){
        return success;
    }
}