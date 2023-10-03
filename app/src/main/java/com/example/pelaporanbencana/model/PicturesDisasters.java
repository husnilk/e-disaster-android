package com.example.pelaporanbencana.model;

public class PicturesDisasters {
    String id_pictures, id_disasters, pictures;

    public PicturesDisasters(String id_pictures, String id_disasters, String pictures) {
        this.id_pictures = id_pictures;
        this.id_disasters = id_disasters;
        this.pictures = pictures;
    }

    public String getId_pictures() {
        return id_pictures;
    }

    public void setId_pictures(String id_pictures) {
        this.id_pictures = id_pictures;
    }

    public String getId_disasters() {
        return id_disasters;
    }

    public void setId_disasters(String id_disasters) {
        this.id_disasters = id_disasters;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }
}
