package com.example.pelaporanbencana.model.VictimStore;

import com.google.gson.annotations.SerializedName;

public class DataPenduduk{

    @SerializedName("nik")
    private String nik;

    @SerializedName("id_disasters")
    private String idDisasters;

    public void setNik(String nik){
        this.nik = nik;
    }

    public String getNik(){
        return nik;
    }

    public void setIdDisasters(String idDisasters){
        this.idDisasters = idDisasters;
    }

    public String getIdDisasters(){
        return idDisasters;
    }
}