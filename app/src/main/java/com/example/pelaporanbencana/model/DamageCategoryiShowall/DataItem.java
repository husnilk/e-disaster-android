package com.example.pelaporanbencana.model.DamageCategoryiShowall;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("id_damage_category")
    private String idDamageCategory;

    @SerializedName("category")
    private String category;

    public String getIdDamageCategory(){
        return idDamageCategory;
    }

    public String getCategory(){
        return category;
    }
}