package com.example.pelaporanbencana.model.EvacueeStore;

import com.google.gson.annotations.SerializedName;

public class DataPengungsi{

    @SerializedName("id_shelter")
    private String idShelter;

    @SerializedName("nik")
    private String nik;

    @SerializedName("id_disasters")
    private String idDisasters;

    public String getIdShelter(){
        return idShelter;
    }

    public String getNik(){
        return nik;
    }

    public String getIdDisasters(){
        return idDisasters;
    }
}