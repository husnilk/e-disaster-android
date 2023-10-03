package com.example.pelaporanbencana.model;

import java.util.Date;

public class BantuanT {
    String  sa_types_name,
            recipient,
            sa_distributed_units;
    int sa_distributed_amount, batch, id_sa_types;
    Date date_sent;

    public BantuanT(int id_sa_types, String sa_types_name, String recipient, Date date_sent, String sa_distributed_units, int sa_distributed_amount, int batch) {
        this.id_sa_types = id_sa_types;
        this.sa_types_name = sa_types_name;
        this.recipient = recipient;
        this.date_sent = date_sent;
        this.sa_distributed_units = sa_distributed_units;
        this.sa_distributed_amount = sa_distributed_amount;
        this.batch = batch;
    }

    public int getId_sa_types() {
        return id_sa_types;
    }

    public String getSa_types_name() {
        return sa_types_name;
    }

    public void setSa_types_name(String sa_types_name) {
        this.sa_types_name = sa_types_name;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Date getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(Date date_sent) {
        this.date_sent = date_sent;
    }

    public String getSa_distributed_units() {
        return sa_distributed_units;
    }

    public void setSa_distributed_units(String sa_distributed_units) {
        this.sa_distributed_units = sa_distributed_units;
    }

    public int getSa_distributed_amount() {
        return sa_distributed_amount;
    }

    public void setSa_distributed_amount(int sa_distributed_amount) {
        this.sa_distributed_amount = sa_distributed_amount;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }
}
