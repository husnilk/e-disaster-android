package com.example.pelaporanbencana.model;

public class KerusakanKategori {
    String id_damage_category, category;

    public KerusakanKategori(String id_damage_category, String category) {
        this.id_damage_category = id_damage_category;
        this.category = category;
    }

    public String getId_damage_category() {
        return id_damage_category;
    }

    public void setId_damage_category(String id_damage_category) {
        this.id_damage_category = id_damage_category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
