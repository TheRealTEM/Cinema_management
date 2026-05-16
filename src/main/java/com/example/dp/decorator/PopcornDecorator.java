package com.example.dp.decorator;

public class PopcornDecorator extends TicketDecorator {
    public PopcornDecorator(Ticket ticket) {
        super(ticket);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Large Popcorn";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 10.0;
    }
}
