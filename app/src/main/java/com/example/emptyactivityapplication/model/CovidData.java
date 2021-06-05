package com.example.emptyactivityapplication.model;

public class CovidData {
    String location;
    String infectedCount;
    String deceasedCount;
    String tested;
    String recovered;
    boolean todaysData;

    public String getTested() {
        return tested;
    }

    public void setTested(String tested) {
        this.tested = tested;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public boolean isTodaysData() {
        return todaysData;
    }

    public void setTodaysData(boolean todaysData) {
        this.todaysData = todaysData;
    }

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
