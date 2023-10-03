package com.example.pelaporanbencana.model.DamageStore;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("damage_units")
    private String damageUnits;

    @SerializedName("damage_amount")
    private int damageAmount;

    @SerializedName("id_damage_category")
    private String idDamageCategory;

    @SerializedName("id_disasters")
    private String idDisasters;

    @SerializedName("damage_name")
    private String damageName;

    @SerializedName("damage_types")
    private String damageTypes;

    @SerializedName("id_damages")
    private int idDamages;

    public void setDamageUnits(String damageUnits){
        this.damageUnits = damageUnits;
    }

    public String getDamageUnits(){
        return damageUnits;
    }

    public void setDamageAmount(int damageAmount){
        this.damageAmount = damageAmount;
    }

    public int getDamageAmount(){
        return damageAmount;
    }

    public void setIdDamageCategory(String idDamageCategory){
        this.idDamageCategory = idDamageCategory;
    }

    public String getIdDamageCategory(){
        return idDamageCategory;
    }

    public void setIdDisasters(String idDisasters){
        this.idDisasters = idDisasters;
    }

    public String getIdDisasters(){
        return idDisasters;
    }

    public void setDamageTypes(String damageTypes){
        this.damageTypes = damageTypes;
    }

    public String getDamageTypes(){
        return damageTypes;
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