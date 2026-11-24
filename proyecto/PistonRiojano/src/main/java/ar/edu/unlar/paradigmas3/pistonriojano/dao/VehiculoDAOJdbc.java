/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.dao;

import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Auto;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Cliente;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Moto;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.TipoCombustible;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Vehiculo;
import dominio.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gustavo
 */
public class VehiculoDAOJdbc implements VehiculoDAO {

    @Override
    public void insertar(Vehiculo vehiculo) throws SQLException {
        String sql = "INSERT INTO vehiculos (patente, marca, modelo, anio, id_cliente, tipo_vehiculo, cantidad_puertas, combustible, cilindrada) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehiculo.getPatente());
            ps.setString(2, vehiculo.getMarca());
            ps.setString(3, vehiculo.getModelo());
            ps.setInt(4, vehiculo.getAnio());
            if (vehiculo.getPropietario() != null && vehiculo.getPropietario().getId() != null) {
                ps.setInt(5, vehiculo.getPropietario().getId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            if (vehiculo instanceof Auto) {
                Auto auto = (Auto) vehiculo;
                ps.setString(6, "AUTO");
                ps.setInt(7, auto.getCantidadPuertas());
                ps.setString(8, auto.getTipoCombustible() != null ? auto.getTipoCombustible().name() : null);
                ps.setNull(9, Types.INTEGER);
            } else if (vehiculo instanceof Moto) {
                Moto moto = (Moto) vehiculo;
                ps.setString(6, "MOTO");
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.VARCHAR);
                ps.setInt(9, moto.getCilindrada());
            } else {
                throw new SQLException("Tipo de vehículo no soportado.");
            }

            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Vehiculo vehiculo) throws SQLException {
        String sql = "UPDATE vehiculos SET marca = ?, modelo = ?, anio = ?, id_cliente = ?, "
                + "tipo_vehiculo = ?, cantidad_puertas = ?, combustible = ?, cilindrada = ? "
                + "WHERE patente = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehiculo.getMarca());
            ps.setString(2, vehiculo.getModelo());
            ps.setInt(3, vehiculo.getAnio());
            if (vehiculo.getPropietario() != null && vehiculo.getPropietario().getId() != null) {
                ps.setInt(4, vehiculo.getPropietario().getId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            if (vehiculo instanceof Auto) {
                Auto auto = (Auto) vehiculo;
                ps.setString(5, "AUTO");
                ps.setInt(6, auto.getCantidadPuertas());
                ps.setString(7, auto.getTipoCombustible() != null ? auto.getTipoCombustible().name() : null);
                ps.setNull(8, Types.INTEGER);
            } else if (vehiculo instanceof Moto) {
                Moto moto = (Moto) vehiculo;
                ps.setString(5, "MOTO");
                ps.setNull(6, Types.INTEGER);
                ps.setNull(7, Types.VARCHAR);
                ps.setInt(8, moto.getCilindrada());
            } else {
                throw new SQLException("Tipo de vehículo no soportado.");
            }

            ps.setString(9, vehiculo.getPatente());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(String patente) throws SQLException {
        String sql = "DELETE FROM vehiculos WHERE patente = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patente);
            ps.executeUpdate();
        }
    }

    @Override
    public Vehiculo buscarPorPatente(String patente) throws SQLException {
        String sql = "SELECT v.id, v.patente, v.marca, v.modelo, v.anio, v.tipo_vehiculo, "
                + "v.cantidad_puertas, v.combustible, v.cilindrada, "
                + "c.id AS c_id, c.dni, c.nombre, c.apellido, c.telefono, c.email "
                + "FROM vehiculos v "
                + "LEFT JOIN clientes c ON v.id_cliente = c.id "
                + "WHERE v.patente = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearVehiculo(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Vehiculo> listarPorCliente(int idCliente) throws SQLException {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT v.id, v.patente, v.marca, v.modelo, v.anio, v.tipo_vehiculo, "
                + "v.cantidad_puertas, v.combustible, v.cilindrada, "
                + "c.id AS c_id, c.dni, c.nombre, c.apellido, c.telefono, c.email "
                + "FROM vehiculos v "
                + "LEFT JOIN clientes c ON v.id_cliente = c.id "
                + "WHERE v.id_cliente = ? "
                + "ORDER BY v.patente";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearVehiculo(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public List<Vehiculo> listarTodos() throws SQLException {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT v.id, v.patente, v.marca, v.modelo, v.anio, v.tipo_vehiculo, "
                + "v.cantidad_puertas, v.combustible, v.cilindrada, "
                + "c.id AS c_id, c.dni, c.nombre, c.apellido, c.telefono, c.email "
                + "FROM vehiculos v "
                + "LEFT JOIN clientes c ON v.id_cliente = c.id "
                + "ORDER BY v.patente";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearVehiculo(rs));
            }
        }
        return lista;
    }

    private Vehiculo mapearVehiculo(ResultSet rs) throws SQLException {
        Cliente propietario = null;
        int idCliente = rs.getInt("c_id");
        if (!rs.wasNull()) {
            propietario = new Cliente();
            propietario.setId(idCliente);
            propietario.setDni(rs.getString("dni"));
            propietario.setNombre(rs.getString("nombre"));
            propietario.setApellido(rs.getString("apellido"));
            propietario.setTelefono(rs.getString("telefono"));
            propietario.setEmail(rs.getString("email"));
        }

        int id = rs.getInt("id");
        String patente = rs.getString("patente");
        String marca = rs.getString("marca");
        String modelo = rs.getString("modelo");
        int anio = rs.getInt("anio");
        String tipo = rs.getString("tipo_vehiculo");

        if ("AUTO".equalsIgnoreCase(tipo)) {
            String combStr = rs.getString("combustible");
            TipoCombustible combustible = combStr != null ? TipoCombustible.valueOf(combStr.toUpperCase()) : null;
            return new Auto(id, patente, marca, modelo, anio, propietario, rs.getInt("cantidad_puertas"), combustible);
        } else if ("MOTO".equalsIgnoreCase(tipo)) {
            return new Moto(id, patente, marca, modelo, anio, propietario, rs.getInt("cilindrada"));
        }
        throw new SQLException("Tipo de vehículo desconocido en base de datos: " + tipo);
    }
}
