package com.example.pelaporanbencana.model.VolunteerStore;

import com.google.gson.annotations.SerializedName;

public class VolunteerStoreResponse{

    @SerializedName("dataRelawan")
    private DataRelawan dataRelawan;

    @SerializedName("success")
    private boolean success;

    @SerializedName("dataDetailRelawan")
    private DataDetailRelawan dataDetailRelawan;

    @SerializedName("message")
    private String message;

    public DataRelawan getDataRelawan(){
        return dataRelawan;
    }

    public boolean isSuccess(){
        return success;
    }

    public DataDetailRelawan getDataDetailRelawan(){
        return dataDetailRelawan;
    }

    public String getMessage(){
        return message;
    }
}