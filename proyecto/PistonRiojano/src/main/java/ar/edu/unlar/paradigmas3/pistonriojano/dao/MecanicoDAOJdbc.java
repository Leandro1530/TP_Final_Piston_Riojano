/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.dao;

import ar.edu.unlar.paradigmas3.pistonriojano.dominio.ConexionBD;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Especialidad;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Mecanico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author river
 */
public class MecanicoDAOJdbc implements MecanicoDAO {

    private static final String SQL_BASE
            = "SELECT id, legajo, nombre, apellido, especialidad, valor_hora FROM mecanicos";

    @Override
    public void insertar(Mecanico mecanico) throws SQLException {
        String sql = "INSERT INTO mecanicos (legajo, nombre, apellido, especialidad, valor_hora) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mecanico.getLegajo());
            ps.setString(2, mecanico.getNombre());
            ps.setString(3, mecanico.getApellido());
            ps.setString(4, mecanico.getEspecialidad() != null ? mecanico.getEspecialidad().name() : null);
            ps.setDouble(5, mecanico.getValorHora());
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Mecanico mecanico) throws SQLException {
        String sql = "UPDATE mecanicos SET legajo = ?, nombre = ?, apellido = ?, especialidad = ?, valor_hora = ? "
                + "WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mecanico.getLegajo());
            ps.setString(2, mecanico.getNombre());
            ps.setString(3, mecanico.getApellido());
            ps.setString(4, mecanico.getEspecialidad() != null ? mecanico.getEspecialidad().name() : null);
            ps.setDouble(5, mecanico.getValorHora());
            ps.setInt(6, mecanico.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM mecanicos WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Mecanico buscarPorId(int id) throws SQLException {
        String sql = SQL_BASE + " WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearMecanico(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Mecanico buscarPorLegajo(String numeroLegajo) throws SQLException {
        if (numeroLegajo == null || numeroLegajo.trim().isEmpty()) {
            return null;
        }

        final int legajoInt;
        try {
            legajoInt = Integer.parseInt(numeroLegajo.trim());
        } catch (NumberFormatException e) {
            throw new SQLException("El número de legajo no es un entero válido: " + numeroLegajo, e);
        }

        String sql = SQL_BASE + " WHERE legajo = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, legajoInt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearMecanico(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Mecanico> listarTodos() throws SQLException {
        List<Mecanico> lista = new ArrayList<>();
        String sql = SQL_BASE + " ORDER BY apellido, nombre";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearMecanico(rs));
            }
        }
        return lista;
    }

    @Override
    public boolean tieneOrdenesTrabajo(int idMecanico) throws SQLException {
        String sql = "SELECT 1 FROM ordenes_trabajo WHERE id_mecanico = ? LIMIT 1";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMecanico);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Mecanico mapearMecanico(ResultSet rs) throws SQLException {
        Mecanico m = new Mecanico();
        m.setId(rs.getInt("id"));
        m.setLegajo(rs.getInt("legajo"));
        m.setNombre(rs.getString("nombre"));
        m.setApellido(rs.getString("apellido"));

        String espStr = rs.getString("especialidad");
        if (espStr != null && !espStr.trim().isEmpty()) {
            m.setEspecialidad(Especialidad.valueOf(espStr.trim().toUpperCase()));
        }

        m.setValorHora(rs.getDouble("valor_hora"));
        return m;
    }
}
