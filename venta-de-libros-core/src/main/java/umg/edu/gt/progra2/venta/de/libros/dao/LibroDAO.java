package umg.edu.gt.progra2.venta.de.libros.dao;

import umg.edu.gt.progra2.venta.de.libros.modelo.Libro;
import java.sql.*;
import java.time.Year;
import java.util.*;

public class LibroDAO {
	
	private static final String URL = "jdbc:mysql://localhost:3307/prog2_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "154872";
    
   
   
 
   
   
    
    public Libro crear(Libro libro) throws SQLException {
        // Validaciones de negocio
    	
        if (libro.getTitulo() == null || libro.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (libro.getAutor() == null || libro.getAutor().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        if (libro.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        if (libro.getExistencias() < 0) {
            throw new IllegalArgumentException("Las existencias no pueden ser negativas");
        }
        if (libro.getAnioPublicacion() > Year.now().getValue()) {
            throw new IllegalArgumentException("El año de publicación no puede ser mayor al actual");
        }

        // SQL para insertar
        String sql = "INSERT INTO libros (titulo, autor, categoria, precio, existencias, anio_publicacion) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getCategoria());
            statement.setDouble(4, libro.getPrecio());
            statement.setInt(5, libro.getExistencias());
            statement.setInt(6, libro.getAnioPublicacion());

            int filas = statement.executeUpdate();

            if (filas > 0) {
                try (ResultSet claves = statement.getGeneratedKeys()) {
                    if (claves.next()) {
                        libro.setId(claves.getInt(1));
                    }
                }
            }
        }
        return libro;
    }
    
    public List<Libro> listarTodos() throws SQLException {
        String sql = "SELECT id, titulo, autor, categoria, precio, existencias, anio_publicacion FROM libros ORDER BY id";
        List<Libro> libros = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                libros.add(mapearFila(resultado));
            }
        }
        return libros;
    }
    
    public Optional<Libro> buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, titulo, autor, categoria, precio, existencias, anio_publicacion FROM libros WHERE id = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearFila(resultado));
                }
                return Optional.empty();
            }
        }
    }

    public boolean actualizar(Libro libro) throws SQLException {
        // Validaciones de negocio
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        if (libro.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        if (libro.getExistencias() < 0) {
            throw new IllegalArgumentException("Las existencias no pueden ser negativas");
        }
        if (libro.getAnioPublicacion() > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("El año de publicación no puede ser mayor al actual");
        }

        String sql = "UPDATE libros SET titulo = ?, autor = ?, categoria = ?, precio = ?, existencias = ?, anio_publicacion = ? WHERE id = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getCategoria());
            statement.setDouble(4, libro.getPrecio());
            statement.setInt(5, libro.getExistencias());
            statement.setInt(6, libro.getAnioPublicacion());
            statement.setInt(7, libro.getId());

            int filas = statement.executeUpdate();
            return filas > 0; // true si se actualizó al menos un registro
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM libros WHERE id = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            int filas = statement.executeUpdate();
            return filas > 0; // true si se eliminó al menos un registro
        }
    }

    
    private Libro mapearFila(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("id");
        String titulo = resultado.getString("titulo");
        String autor = resultado.getString("autor");
        String categoria = resultado.getString("categoria");
        double precio = resultado.getDouble("precio");
        int existencias = resultado.getInt("existencias");
        int anio_publicacion = resultado.getInt("anio_publicacion");
        return new Libro(id, titulo, autor, categoria, precio, existencias, anio_publicacion);
    }
}