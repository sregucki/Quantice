CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS user_login_data
(
    user_id           UUID DEFAULT gen_random_uuid(),
    login_name        TEXT NOT NULL,
    password_hash     TEXT NOT NULL,
    roles             TEXT NOT NULL,
    PRIMARY KEY (user_id)
);