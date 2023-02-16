INSERT INTO role (role_id, role_type)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO auth_provider (auth_provider_id, auth_provider_type)
VALUES (1, 'QUANTICE'),
       (2, 'GOOGLE')
ON CONFLICT DO NOTHING;