package umg.edu.gt.progra2.venta.de.libros.dao;

import umg.edu.gt.progra2.venta.de.libros.modelo.Libro;
import java.sql.*;
import java.util.*;

public class LibroDAO {
    public Libro crear(Libro libro) throws SQLException { ... }
    public List<Libro> listarTodos() throws SQLException { ... }
    public Optional<Libro> buscarPorId(int id) throws SQLException { ... }
    public boolean actualizar(Libro libro) throws SQLException { ... }
    public boolean eliminar(int id) throws SQLException { ... }
}
