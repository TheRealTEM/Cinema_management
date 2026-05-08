package com.example.dp.model;

public class Seat {

    private int id;

    private int hallId;

    private String seatRow;

    private int seatNumber;

    private String seatType;

    public Seat() {
    }

    public Seat(
            int id,
            int hallId,
            String seatRow,
            int seatNumber,
            String seatType
    ) {
        this.id = id;
        this.hallId = hallId;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(String seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    @Override
    public String toString() {
        return seatRow + seatNumber;
    }
}