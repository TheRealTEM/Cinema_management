package com.example.dp.model;

public class BookingSeat {

    private int bookingId;

    private int showtimeId;

    private int seatId;

    public BookingSeat() {
    }

    public BookingSeat(
            int bookingId,
            int showtimeId,
            int seatId
    ) {
        this.bookingId = bookingId;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
}