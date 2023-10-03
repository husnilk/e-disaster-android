package com.example.pelaporanbencana.model.Spinner;

public class StatusKorban {
    String id_status_korban;
    String name_status_korban;

    public StatusKorban(String id_status_korban, String name_status_korban) {
        this.id_status_korban = id_status_korban;
        this.name_status_korban = name_status_korban;
    }

    public String getId_status_korban() {
        return id_status_korban;
    }

    public void setId_status_korban(String id_status_korban) {
        this.id_status_korban = id_status_korban;
    }

    public String getName_status_korban() {
        return name_status_korban;
    }

    public void setName_status_korban(String name_status_korban) {
        this.name_status_korban = name_status_korban;
    }

    @Override
    public String toString() {
        return name_status_korban;
    }
}
