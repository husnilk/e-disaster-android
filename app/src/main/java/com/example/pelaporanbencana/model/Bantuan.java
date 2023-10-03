package com.example.pelaporanbencana.model;

import java.util.Date;

public class Bantuan {
    String donor, sa_types_name, social_assistance_unit;
    int social_assistance_amount, batch, id_sa_types;
    Date dateReceived;

    public Bantuan(int id_sa_types, String donor, String sa_types_name, String social_assistance_unit, int social_assistance_amount, int batch, Date dateReceived) {
        this.id_sa_types = id_sa_types;
        this.donor = donor;
        this.sa_types_name = sa_types_name;
        this.social_assistance_unit = social_assistance_unit;
        this.social_assistance_amount = social_assistance_amount;
        this.batch = batch;
        this.dateReceived = dateReceived;
    }

    public int getId_sa_types() {
        return id_sa_types;
    }

    public void setId_sa_types(int id_sa_types) {
        this.id_sa_types = id_sa_types;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }

    public String getSa_types_name() {
        return sa_types_name;
    }

    public void setSa_types_name(String sa_types_name) {
        this.sa_types_name = sa_types_name;
    }

    public String getSocial_assistance_unit() {
        return social_assistance_unit;
    }

    public void setSocial_assistance_unit(String social_assistance_unit) {
        this.social_assistance_unit = social_assistance_unit;
    }

    public int getSocial_assistance_amount() {
        return social_assistance_amount;
    }

    public void setSocial_assistance_amount(int social_assistance_amount) {
        this.social_assistance_amount = social_assistance_amount;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }
}
