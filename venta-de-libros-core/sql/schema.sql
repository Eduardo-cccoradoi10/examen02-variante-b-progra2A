CREATE DATABASE IF NOT EXISTS prog2_db;

USE prog2_db;

CREATE TABLE IF NOT EXISTS libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    existencias INT NOT NULL,
    anio_publicacion INT NOT NULL
);

-- Agregar columna categoria
ALTER TABLE libros
ADD COLUMN categoria VARCHAR(50) NOT NULL;

-- Agregar columna precio con restricción positiva
ALTER TABLE libros
ADD COLUMN precio DECIMAL(10,2) NOT NULL;

-- Agregar columna existencias con restricción >= 0
ALTER TABLE libros
ADD COLUMN existencias INT NOT NULL;

-- Agregar columna año de publicación con restricción <= año actual
ALTER TABLE libros
ADD COLUMN anio_publicacion INT NOT NULL;
