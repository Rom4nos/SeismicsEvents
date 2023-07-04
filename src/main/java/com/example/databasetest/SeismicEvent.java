package com.example.databasetest;

public class SeismicEvent {
    private int ID;
    private int year;
    private int magnitude;
    private int eventCount;

    public SeismicEvent(int ID, int year, int magnitude, int eventCount) {
        this.ID = ID;
        this.year = year;
        this.magnitude = magnitude;
        this.eventCount = eventCount;
    }

    public int getID() {
        return ID;
    }

    public int getYear() {
        return year;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public int getEventCount() {
        return eventCount;
    }
}
