package com.example.pelaporanbencana.model.Spinner;

public class JenisHunian {
    String key_jenis_hunian, jenis_hunian;

    public JenisHunian(String key_jenis_hunian, String jenis_hunian) {
        this.key_jenis_hunian = key_jenis_hunian;
        this.jenis_hunian = jenis_hunian;
    }

    public String getKey_jenis_hunian() {
        return key_jenis_hunian;
    }

    public void setKey_jenis_hunian(String key_jenis_hunian) {
        this.key_jenis_hunian = key_jenis_hunian;
    }

    public String getJenis_hunian() {
        return jenis_hunian;
    }

    public void setJenis_hunian(String jenis_hunian) {
        this.jenis_hunian = jenis_hunian;
    }

    @Override
    public String toString() {
        return jenis_hunian;
    }
}
