package com.example.pelaporanbencana.model.UrbanVillage;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("district_name")
    private String districtName;

    @SerializedName("urban_village_name")
    private String urbanVillageName;

    @SerializedName("sub_district_name")
    private String subDistrictName;

    @SerializedName("province_name")
    private String provinceName;

    @SerializedName("id_urban_village")
    private String idUrbanVillage;

    public void setDistrictName(String districtName){
        this.districtName = districtName;
    }

    public String getDistrictName(){
        return districtName;
    }

    public void setUrbanVillageName(String urbanVillageName){
        this.urbanVillageName = urbanVillageName;
    }

    public String getUrbanVillageName(){
        return urbanVillageName;
    }

    public void setSubDistrictName(String subDistrictName){
        this.subDistrictName = subDistrictName;
    }

    public String getIdUrbanVillageName(){
        return idUrbanVillage;
    }

    public String getSubDistrictName(){
        return subDistrictName;
    }

    public void setProvinceName(String provinceName){
        this.provinceName = provinceName;
    }

    public String getProvinceName(){
        return provinceName;
    }
}