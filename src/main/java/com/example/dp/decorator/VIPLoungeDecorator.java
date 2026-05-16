package com.example.dp.decorator;

public class VIPLoungeDecorator extends TicketDecorator {
    public VIPLoungeDecorator(Ticket ticket) {
        super(ticket);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + VIP Lounge Access";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 25.0;
    }
}
