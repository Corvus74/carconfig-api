INSERT INTO car_engine_order (id, car_engine_order_id, car_engine_id, order_status_id, delete_flag, created_at, updated_at, created_by, updated_by)
VALUES
(1, 'ceo-1', 1, 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', NULL, NOW(), NOW(), 'test', 'test'),
(2, 'ceo-2', 2, 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', NULL, NOW(), NOW(), 'test', 'test'),
(3, 'ceo-3', 1, 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Y', NOW(), NOW(), 'test', 'test');
