package com.example.pelaporanbencana.model.Disaster;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("district_name")
    private String districtName;

    @SerializedName("disasters_date")
    private String disastersDate;

    @SerializedName("urban_village_name")
    private String urbanVillageName;

    @SerializedName("id_disasters")
    private String idDisasters;

    @SerializedName("disasters_types")
    private String disastersTypes;

    @SerializedName("disasters_time")
    private String disastersTime;

    @SerializedName("disasters_village")
    private String disastersVillage;

    @SerializedName("sub_district_name")
    private String subDistrictName;

    @SerializedName("disasters_types_name")
    private String disasters_types_name;

    public String getDistrictName(){
        return districtName;
    }

    public String getDisastersDate(){
        return disastersDate;
    }

    public String getUrbanVillageName(){
        return urbanVillageName;
    }

    public String getIdDisasters(){
        return idDisasters;
    }

    public String getDisastersTypes(){
        return disastersTypes;
    }

    public String getDisastersTime(){
        return disastersTime;
    }

    public String getDisastersVillage(){
        return disastersVillage;
    }

    public String getSubDistrictName(){
        return subDistrictName;
    }

    public String getDisasters_types_name() {
        return disasters_types_name;
    }

    public void setDisasters_types_name(String disasters_types_name) {
        this.disasters_types_name = disasters_types_name;
    }
}