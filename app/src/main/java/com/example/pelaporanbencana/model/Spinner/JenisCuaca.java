package com.example.pelaporanbencana.model.Spinner;

public class JenisCuaca {
    String cuaca;

    public JenisCuaca(String cuaca) {
        this.cuaca = cuaca;
    }

    public String getCuaca() {
        return cuaca;
    }

    public void setCuaca(String cuaca) {
        this.cuaca = cuaca;
    }

    @Override
    public String toString() {
        return cuaca;
    }
}
