```sql
CREATE DATABASE cinema_system;

USE cinema_system;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,

    full_name VARCHAR(100) NOT NULL,

    email VARCHAR(100) UNIQUE NOT NULL,

    password VARCHAR(255) NOT NULL,

    phone VARCHAR(20),

    role ENUM('ADMIN', 'CUSTOMER')
    DEFAULT 'CUSTOMER',

    customer_type ENUM('NORMAL', 'VIP')
    DEFAULT 'NORMAL',

    created_at TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE movies (
    id INT PRIMARY KEY AUTO_INCREMENT,

    title VARCHAR(150) NOT NULL,

    description TEXT,

    genre VARCHAR(50),

    duration_minutes INT,

    rating VARCHAR(10),

    language VARCHAR(50),

    release_date DATE,

    poster_path VARCHAR(255),

    status ENUM('NOW_SHOWING', 'COMING_SOON')
    DEFAULT 'COMING_SOON',

    created_at TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cinema_halls (
    id INT PRIMARY KEY AUTO_INCREMENT,

    hall_name VARCHAR(50) NOT NULL,

    capacity INT NOT NULL,

    screen_type ENUM('2D', '3D', 'IMAX')
    DEFAULT '2D'
);

CREATE TABLE seats (
    id INT PRIMARY KEY AUTO_INCREMENT,

    hall_id INT NOT NULL,

    seat_row CHAR(1) NOT NULL,

    seat_number INT NOT NULL,

    seat_type ENUM('REGULAR', 'VIP')
    DEFAULT 'REGULAR',

    FOREIGN KEY (hall_id)
    REFERENCES cinema_halls(id),

    UNIQUE(hall_id, seat_row, seat_number)
);

CREATE TABLE showtimes (
    id INT PRIMARY KEY AUTO_INCREMENT,

    movie_id INT NOT NULL,

    hall_id INT NOT NULL,

    start_time DATETIME NOT NULL,

    end_time DATETIME NOT NULL,

    base_price DECIMAL(10,2) NOT NULL,

    status ENUM('AVAILABLE', 'CANCELLED', 'FULL')
    DEFAULT 'AVAILABLE',

    FOREIGN KEY (movie_id)
    REFERENCES movies(id),

    FOREIGN KEY (hall_id)
    REFERENCES cinema_halls(id)
);

CREATE TABLE bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,

    user_id INT NOT NULL,

    showtime_id INT NOT NULL,

    booking_date TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP,

    total_amount DECIMAL(10,2) NOT NULL,

    booking_status ENUM('PENDING', 'CONFIRMED', 'CANCELLED')
    DEFAULT 'PENDING',

    FOREIGN KEY (user_id)
    REFERENCES users(id),

    FOREIGN KEY (showtime_id)
    REFERENCES showtimes(id)
);

CREATE TABLE booking_seats (
    booking_id INT NOT NULL,

    showtime_id INT NOT NULL,

    seat_id INT NOT NULL,

    PRIMARY KEY (booking_id, seat_id),

    FOREIGN KEY (booking_id)
    REFERENCES bookings(id),

    FOREIGN KEY (showtime_id)
    REFERENCES showtimes(id),

    FOREIGN KEY (seat_id)
    REFERENCES seats(id),

    UNIQUE(showtime_id, seat_id)
);

CREATE TABLE payments (
    id INT PRIMARY KEY AUTO_INCREMENT,

    booking_id INT NOT NULL,

    payment_method ENUM('CASH', 'CARD', 'WALLET')
    NOT NULL,

    amount DECIMAL(10,2) NOT NULL,

    payment_status ENUM('PENDING', 'PAID', 'FAILED')
    DEFAULT 'PENDING',

    payment_date TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (booking_id)
    REFERENCES bookings(id)
);

INSERT INTO users (
    full_name,
    email,
    password,
    role,
    customer_type
)
VALUES (
    'Admin',
    'admin@gmail.com',
    '1234',
    'ADMIN',
    'VIP'
);
```
