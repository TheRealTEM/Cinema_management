package com.example.dp.decorator;

public abstract class TicketDecorator implements Ticket {
    protected Ticket decoratedTicket;

    public TicketDecorator(Ticket ticket) {
        this.decoratedTicket = ticket;
    }

    @Override
    public String getDescription() {
        return decoratedTicket.getDescription();
    }

    @Override
    public double getPrice() {
        return decoratedTicket.getPrice();
    }
}
