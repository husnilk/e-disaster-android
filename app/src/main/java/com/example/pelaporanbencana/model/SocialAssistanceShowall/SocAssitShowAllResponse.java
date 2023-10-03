package com.example.pelaporanbencana.model.SocialAssistanceShowall;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SocAssitShowAllResponse{

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