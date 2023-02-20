INSERT INTO oidc_provider (id, oidc_provider_name, oidc_provider_type)
VALUES (1, 'quantice', 'INTERNAL'),
       (2, 'google', 'EXTERNAL')
ON CONFLICT DO NOTHING;