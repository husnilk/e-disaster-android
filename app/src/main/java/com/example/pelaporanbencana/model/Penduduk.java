package com.example.pelaporanbencana.model;

import java.util.Date;

public class Penduduk {
    public String name, nik, gender, address, heir;
    public Date birthdate;
//    public Boolean isSelected;


    public Penduduk(String name, String nik, String gender, Date birthdate) {
        this.name = name;
        this.nik = nik;
        this.gender = gender;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    //    public Boolean getSelected() {
//        return isSelected;
//    }
//
//    public void setSelected(Boolean selected) {
//        isSelected = selected;
//    }

    //    @Override
//    public String toString() {
//        return "Penduduk{" +
//                "namaKorban='" + namaKorban + '\'' +
//                ", nikKorban='" + nikKorban + '\'' +
//                ", usiaKorban='" + usiaKorban + '\'' +
////                ", jkKorban='" + jkKorban + '\'' +
//                ", isSelected=" + isSelected +
//                '}';
//    }
}
