package com.example.pelaporanbencana.model.SocAssistShowaResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SocAssistShowaResponse{

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