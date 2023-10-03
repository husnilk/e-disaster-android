package com.example.pelaporanbencana.model.UrbanVillage;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UrbanVillageResponse{

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("success")
    private boolean success;

    public void setData(List<DataItem> data){
        this.data = data;
    }

    public List<DataItem> getData(){
        return data;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean isSuccess(){
        return success;
    }
}