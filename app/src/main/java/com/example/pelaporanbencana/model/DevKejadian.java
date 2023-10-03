package com.example.pelaporanbencana.model;

public class DevKejadian {
    String id_disasters, disasters_date, disasters_time, disasters_desc, disasters_impact, disasters_causes,
            weather_conditions, disasters_potential, disasters_effort;

    public DevKejadian(String id_disasters, String disasters_date, String disasters_time, String disasters_desc, String disasters_impact, String disasters_causes,
                       String weather_conditions, String disasters_potential, String disasters_effort) {
        this.id_disasters = id_disasters;
        this.disasters_date = disasters_date;
        this.disasters_time = disasters_time;
        this.disasters_desc = disasters_desc;
        this.disasters_impact = disasters_impact;
        this.disasters_causes = disasters_causes;
        this.weather_conditions = weather_conditions;
        this.disasters_potential = disasters_potential;
        this.disasters_effort = disasters_effort;
    }

    public String getId_disasters() {
        return id_disasters;
    }

    public void setId_disasters(String id_disasters) {
        this.id_disasters = id_disasters;
    }

    public String getDisasters_date() {
        return disasters_date;
    }

    public void setDisasters_date(String disasters_date) {
        this.disasters_date = disasters_date;
    }

    public String getDisasters_time() {
        return disasters_time;
    }

    public void setDisasters_time(String disasters_time) {
        this.disasters_time = disasters_time;
    }

    public String getDisasters_desc() {
        return disasters_desc;
    }

    public void setDisasters_desc(String disasters_desc) {
        this.disasters_desc = disasters_desc;
    }

    public String getDisasters_impact() {
        return disasters_impact;
    }

    public void setDisasters_impact(String disasters_impact) {
        this.disasters_impact = disasters_impact;
    }

    public String getDisasters_causes() {
        return disasters_causes;
    }

    public void setDisasters_causes(String disasters_causes) {
        this.disasters_causes = disasters_causes;
    }

    public String getWeather_conditions() {
        return weather_conditions;
    }

    public void setWeather_conditions(String weather_conditions) {
        this.weather_conditions = weather_conditions;
    }

    public String getDisasters_potential() {
        return disasters_potential;
    }

    public void setDisasters_potential(String disasters_potential) {
        this.disasters_potential = disasters_potential;
    }

    public String getDisasters_effort() {
        return disasters_effort;
    }

    public void setDisasters_effort(String disasters_effort) {
        this.disasters_effort = disasters_effort;
    }
}
