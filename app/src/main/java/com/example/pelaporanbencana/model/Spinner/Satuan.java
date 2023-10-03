package com.example.pelaporanbencana.model.Spinner;

public class Satuan {
    String id;
    String units;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return id;
    }
}
