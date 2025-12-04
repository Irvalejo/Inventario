package ui;

import model.Almacen;
import model.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductoFormDialog extends JDialog {

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JTextField txtDepto;
    private JComboBox<Almacen> cbAlmacen;

    private Producto producto;

    public ProductoFormDialog(Frame owner, String titulo,
                              Producto existente,
                              List<Almacen> almacenes) {
        super(owner, titulo, true);
        initComponents(almacenes);

        if (existente != null) {
            cargarProducto(existente);
        }

        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents(List<Almacen> almacenes) {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre = new JTextField(20);
        txtPrecio = new JTextField(10);
        txtCantidad = new JTextField(10);
        txtDepto = new JTextField(20);
        cbAlmacen = new JComboBox<>();

        for (Almacen a : almacenes) {
            cbAlmacen.addItem(a);
        }

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        form.add(txtNombre, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1;
        form.add(txtPrecio, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        form.add(txtCantidad, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Departamento:"), gbc);
        gbc.gridx = 1;
        form.add(txtDepto, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Almacén:"), gbc);
        gbc.gridx = 1;
        form.add(cbAlmacen, gbc);

        add(form, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(e -> onAceptar());
        btnCancelar.addActionListener(e -> {
            producto = null;
            dispose();
        });

        botones.add(btnAceptar);
        botones.add(btnCancelar);

        add(botones, BorderLayout.SOUTH);
    }

    private void cargarProducto(Producto p) {
        txtNombre.setText(p.getNombre());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        txtCantidad.setText(String.valueOf(p.getCantidad()));
        txtDepto.setText(p.getDepartamento());

        for (int i = 0; i < cbAlmacen.getItemCount(); i++) {
            Almacen a = cbAlmacen.getItemAt(i);
            if (a.getId() == p.getAlmacenId()) {
                cbAlmacen.setSelectedIndex(i);
                break;
            }
        }
    }

    private void onAceptar() {
        String nombre = txtNombre.getText().trim();
        String precioTxt = txtPrecio.getText().trim();
        String cantTxt = txtCantidad.getText().trim();
        String depto = txtDepto.getText().trim();
        Almacen almacen = (Almacen) cbAlmacen.getSelectedItem();

        if (nombre.isEmpty() || precioTxt.isEmpty() ||
                cantTxt.isEmpty() || depto.isEmpty() || almacen == null) {
            JOptionPane.showMessageDialog(this,
                    "Completa todos los campos.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precio;
        int cantidad;

        try {
            precio = Double.parseDouble(precioTxt);
            cantidad = Integer.parseInt(cantTxt);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Precio y cantidad deben ser numéricos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        producto = new Producto(
                0,
                nombre,
                precio,
                cantidad,
                depto,
                almacen.getId(),
                almacen.getNombre(),
                null, null, null
        );

        dispose();
    }

    public Producto getProducto() {
        return producto;
    }
}
