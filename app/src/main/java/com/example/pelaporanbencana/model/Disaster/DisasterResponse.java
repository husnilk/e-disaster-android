package com.example.pelaporanbencana.model.Disaster;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DisasterResponse{

    @SerializedName("dataDisasters")
    private DataDisasters dataDisasters;

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("success")
    private boolean success;

    @SerializedName("dataSelesai")
    private DataSelesai dataSelesai;

    @SerializedName("dataOnProgress")
    private DataOnProgress dataOnProgress;

    public DataDisasters getDataDisasters(){
        return dataDisasters;
    }

    public List<DataItem> getData(){
        return data;
    }

    public boolean isSuccess(){
        return success;
    }

    public DataSelesai getDataSelesai(){
        return dataSelesai;
    }

    public DataOnProgress getDataOnProgress(){
        return dataOnProgress;
    }
}