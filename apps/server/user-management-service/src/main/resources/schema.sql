CREATE TABLE IF NOT EXISTS user_data
(
    user_id                 TEXT NOT NULL UNIQUE PRIMARY KEY,
    user_email              TEXT NOT NULL UNIQUE,
    user_name               TEXT NOT NULL,
    user_password_hash      TEXT NOT NULL
);