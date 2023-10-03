package com.example.pelaporanbencana.model.Spinner;

public class JenisBencana {
    String idJenisKejadian, jenisKejadian;

    public JenisBencana(String idJenisKejadian, String jenisKejadian) {
        this.idJenisKejadian = idJenisKejadian;
        this.jenisKejadian = jenisKejadian;
    }

    public String getIdJenisKejadian() {
        return idJenisKejadian;
    }

    public void setIdJenisKejadian(String idJenisKejadian) {
        this.idJenisKejadian = idJenisKejadian;
    }

    public String getJenisKejadian() {
        return jenisKejadian;
    }

    public void setJenisKejadian(String jenisKejadian) {
        this.jenisKejadian = jenisKejadian;
    }

    @Override
    public String toString() {
        return jenisKejadian;
    }
}
