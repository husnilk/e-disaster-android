package com.example.pelaporanbencana.model.Spinner;

public class StatusMedis {
    String id_status_medis;
    String name_status_medis;

    public StatusMedis(String id_status_medis, String name_status_medis) {
        this.id_status_medis = id_status_medis;
        this.name_status_medis = name_status_medis;
    }

    public String getId_status_medis() {
        return id_status_medis;
    }

    public void setId_status_medis(String id_status_medis) {
        this.id_status_medis = id_status_medis;
    }

    public String getName_status_medis() {
        return name_status_medis;
    }

    public void setName_status_medis(String name_status_medis) {
        this.name_status_medis = name_status_medis;
    }

    @Override
    public String toString() {
        return name_status_medis ;
    }
}
