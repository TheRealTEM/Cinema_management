package com.example.dp.facade;

import com.example.dp.adapter.SmsAdapter;
import com.example.dp.dao.BookingDAO;
import com.example.dp.dao.PaymentDAO;
import com.example.dp.model.Booking;
import com.example.dp.model.Payment;
import com.example.dp.proxy.CachedMovieProxy;
import com.example.dp.service.MovieService;
import com.example.dp.service.NotificationService;
import com.example.dp.state.Session;

import java.util.List;

public class CinemaFacade {
    private MovieService movieService;
    private BookingDAO bookingDAO;
    private PaymentDAO paymentDAO;
    private NotificationService notificationService;

    public CinemaFacade() {
        this.movieService = new CachedMovieProxy();
        this.bookingDAO = new BookingDAO();
        this.paymentDAO = new PaymentDAO();
        this.notificationService = new SmsAdapter();
    }

    public boolean purchaseTicket(int showtimeId, List<String> seatIds, double amount, String paymentMethod) {
        System.out.println("[FACADE] Starting ticket purchase process...");
        
        int userId = Session.getInstance().getLoggedInUser().getId();

        // 1. Create Booking
        Booking booking = new Booking.Builder()
                .setUserId(userId)
                .setShowtimeId(showtimeId)
                .setTotalAmount(amount)
                .setBookingStatus("CONFIRMED")
                .build();
        
        int bookingId = bookingDAO.createBooking(booking);
        
        if (bookingId == -1) return false;

        // 2. Process Payment
        Payment payment = new Payment(0, bookingId, amount, paymentMethod, "PAID", null);
        paymentDAO.addPayment(payment);

        // 3. Send Notification via Adapter
        notificationService.sendNotification(
            String.valueOf(userId), 
            "Booking #" + bookingId + " confirmed for Showtime " + showtimeId
        );

        System.out.println("[FACADE] Purchase completed successfully.");
        return true;
    }

    public MovieService getMovieService() {
        return movieService;
    }
}
