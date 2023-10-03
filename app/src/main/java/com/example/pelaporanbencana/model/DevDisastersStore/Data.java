package com.example.pelaporanbencana.model.DevDisastersStore;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("disasters_causes")
    private String disastersCauses;

    @SerializedName("disasters_date")
    private String disastersDate;

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

    @SerializedName("disasters_time")
    private String disastersTime;

    @SerializedName("weather_conditions")
    private String weatherConditions;

    public String getDisastersCauses(){
        return disastersCauses;
    }

    public String getDisastersDate(){
        return disastersDate;
    }

    public String getDisastersEffort(){
        return disastersEffort;
    }

    public String getDisastersDesc(){
        return disastersDesc;
    }

    public String getDisastersPotential(){
        return disastersPotential;
    }

    public String getIdDisasters(){
        return idDisasters;
    }

    public String getDisastersImpact(){
        return disastersImpact;
    }

    public String getDisastersTime(){
        return disastersTime;
    }

    public String getWeatherConditions(){
        return weatherConditions;
    }
}