package com.example.pelaporanbencana.model.SocialAssistanceStore;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Data{

    @SerializedName("id_sa_types")
    private int idSaTypes;

    @SerializedName("donor")
    private String donor;

    @SerializedName("batch")
    private int batch;

    @SerializedName("id_disasters")
    private String idDisasters;

    @SerializedName("social_assistance_amount")
    private int socialAssistanceAmount;

    @SerializedName("social_assistance_unit")
    private String socialAssistanceUnit;

    @SerializedName("date_received")
    private Date dateReceived;

    public int getIdSaTypes(){
        return idSaTypes;
    }

    public String getDonor(){
        return donor;
    }

    public int getBatch(){
        return batch;
    }

    public String getIdDisasters(){
        return idDisasters;
    }

    public int getSocialAssistanceAmount(){
        return socialAssistanceAmount;
    }

    public String getSocialAssistanceUnit(){
        return socialAssistanceUnit;
    }

    public Date getDateReceived(){
        return dateReceived;
    }
}