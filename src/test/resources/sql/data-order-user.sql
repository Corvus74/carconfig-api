INSERT INTO orders_user (id, user_id, user_name, email, password, is_valid, role, created_at, updated_at, created_by, updated_by)
VALUES
(1, 'user-123', 'testuser', 'test@example.com', '$2a$10$P95yi28vQPLyDBCI1XTX3O9JFALGHk.k.gzjNolJIQhhTG1KoBnLG', true, 'ADMIN', NOW(), NOW(), 'test', 'test'),
(2, 'user-456', 'inactiveuser', 'inactive@example.com', '$2a$10$P95yi28vQPLyDBCI1XTX3O9JFALGHk.k.gzjNolJIQhhTG1KoBnLG', false, 'USER', NOW(), NOW(), 'test', 'test'),
(3, 'user-789', 'deleteduser', 'deleted@example.com', '$2a$10$P95yi28vQPLyDBCI1XTX3O9JFALGHk.k.gzjNolJIQhhTG1KoBnLG', true, 'USER', NOW(), NOW(), 'test', 'test');
