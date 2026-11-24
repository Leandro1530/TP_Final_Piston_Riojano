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
import ar.edu.unlar.paradigmas3.pistonriojano.dominio.ConexionBD;
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
public class ClienteDAOJdbc implements ClienteDAO {

    @Override
    public void insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (dni, nombre, apellido, telefono, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getEmail());
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nombre = ?, apellido = ?, telefono = ?, email = ? WHERE dni = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getDni());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(String dni) throws SQLException {
        String sql = "DELETE FROM clientes WHERE dni = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.executeUpdate();
        }
    }

    @Override
    public Cliente buscarPorDni(String dni) throws SQLException {
        String sql = "SELECT id, dni, nombre, apellido, telefono, email FROM clientes WHERE dni = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = mapearCliente(rs);
                    if (cliente.getId() != null) {
                        cliente.setVehiculos(buscarVehiculosPorIdCliente(conn, cliente.getId()));
                    }
                    return cliente;
                }
            }
        }
        return null;
    }

    @Override
    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, dni, nombre, apellido, telefono, email FROM clientes ORDER BY apellido, nombre";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = mapearCliente(rs);
                if (cliente.getId() != null) {
                    cliente.setVehiculos(buscarVehiculosPorIdCliente(conn, cliente.getId()));
                }
                lista.add(cliente);
            }
        }
        return lista;
    }

    @Override
    public boolean tieneVehiculos(String dni) throws SQLException {
        String sql = "SELECT 1 FROM vehiculos v "
                + "INNER JOIN clientes c ON v.id_cliente = c.id "
                + "WHERE c.dni = ? LIMIT 1";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private List<Vehiculo> buscarVehiculosPorIdCliente(Connection conn, int idCliente) throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT patente, marca, modelo, anio, tipo_vehiculo, cantidad_puertas, combustible, cilindrada "
                + "FROM vehiculos WHERE id_cliente = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("tipo_vehiculo");
                    if ("AUTO".equalsIgnoreCase(tipo)) {
                        String combStr = rs.getString("combustible");
                        TipoCombustible combustible = combStr != null ? TipoCombustible.valueOf(combStr.toUpperCase()) : null;
                        vehiculos.add(new Auto(
                                rs.getString("patente"),
                                rs.getString("marca"),
                                rs.getString("modelo"),
                                rs.getInt("anio"),
                                rs.getInt("cantidad_puertas"),
                                combustible
                        ));
                    } else if ("MOTO".equalsIgnoreCase(tipo)) {
                        vehiculos.add(new Moto(
                                rs.getString("patente"),
                                rs.getString("marca"),
                                rs.getString("modelo"),
                                rs.getInt("anio"),
                                rs.getInt("cilindrada")
                        ));
                    }
                }
            }
        }
        return vehiculos;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setDni(rs.getString("dni"));
        c.setNombre(rs.getString("nombre"));
        c.setApellido(rs.getString("apellido"));
        c.setTelefono(rs.getString("telefono"));
        c.setEmail(rs.getString("email"));
        return c;
    }
}
