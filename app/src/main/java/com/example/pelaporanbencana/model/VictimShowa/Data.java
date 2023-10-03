package com.example.pelaporanbencana.model.VictimShowa;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("nik")
    private String nik;

    @SerializedName("victims_status")
    private String victimsStatus;

    @SerializedName("medical_status")
    private String medicalStatus;

    @SerializedName("additional_info")
    private String additionalInfo;

    @SerializedName("id_victims")
    private String idVictims;

    @SerializedName("id_disasters")
    private String idDisasters;

    @SerializedName("hospital")
    private String hospital;

    public String getNik(){
        return nik;
    }

    public String getVictimsStatus(){
        return victimsStatus;
    }

    public String getMedicalStatus(){
        return medicalStatus;
    }

    public String getAdditionalInfo(){
        return additionalInfo;
    }

    public String getIdVictims(){
        return idVictims;
    }

    public String getIdDisasters(){
        return idDisasters;
    }

    public String getHospital(){
        return hospital;
    }
}