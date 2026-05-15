package com.example.dp.dao;

import com.example.dp.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SeatDAO {

    private final Connection connection =
            DatabaseConnection
                    .getInstance()
                    .getConnection();

    public int getSeatIdBySeatNumber(
            String seat
    ) {

        String row =
                seat.substring(
                        0,
                        1
                );

        int number =
                Integer.parseInt(
                        seat.substring(1)
                );

        String query =
                "SELECT id FROM seats " +
                        "WHERE seat_row = ? " +
                        "AND seat_number = ?";

        try {

            PreparedStatement statement =
                    connection.prepareStatement(
                            query
                    );

            statement.setString(
                    1,
                    row
            );

            statement.setInt(
                    2,
                    number
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if(resultSet.next()) {

                return resultSet.getInt(
                        "id"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return -1;
    }
}