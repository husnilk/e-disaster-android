package com.example.pelaporanbencana.model;

public class Kerusakan {
    String id_disasters,
            id_damage_category,
            category,
            damage_name,
            damage_types,
            damage_units,
            disasters_village,
            urban_village_name,
            sub_district_name;
    int damage_amount, id_damages;

    public Kerusakan(String id_disasters, String id_damage_category ,String category,
                     String damage_types, String damage_units,
                     String disasters_village, String urban_village_name,
                     String sub_district_name, int damage_amount, int id_damages, String damage_name) {
        this.id_disasters = id_disasters;
        this.id_damage_category = id_damage_category;
        this.category = category;
        this.damage_types = damage_types;
        this.damage_units = damage_units;
        this.disasters_village = disasters_village;
        this.urban_village_name = urban_village_name;
        this.sub_district_name = sub_district_name;
        this.damage_amount = damage_amount;
        this.id_damages = id_damages;
        this.damage_name = damage_name;
    }

    public String getId_disasters() {
        return id_disasters;
    }

    public void setId_disasters(String id_disasters) {
        this.id_disasters = id_disasters;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDamage_types() {
        return damage_types;
    }

    public void setDamage_types(String damage_types) {
        this.damage_types = damage_types;
    }

    public String getDamage_units() {
        return damage_units;
    }

    public void setDamage_units(String damage_units) {
        this.damage_units = damage_units;
    }

    public String getDisasters_village() {
        return disasters_village;
    }

    public void setDisasters_village(String disasters_village) {
        this.disasters_village = disasters_village;
    }

    public String getUrban_village_name() {
        return urban_village_name;
    }

    public void setUrban_village_name(String urban_village_name) {
        this.urban_village_name = urban_village_name;
    }

    public String getSub_district_name() {
        return sub_district_name;
    }

    public void setSub_district_name(String sub_district_name) {
        this.sub_district_name = sub_district_name;
    }

    public int getDamage_amount() {
        return damage_amount;
    }

    public void setDamage_amount(int damage_amount) {
        this.damage_amount = damage_amount;
    }

    public String getId_damage_category() {
        return id_damage_category;
    }

    public void setId_damage_category(String id_damage_category) {
        this.id_damage_category = id_damage_category;
    }

    public String getDamage_name() {
        return damage_name;
    }

    public void setDamage_name(String damage_name) {
        this.damage_name = damage_name;
    }

    public int getId_damages() {
        return id_damages;
    }

    public void setId_damages(int id_damages) {
        this.id_damages = id_damages;
    }

    //    public Kerusakan(String idDisasters, String disastersVillage, String bidangKerusakan, String jenisKerusakan, String kelurahan, int damageAmount, String jumlah, String satuan) {
//        this.bidangKerusakan = bidangKerusakan;
//        this.jenisKerusakan = jenisKerusakan;
//        this.kelurahan = kelurahan;
//        this.jumlah = jumlah;
//        this.satuan = satuan;
//    }
//
//    public String getBidangKerusakan() {
//        return bidangKerusakan;
//    }
//
//    public void setBidangKerusakan(String bidangKerusakan) {
//        this.bidangKerusakan = bidangKerusakan;
//    }
//
//    public String getJenisKerusakan() {
//        return jenisKerusakan;
//    }
//
//    public void setJenisKerusakan(String jenisKerusakan) {
//        this.jenisKerusakan = jenisKerusakan;
//    }
//
//    public String getKelurahan() {
//        return kelurahan;
//    }
//
//    public void setKelurahan(String kelurahan) {
//        this.kelurahan = kelurahan;
//    }
//
//    public String getJumlah() {
//        return jumlah;
//    }
//
//    public void setJumlah(String jumlah) {
//        this.jumlah = jumlah;
//    }
//
//    public String getSatuan() {
//        return satuan;
//    }
//
//    public void setSatuan(String satuan) {
//        this.satuan = satuan;
//    }
//
//    @Override
//    public String toString() {
//        return "Kerusakan{" +
//                "bidangKerusakan='" + bidangKerusakan + '\'' +
//                ", jenisKerusakan='" + jenisKerusakan + '\'' +
//                ", kelurahan='" + kelurahan + '\'' +
//                ", jumlah='" + jumlah + '\'' +
//                ", satuan='" + satuan + '\'' +
//                '}';
//    }
}
