package dao;

import model.Usuario;
import util.Database;
import util.SecurityUtil;

import java.sql.*;

public class UsuarioDAO {

    public Usuario login(String nombre, String plainPassword) {
        String sql = "SELECT id, nombre, password, rol, fecha_hora_ultimo_inicio " +
                "FROM usuarios WHERE nombre = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null; // usuario no encontrado
                }

                int id = rs.getInt("id");
                String hashBD = rs.getString("password");
                String rol = rs.getString("rol");
                String ultimoInicio = rs.getString("fecha_hora_ultimo_inicio");

                String hashInput = SecurityUtil.sha256(plainPassword);

                //Para saber el hash de la contraseña ingresada y ver que coincida con el hash de la base de datos
                System.out.println("DEBUG usuario = " + nombre);
                System.out.println("DEBUG hashBD   = " + hashBD);
                System.out.println("DEBUG hashIn   = " + hashInput);


                if (!hashBD.equalsIgnoreCase(hashInput)) {
                    return null; // contraseña incorrecta
                }

                actualizarUltimoInicio(conn, id);

                String nuevoUltimoInicio = obtenerUltimoInicio(conn, id);

                return new Usuario(id, nombre, rol, nuevoUltimoInicio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void actualizarUltimoInicio(Connection conn, int idUsuario) throws SQLException {
        String updateSql = "UPDATE usuarios " +
                "SET fecha_hora_ultimo_inicio = datetime('now','localtime') " +
                "WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }

    private String obtenerUltimoInicio(Connection conn, int idUsuario) throws SQLException {
        String sql = "SELECT fecha_hora_ultimo_inicio FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getString(1) : null;
            }
        }
    }
}
