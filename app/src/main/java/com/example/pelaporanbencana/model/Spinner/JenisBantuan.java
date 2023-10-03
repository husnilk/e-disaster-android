package com.example.pelaporanbencana.model.Spinner;

public class JenisBantuan {
    int id_bantuan;
    String nama_bantuan;

    public JenisBantuan(int id_bantuan, String nama_bantuan) {
        this.id_bantuan = id_bantuan;
        this.nama_bantuan = nama_bantuan;
    }

    public int getId_bantuan() {
        return id_bantuan;
    }

    public String getNama_bantuan() {
        return nama_bantuan;
    }

    @Override
    public String toString() {
        return nama_bantuan;
    }
}
