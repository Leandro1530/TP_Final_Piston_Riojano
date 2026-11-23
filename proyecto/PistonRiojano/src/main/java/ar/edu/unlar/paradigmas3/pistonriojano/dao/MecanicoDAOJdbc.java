/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.dao;

import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Especialidad;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Mecanico;
import dominio.ConexionBD;
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
