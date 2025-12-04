package ui;

import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private LoginPanel loginPanel;
    private HomePanel homePanel;
    private ProductosPanel productosPanel;
    private AlmacenesPanel almacenesPanel;

    private Usuario usuarioActual;

    public MainFrame() {
        super("Sistema de Inventario - UNISON");

        UiStyle.applyGlobalStyle();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 650);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this);
        homePanel = new HomePanel(this);
        productosPanel = new ProductosPanel(this);
        almacenesPanel = new AlmacenesPanel(this);

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(productosPanel, "PRODUCTOS");
        mainPanel.add(almacenesPanel, "ALMACENES");

        setContentPane(mainPanel);
    }

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void mostrarLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void mostrarInicio() {
        homePanel.actualizarUsuario(usuarioActual);
        cardLayout.show(mainPanel, "HOME");
    }

    public void mostrarProductos() {
        productosPanel.actualizarUsuario(usuarioActual);
        cardLayout.show(mainPanel, "PRODUCTOS");
    }

    public void mostrarAlmacenes() {
        almacenesPanel.actualizarUsuario(usuarioActual);
        cardLayout.show(mainPanel, "ALMACENES");
    }
}
