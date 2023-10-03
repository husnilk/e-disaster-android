package com.example.pelaporanbencana.model.DisasterStore;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("disasters_causes")
    private String disastersCauses;

    @SerializedName("disasters_date")
    private String disastersDate;

    @SerializedName("id_urban_village")
    private String idUrbanVillage;

    @SerializedName("disasters_effort")
    private String disastersEffort;

    @SerializedName("disasters_desc")
    private String disastersDesc;

    @SerializedName("disasters_potential")
    private String disastersPotential;

    @SerializedName("id_disasters")
    private String idDisasters;

    @SerializedName("disasters_impact")
    private String disastersImpact;

    @SerializedName("disasters_types")
    private String disastersTypes;

    @SerializedName("weather_conditions")
    private String weatherConditions;

    @SerializedName("disasters_village")
    private String disastersVillage;

    @SerializedName("disasters_lat")
    private String disastersLat;

    @SerializedName("disasters_long")
    private String disastersLong;

    @SerializedName("disasters_status")
    private String disastersStatus;

    @SerializedName("disasters_time")
    private String disastersTime;

    public void setDisastersCauses(String disastersCauses){
        this.disastersCauses = disastersCauses;
    }

    public String getDisastersCauses(){
        return disastersCauses;
    }

    public void setDisastersDate(String disastersDate){
        this.disastersDate = disastersDate;
    }

    public String getDisastersDate(){
        return disastersDate;
    }

    public void setIdUrbanVillage(String idUrbanVillage){
        this.idUrbanVillage = idUrbanVillage;
    }

    public Object getIdUrbanVillage(){
        return idUrbanVillage;
    }

    public void setDisastersEffort(String disastersEffort){
        this.disastersEffort = disastersEffort;
    }

    public String getDisastersEffort(){
        return disastersEffort;
    }

    public void setDisastersDesc(String disastersDesc){
        this.disastersDesc = disastersDesc;
    }

    public String getDisastersDesc(){
        return disastersDesc;
    }

    public void setDisastersPotential(String disastersPotential){
        this.disastersPotential = disastersPotential;
    }

    public String getDisastersPotential(){
        return disastersPotential;
    }

    public void setIdDisasters(String idDisasters){
        this.idDisasters = idDisasters;
    }

    public String getIdDisasters(){
        return idDisasters;
    }

    public void setDisastersImpact(String disastersImpact){
        this.disastersImpact = disastersImpact;
    }

    public String getDisastersImpact(){
        return disastersImpact;
    }

    public void setDisastersTypes(String disastersTypes){
        this.disastersTypes = disastersTypes;
    }

    public String getDisastersTypes(){
        return disastersTypes;
    }

    public void setWeatherConditions(String weatherConditions){
        this.weatherConditions = weatherConditions;
    }

    public String getWeatherConditions(){
        return weatherConditions;
    }

    public void setDisastersVillage(String disastersVillage){
        this.disastersVillage = disastersVillage;
    }

    public String getDisastersVillage(){
        return disastersVillage;
    }

    public void setDisastersLat(String disastersLat){
        this.disastersLat = disastersLat;
    }

    public String getDisastersLat(){
        return disastersLat;
    }

    public void setDisastersLong(String disastersLong){
        this.disastersLong = disastersLong;
    }

    public String getDisastersLong(){
        return disastersLong;
    }

    public void setDisastersStatus(String disastersStatus){
        this.disastersStatus = disastersStatus;
    }

    public String getDisastersStatus(){
        return disastersStatus;
    }

    public void setDisastersTime(String disastersTime){
        this.disastersTime = disastersTime;
    }

    public String getDisastersTime(){
        return disastersTime;
    }
}