package umg.edu.gt.progra2.venta.de.libros.temp;


import umg.edu.gt.progra2.venta.de.libros.dao.LibroDAO;
import umg.edu.gt.progra2.venta.de.libros.modelo.Libro;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        LibroDAO dao = new LibroDAO();

        try {
            System.out.println("===== 1. CREAR =====");
            Libro nuevo = new Libro(0, "Clean Code", "Robert C. Martin", "Programación", 450.0, 10, 2008);
            Libro creado = dao.crear(nuevo);
            System.out.println("Libro creado con ID: " + creado.getId());

            System.out.println("\n===== 2. LISTAR TODOS =====");
            List<Libro> libros = dao.listarTodos();
            for (Libro l : libros) {
                System.out.println(l.getId() + " - " + l.getTitulo() + " - " + l.getAutor());
            }

            System.out.println("\n===== 3. BUSCAR POR ID =====");
            Optional<Libro> buscado = dao.buscarPorId(creado.getId());
            if (buscado.isPresent()) {
                System.out.println("Encontrado: " + buscado.get().getTitulo());
            } else {
                System.out.println("No se encontró el libro con ID " + creado.getId());
            }

            System.out.println("\n===== 4. ACTUALIZAR =====");
            creado.setPrecio(500.0);
            boolean actualizado = dao.actualizar(creado);
            System.out.println(actualizado ? "Libro actualizado correctamente" : "No se pudo actualizar");

            System.out.println("\n===== 5. ELIMINAR =====");
            boolean eliminado = dao.eliminar(creado.getId());
            System.out.println(eliminado ? "Libro eliminado correctamente" : "No se pudo eliminar");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
