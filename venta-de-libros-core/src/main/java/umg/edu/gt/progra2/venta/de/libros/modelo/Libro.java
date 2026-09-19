package umg.edu.gt.progra2.venta.de.libros.modelo;

public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private double precio;
    private int existencias;
    private int anio_publicacion;
    private boolean disponibleParaPrestamo;

    
    //Constructor
    public Libro(int id, String titulo, String autor, String categoria, double precio, int existencias, int anio_publicacion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.precio = precio;
        this.existencias = existencias;
        this.anio_publicacion = anio_publicacion;
    }
    
    public Libro(String titulo, String autor, String categoria, double precio, int existencias, int anio_publicacion) {
        this(0, titulo, autor, categoria, precio, existencias, anio_publicacion);
    }
    
    // Getters y setters
    
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getExistencias() {
		return existencias;
	}
	public void setExistencias(int existencias) {
		this.existencias = existencias;
	}
	public int getAnioPublicacion() {
		return anio_publicacion;
	}
	public void setAnioPublicacion(int anio_publicacion) {
		this.anio_publicacion = anio_publicacion;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public boolean isDisponibleParaPrestamo() {
		return disponibleParaPrestamo;
	}

	public void setDisponibleParaPrestamo(boolean disponibleParaPrestamo) {
		this.disponibleParaPrestamo = disponibleParaPrestamo;
	}

}
