package ui;

import dao.AlmacenDAO;
import model.Almacen;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AlmacenesPanel extends JPanel {

    private MainFrame mainFrame;
    private AlmacenDAO almacenDAO;

    private JTextField txtFiltroNombre;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;

    public AlmacenesPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.almacenDAO = new AlmacenDAO();
        initComponents();
        cargarAlmacenes();
    }

    public void actualizarUsuario(Usuario usuario) {
        if (usuario == null) return;

        String rol = usuario.getRol();
        boolean puedeEditar = rol.equalsIgnoreCase("ADMIN") ||
                rol.equalsIgnoreCase("ALMACENES");

        btnAgregar.setVisible(puedeEditar);
        btnEditar.setVisible(puedeEditar);
        btnEliminar.setVisible(puedeEditar);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // NavBar
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navBar.setBackground(UiStyle.AZUL_UNISON);

        JButton btnInicio = new JButton("Inicio");
        JButton btnProductos = new JButton("Productos");
        JButton btnAlmacenes = new JButton("Almacenes");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        estiloNav(btnInicio);
        estiloNav(btnProductos);
        estiloNav(btnAlmacenes);
        estiloNav(btnCerrarSesion);

        btnInicio.addActionListener(e -> mainFrame.mostrarInicio());
        btnProductos.addActionListener(e -> mainFrame.mostrarProductos());
        btnAlmacenes.setEnabled(false);
        btnCerrarSesion.addActionListener(e -> {
            mainFrame.setUsuarioActual(null);
            mainFrame.mostrarLogin();
        });

        navBar.add(btnInicio);
        navBar.add(btnProductos);
        navBar.add(btnAlmacenes);
        navBar.add(Box.createHorizontalStrut(20));
        navBar.add(btnCerrarSesion);

        add(navBar, BorderLayout.NORTH);

        // Filtros
        JPanel filtros = new JPanel(new GridBagLayout());
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros de búsqueda"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtFiltroNombre = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        filtros.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        filtros.add(txtFiltroNombre, gbc);

        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnBuscar.addActionListener(e -> cargarAlmacenes());
        btnLimpiar.addActionListener(e -> {
            txtFiltroNombre.setText("");
            cargarAlmacenes();
        });

        gbc.gridx = 2;
        filtros.add(btnBuscar, gbc);
        gbc.gridx = 3;
        filtros.add(btnLimpiar, gbc);

        // Tabla
        String[] columnas = {
                "ID", "Nombre", "Creación", "Última mod.", "Último usuario"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tabla);

        // Acciones
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        btnAgregar.addActionListener(e -> agregarAlmacen());
        btnEditar.addActionListener(e -> editarAlmacen());
        btnEliminar.addActionListener(e -> eliminarAlmacen());

        acciones.add(btnAgregar);
        acciones.add(btnEditar);
        acciones.add(btnEliminar);

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(filtros, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);
        centro.add(acciones, BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
    }

    private void estiloNav(JButton btn) {
        btn.setBackground(UiStyle.AZUL_OSCURO_UNISON);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
    }

    private void cargarAlmacenes() {
        String nombre = txtFiltroNombre.getText().trim();

        List<Almacen> lista = almacenDAO.buscar(nombre);

        modeloTabla.setRowCount(0);
        for (Almacen a : lista) {
            modeloTabla.addRow(new Object[]{
                    a.getId(),
                    a.getNombre(),
                    a.getFechaHoraCreacion(),
                    a.getFechaHoraUltimaMod(),
                    a.getUltimoUsuario()
            });
        }
    }

    // CRUD

    private void agregarAlmacen() {
        Usuario u = mainFrame.getUsuarioActual();
        if (u == null) return;

        String nombre = JOptionPane.showInputDialog(
                this,
                "Nombre del almacén:",
                "Agregar almacén",
                JOptionPane.PLAIN_MESSAGE
        );

        if (nombre == null) return;
        nombre = nombre.trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre no puede estar vacío.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        almacenDAO.insertar(nombre, u.getNombre());
        cargarAlmacenes();
    }

    private void editarAlmacen() {
        Usuario u = mainFrame.getUsuarioActual();
        if (u == null) return;

        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un almacén.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombreActual = (String) modeloTabla.getValueAt(fila, 1);

        String nuevoNombre = JOptionPane.showInputDialog(
                this,
                "Nuevo nombre del almacén:",
                nombreActual
        );

        if (nuevoNombre == null) return;
        nuevoNombre = nuevoNombre.trim();
        if (nuevoNombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre no puede estar vacío.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        almacenDAO.actualizar(id, nuevoNombre, u.getNombre());
        cargarAlmacenes();
    }

    private void eliminarAlmacen() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un almacén.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);

        int resp = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar el almacén \"" + nombre + "\" (ID " + id + ")?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (resp == JOptionPane.YES_OPTION) {
            almacenDAO.eliminar(id);
            cargarAlmacenes();
        }
    }
}
