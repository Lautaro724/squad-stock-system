CREATE DATABASE squad_stock;

USE squad_stock;

CREATE TABLE categorias (
    categoria_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE productos (
    producto_id INT AUTO_INCREMENT PRIMARY KEY,
    categoria_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    talle VARCHAR(20) NOT NULL,
    color VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock_actual INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_productos_categorias
    FOREIGN KEY (categoria_id)
    REFERENCES categorias(categoria_id)
);

CREATE TABLE movimientos_stock (
    movimiento_id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT NOT NULL,
    tipo_movimiento ENUM('INGRESO', 'EGRESO') NOT NULL,
    cantidad INT NOT NULL,
    fecha_movimiento DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_movimientos_productos
    FOREIGN KEY (producto_id)
    REFERENCES productos(producto_id)
);

CREATE TABLE usuarios_administradores (
    usuario_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL
);

INSERT INTO categorias (nombre, descripcion)
VALUES
('Remeras Oversize', 'Remeras deportivas oversize SQUAD'),
('Musculosas', 'Musculosas deportivas para entrenamiento'),
('Accesorios', 'Accesorios deportivos de la marca');

INSERT INTO productos (categoria_id, nombre, talle, color, precio, stock_actual)
VALUES
(1, 'Remera Oversize Disciplina', 'L', 'Negro', 24000, 10),
(1, 'Remera Oversize One More Rep', 'M', 'Blanco', 24000, 8),
(2, 'Musculosa SQUAD Training Club', 'XL', 'Gris', 22000, 5);

INSERT INTO usuarios_administradores (nombre, usuario, contrasena)
VALUES
('Lautaro', 'lautaro_admin', 'admin123');

INSERT INTO movimientos_stock (producto_id, tipo_movimiento, cantidad)
VALUES
(1, 'INGRESO', 10),
(2, 'INGRESO', 8),
(3, 'INGRESO', 5);


SELECT * FROM categorias;

SELECT * FROM productos;

SELECT * FROM movimientos_stock;

SELECT * FROM usuarios_administradores;


DELETE FROM movimientos_stock
WHERE movimiento_id = 3;

SELECT * FROM movimientos_stock;