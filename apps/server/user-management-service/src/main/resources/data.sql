INSERT INTO role (role_id, role_type)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN')
ON CONFLICT DO NOTHING;
