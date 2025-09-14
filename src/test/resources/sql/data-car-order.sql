INSERT INTO car_order (id, car_order_id, order_user_id, car_engine_order_id, car_rim_order_id, car_color_order_id, order_status_id, description, total_price, delete_flag, created_at, updated_at, created_by, updated_by)
VALUES
(1, 'order-1', 1, 1, 1, 1, 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'A standard order', 50000, NULL, NOW(), NOW(), 'test', 'test'),
(2, 'order-2', 2, 2, 2, 2, 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'A second order with equipment', 75000, NULL, NOW(), NOW(), 'test', 'test'),
(3, 'order-3', 1, 1, 1, 3, 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'A deleted order', 60000, 'Y', NOW(), NOW(), 'test', 'test');

-- Link special equipment to car_order with id=2
INSERT INTO car_config_special_equipments_group (car_order_id, special_equipment_order_id)
VALUES
(2, 1),
(2, 2);
