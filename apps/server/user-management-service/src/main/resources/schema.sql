CREATE TABLE IF NOT EXISTS role
(
    role_id            SERIAL PRIMARY KEY,
    role_type          TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS auth_provider
(
    auth_provider_id        SERIAL PRIMARY KEY,
    auth_provider_type      TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS user_data
(
    user_id                 UUID PRIMARY KEY,
    user_email              TEXT NOT NULL UNIQUE,
    user_name               TEXT NOT NULL,
    role_id_fk              INTEGER NOT NULL,
    user_is_active          BOOLEAN NOT NULL,
    user_created_at         TIMESTAMP NOT NULL,
    user_modified_at        TIMESTAMP NOT NULL,

    CONSTRAINT user_data_role_fk FOREIGN KEY(role_id_fk) REFERENCES role(role_id)
);

CREATE TABLE IF NOT EXISTS token_data
(
    token_id                UUID PRIMARY KEY,
    access_token            TEXT NOT NULL,
    refresh_token           TEXT NOT NULL,
    user_id_fk              UUID UNIQUE NOT NULL,
    auth_provider_id_fk     INTEGER NOT NULL,
    token_issued_at         TIMESTAMP NOT NULL,
    token_expires_at        TIMESTAMP NOT NULL,

    CONSTRAINT token_data_user_data_fk FOREIGN KEY(user_id_fk) REFERENCES user_data(user_id),
    CONSTRAINT token_data_auth_provider_fk FOREIGN KEY (auth_provider_id_fk) REFERENCES auth_provider(auth_provider_id)
);