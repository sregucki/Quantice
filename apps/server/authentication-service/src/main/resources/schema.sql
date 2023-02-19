CREATE TABLE IF NOT EXISTS oidc_provider
(
    id                 SERIAL PRIMARY KEY,
    oidc_provider_name      TEXT NOT NULL,
    oidc_provider_type      TEXT NOT NULL
);


CREATE TABLE IF NOT EXISTS user_login_data
(
    id                 UUID PRIMARY KEY,
    email              TEXT NOT NULL UNIQUE,
    password_hash      TEXT,
    auth_provider_id   INTEGER NOT NULL,

    CONSTRAINT oidc_provider_id_fk FOREIGN KEY(auth_provider_id) REFERENCES oidc_provider(id)
);

