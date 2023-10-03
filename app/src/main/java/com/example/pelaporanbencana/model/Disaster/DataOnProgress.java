package com.example.pelaporanbencana.model.Disaster;

import com.google.gson.annotations.SerializedName;

public class DataOnProgress{

    @SerializedName("jumlahkontribusi")
    private int jumlahkontribusi;

    public int getJumlahkontribusi(){
        return jumlahkontribusi;
    }
}