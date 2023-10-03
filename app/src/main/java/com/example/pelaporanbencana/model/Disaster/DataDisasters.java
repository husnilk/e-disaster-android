package com.example.pelaporanbencana.model.Disaster;

import com.google.gson.annotations.SerializedName;

public class DataDisasters{

    @SerializedName("count")
    private int count;

    public int getCount(){
        return count;
    }
}