CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS user_login_data
(
    user_id           UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    login_name        TEXT NOT NULL UNIQUE,
    password_hash     TEXT NOT NULL,
    roles             TEXT NOT NULL
);