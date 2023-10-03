package com.example.pelaporanbencana.model.SocAssistDistributedShowa;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("id_sa_types")
    private int idSaTypes;

    @SerializedName("date_sent")
    private String dateSent;

    @SerializedName("recipient")
    private String recipient;

    @SerializedName("batch")
    private int batch;

    @SerializedName("sa_distributed_amount")
    private int saDistributedAmount;

    @SerializedName("sa_types_name")
    private String saTypesName;

    @SerializedName("sa_distributed_units")
    private String saDistributedUnits;

    public int getIdSaTypes(){
        return idSaTypes;
    }

    public String getDateSent(){
        return dateSent;
    }

    public String getRecipient(){
        return recipient;
    }

    public int getBatch(){
        return batch;
    }

    public int getSaDistributedAmount(){
        return saDistributedAmount;
    }

    public String getSaTypesName(){
        return saTypesName;
    }

    public String getSaDistributedUnits(){
        return saDistributedUnits;
    }
}