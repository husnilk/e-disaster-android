package com.example.pelaporanbencana.model;

public class LokasiPengungsiAll {
    String id_shelter,
            location,
            address,
            hunian_types;
    int capacity;

    public LokasiPengungsiAll(String id_shelter, String location, String address, String hunian_types, int capacity) {
        this.id_shelter = id_shelter;
        this.location = location;
        this.address = address;
        this.hunian_types = hunian_types;
        this.capacity = capacity;
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
}
