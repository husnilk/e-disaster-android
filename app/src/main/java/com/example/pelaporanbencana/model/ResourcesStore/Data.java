package com.example.pelaporanbencana.model.ResourcesStore;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("lack_of_resources")
    private int lackOfResources;

    @SerializedName("id_resources")
    private int idResources;

    @SerializedName("resources_required")
    private int resourcesRequired;

    @SerializedName("additional_info")
    private String additionalInfo;

    @SerializedName("resources_available")
    private int resourcesAvailable;

    @SerializedName("id_disasters")
    private String idDisasters;

    public void setLackOfResources(int lackOfResources){
        this.lackOfResources = lackOfResources;
    }

    public int getLackOfResources(){
        return lackOfResources;
    }

    public void setIdResources(int idResources){
        this.idResources = idResources;
    }

    public int getIdResources(){
        return idResources;
    }

    public void setResourcesRequired(int resourcesRequired){
        this.resourcesRequired = resourcesRequired;
    }

    public int getResourcesRequired(){
        return resourcesRequired;
    }

    public void setAdditionalInfo(String additionalInfo){
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo(){
        return additionalInfo;
    }

    public void setResourcesAvailable(int resourcesAvailable){
        this.resourcesAvailable = resourcesAvailable;
    }

    public int getResourcesAvailable(){
        return resourcesAvailable;
    }

    public void setIdDisasters(String idDisasters){
        this.idDisasters = idDisasters;
    }

    public String getIdDisasters(){
        return idDisasters;
    }
}