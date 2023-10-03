package com.example.pelaporanbencana.model;

public class DetailKejadian {
    private String ImageBencana;

    public DetailKejadian(String imageBencana) {
        ImageBencana = imageBencana;
    }

    public String getImageBencana() {
        return ImageBencana;
    }

    public void setImageBencana(String imageBencana) {
        ImageBencana = imageBencana;
    }

    @Override
    public String toString() {
        return "DetailKejadian{" +
                "ImageBencana='" + ImageBencana + '\'' +
                '}';
    }
}
