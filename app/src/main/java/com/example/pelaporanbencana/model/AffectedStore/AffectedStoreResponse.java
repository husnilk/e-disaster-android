package com.example.pelaporanbencana.model.AffectedStore;

import com.google.gson.annotations.SerializedName;

public class AffectedStoreResponse{

    @SerializedName("dataTerdampak")
    private DataTerdampak dataTerdampak;

    @SerializedName("success")
    private boolean success;

    @SerializedName("dataPenduduk")
    private DataPenduduk dataPenduduk;

    @SerializedName("message")
    private String message;

    public DataTerdampak getDataTerdampak(){
        return dataTerdampak;
    }

    public boolean isSuccess(){
        return success;
    }

    public DataPenduduk getDataPenduduk(){
        return dataPenduduk;
    }

    public String getMessage(){
        return message;
    }
}