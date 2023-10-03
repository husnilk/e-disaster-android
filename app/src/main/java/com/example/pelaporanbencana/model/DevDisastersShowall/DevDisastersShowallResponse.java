package com.example.pelaporanbencana.model.DevDisastersShowall;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DevDisastersShowallResponse{

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