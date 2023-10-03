package com.example.pelaporanbencana.model.VictimStore;

import com.google.gson.annotations.SerializedName;

public class DataKorban{

    @SerializedName("nik")
    private String nik;

    @SerializedName("victims_status")
    private String victimsStatus;

    @SerializedName("medical_status")
    private String medicalStatus;

    @SerializedName("additional_info")
    private String additionalInfo;

    @SerializedName("id_disasters")
    private String idDisasters;

    @SerializedName("hospital")
    private String hospital;

    public void setNik(String nik){
        this.nik = nik;
    }

    public String getNik(){
        return nik;
    }

    public void setVictimsStatus(String victimsStatus){
        this.victimsStatus = victimsStatus;
    }

    public String getVictimsStatus(){
        return victimsStatus;
    }

    public void setMedicalStatus(String medicalStatus){
        this.medicalStatus = medicalStatus;
    }

    public String getMedicalStatus(){
        return medicalStatus;
    }

    public void setAdditionalInfo(String additionalInfo){
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo(){
        return additionalInfo;
    }

    public void setIdDisasters(String idDisasters){
        this.idDisasters = idDisasters;
    }

    public String getIdDisasters(){
        return idDisasters;
    }

    public void setHospital(String hospital){
        this.hospital = hospital;
    }

    public String getHospital(){
        return hospital;
    }
}