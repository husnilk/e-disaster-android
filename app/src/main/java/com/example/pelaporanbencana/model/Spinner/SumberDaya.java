package com.example.pelaporanbencana.model.Spinner;

public class SumberDaya {
    int id_sumber_daya;
    String sumber_daya;

    public SumberDaya(int id_sumber_daya, String sumber_daya) {
        this.id_sumber_daya = id_sumber_daya;
        this.sumber_daya = sumber_daya;
    }

    public int getId_sumber_daya() {
        return id_sumber_daya;
    }

    public void setId_sumber_daya(int id_sumber_daya) {
        this.id_sumber_daya = id_sumber_daya;
    }

    public String getSumber_daya() {
        return sumber_daya;
    }

    public void setSumber_daya(String sumber_daya) {
        this.sumber_daya = sumber_daya;
    }

    @Override
    public String toString() {
        return sumber_daya;
    }
}
