# examen02-variante-b-progra2A
# 📚 Catálogo de Librería 

## 📝 Descripción
Aplicación de escritorio desarrollada en **Java (Swing + JDBC)** para la gestión de un catálogo de libros.  
Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre una base de datos MySQL.

---

## 🧱 Arquitectura
- **Modelo (`Libro`)**: Representa la entidad libro con atributos como título, autor, categoría, precio, existencias y año de publicación.  
- **DAO (`LibroDAO`)**: Encapsula la lógica de acceso a datos mediante JDBC.  
- **UI (`VentanaPrincipal`)**: Interfaz gráfica con `JTable` y formularios para gestionar los libros.  
- **Main**: Punto de entrada de la aplicación.

---

## ⚙️ Tecnologías utilizadas
- Java 17  
- Swing (Interfaz gráfica)  
- JDBC (Conexión a MySQL)  
- MySQL/MariaDB (Base de datos)  
- Maven (Gestión de dependencias)

---

## 📄 Funcionalidades
- **Listado de libros** con título, autor, categoría, precio y existencias.  
- **Registro de libro nuevo** mediante formulario.  
- **Edición de libro existente** al seleccionar un registro en la tabla.  
- **Eliminación de libro** con confirmación previa.  
- Validaciones de negocio: precio > 0, existencias ≥ 0, año ≤ actual.

---

---

## 🚀 Ejecución
1. Configura la base de datos MySQL:
   ```sql
   CREATE DATABASE prog2_db;
   USE prog2_db;

   CREATE TABLE libros (
       id INT AUTO_INCREMENT PRIMARY KEY,
       titulo VARCHAR(150) NOT NULL,
       autor VARCHAR(100) NOT NULL,
       categoria VARCHAR(50),
       precio DOUBLE NOT NULL,
       existencias INT NOT NULL,
       anio_publicacion INT NOT NULL
   );
