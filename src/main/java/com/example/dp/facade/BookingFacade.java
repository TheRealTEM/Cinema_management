package com.example.dp.facade;

import com.example.dp.dao.BookingDAO;
import com.example.dp.dao.PaymentDAO;
import com.example.dp.dao.SeatDAO;

public class BookingFacade {

    private final BookingDAO bookingDAO =
            new BookingDAO();

    private final PaymentDAO paymentDAO =
            new PaymentDAO();

    private final SeatDAO seatDAO =
            new SeatDAO();

    public boolean completeBooking(
            int userId,
            int showtimeId,
            String[] seats,
            double total,
            String paymentMethod
    ) {

        int bookingId =
                bookingDAO.createBooking(
                        userId,
                        showtimeId,
                        total
                );

        if(bookingId == -1) {

            return false;
        }

        for(String seat : seats) {

            int seatId =
                    seatDAO.getSeatIdBySeatNumber(
                            seat
                    );

            if(seatId != -1) {

                bookingDAO.saveBookedSeat(
                        bookingId,
                        showtimeId,
                        seatId
                );
            }
        }

        paymentDAO.createPayment(
                bookingId,
                paymentMethod,
                total
        );

        return true;
    }
}