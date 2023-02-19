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
    auth_provider_id        INTEGER NOT NULL,

    CONSTRAINT auth_provider_id_fk FOREIGN KEY(auth_provider_id) REFERENCES auth_provider(auth_provider_id)
);

CREATE TABLE IF NOT EXISTS token_data
(
    token_id                BIGSERIAL PRIMARY KEY,
    access_token            TEXT NOT NULL,
    refresh_token           TEXT,
    token_issued_at         TIMESTAMP NOT NULL,
    token_expires_at        TIMESTAMP NOT NULL,
    user_id                 UUID NOT NULL,

    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user_login_data(user_id)
);

