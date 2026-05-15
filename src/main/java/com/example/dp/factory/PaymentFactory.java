package com.example.dp.factory;

import com.example.dp.strategy.CardPaymentStrategy;
import com.example.dp.strategy.CashPaymentStrategy;
import com.example.dp.strategy.PaymentStrategy;
import com.example.dp.strategy.WalletPaymentStrategy;

public class PaymentFactory {

    public static PaymentStrategy
    createPayment(
            String type
    ) {

        if(type.equalsIgnoreCase("CARD")) {

            return new CardPaymentStrategy();
        }

        if(type.equalsIgnoreCase("CASH")) {

            return new CashPaymentStrategy();
        }

        if(type.equalsIgnoreCase("WALLET")) {

            return new WalletPaymentStrategy();
        }

        return null;
    }
}