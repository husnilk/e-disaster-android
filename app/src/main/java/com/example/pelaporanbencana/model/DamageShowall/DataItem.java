package com.example.pelaporanbencana.model.DamageShowall;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("damage_units")
    private String damageUnits;

    @SerializedName("damage_amount")
    private int damageAmount;

    @SerializedName("urban_village_name")
    private String urbanVillageName;

    @SerializedName("id_disasters")
    private String idDisasters;

    @SerializedName("id_damage_category")
    private String idDamageCategory;

    @SerializedName("category")
    private String category;

    @SerializedName("damage_types")
    private String damageTypes;

    @SerializedName("disasters_village")
    private String disastersVillage;

    @SerializedName("sub_district_name")
    private String subDistrictName;

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

    public String getUrbanVillageName(){
        return urbanVillageName;
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

    public String getDisastersVillage(){
        return disastersVillage;
    }

    public String getSubDistrictName(){
        return subDistrictName;
    }

    public String getIdDamageCategory() {
        return idDamageCategory;
    }

    public void setIdDamageCategory(String idDamageCategory) {
        this.idDamageCategory = idDamageCategory;
    }

    public int getIdDamages() {
        return idDamages;
    }

    public void setIdDamages(int idDamages) {
        this.idDamages = idDamages;
    }

    public String getDamageName() {
        return damageName;
    }

    public void setDamageName(String damageName) {
        this.damageName = damageName;
    }
}