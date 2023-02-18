INSERT INTO auth_provider (auth_provider_id, auth_provider_name, auth_provider_type)
VALUES (1, 'QUANTICE', 'INTERNAL'),
       (2, 'GOOGLE', 'EXTERNAL')
ON CONFLICT DO NOTHING;