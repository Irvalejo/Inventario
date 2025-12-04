package ui;

import model.Usuario;

import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    private MainFrame mainFrame;
    private JLabel lblUsuario;
    private JLabel lblRol;
    private JLabel lblUltimoInicio;

    public HomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navBar.setBackground(UiStyle.AZUL_UNISON);

        JButton btnInicio = new JButton("Inicio");
        JButton btnProductos = new JButton("Productos");
        JButton btnAlmacenes = new JButton("Almacenes");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        estiloBotonNav(btnInicio);
        estiloBotonNav(btnProductos);
        estiloBotonNav(btnAlmacenes);
        estiloBotonNav(btnCerrarSesion);

        btnInicio.addActionListener(e -> mainFrame.mostrarInicio());
        btnProductos.addActionListener(e -> mainFrame.mostrarProductos());
        btnAlmacenes.addActionListener(e -> mainFrame.mostrarAlmacenes());

        btnCerrarSesion.addActionListener(e -> {
            mainFrame.setUsuarioActual(null);
            mainFrame.mostrarLogin();
        });


        navBar.add(btnInicio);
        navBar.add(btnProductos);
        navBar.add(btnAlmacenes);
        navBar.add(Box.createHorizontalStrut(20));
        navBar.add(btnCerrarSesion);

        add(navBar, BorderLayout.NORTH);/*

-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-0-O-
                                                                                                        */

        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        ImageIcon icon = new ImageIcon("unisonLogo.png");
        Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon iconEscalado = new ImageIcon(img);
        JLabel lblLogo = new JLabel(iconEscalado);

        content.add(lblLogo);
        content.add(Box.createVerticalStrut(20));

        JLabel lblTitulo = new JLabel("Sistema Básico de Inventario");
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 24f));

        JLabel lblSubtitulo = new JLabel("Universidad de Sonora");
        lblSubtitulo.setFont(lblSubtitulo.getFont().deriveFont(18f));
        lblSubtitulo.setForeground(UiStyle.AZUL_OSCURO_UNISON);

        JLabel lblAlumno = new JLabel("Desarrollado por: Irving Duarte");
        lblAlumno.setForeground(Color.DARK_GRAY);

        lblUsuario = new JLabel("Usuario: -");
        lblUsuario.setForeground(UiStyle.DORADO_OSCURO_UNISON);

        lblRol = new JLabel("Rol: -");
        lblRol.setForeground(UiStyle.DORADO_OSCURO_UNISON);

        lblUltimoInicio = new JLabel("Último inicio de sesión: -");
        lblUltimoInicio.setForeground(UiStyle.DORADO_OSCURO_UNISON);

        content.add(lblTitulo);
        content.add(Box.createVerticalStrut(10));
        content.add(lblSubtitulo);
        content.add(Box.createVerticalStrut(20));
        content.add(lblAlumno);
        content.add(Box.createVerticalStrut(30));
        content.add(lblUsuario);
        content.add(lblRol);
        content.add(lblUltimoInicio);

        add(content, BorderLayout.CENTER);
    }

    private void estiloBotonNav(JButton btn) {
        btn.setBackground(UiStyle.AZUL_OSCURO_UNISON);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
    }

    public void actualizarUsuario(Usuario usuario) {
        if (usuario == null) {
            lblUsuario.setText("Usuario: -");
            lblRol.setText("Rol: -");
            lblUltimoInicio.setText("Último inicio de sesión: -");
        } else {
            lblUsuario.setText("Usuario: " + usuario.getNombre());
            lblRol.setText("Rol: " + usuario.getRol());
            lblUltimoInicio.setText("Último inicio de sesión: " +
                    (usuario.getFechaHoraUltimoInicio() != null ? usuario.getFechaHoraUltimoInicio() : "-"));
        }
    }
}
