INSERT INTO auth_provider (auth_provider_id, auth_provider_name, auth_provider_type)
VALUES (1, 'quantice', 'INTERNAL'),
       (2, 'google', 'EXTERNAL')
ON CONFLICT DO NOTHING;