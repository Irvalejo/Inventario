package dao;

import model.Producto;
import util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> buscar(String nombre, String departamento,
                                 Double precioMin, Double precioMax,
                                 Integer cantMin, Integer cantMax,
                                 Integer almacenId) {

        List<Producto> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT p.id, p.nombre, p.precio, p.cantidad, p.departamento, " +
                        "p.almacen_id, a.nombre AS almacen_nombre, " +
                        "p.fecha_hora_creacion, p.fecha_hora_ultima_modificacion, " +
                        "p.ultimo_usuario_en_modificar " +
                        "FROM productos p " +
                        "JOIN almacenes a ON p.almacen_id = a.id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (nombre != null && !nombre.isBlank()) {
            sql.append("AND p.nombre LIKE ? ");
            params.add("%" + nombre + "%");
        }
        if (departamento != null && !departamento.isBlank()) {
            sql.append("AND p.departamento LIKE ? ");
            params.add("%" + departamento + "%");
        }
        if (precioMin != null) {
            sql.append("AND p.precio >= ? ");
            params.add(precioMin);
        }
        if (precioMax != null) {
            sql.append("AND p.precio <= ? ");
            params.add(precioMax);
        }
        if (cantMin != null) {
            sql.append("AND p.cantidad >= ? ");
            params.add(cantMin);
        }
        if (cantMax != null) {
            sql.append("AND p.cantidad <= ? ");
            params.add(cantMax);
        }
        if (almacenId != null && almacenId > 0) {
            sql.append("AND p.almacen_id = ? ");
            params.add(almacenId);
        }

        sql.append("ORDER BY p.nombre");

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Producto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getInt("cantidad"),
                            rs.getString("departamento"),
                            rs.getInt("almacen_id"),
                            rs.getString("almacen_nombre"),
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

    public void insertar(Producto p, String usuario) {
        String sql = "INSERT INTO productos " +
                "(nombre, precio, cantidad, departamento, almacen_id, " +
                " fecha_hora_creacion, fecha_hora_ultima_modificacion, ultimo_usuario_en_modificar) " +
                "VALUES (?, ?, ?, ?, ?, datetime('now','localtime'), datetime('now','localtime'), ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setDouble(2, p.getPrecio());
            stmt.setInt(3, p.getCantidad());
            stmt.setString(4, p.getDepartamento());
            stmt.setInt(5, p.getAlmacenId());
            stmt.setString(6, usuario);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(Producto p, String usuario) {
        String sql = "UPDATE productos SET " +
                "nombre = ?, precio = ?, cantidad = ?, departamento = ?, almacen_id = ?, " +
                "fecha_hora_ultima_modificacion = datetime('now','localtime'), " +
                "ultimo_usuario_en_modificar = ? " +
                "WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setDouble(2, p.getPrecio());
            stmt.setInt(3, p.getCantidad());
            stmt.setString(4, p.getDepartamento());
            stmt.setInt(5, p.getAlmacenId());
            stmt.setString(6, usuario);
            stmt.setInt(7, p.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
