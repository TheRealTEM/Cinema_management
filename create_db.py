import sqlite3

sql_script = """
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'CUSTOMER',
    customer_type VARCHAR(20) DEFAULT 'NORMAL',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS movies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    genre VARCHAR(50),
    duration_minutes INT,
    rating VARCHAR(10),
    language VARCHAR(50),
    release_date DATE,
    poster_path VARCHAR(255),
    status VARCHAR(20) DEFAULT 'COMING_SOON',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cinema_halls (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    hall_name VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    screen_type VARCHAR(20) DEFAULT '2D'
);

CREATE TABLE IF NOT EXISTS seats (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    hall_id INT NOT NULL,
    seat_row CHAR(1) NOT NULL,
    seat_number INT NOT NULL,
    seat_type VARCHAR(20) DEFAULT 'REGULAR',
    FOREIGN KEY (hall_id) REFERENCES cinema_halls(id),
    UNIQUE(hall_id, seat_row, seat_number)
);

CREATE TABLE IF NOT EXISTS showtimes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    movie_id INT NOT NULL,
    hall_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    base_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (hall_id) REFERENCES cinema_halls(id)
);

CREATE TABLE IF NOT EXISTS bookings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INT NOT NULL,
    showtime_id INT NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    booking_status VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (showtime_id) REFERENCES showtimes(id)
);

CREATE TABLE IF NOT EXISTS booking_seats (
    booking_id INT NOT NULL,
    showtime_id INT NOT NULL,
    seat_id INT NOT NULL,
    PRIMARY KEY (booking_id, seat_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (showtime_id) REFERENCES showtimes(id),
    FOREIGN KEY (seat_id) REFERENCES seats(id),
    UNIQUE(showtime_id, seat_id)
);

CREATE TABLE IF NOT EXISTS payments (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    booking_id INT NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

INSERT OR IGNORE INTO users (full_name, email, password, role, customer_type)
VALUES ('Admin', 'admin@gmail.com', '1234', 'ADMIN', 'VIP');
"""

conn = sqlite3.connect('cinema_system.db')
cursor = conn.cursor()
cursor.executescript(sql_script)
conn.commit()
conn.close()
print("Database created successfully")
