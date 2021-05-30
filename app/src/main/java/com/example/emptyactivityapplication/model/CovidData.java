package com.example.emptyactivityapplication.model;

public class CovidData {
    String location;
    String infectedCount;
    String deceasedCount;

    public String getDeceasedCount() {
        return deceasedCount;
    }

    public void setDeceasedCount(String deceasedCount) {
        this.deceasedCount = deceasedCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInfectedCount() {
        return infectedCount;
    }

    public void setInfectedCount(String infectedCount) {
        this.infectedCount = infectedCount;
    }
}
