CREATE TABLE IF NOT EXISTS user_data
(
    user_id                 UUID NOT NULL UNIQUE PRIMARY KEY,
    user_email              TEXT NOT NULL UNIQUE,
    user_name               TEXT NOT NULL,
    user_password_hash      TEXT,
    user_auth_provider      TEXT NOT NULL,
    user_role               TEXT NOT NULL,
    user_is_active          BOOLEAN NOT NULL,
    user_created_at         TIMESTAMP NOT NULL,
    user_modified_at        TIMESTAMP NOT NULL
);