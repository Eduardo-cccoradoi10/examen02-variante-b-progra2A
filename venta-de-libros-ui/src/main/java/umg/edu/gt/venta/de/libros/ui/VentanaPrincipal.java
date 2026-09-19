package umg.edu.gt.venta.de.libros.ui;

import umg.edu.gt.progra2.venta.de.libros.modelo.*;
import umg.edu.gt.progra2.venta.de.libros.dao.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

    private final LibroDAO libroDAO;
    private final DefaultTableModel tableModel;

    // Componentes visuales
    private JTable tablaLibros;
    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtCategoria;
    private JTextField txtPrecio;
    private JTextField txtExistencias;
    private JTextField txtAnio;

    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnEliminar;
    
    private JCheckBox chkDisponible;


    public VentanaPrincipal() {
        this.libroDAO = new LibroDAO();

        
        setTitle("Catálogo de Librería");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Configuración de la Tabla (Listado)
        String[] columnas = {"ID", "Título", "Autor", "Categoría", "Precio (Q)", "Existencias", "Año", "Disponible"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita edición directa sobre las celdas
            }
        };

        tablaLibros = new JTable(tableModel);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTabla = new JScrollPane(tablaLibros);
        add(scrollTabla, BorderLayout.CENTER);

        // 2. Formulario y Botones (Panel Este)
        JPanel panelDerecho = new JPanel(new BorderLayout(5, 5));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 8));
        txtId = new JTextField();
        txtId.setEditable(false); // Autoincremental por la BD
        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtCategoria = new JTextField();
        txtPrecio = new JTextField();
        txtExistencias = new JTextField();
        txtAnio = new JTextField();
        chkDisponible = new JCheckBox("Disponible para préstamo");

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Título:"));
        panelFormulario.add(txtTitulo);
        panelFormulario.add(new JLabel("Autor:"));
        panelFormulario.add(txtAutor);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(txtCategoria);
        panelFormulario.add(new JLabel("Precio de Venta:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Existencias:"));
        panelFormulario.add(txtExistencias);
        panelFormulario.add(new JLabel("Año de Publicación:"));
        panelFormulario.add(txtAnio);
        panelFormulario.add(chkDisponible);

        panelDerecho.add(panelFormulario, BorderLayout.NORTH);

        // Panel de Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Nuevo / Limpiar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEliminar);

        panelDerecho.add(panelBotones, BorderLayout.SOUTH);
        add(panelDerecho, BorderLayout.EAST);

        // 3. Registro de Eventos
        configurarEventos();

        // 4. Carga inicial de datos
        cargarDatosTabla();
    }

    private void configurarEventos() {
        // Al hacer clic en un registro de la tabla se cargan los datos al formulario para editar
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaLibros.getSelectedRow();
                if (fila != -1) {
                    txtId.setText(tableModel.getValueAt(fila, 0).toString());
                    txtTitulo.setText(tableModel.getValueAt(fila, 1).toString());
                    txtAutor.setText(tableModel.getValueAt(fila, 2).toString());
                    txtCategoria.setText(tableModel.getValueAt(fila, 3).toString());
                    txtPrecio.setText(tableModel.getValueAt(fila, 4).toString());
                    txtExistencias.setText(tableModel.getValueAt(fila, 5).toString());
                    txtAnio.setText(tableModel.getValueAt(fila, 6).toString());
                    chkDisponible.setSelected("Sí".equals(tableModel.getValueAt(fila, 7).toString()));
                }
            }
        });

        btnGuardar.addActionListener(e -> accionGuardar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnEliminar.addActionListener(e -> accionEliminar());
        

    }

    private void cargarDatosTabla() {
        tableModel.setRowCount(0);
        try {
            List<Libro> lista = libroDAO.listarTodos();
            for (Libro l : lista) {
                tableModel.addRow(new Object[]{
                    l.getId(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getCategoria(),
                    l.getPrecio(),
                    l.getExistencias(),
                    l.getAnioPublicacion(),
                    l.isDisponible() ? "Sí" : "No"
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "No fue posible consultar los libros desde la base de datos.", 
                "Error de Base de Datos", 
                JOptionPane.ERROR_MESSAGE);
        }
    }


    private void accionGuardar() {
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String strPrecio = txtPrecio.getText().trim();
        String strExistencias = txtExistencias.getText().trim();
        String strAnio = txtAnio.getText().trim();

        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título, Autor y Categoría son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(strPrecio);
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a cero.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Formato de precio inválido.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int existencias;
        try {
            existencias = Integer.parseInt(strExistencias);
            if (existencias < 0) {
                JOptionPane.showMessageDialog(this, "Las existencias no pueden ser negativas.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Existencias debe ser un número entero válido.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int anio;
        try {
            anio = Integer.parseInt(strAnio);
            if (anio > java.time.Year.now().getValue()) {
                JOptionPane.showMessageDialog(this, "El año de publicación no puede superar el actual.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número entero válido.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean disponible = chkDisponible.isSelected();

        try {
            if (txtId.getText().trim().isEmpty()) {
                Libro nuevoLibro = new Libro(titulo, autor, categoria, precio, existencias, anio, disponible);
                libroDAO.crear(nuevoLibro);
                JOptionPane.showMessageDialog(this, "Libro registrado exitosamente.");
            } else {
                int id = Integer.parseInt(txtId.getText().trim());
                Libro libroExistente = new Libro(id, titulo, autor, categoria, precio, existencias, anio, disponible);
                libroDAO.actualizar(libroExistente);
                JOptionPane.showMessageDialog(this, "Libro actualizado correctamente.");
            }
            limpiarFormulario();
            cargarDatosTabla();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al persistir cambios en la BD.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void accionEliminar() {
        String strId = txtId.getText().trim();
        if (strId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de que deseas eliminar este libro del catálogo?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(strId);
                libroDAO.eliminar(id);
                JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.");
                limpiarFormulario();
                cargarDatosTabla();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo eliminar el libro seleccionado.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtExistencias.setText("");
        txtAnio.setText("");
        tablaLibros.clearSelection();
    }
}
