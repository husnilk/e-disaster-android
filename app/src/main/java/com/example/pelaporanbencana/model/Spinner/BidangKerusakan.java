package com.example.pelaporanbencana.model.Spinner;

public class BidangKerusakan {
    String id, bidang_kerusakan;

    public BidangKerusakan(String id, String bidang_kerusakan) {
        this.id = id;
        this.bidang_kerusakan = bidang_kerusakan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBidang_kerusakan() {
        return bidang_kerusakan;
    }

    public void setBidang_kerusakan(String bidang_kerusakan) {
        this.bidang_kerusakan = bidang_kerusakan;
    }

    @Override
    public String toString() {
        return bidang_kerusakan;
    }
}
