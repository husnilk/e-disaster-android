package com.example.pelaporanbencana.model.DisasterShowInMenus;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("disasters_date")
    private String disastersDate;

//    @SerializedName("disasters_desc")
//    private String disastersDesc;

    @SerializedName("disasters_village")
    private String disastersVillage;

    @SerializedName("id_disasters")
    private String idDisasters;

    @SerializedName("id_urban_village")
    private String idUrbanVillage;

    @SerializedName("disasters_types_name")
    private String disastersTypes;

    @SerializedName("disasters_time")
    private String disastersTime;

    @SerializedName("disasters_status")
    private String disastersStatus;

    @SerializedName("disasters_lat")
    private String disastersLat;

    @SerializedName("disasters_long")
    private String disastersLong;

    @SerializedName("urban_village_name")
    private String urbanVillageName;

    public String getUrbanVillageName() {
        return urbanVillageName;
    }

    public String getDisastersStatus() {
        return disastersStatus;
    }

    public String getDisastersLat() {
        return disastersLat;
    }

    public String getDisastersLong() {
        return disastersLong;
    }

    public String getIdUrbanVillage() {
        return idUrbanVillage;
    }

    public String getDisastersVillage() {
        return disastersVillage;
    }

    public String getDisastersDate(){
        return disastersDate;
    }

//    public String getDisastersDesc(){
//        return disastersDesc;
//    }

    public String getIdDisasters(){
        return idDisasters;
    }

    public String getDisastersTypes(){
        return disastersTypes;
    }

    public String getDisastersTime(){
        return disastersTime;
    }
}