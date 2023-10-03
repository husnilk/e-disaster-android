package com.example.pelaporanbencana.model.ResourceShowa;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("id_resources")
    private int id_resources;

    @SerializedName("lack_of_resources")
    private int lackOfResources;

    @SerializedName("additional_info")
    private String additionalInfo;

    @SerializedName("resources_units")
    private String resourcesUnits;

    @SerializedName("resources_required")
    private int resources_required;

    @SerializedName("resources_available")
    private int resourcesAvailable;

    @SerializedName("resources_types")
    private String resourcesTypes;

    public int getId_resources() {
        return id_resources;
    }

    public int getResources_required() {
        return resources_required;
    }

    public int getLackOfResources(){
        return lackOfResources;
    }

    public String getAdditionalInfo(){
        return additionalInfo;
    }

    public String getResourcesUnits(){
        return resourcesUnits;
    }

    public int getResourcesAvailable(){
        return resourcesAvailable;
    }

    public String getResourcesTypes(){
        return resourcesTypes;
    }
}