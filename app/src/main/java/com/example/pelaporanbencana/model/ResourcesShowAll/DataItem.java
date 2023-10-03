package com.example.pelaporanbencana.model.ResourcesShowAll;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("lack_of_resources")
    private int lackOfResources;

    @SerializedName("id_resources")
    private int idResources;

    @SerializedName("additional_info")
    private String additionalInfo;

    @SerializedName("id_disaster_resources")
    private int idDisasterResources;

    @SerializedName("resources_units")
    private String resourcesUnits;

    @SerializedName("resources_available")
    private int resourcesAvailable;

    @SerializedName("resources_types")
    private String resourcesTypes;

    public int getLackOfResources(){
        return lackOfResources;
    }

    public int getIdResources(){
        return idResources;
    }

    public String getAdditionalInfo(){
        return additionalInfo;
    }

    public int getIdDisasterResources(){
        return idDisasterResources;
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