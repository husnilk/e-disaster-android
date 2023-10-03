package com.example.pelaporanbencana.model.VictimStore;

import com.google.gson.annotations.SerializedName;

public class VictimStoreResponse {

    @SerializedName("dataKorban")
    private DataKorban dataKorban;

    @SerializedName("success")
    private boolean success;

    @SerializedName("dataPenduduk")
    private DataPenduduk dataPenduduk;

    @SerializedName("message")
    private String message;

    public void setDataKorban(DataKorban dataKorban){
        this.dataKorban = dataKorban;
    }

    public DataKorban getDataKorban(){
        return dataKorban;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean isSuccess(){
        return success;
    }

    public void setDataPenduduk(DataPenduduk dataPenduduk){
        this.dataPenduduk = dataPenduduk;
    }

    public DataPenduduk getDataPenduduk(){
        return dataPenduduk;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}