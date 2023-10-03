package com.example.pelaporanbencana.model.SocAssistShowaResponse;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("id_sa_types")
    private int idSaTypes;

    @SerializedName("donor")
    private String donor;

    @SerializedName("batch")
    private int batch;

    @SerializedName("social_assistance_amount")
    private int socialAssistanceAmount;

    @SerializedName("social_assistance_unit")
    private String socialAssistanceUnit;

    @SerializedName("date_received")
    private String dateReceived;

    @SerializedName("sa_types_name")
    private String saTypesName;

    public int getIdSaTypes(){
        return idSaTypes;
    }

    public String getDonor(){
        return donor;
    }

    public int getBatch(){
        return batch;
    }

    public int getSocialAssistanceAmount(){
        return socialAssistanceAmount;
    }

    public String getSocialAssistanceUnit(){
        return socialAssistanceUnit;
    }

    public String getDateReceived(){
        return dateReceived;
    }

    public String getSaTypesName(){
        return saTypesName;
    }
}