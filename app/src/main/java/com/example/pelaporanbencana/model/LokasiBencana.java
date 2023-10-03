package com.example.pelaporanbencana.model;

public class LokasiBencana {
    String urban_village_name,sub_district_name, district_name, province_name, id_urban_village;

    public LokasiBencana(String urban_village_name, String sub_district_name, String district_name, String province_name, String id_urban_village) {
        this.urban_village_name = urban_village_name;
        this.sub_district_name = sub_district_name;
        this.district_name = district_name;
        this.province_name = province_name;
        this.id_urban_village = id_urban_village;
    }

    public String getId_urban_village() {
        return id_urban_village;
    }

    public void setId_urban_village(String id_urban_village) {
        this.id_urban_village = id_urban_village;
    }

    public String getUrban_village_name() {
        return urban_village_name;
    }

    public void setUrban_village_name(String urban_village_name) {
        this.urban_village_name = urban_village_name;
    }

    public String getSub_district_name() {
        return sub_district_name;
    }

    public void setSub_district_name(String sub_district_name) {
        this.sub_district_name = sub_district_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }
}
