INSERT INTO orders_user (id, user_id, user_name, email, is_valid, delete_flag, created_at, updated_at, created_by, updated_by)
VALUES
(1, 'user-123', 'testuser', 'test@example.com', true, NULL, NOW(), NOW(), 'test', 'test'),
(2, 'user-456', 'inactiveuser', 'inactive@example.com', false, NULL, NOW(), NOW(), 'test', 'test'),
(3, 'user-789', 'deleteduser', 'deleted@example.com', true, 'Y', NOW(), NOW(), 'test', 'test');
