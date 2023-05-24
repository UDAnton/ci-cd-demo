-- Create movies table
CREATE TABLE movies
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255)                                                                                                         NOT NULL,
    genre ENUM ('ACTION', 'COMEDY', 'DRAMA', 'FANTASY', 'HORROR', 'ROMANCE', 'THRILLER', 'ANIMATION', 'DOCUMENTARY', 'SCI_FI') NOT NULL,
    tags  SET ('Happy', 'Sad', 'Excited', 'Angry', 'Relaxed', 'Surprised', 'Scared', 'Romantic')                               NOT NULL
);

-- Add indexes for faster querying
CREATE INDEX idx_movies_genre ON movies (genre);

-- Insert sample movie records
INSERT INTO movies (title, genre, tags) VALUES ('The Shawshank Redemption', 'DRAMA', 'Sad, Relaxed');
INSERT INTO movies (title, genre, tags) VALUES ('Pulp Fiction', 'CRIME', 'Excited, Scared, Surprised');
INSERT INTO movies (title, genre, tags) VALUES ('The Dark Knight', 'ACTION', 'Excited, Scared');
INSERT INTO movies (title, genre, tags) VALUES ('Forrest Gump', 'DRAMA', 'Happy, Sad, Romantic');
INSERT INTO movies (title, genre, tags) VALUES ('The Matrix', 'SCI_FI', 'Excited, Surprised');
INSERT INTO movies (title, genre, tags) VALUES ('The Godfather', 'CRIME', 'Angry, Scared');
INSERT INTO movies (title, genre, tags) VALUES ('The Avengers', 'ACTION', 'Excited');
INSERT INTO movies (title, genre, tags) VALUES ('Titanic', 'ROMANCE', 'Sad, Romantic');
INSERT INTO movies (title, genre, tags) VALUES ('Jurassic Park', 'ADVENTURE', 'Excited, Scared');
INSERT INTO movies (title, genre, tags) VALUES ('Inception', 'SCI_FI', 'Surprised');

