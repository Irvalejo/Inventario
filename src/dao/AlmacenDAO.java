package dao;

import model.Almacen;
import util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlmacenDAO {


    public List<Almacen> listarTodos() {
        List<Almacen> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM almacenes ORDER BY nombre";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Almacen(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    public List<Almacen> buscar(String nombre) {
        List<Almacen> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT id, nombre, " +
                        "fecha_hora_creacion, fecha_hora_ultima_modificacion, " +
                        "ultimo_usuario_en_modificar " +
                        "FROM almacenes WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (nombre != null && !nombre.isBlank()) {
            sql.append("AND nombre LIKE ? ");
            params.add("%" + nombre + "%");
        }

        sql.append("ORDER BY nombre");

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Almacen(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("fecha_hora_creacion"),
                            rs.getString("fecha_hora_ultima_modificacion"),
                            rs.getString("ultimo_usuario_en_modificar")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertar(String nombre, String usuario) {
        String sql = "INSERT INTO almacenes " +
                "(nombre, fecha_hora_creacion, fecha_hora_ultima_modificacion, ultimo_usuario_en_modificar) " +
                "VALUES (?, datetime('now','localtime'), datetime('now','localtime'), ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, usuario);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(int id, String nombre, String usuario) {
        String sql = "UPDATE almacenes SET " +
                "nombre = ?, " +
                "fecha_hora_ultima_modificacion = datetime('now','localtime'), " +
                "ultimo_usuario_en_modificar = ? " +
                "WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, usuario);
            stmt.setInt(3, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM almacenes WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
