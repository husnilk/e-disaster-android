package com.example.pelaporanbencana.model.EvacueeStore;

import com.google.gson.annotations.SerializedName;

public class EvacueeStoreResponse{

    @SerializedName("success")
    private boolean success;

    @SerializedName("dataPenduduk")
    private DataPenduduk dataPenduduk;

    @SerializedName("message")
    private String message;

    @SerializedName("dataPengungsi")
    private DataPengungsi dataPengungsi;

    public boolean isSuccess(){
        return success;
    }

    public DataPenduduk getDataPenduduk(){
        return dataPenduduk;
    }

    public String getMessage(){
        return message;
    }

    public DataPengungsi getDataPengungsi(){
        return dataPengungsi;
    }
}