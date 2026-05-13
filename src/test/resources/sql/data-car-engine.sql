INSERT INTO car_engine (id, order_number, description, fuel_type, engine_type, product_id, model, price, car_name, displacement_l, cylinders, horsepower_kw, torque_nm, drivetrain, co2,  created_at, updated_at, created_by, updated_by)
VALUES
(1, 'E001', '2.0L 4-cylinder petrol engine', 'GASOLINE', 'I4', 'P-E01', 'B48', 8000, '330i', 2.0, 4, 190, 400, 'RWD', 140,  NOW(), NOW(), 'test', 'test'),
(2, 'E002', '3.0L 6-cylinder diesel engine', 'HYBRID', 'I6', 'P-E02', 'B57', 12000, '330d', 3.0, 6, 210, 620, 'xDrive', 160,  NOW(), NOW(), 'test', 'test'),
(3, 'E003', 'Electric motor', 'ELECTRIC', 'E', 'P-E03', 'eDrive40', 15000, 'i4', 0, 0, 250, 430, 'RWD', 0,  NOW(), NOW(), 'test', 'test'),
(4, 'E004', '3.0L 6-cylinder petrol engine', 'GASOLINE', 'I6', 'P-E02', 'B58', 11000, 'M340i', 3.0, 6, 275, 500, 'xDrive', 175,  NOW(), NOW(), 'test', 'test');
