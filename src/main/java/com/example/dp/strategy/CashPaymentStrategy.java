package com.example.dp.strategy;

public class CashPaymentStrategy
        implements PaymentStrategy {

    @Override
    public void pay(double amount) {

        System.out.println(
                "Paid "
                        + amount
                        + " using Cash"
        );
    }
}