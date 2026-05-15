package com.example.dp.model;

import java.sql.Timestamp;

public class Payment {

    private int id;

    private int bookingId;

    private String paymentMethod;

    private double amount;

    private String paymentStatus;

    private Timestamp paymentDate;

    public Payment() {
    }

    public Payment(
            int id,
            int bookingId,
            String paymentMethod,
            double amount,
            String paymentStatus,
            Timestamp paymentDate
    ) {
        this.id = id;
        this.bookingId = bookingId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }
}