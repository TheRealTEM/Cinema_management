package com.example.dp.model;

import java.sql.Timestamp;

public class Booking {

    private int id;

    private int userId;

    private int showtimeId;

    private Timestamp bookingDate;

    private double totalAmount;

    private String bookingStatus;

    public Booking() {
    }

    public Booking(
            int id,
            int userId,
            int showtimeId,
            Timestamp bookingDate,
            double totalAmount,
            String bookingStatus
    ) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public static class Builder {
        private int id;
        private int userId;
        private int showtimeId;
        private Timestamp bookingDate;
        private double totalAmount;
        private String bookingStatus;

        public Builder setId(int id) { this.id = id; return this; }
        public Builder setUserId(int userId) { this.userId = userId; return this; }
        public Builder setShowtimeId(int showtimeId) { this.showtimeId = showtimeId; return this; }
        public Builder setBookingDate(Timestamp bookingDate) { this.bookingDate = bookingDate; return this; }
        public Builder setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; return this; }
        public Builder setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; return this; }

        public Booking build() {
            return new Booking(id, userId, showtimeId, bookingDate, totalAmount, bookingStatus);
        }
    }
}