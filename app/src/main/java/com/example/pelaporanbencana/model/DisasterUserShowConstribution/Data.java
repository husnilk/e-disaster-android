package com.example.pelaporanbencana.model.DisasterUserShowConstribution;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("jumlahkontribusi")
    private int jumlahkontribusi;

    public int getJumlahkontribusi(){
        return jumlahkontribusi;
    }
}