package com.example.dp.model;

public class CinemaHall {

    private int id;

    private String hallName;

    private int capacity;

    private String screenType;

    public CinemaHall() {
    }

    public CinemaHall(
            int id,
            String hallName,
            int capacity,
            String screenType
    ) {
        this.id = id;
        this.hallName = hallName;
        this.capacity = capacity;
        this.screenType = screenType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    @Override
    public String toString() {
        return hallName;
    }
}