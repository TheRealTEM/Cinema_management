-- Insert Sample Movies for Cinema Management System
USE cinema_system;

INSERT INTO movies (title, description, genre, duration_minutes, rating, language, release_date, poster_path, status)
VALUES
    ('The Dark Knight', 'A masked vigilante fights crime in Gotham City', 'Action', 152, '9.0', 'English', '2024-01-15', '/posters/dark-knight.jpg', 'NOW_SHOWING'),
    ('Inception', 'A thief who steals corporate secrets through dream-sharing technology', 'Sci-Fi', 148, '8.8', 'English', '2024-02-20', '/posters/inception.jpg', 'NOW_SHOWING'),
    ('Interstellar', 'A team of explorers travel through a wormhole in space', 'Sci-Fi', 169, '8.6', 'English', '2024-03-10', '/posters/interstellar.jpg', 'NOW_SHOWING'),
    ('Avatar', 'A paraplegic Marine dispatched to the moon Pandora', 'Sci-Fi', 192, '7.2', 'English', '2024-04-05', '/posters/avatar.jpg', 'NOW_SHOWING'),
    ('The Matrix', 'A computer hacker learns about the true nature of his reality', 'Sci-Fi', 136, '8.7', 'English', '2024-05-01', '/posters/matrix.jpg', 'NOW_SHOWING'),
    ('Avengers: Endgame', 'Final battle against Thanos to save the universe', 'Action', 181, '8.4', 'English', '2024-05-25', '/posters/avengers.jpg', 'COMING_SOON'),
    ('Dune', 'Political intrigue and battle on the desert planet Arrakis', 'Sci-Fi', 166, '8.0', 'English', '2024-06-10', '/posters/dune.jpg', 'COMING_SOON'),
    ('Oppenheimer', 'Story of J. Robert Oppenheimer and the Manhattan Project', 'Drama', 180, '8.5', 'English', '2024-06-20', '/posters/oppenheimer.jpg', 'COMING_SOON');

-- Verify movies were inserted
SELECT id, title, genre, status, release_date FROM movies ORDER BY release_date DESC;

