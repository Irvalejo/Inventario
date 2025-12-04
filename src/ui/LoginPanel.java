package ui;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private MainFrame mainFrame;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JLabel lblMensaje;

    private UsuarioDAO usuarioDAO;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.usuarioDAO = new UsuarioDAO();
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(UiStyle.AZUL_OSCURO_UNISON);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiStyle.AZUL_UNISON, 2),
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblTitulo = new JLabel("Inicio de sesión");
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 20f));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        JLabel lblUsuario = new JLabel("Usuario:");
        gbc.gridy = 1;
        gbc.gridx = 0;
        card.add(lblUsuario, gbc);

        txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        card.add(txtUsuario, gbc);

        JLabel lblPassword = new JLabel("Contraseña:");
        gbc.gridy = 2;
        gbc.gridx = 0;
        card.add(lblPassword, gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        card.add(txtPassword, gbc);

        lblMensaje = new JLabel(" ");
        lblMensaje.setForeground(Color.RED);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        card.add(lblMensaje, gbc);
        gbc.gridwidth = 1;

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBackground(UiStyle.DORADO_UNISON);
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(e -> onLogin());

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        card.add(btnLogin, gbc);

        add(card);
    }

    private void onLogin() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Ingresa usuario y contraseña.");
            return;
        }

        Usuario u = usuarioDAO.login(usuario, password);
        if (u == null) {
            lblMensaje.setText("Usuario o contraseña incorrectos.");
            return;
        }

        mainFrame.setUsuarioActual(u);
        mainFrame.mostrarInicio();
    }
}
