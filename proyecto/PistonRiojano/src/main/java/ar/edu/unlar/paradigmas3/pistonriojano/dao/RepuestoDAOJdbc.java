/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.dao;

import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Repuesto;
import ar.edu.unlar.paradigmas3.pistonriojano.dominio.ConexionBD;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gustavo
 */
public class RepuestoDAOJdbc implements RepuestoDAO {

    @Override
    public void insertar(Repuesto repuesto) throws SQLException {
        String sql = "INSERT INTO repuestos (codigo, descripcion, precio_unitario, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, repuesto.getCodigo());
            ps.setString(2, repuesto.getDescripcion());
            ps.setBigDecimal(3, BigDecimal.valueOf(repuesto.getPrecioUnitarioActual()));
            ps.setInt(4, repuesto.getStockDisponible());
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Repuesto repuesto) throws SQLException {
        String sql = "UPDATE repuestos SET descripcion = ?, precio_unitario = ?, stock = ? WHERE codigo = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, repuesto.getDescripcion());
            ps.setBigDecimal(2, BigDecimal.valueOf(repuesto.getPrecioUnitarioActual()));
            ps.setInt(3, repuesto.getStockDisponible());
            ps.setString(4, repuesto.getCodigo());
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizarPrecioYStock(String codigo, double nuevoPrecio, int nuevoStock) throws SQLException {
        String sql = "UPDATE repuestos SET precio_unitario = ?, stock = ? WHERE codigo = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, BigDecimal.valueOf(nuevoPrecio));
            ps.setInt(2, nuevoStock);
            ps.setString(3, codigo);
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(String codigo) throws SQLException {
        String sql = "DELETE FROM repuestos WHERE codigo = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.executeUpdate();
        }
    }

    @Override
    public Repuesto buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT id, codigo, descripcion, precio_unitario, stock FROM repuestos WHERE codigo = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearRepuesto(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Repuesto> listarTodos() throws SQLException {
        List<Repuesto> lista = new ArrayList<>();
        String sql = "SELECT id, codigo, descripcion, precio_unitario, stock FROM repuestos ORDER BY codigo";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearRepuesto(rs));
            }
        }
        return lista;
    }

    private Repuesto mapearRepuesto(ResultSet rs) throws SQLException {
        Repuesto r = new Repuesto();
        r.setId(rs.getInt("id"));
        r.setCodigo(rs.getString("codigo"));
        r.setDescripcion(rs.getString("descripcion"));
        BigDecimal precio = rs.getBigDecimal("precio_unitario");
        r.setPrecioUnitarioActual(precio != null ? precio.doubleValue() : 0.0);
        r.setStockDisponible(rs.getInt("stock"));
        return r;
    }
}