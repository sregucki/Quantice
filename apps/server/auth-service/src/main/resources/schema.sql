CREATE TABLE IF NOT EXISTS user_auth_data
(
    id                 UUID PRIMARY KEY,
    email              TEXT NOT NULL UNIQUE,
    password_hash      TEXT,
    auth_provider      TEXT NOT NULL,
    user_role          TEXT NOT NULL
);