package com.example.pelaporanbencana.model.DisasterUserShowConstribution;

import com.google.gson.annotations.SerializedName;

public class ShowContributionResponse{

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