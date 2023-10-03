package com.example.pelaporanbencana.model;

public class LokasiPengungsi {
    String id_shelter,
            location,
            address,
            hunian_types;
    int capacity, jml_org;

    public LokasiPengungsi(String id_shelter, String location, String address, String hunian_types, int capacity, int jml_org) {
        this.id_shelter = id_shelter;
        this.location = location;
        this.address = address;
        this.hunian_types = hunian_types;
        this.capacity = capacity;
        this.jml_org = jml_org;
    }


    public String getId_shelter() {
        return id_shelter;
    }

    public void setId_shelter(String id_shelter) {
        this.id_shelter = id_shelter;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHunian_types() {
        return hunian_types;
    }

    public void setHunian_types(String hunian_types) {
        this.hunian_types = hunian_types;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getJml_org() {
        return jml_org;
    }

    public void setJml_org(int jml_org) {
        this.jml_org = jml_org;
    }

    //    String kodeLokasiPengungsi, lokPengungsi, latlongPengungsi, jmlKapasitas, jenisHunian;
//
//    public String getKodeLokasiPengungsi() {
//        return kodeLokasiPengungsi;
//    }
//
//    public void setKodeLokasiPengungsi(String kodeLokasiPengungsi) {
//        this.kodeLokasiPengungsi = kodeLokasiPengungsi;
//    }
//
//    public String getLokPengungsi() {
//        return lokPengungsi;
//    }
//
//    public void setLokPengungsi(String lokPengungsi) {
//        this.lokPengungsi = lokPengungsi;
//    }
//
//    public String getLatlongPengungsi() {
//        return latlongPengungsi;
//    }
//
//    public void setLatlongPengungsi(String latlongPengungsi) {
//        this.latlongPengungsi = latlongPengungsi;
//    }
//
//    public String getJmlKapasitas() {
//        return jmlKapasitas;
//    }
//
//    public void setJmlKapasitas(String jmlKapasitas) {
//        this.jmlKapasitas = jmlKapasitas;
//    }
//
//    public String getJenisHunian() {
//        return jenisHunian;
//    }
//
//    public void setJenisHunian(String jenisHunian) {
//        this.jenisHunian = jenisHunian;
//    }
}
