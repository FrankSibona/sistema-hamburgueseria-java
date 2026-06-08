CREATE DATABASE IF NOT EXISTS proyecto_sistema;
USE proyecto_sistema;

-- 1. Tabla de Usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contrasena_cifrada VARCHAR(255) NOT NULL,
    mail VARCHAR(150) UNIQUE NOT NULL,
    permisos VARCHAR(50) NOT NULL
);

-- 2. Tabla de Productos
CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    tipo VARCHAR(50) NOT NULL
);

-- 3. Tabla de Informes
CREATE TABLE informes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    descripcion TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
);
