package com.example.pelaporanbencana.model.ShelterShowallAsId;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ShelterShowallAsIdResponse {

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