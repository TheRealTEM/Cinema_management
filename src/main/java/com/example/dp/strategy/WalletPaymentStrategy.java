package com.example.dp.strategy;

public class WalletPaymentStrategy
        implements PaymentStrategy {

    @Override
    public void pay(double amount) {

        System.out.println(
                "Paid "
                        + amount
                        + " using Wallet"
        );
    }
}