package com.example.pelaporanbencana.model.AffectedStore;

import com.google.gson.annotations.SerializedName;

public class DataPenduduk {

    @SerializedName("nik")
    private String nik;

    @SerializedName("id_disasters")
    private String idDisasters;

    public String getNik(){
        return nik;
    }

    public String getIdDisasters(){
        return idDisasters;
    }
}