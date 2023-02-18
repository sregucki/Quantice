CREATE TABLE IF NOT EXISTS auth_provider
(
    auth_provider_id        SERIAL PRIMARY KEY,
    auth_provider_name      TEXT NOT NULL,
    auth_provider_type      TEXT NOT NULL
);


CREATE TABLE IF NOT EXISTS user_login_data
(
    user_id                 UUID PRIMARY KEY,
    user_email              TEXT NOT NULL UNIQUE,
    user_password_hash      TEXT,
    auth_provider_id        INTEGER NOT NULL
);