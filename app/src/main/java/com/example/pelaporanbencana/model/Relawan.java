package com.example.pelaporanbencana.model;

import java.util.Date;

public class Relawan {
    String id_volunteers, volunteers_name, volunteers_gender, volunteers_skill;
    Date volunteers_birthdate;
    Boolean isSelected;

    public Relawan(String id_volunteers,
                   String volunteers_name,
                   Date volunteers_birthdate,
                   String volunteers_gender,
                   String volunteers_skill) {
        this.id_volunteers = id_volunteers;
        this.volunteers_name = volunteers_name;
        this.volunteers_birthdate = volunteers_birthdate;
        this.volunteers_gender = volunteers_gender;
        this.volunteers_skill = volunteers_skill;
    }

    public String getId_volunteers() {
        return id_volunteers;
    }

    public void setId_volunteers(String id_volunteers) {
        this.id_volunteers = id_volunteers;
    }

    public String getVolunteers_name() {
        return volunteers_name;
    }

    public void setVolunteers_name(String volunteers_name) {
        this.volunteers_name = volunteers_name;
    }

    public Date getVolunteers_birthdate() {
        return volunteers_birthdate;
    }

    public void setVolunteers_birthdate(Date volunteers_birthdate) {
        this.volunteers_birthdate = volunteers_birthdate;
    }

    public String getVolunteers_gender() {
        return volunteers_gender;
    }

    public void setVolunteers_gender(String volunteers_gender) {
        this.volunteers_gender = volunteers_gender;
    }

    public String getVolunteers_skill() {
        return volunteers_skill;
    }

    public void setVolunteers_skill(String volunteers_skill) {
        this.volunteers_skill = volunteers_skill;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
