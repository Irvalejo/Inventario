package ui;

import dao.AlmacenDAO;
import dao.ProductoDAO;
import model.Almacen;
import model.Producto;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductosPanel extends JPanel {

    private MainFrame mainFrame;
    private ProductoDAO productoDAO;
    private AlmacenDAO almacenDAO;

    // filtros
    private JTextField txtFiltroNombre;
    private JTextField txtFiltroDepto;
    private JTextField txtPrecioMin;
    private JTextField txtPrecioMax;
    private JTextField txtCantMin;
    private JTextField txtCantMax;
    private JComboBox<Almacen> cbAlmacen;

    // tabla
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // botones CRUD
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;

    public ProductosPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.productoDAO = new ProductoDAO();
        this.almacenDAO = new AlmacenDAO();
        initComponents();
        cargarAlmacenesEnCombo();
        cargarProductos();
    }

    public void actualizarUsuario(Usuario usuario) {
        // controlar permisos según rol
        if (usuario == null) return;
        String rol = usuario.getRol();
        boolean puedeEditar = rol.equalsIgnoreCase("ADMIN") ||
                rol.equalsIgnoreCase("PRODUCTOS");

        btnAgregar.setVisible(puedeEditar);
        btnEditar.setVisible(puedeEditar);
        btnEliminar.setVisible(puedeEditar);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // NavBar simplificada arriba
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
        btnProductos.setEnabled(false); // ya estamos aquí
        btnAlmacenes.setEnabled(false); // por ahora
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

        // Panel filtros
        JPanel filtros = new JPanel(new GridBagLayout());
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros de búsqueda"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtFiltroNombre = new JTextField(12);
        txtFiltroDepto = new JTextField(12);
        txtPrecioMin = new JTextField(6);
        txtPrecioMax = new JTextField(6);
        txtCantMin = new JTextField(6);
        txtCantMax = new JTextField(6);
        cbAlmacen = new JComboBox<>();

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        filtros.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        filtros.add(txtFiltroNombre, gbc);

        gbc.gridx = 2;
        filtros.add(new JLabel("Departamento:"), gbc);
        gbc.gridx = 3;
        filtros.add(txtFiltroDepto, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        filtros.add(new JLabel("Precio mín:"), gbc);
        gbc.gridx = 1;
        filtros.add(txtPrecioMin, gbc);

        gbc.gridx = 2;
        filtros.add(new JLabel("Precio máx:"), gbc);
        gbc.gridx = 3;
        filtros.add(txtPrecioMax, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        filtros.add(new JLabel("Cant. mín:"), gbc);
        gbc.gridx = 1;
        filtros.add(txtCantMin, gbc);

        gbc.gridx = 2;
        filtros.add(new JLabel("Cant. máx:"), gbc);
        gbc.gridx = 3;
        filtros.add(txtCantMax, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        filtros.add(new JLabel("Almacén:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        filtros.add(cbAlmacen, gbc);
        gbc.gridwidth = 1;

        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnBuscar.addActionListener(e -> cargarProductos());
        btnLimpiar.addActionListener(e -> limpiarFiltros());

        gbc.gridx = 3;
        filtros.add(btnBuscar, gbc);
        gbc.gridx = 4;
        filtros.add(btnLimpiar, gbc);

        // Tabla
        String[] columnas = {
                "ID", "Nombre", "Precio", "Cantidad",
                "Departamento", "Almacén",
                "Creación", "Última mod.", "Último usuario"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollTabla = new JScrollPane(tabla);

        // Botones CRUD
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        btnAgregar.addActionListener(e -> agregarProducto());
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());

        acciones.add(btnAgregar);
        acciones.add(btnEditar);
        acciones.add(btnEliminar);

        // Centro
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(filtros, BorderLayout.NORTH);
        centro.add(scrollTabla, BorderLayout.CENTER);
        centro.add(acciones, BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
    }

    private void estiloNav(JButton btn) {
        btn.setBackground(UiStyle.AZUL_OSCURO_UNISON);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
    }

    private void cargarAlmacenesEnCombo() {
        cbAlmacen.removeAllItems();
        cbAlmacen.addItem(null); // "todos"
        List<Almacen> almacenes = almacenDAO.listarTodos();
        for (Almacen a : almacenes) {
            cbAlmacen.addItem(a);
        }
    }

    private void limpiarFiltros() {
        txtFiltroNombre.setText("");
        txtFiltroDepto.setText("");
        txtPrecioMin.setText("");
        txtPrecioMax.setText("");
        txtCantMin.setText("");
        txtCantMax.setText("");
        cbAlmacen.setSelectedIndex(0);
        cargarProductos();
    }

    private void cargarProductos() {
        String nombre = txtFiltroNombre.getText().trim();
        String depto = txtFiltroDepto.getText().trim();

        Double precioMin = parseDouble(txtPrecioMin.getText());
        Double precioMax = parseDouble(txtPrecioMax.getText());
        Integer cantMin = parseInt(txtCantMin.getText());
        Integer cantMax = parseInt(txtCantMax.getText());

        Almacen aSel = (Almacen) cbAlmacen.getSelectedItem();
        Integer almacenId = (aSel != null ? aSel.getId() : null);

        List<Producto> productos = productoDAO.buscar(
                nombre, depto,
                precioMin, precioMax,
                cantMin, cantMax,
                almacenId
        );

        modeloTabla.setRowCount(0);
        for (Producto p : productos) {
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getCantidad(),
                    p.getDepartamento(),
                    p.getAlmacenNombre(),
                    p.getFechaHoraCreacion(),
                    p.getFechaHoraUltimaMod(),
                    p.getUltimoUsuario()
            });
        }
    }

    private Double parseDouble(String txt) {
        try {
            if (txt == null || txt.isBlank()) return null;
            return Double.parseDouble(txt.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInt(String txt) {
        try {
            if (txt == null || txt.isBlank()) return null;
            return Integer.parseInt(txt.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // ================== CRUD ==================

    private void agregarProducto() {
        Usuario u = mainFrame.getUsuarioActual();
        if (u == null) return;

        ui.ProductoFormDialog dialog = new ui.ProductoFormDialog(
                mainFrame,
                "Agregar producto",
                null,
                almacenDAO.listarTodos()
        );
        dialog.setVisible(true);

        Producto nuevo = dialog.getProducto();
        if (nuevo != null) {
            productoDAO.insertar(nuevo, u.getNombre());
            cargarProductos();
        }
    }

    private void editarProducto() {
        Usuario u = mainFrame.getUsuarioActual();
        if (u == null) return;

        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);
        double precio = ((Number) modeloTabla.getValueAt(fila, 2)).doubleValue();
        int cantidad = ((Number) modeloTabla.getValueAt(fila, 3)).intValue();
        String depto = (String) modeloTabla.getValueAt(fila, 4);
        String almacenNombre = (String) modeloTabla.getValueAt(fila, 5);

        // buscar id de almacén según nombre
        int almacenId = -1;
        for (Almacen a : almacenDAO.listarTodos()) {
            if (a.getNombre().equals(almacenNombre)) {
                almacenId = a.getId();
                break;
            }
        }

        Producto existente = new Producto(
                id, nombre, precio, cantidad, depto,
                almacenId, almacenNombre,
                null, null, u.getNombre()
        );

        ui.ProductoFormDialog dialog = new ui.ProductoFormDialog(
                mainFrame,
                "Modificar producto",
                existente,
                almacenDAO.listarTodos()
        );
        dialog.setVisible(true);

        Producto modificado = dialog.getProducto();
        if (modificado != null) {
            // conservar id
            modificado = new Producto(
                    id,
                    modificado.getNombre(),
                    modificado.getPrecio(),
                    modificado.getCantidad(),
                    modificado.getDepartamento(),
                    modificado.getAlmacenId(),
                    modificado.getAlmacenNombre(),
                    null, null, u.getNombre()
            );
            productoDAO.actualizar(modificado, u.getNombre());
            cargarProductos();
        }
    }

    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        int resp = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar el producto ID " + id + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (resp == JOptionPane.YES_OPTION) {
            productoDAO.eliminar(id);
            cargarProductos();
        }
    }
}
