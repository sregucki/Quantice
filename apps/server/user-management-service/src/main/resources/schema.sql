CREATE TABLE IF NOT EXISTS role
(
    role_id            SERIAL PRIMARY KEY,
    role_type          TEXT NOT NULL
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
