package com.example.dp.facade;

import com.example.dp.factory.PaymentFactory;
import com.example.dp.strategy.PaymentStrategy;

public class BookingFacade {

    public void completeBooking(
            double amount,
            String paymentType
    ) {

        PaymentStrategy strategy =
                PaymentFactory.createPayment(
                        paymentType
                );

        if(strategy != null) {

            strategy.pay(amount);

            System.out.println(
                    "Booking completed successfully"
            );

        } else {

            System.out.println(
                    "Invalid payment method"
            );
        }
    }
}