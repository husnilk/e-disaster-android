package com.example.pelaporanbencana.model;

public class SumberDaya {
    int resources_available, lack_of_resources, id_resources, id_disaster_resources;
    String additional_info, resources_types, resources_units;

    public SumberDaya(int id_resources, int resources_available, int lack_of_resources,
                      String additional_info, String resources_types, String resources_units, int id_disaster_resources) {
        this.id_resources = id_resources;
        this.resources_available = resources_available;
        this.lack_of_resources = lack_of_resources;
        this.additional_info = additional_info;
        this.resources_types = resources_types;
        this.resources_units = resources_units;
        this.id_disaster_resources = id_disaster_resources;
    }

    public int getId_resources() {
        return id_resources;
    }

    public void setId_resources(int id_resources) {
        this.id_resources = id_resources;
    }

    public int getResources_available() {
        return resources_available;
    }

    public void setResources_available(int resources_available) {
        this.resources_available = resources_available;
    }

    public int getLack_of_resources() {
        return lack_of_resources;
    }

    public void setLack_of_resources(int lack_of_resources) {
        this.lack_of_resources = lack_of_resources;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getResources_types() {
        return resources_types;
    }

    public void setResources_types(String resources_types) {
        this.resources_types = resources_types;
    }

    public String getResources_units() {
        return resources_units;
    }

    public void setResources_units(String resources_units) {
        this.resources_units = resources_units;
    }

    public int getId_disaster_resources() {
        return id_disaster_resources;
    }

    public void setId_disaster_resources(int id_disaster_resources) {
        this.id_disaster_resources = id_disaster_resources;
    }
}
