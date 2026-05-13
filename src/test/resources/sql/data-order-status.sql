INSERT INTO order_status (order_status_id, current_status, shipping_date, delivery_date, created_at, updated_at, created_by, updated_by)
VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'RECEIVED', NULL, NULL, NOW(), NOW(), 'test', 'test'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'IN_PROGRESS', '2024-08-01', '2024-08-15',  NOW(), NOW(), 'test', 'test'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'PENDING', '2024-07-20', '2024-08-01', NOW(), NOW(), 'test', 'test');
