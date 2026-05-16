package com.example.dp.dao;

import com.example.dp.database.DatabaseConnection;
import com.example.dp.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private Connection connection;

    public PaymentDAO() {
        connection = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    public Payment getPaymentById(int paymentId) {
        String query =
                "SELECT * FROM payments WHERE id = ?";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, paymentId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                Payment payment = new Payment();
                payment.setId(resultSet.getInt("id"));
                payment.setBookingId(resultSet.getInt("booking_id"));
                payment.setPaymentMethod(resultSet.getString("payment_method"));
                payment.setAmount(resultSet.getDouble("amount"));
                payment.setPaymentStatus(resultSet.getString("payment_status"));
                payment.setPaymentDate(resultSet.getTimestamp("payment_date"));
                return payment;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public double getTotalRevenuePaid() {
        String query =
                "SELECT COALESCE(SUM(amount), 0) as total FROM payments WHERE payment_status = 'PAID'";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public double getTotalRevenueByMethod(String method) {
        String query =
                "SELECT COALESCE(SUM(amount), 0) as total FROM payments " +
                "WHERE payment_status = 'PAID' AND payment_method = ?";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(1, method);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payments";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setId(resultSet.getInt("id"));
                payment.setBookingId(resultSet.getInt("booking_id"));
                payment.setPaymentMethod(resultSet.getString("payment_method"));
                payment.setAmount(resultSet.getDouble("amount"));
                payment.setPaymentStatus(resultSet.getString("payment_status"));
                payment.setPaymentDate(resultSet.getTimestamp("payment_date"));
                payments.add(payment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return payments;
    }

    public List<Payment> getPaymentsByStatus(String status) {
        List<Payment> payments = new ArrayList<>();
        String query =
                "SELECT * FROM payments WHERE payment_status = ?";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(1, status);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setId(resultSet.getInt("id"));
                payment.setBookingId(resultSet.getInt("booking_id"));
                payment.setPaymentMethod(resultSet.getString("payment_method"));
                payment.setAmount(resultSet.getDouble("amount"));
                payment.setPaymentStatus(resultSet.getString("payment_status"));
                payment.setPaymentDate(resultSet.getTimestamp("payment_date"));
                payments.add(payment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return payments;
    }

    public List<Payment> getPaymentsByMethod(String method) {
        List<Payment> payments = new ArrayList<>();
        String query =
                "SELECT * FROM payments WHERE payment_method = ?";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(1, method);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setId(resultSet.getInt("id"));
                payment.setBookingId(resultSet.getInt("booking_id"));
                payment.setPaymentMethod(resultSet.getString("payment_method"));
                payment.setAmount(resultSet.getDouble("amount"));
                payment.setPaymentStatus(resultSet.getString("payment_status"));
                payment.setPaymentDate(resultSet.getTimestamp("payment_date"));
                payments.add(payment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return payments;
    }

    public List<Payment> getPaymentsByFilters(
            String status,
            String method
    ) {
        List<Payment> payments = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM payments WHERE 1=1");

        if (status != null && !status.equals("All Statuses")) {
            query.append(" AND payment_status = ?");
        }

        if (method != null && !method.equals("All Methods")) {
            query.append(" AND payment_method = ?");
        }

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query.toString());

            int index = 1;
            if (status != null && !status.equals("All Statuses")) {
                statement.setString(index++, status);
            }

            if (method != null && !method.equals("All Methods")) {
                statement.setString(index, method);
            }

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setId(resultSet.getInt("id"));
                payment.setBookingId(resultSet.getInt("booking_id"));
                payment.setPaymentMethod(resultSet.getString("payment_method"));
                payment.setAmount(resultSet.getDouble("amount"));
                payment.setPaymentStatus(resultSet.getString("payment_status"));
                payment.setPaymentDate(resultSet.getTimestamp("payment_date"));
                payments.add(payment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return payments;
    }

    public boolean updatePaymentStatus(
            int paymentId,
            String status
    ) {
        String query =
                "UPDATE payments SET payment_status = ? WHERE id = ?";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setString(1, status);
            statement.setInt(2, paymentId);

            int rows = statement.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean createPayment(
            int bookingId,
            String method,
            double amount
    ) {
        String query =
                "INSERT INTO payments (booking_id, payment_method, amount, payment_status, payment_date) " +
                "VALUES (?, ?, ?, 'PENDING', NOW())";

        try {
            PreparedStatement statement =
                    connection.prepareStatement(query);

            statement.setInt(1, bookingId);
            statement.setString(2, method);
            statement.setDouble(3, amount);

            int rows = statement.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}

