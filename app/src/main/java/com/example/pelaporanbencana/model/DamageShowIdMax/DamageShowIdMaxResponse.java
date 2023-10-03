package com.example.pelaporanbencana.model.DamageShowIdMax;

import com.google.gson.annotations.SerializedName;

public class DamageShowIdMaxResponse{

    @SerializedName("data")
    private int data;

    @SerializedName("success")
    private boolean success;

    public int getData(){
        return data;
    }

    public boolean isSuccess(){
        return success;
    }
}