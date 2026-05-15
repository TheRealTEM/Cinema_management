-- Insert Admin Account for Cinema Management System
USE cinema_system;

INSERT INTO users (
    full_name,
    email,
    password,
    phone,
    role,
    customer_type
)
VALUES (
    'Cinema Admin',
    'admin.dashboard@cinema.com',
    'AdminPanel@2026',
    '+91-9000000000',
    'ADMIN',
    'VIP'
);

