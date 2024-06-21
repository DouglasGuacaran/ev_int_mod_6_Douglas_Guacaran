-- Crear base de datos
CREATE DATABASE alke_wallet;

-- Usar la base de datos
USE alke_wallet;

-- Crear tabla de roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Crear tabla de usuarios
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(255) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_email (email)
);

-- Crear tabla de relación usuario_roles
CREATE TABLE usuario_roles (
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, role_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Crear tabla de cuentas
CREATE TABLE cuenta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    saldo DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Crear tabla de transacciones
CREATE TABLE transaccion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cuenta_origen_id BIGINT,
    cuenta_destino_id BIGINT,
    monto DECIMAL(15, 2) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cuenta_origen_id) REFERENCES cuenta(id),
    FOREIGN KEY (cuenta_destino_id) REFERENCES cuenta(id)
);

-- Insertar roles
INSERT INTO roles (role_name) VALUES ('USER');
INSERT INTO roles (role_name) VALUES ('ADMIN');

-- Insertar usuarios
-- Asegúrate de que las contraseñas estén encriptadas usando BCrypt antes de insertarlas
INSERT INTO usuario (rut, nombre, apellido, direccion, email, password) VALUES 
('12345678-9', 'John', 'Doe', '123 Main St', 'john@example.com', '$2a$10$7dSGiUX8EeC5wGFL1Z0uUORiPZlE5ZdX/dU.kTZQG7TT.yBO5Y5nu'), -- password: password123
('98765432-1', 'Jane', 'Smith', '456 Elm St', 'jane@example.com', '$2a$10$eI.1YZJ4B.TF8lUJh0PVs.yxzGlt.1YH7c8VhE9ht7FjE6R9KdePe'); -- password: password123

-- Asignar roles a los usuarios
INSERT INTO usuario_roles (usuario_id, role_id) VALUES 
((SELECT id FROM usuario WHERE email = 'john@example.com'), (SELECT id FROM roles WHERE role_name = 'USER')),
((SELECT id FROM usuario WHERE email = 'jane@example.com'), (SELECT id FROM roles WHERE role_name = 'ADMIN'));

-- Insertar cuentas para los usuarios
INSERT INTO cuenta (usuario_id, saldo) VALUES 
((SELECT id FROM usuario WHERE email = 'john@example.com'), 1000.00),
((SELECT id FROM usuario WHERE email = 'jane@example.com'), 2000.00);

-- Insertar transacciones iniciales
INSERT INTO transaccion (cuenta_origen_id, cuenta_destino_id, monto, tipo) VALUES 
((SELECT id FROM cuenta WHERE usuario_id = (SELECT id FROM usuario WHERE email = 'john@example.com')), (SELECT id FROM cuenta WHERE usuario_id = (SELECT id FROM usuario WHERE email = 'jane@example.com')), 100.00, 'TRANSFERENCIA'),
((SELECT id FROM cuenta WHERE usuario_id = (SELECT id FROM usuario WHERE email = 'jane@example.com')), (SELECT id FROM cuenta WHERE usuario_id = (SELECT id FROM usuario WHERE email = 'john@example.com')), 50.00, 'TRANSFERENCIA');
