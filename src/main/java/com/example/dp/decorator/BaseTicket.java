package com.example.dp.decorator;

public class BaseTicket implements Ticket {
    private String movieTitle;
    private double basePrice;

    public BaseTicket(String movieTitle, double basePrice) {
        this.movieTitle = movieTitle;
        this.basePrice = basePrice;
    }

    @Override
    public String getDescription() {
        return "Movie Ticket: " + movieTitle;
    }

    @Override
    public double getPrice() {
        return basePrice;
    }
}
