package com.example.pelaporanbencana.model.VolunteerStore;

import com.google.gson.annotations.SerializedName;

public class DataDetailRelawan{

    @SerializedName("assignment")
    private String assignment;

    @SerializedName("id_disasters")
    private String idDisasters;

    @SerializedName("id_volunteer")
    private String idVolunteer;

    @SerializedName("placement")
    private String placement;

    public String getAssignment(){
        return assignment;
    }

    public String getIdDisasters(){
        return idDisasters;
    }

    public String getIdVolunteer(){
        return idVolunteer;
    }

    public String getPlacement(){
        return placement;
    }
}