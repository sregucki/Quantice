CREATE TABLE IF NOT EXISTS user_auth_data
(
    id                 UUID PRIMARY KEY,
    username           TEXT NOT NULL,
    email              TEXT NOT NULL UNIQUE,
    password_hash      TEXT,
    image_url          TEXT,
    auth_provider      TEXT NOT NULL,
    user_role          TEXT NOT NULL
);