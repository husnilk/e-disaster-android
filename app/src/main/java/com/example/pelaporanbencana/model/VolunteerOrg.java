package com.example.pelaporanbencana.model;

public class VolunteerOrg {
    String id_volunteer_org, volunteer_org_name, volunteer_org_address, volunteer_org_status;

    public VolunteerOrg(String id_volunteer_org, String volunteer_org_name, String volunteer_org_address,
                        String volunteer_org_status) {
        this.id_volunteer_org = id_volunteer_org;
        this.volunteer_org_name = volunteer_org_name;
        this.volunteer_org_address = volunteer_org_address;
        this.volunteer_org_status = volunteer_org_status;
    }

    public String getId_volunteer_org() {
        return id_volunteer_org;
    }

    public void setId_volunteer_org(String id_volunteer_org) {
        this.id_volunteer_org = id_volunteer_org;
    }

    public String getVolunteer_org_name() {
        return volunteer_org_name;
    }

    public void setVolunteer_org_name(String volunteer_org_name) {
        this.volunteer_org_name = volunteer_org_name;
    }

    public String getVolunteer_org_address() {
        return volunteer_org_address;
    }

    public void setVolunteer_org_address(String volunteer_org_address) {
        this.volunteer_org_address = volunteer_org_address;
    }

    public String getVolunteer_org_status() {
        return volunteer_org_status;
    }

    public void setVolunteer_org_status(String volunteer_org_status) {
        this.volunteer_org_status = volunteer_org_status;
    }
}
