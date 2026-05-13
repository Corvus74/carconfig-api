-- This script creates the initial database schema for the carconfig application.
-- It includes DROP statements to ensure a clean slate on first run.

-- Drop join table first due to foreign key constraints
DROP TABLE IF EXISTS car_config_special_equipments_group CASCADE;

-- Drop tables with foreign keys
DROP TABLE IF EXISTS car_order CASCADE;
DROP TABLE IF EXISTS car_colors_order CASCADE;
DROP TABLE IF EXISTS car_engine_order CASCADE;
DROP TABLE IF EXISTS car_rims_order CASCADE;
DROP TABLE IF EXISTS special_equipment_order CASCADE;

-- Drop base and user tables
DROP TABLE IF EXISTS car_color CASCADE;
DROP TABLE IF EXISTS car_engine CASCADE;
DROP TABLE IF EXISTS car_rim CASCADE;
DROP TABLE IF EXISTS special_equipment CASCADE;
DROP TABLE IF EXISTS orders_user CASCADE;
DROP TABLE IF EXISTS order_status CASCADE;

-- Drop sequences
DROP SEQUENCE IF EXISTS car_color_seq;
DROP SEQUENCE IF EXISTS car_engine_seq;
DROP SEQUENCE IF EXISTS car_rim_seq;
DROP SEQUENCE IF EXISTS car_rims_order_seq;
DROP SEQUENCE IF EXISTS special_equipment_seq;
DROP SEQUENCE IF EXISTS car_colors_order_seq;
DROP SEQUENCE IF EXISTS car_engine_order_seq;
DROP SEQUENCE IF EXISTS car_order_seq;
DROP SEQUENCE IF EXISTS orders_user_seq;
DROP SEQUENCE IF EXISTS special_equipment_order_seq;

-- Create sequence for entities if not using AUTO
CREATE SEQUENCE IF NOT EXISTS car_color_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS car_engine_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS car_rim_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS car_rims_order_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS special_equipment_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS car_colors_order_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS car_engine_order_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS car_order_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS orders_user_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS special_equipment_order_seq START WITH 1 INCREMENT BY 50;

-- Base configuration tables
CREATE TABLE car_color (
    id BIGINT NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    order_number VARCHAR(20),
    color_name VARCHAR(20),
    description VARCHAR(400),
    product_id VARCHAR(20),
    material_type VARCHAR(255),
    painting_type VARCHAR(255),
    color_code_hex VARCHAR(10),
    price INTEGER
);

CREATE TABLE car_engine (
    id BIGINT NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    order_number VARCHAR(20),
    description VARCHAR(400),
    fuel_type VARCHAR(20),
    engine_type VARCHAR(255),
    product_id VARCHAR(20),
    model VARCHAR(20),
    price INTEGER,
    car_name VARCHAR(255),
    displacement_l NUMERIC(4, 2),
    cylinders INTEGER,
    horsepower_kw NUMERIC(6, 2),
    torque_nm NUMERIC(6, 2),
    drivetrain VARCHAR(50),
    co2 NUMERIC(19, 2)
);

CREATE TABLE car_rim (
    id BIGINT NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    order_number VARCHAR(20),
    rim_name VARCHAR(20),
    inner_diameter INTEGER,
    model VARCHAR(20),
    description VARCHAR(400),
    product_id VARCHAR(20),
    price INTEGER
);

CREATE TABLE special_equipment (
    id BIGINT NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    order_number VARCHAR(20),
    category_type VARCHAR(20),
    equipment_name VARCHAR(30),
    description VARCHAR(400),
    product_id VARCHAR(20),
    equipment_location VARCHAR(255),
    price INTEGER
);

-- User and Order Status tables
CREATE TABLE orders_user (
    id BIGINT NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    user_id VARCHAR(40) NOT NULL,
    user_name VARCHAR(20),
    password VARCHAR(20),
    valid_until TIMESTAMP WITHOUT TIME ZONE,
    email VARCHAR(20),
    token VARCHAR(255),
    is_valid BOOLEAN
);

CREATE TABLE order_status (
    order_status_id UUID NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    current_status VARCHAR(255),
    shipping_date DATE,
    delivery_date DATE
);

-- Sub-order tables with foreign keys
CREATE TABLE car_colors_order (
    id BIGINT NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    car_color_order_id VARCHAR(40),
    car_color_id BIGINT REFERENCES car_color(id),
    order_status_id UUID REFERENCES order_status(order_status_id)
);

CREATE TABLE car_engine_order (
    id BIGINT NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    car_engine_order_id VARCHAR(40),
    car_engine_id BIGINT REFERENCES car_engine(id),
    order_status_id UUID REFERENCES order_status(order_status_id)
);

CREATE TABLE car_rims_order (
    id BIGINT NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    car_rim_order_id VARCHAR(40),
    car_rim_id BIGINT REFERENCES car_rim(id),
    order_status_id UUID REFERENCES order_status(order_status_id)
);

CREATE TABLE special_equipment_order (
    id BIGINT NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    special_equipment_order_id VARCHAR(40),
    special_equipment_id BIGINT REFERENCES special_equipment(id),
    order_status_id UUID REFERENCES order_status(order_status_id)
);

-- Main order table
CREATE TABLE car_order (
    id BIGINT NOT NULL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    car_order_id VARCHAR(40),
    order_user_id BIGINT REFERENCES orders_user(id),
    car_engine_order_id BIGINT REFERENCES car_engine_order(id),
    car_rim_order_id BIGINT REFERENCES car_rims_order(id),
    car_color_order_id BIGINT REFERENCES car_colors_order(id),
    order_status_id UUID REFERENCES order_status(order_status_id),
    description VARCHAR(400),
    total_price INTEGER
);

-- Join table for many-to-many relationship
CREATE TABLE car_config_special_equipments_group (
    car_order_id BIGINT NOT NULL REFERENCES car_order(id),
    special_equipment_order_id BIGINT NOT NULL REFERENCES special_equipment_order(id),
    PRIMARY KEY (car_order_id, special_equipment_order_id)
);
