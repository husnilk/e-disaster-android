package com.example.pelaporanbencana.model;

import java.util.Calendar;
import java.util.Date;

public class Korban {
    String nik, victims_status, gender, name, heir, address;
    Date birthdate;

    public Korban(String nik, String victims_status, String gender, String name, Date birthdate, String heir, String address) {
        this.nik = nik;
        this.victims_status = victims_status;
        this.gender = gender;
        this.name = name;
        this.birthdate = birthdate;
        this.heir = heir;
        this.address = address;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getVictims_status() {
        return victims_status;
    }

    public void setVictims_status(String victims_status) {
        this.victims_status = victims_status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getHeir() {
        return heir;
    }

    public void setHeir(String heir) {
        this.heir = heir;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
