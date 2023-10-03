package com.example.pelaporanbencana.model.DamageShowa;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("damage_units")
    private String damageUnits;

    @SerializedName("damage_amount")
    private int damageAmount;

    @SerializedName("id_damage_category")
    private String idDamageCategory;

    @SerializedName("id_damage")
    private String idDamage;

    @SerializedName("id_disasters")
    private String idDisasters;

    @SerializedName("category")
    private String category;

    @SerializedName("damage_types")
    private String damageTypes;

    @SerializedName("id_damages")
    private int idDamages;

    @SerializedName("damage_name")
    private String damageName;


    public String getDamageUnits(){
        return damageUnits;
    }

    public int getDamageAmount(){
        return damageAmount;
    }

    public String getIdDamageCategory(){
        return idDamageCategory;
    }

    public String getIdDamage(){
        return idDamage;
    }

    public String getIdDisasters(){
        return idDisasters;
    }

    public String getCategory(){
        return category;
    }

    public String getDamageTypes(){
        return damageTypes;
    }

    public int getIdDamages() {
        return idDamages;
    }

    public String getDamageName() {
        return damageName;
    }
}