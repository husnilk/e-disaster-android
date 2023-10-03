package com.example.pelaporanbencana.model.DamageShowall;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DamageShowallResponse{

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("success")
    private boolean success;

    public List<DataItem> getData(){
        return data;
    }

    public boolean isSuccess(){
        return success;
    }
}