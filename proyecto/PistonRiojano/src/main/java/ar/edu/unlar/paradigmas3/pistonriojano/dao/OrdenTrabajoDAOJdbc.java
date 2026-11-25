/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.dao;

import ar.edu.unlar.paradigmas3.pistonriojano.modelo.*;
import ar.edu.unlar.paradigmas3.pistonriojano.dominio.ConexionBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leandro
 */
public class OrdenTrabajoDAOJdbc implements OrdenTrabajoDAO {

    private static final String SQL_SELECT_ORDEN =
            "SELECT o.id, o.numero, o.fecha_ingreso, o.descripcion_problema, o.estado, o.horas_mano_obra, "
            + "v.id AS v_id, v.patente, v.marca, v.modelo, v.anio, v.tipo_vehiculo, v.cantidad_puertas, v.combustible, v.cilindrada, "
            + "c.id AS c_id, c.dni AS c_dni, c.nombre AS c_nombre, c.apellido AS c_apellido, c.telefono AS c_telefono, c.email AS c_email, "
            + "m.id AS m_id, m.legajo, m.nombre AS m_nombre, m.apellido AS m_apellido, m.especialidad, m.valor_hora "
            + "FROM ordenes_trabajo o "
            + "JOIN vehiculos v ON v.id = o.id_vehiculo "
            + "LEFT JOIN clientes c ON c.id = v.id_cliente "
            + "JOIN mecanicos m ON m.id = o.id_mecanico ";

    private static final String SQL_SELECT_DETALLES =
            "SELECT d.id, d.cantidad, d.precio_aplicado, r.id AS r_id, r.codigo, r.descripcion, r.precio_unitario, r.stock "
            + "FROM detalles_orden d JOIN repuestos r ON r.id = d.id_repuesto "
            + "WHERE d.id_orden_trabajo = ? ORDER BY d.id";

    @Override
    public void insertar(OrdenTrabajo orden) throws SQLException, StockInsuficienteException {
        try (Connection cn = ConexionBD.getConnection()) {
            boolean autoCommitOriginal = cn.getAutoCommit();
            cn.setAutoCommit(false);
            try {
                insertarCabecera(cn, orden);
                for (DetalleOrden d : orden.getDetalles()) {
                    insertarDetalle(cn, orden.getId(), d);
                    descontarStockBD(cn, d);
                }
                cn.commit();
            } catch (SQLException | StockInsuficienteException | RuntimeException e) {
                cn.rollback();
                orden.setId(0);
                for (DetalleOrden d : orden.getDetalles()) {
                    d.setId(0);
                }
                throw e;
            } finally {
                cn.setAutoCommit(autoCommitOriginal);
            }
        }
    }

    @Override
    public void actualizar(OrdenTrabajo orden) throws SQLException {
        if (orden.getEstado() == null) {
            throw new SQLException("La orden no tiene estado asignado.");
        }
        String sql = "UPDATE ordenes_trabajo SET numero = ?, fecha_ingreso = COALESCE(?, fecha_ingreso), "
                + "descripcion_problema = ?, estado = ?, horas_mano_obra = ?, id_vehiculo = ?, id_mecanico = ? "
                + "WHERE id = ?";
        try (Connection cn = ConexionBD.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, orden.getNumero());
            asignarFecha(ps, 2, orden.getFechaIngreso());
            ps.setString(3, orden.getDescripcionProblema());
            ps.setString(4, orden.getEstado().name());
            ps.setDouble(5, orden.getHorasManoObra());
            ps.setInt(6, buscarIdVehiculo(cn, orden.getVehiculo()));
            ps.setInt(7, obtenerIdMecanico(orden.getMecanico()));
            ps.setInt(8, orden.getId());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No existe la orden de trabajo con id " + orden.getId() + ".");
            }
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM ordenes_trabajo WHERE id = ?";
        try (Connection cn = ConexionBD.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public OrdenTrabajo buscarPorId(int id) throws SQLException {
        String sql = SQL_SELECT_ORDEN + "WHERE o.id = ?";
        try (Connection cn = ConexionBD.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearOrden(cn, rs);
                }
                return null;
            }
        }
    }

    @Override
    public OrdenTrabajo buscarPorNumero(int numero) throws SQLException {
        String sql = SQL_SELECT_ORDEN + "WHERE o.numero = ?";
        try (Connection cn = ConexionBD.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearOrden(cn, rs);
                }
                return null;
            }
        }
    }

    @Override
    public List<OrdenTrabajo> listarTodos() throws SQLException {
        String sql = SQL_SELECT_ORDEN + "ORDER BY o.numero";
        List<OrdenTrabajo> ordenes = new ArrayList<>();
        try (Connection cn = ConexionBD.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ordenes.add(mapearOrden(cn, rs));
            }
        }
        return ordenes;
    }

    private void insertarCabecera(Connection cn, OrdenTrabajo orden) throws SQLException {
        String sql = "INSERT INTO ordenes_trabajo (numero, fecha_ingreso, descripcion_problema, estado, "
                + "horas_mano_obra, id_vehiculo, id_mecanico) "
                + "VALUES (?, COALESCE(?, CURRENT_DATE), ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, orden.getNumero());
            asignarFecha(ps, 2, orden.getFechaIngreso());
            ps.setString(3, orden.getDescripcionProblema());
            ps.setString(4, orden.getEstado().name());
            ps.setDouble(5, orden.getHorasManoObra());
            ps.setInt(6, buscarIdVehiculo(cn, orden.getVehiculo()));
            ps.setInt(7, obtenerIdMecanico(orden.getMecanico()));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                orden.setId(rs.getInt(1));
            }
        }
    }

    private void insertarDetalle(Connection cn, int idOrden, DetalleOrden detalle) throws SQLException {
        String sql = "INSERT INTO detalles_orden (cantidad, precio_aplicado, id_orden_trabajo, id_repuesto) "
                + "VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, detalle.getCantidad());
            ps.setDouble(2, detalle.getPrecioAplicado());
            ps.setInt(3, idOrden);
            ps.setInt(4, buscarIdRepuesto(cn, detalle.getRepuesto()));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                detalle.setId(rs.getInt(1));
            }
        }
    }

    private void descontarStockBD(Connection cn, DetalleOrden detalle) throws SQLException, StockInsuficienteException {
        String sql = "UPDATE repuestos SET stock = stock - ? WHERE codigo = ? AND stock >= ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, detalle.getCantidad());
            ps.setString(2, detalle.getRepuesto().getCodigo());
            ps.setInt(3, detalle.getCantidad());
            if (ps.executeUpdate() == 0) {
                throw new StockInsuficienteException("Stock insuficiente en la base de datos para el repuesto "
                        + detalle.getRepuesto().getCodigo() + " (solicitado " + detalle.getCantidad() + ").");
            }
        }
    }

    private void asignarFecha(PreparedStatement ps, int indice, LocalDate fecha) throws SQLException {
        if (fecha == null) {
            ps.setNull(indice, Types.DATE);
        } else {
            ps.setDate(indice, Date.valueOf(fecha));
        }
    }

    private int buscarIdVehiculo(Connection cn, Vehiculo vehiculo) throws SQLException {
        if (vehiculo == null) {
            throw new SQLException("La orden no tiene vehiculo asignado.");
        }
        String sql = "SELECT id FROM vehiculos WHERE patente = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, vehiculo.getPatente());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("El vehiculo con patente " + vehiculo.getPatente() + " no esta registrado.");
            }
        }
    }

    private int buscarIdRepuesto(Connection cn, Repuesto repuesto) throws SQLException {
        String sql = "SELECT id FROM repuestos WHERE codigo = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, repuesto.getCodigo());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("El repuesto con codigo " + repuesto.getCodigo() + " no esta registrado.");
            }
        }
    }

    private int obtenerIdMecanico(Mecanico mecanico) throws SQLException {
        if (mecanico == null || mecanico.getId() == null) {
            throw new SQLException("La orden no tiene un mecanico persistido asignado.");
        }
        return mecanico.getId();
    }

    private OrdenTrabajo mapearOrden(Connection cn, ResultSet rs) throws SQLException {
        Vehiculo vehiculo = mapearVehiculo(rs);
        Mecanico mecanico = new Mecanico(
                rs.getInt("legajo"),
                Especialidad.valueOf(rs.getString("especialidad")),
                rs.getDouble("valor_hora"),
                rs.getInt("m_id"),
                rs.getString("m_nombre"),
                rs.getString("m_apellido"));
        Date fechaIngreso = rs.getDate("fecha_ingreso");
        LocalDate fecha = null;
        if (fechaIngreso != null) {
            fecha = fechaIngreso.toLocalDate();
        }
        OrdenTrabajo orden = new OrdenTrabajo(
                rs.getInt("id"),
                rs.getInt("numero"),
                fecha,
                rs.getString("descripcion_problema"),
                EstadoOrden.valueOf(rs.getString("estado")),
                rs.getDouble("horas_mano_obra"),
                vehiculo,
                mecanico);
        cargarDetalles(cn, orden);
        return orden;
    }

    private Vehiculo mapearVehiculo(ResultSet rs) throws SQLException {
        Cliente propietario = null;
        int idCliente = rs.getInt("c_id");
        if (!rs.wasNull()) {
            propietario = new Cliente();
            propietario.setId(idCliente);
            propietario.setDni(rs.getString("c_dni"));
            propietario.setNombre(rs.getString("c_nombre"));
            propietario.setApellido(rs.getString("c_apellido"));
            propietario.setTelefono(rs.getString("c_telefono"));
            propietario.setEmail(rs.getString("c_email"));
        }
        int idVehiculo = rs.getInt("v_id");
        String patente = rs.getString("patente");
        String marca = rs.getString("marca");
        String modelo = rs.getString("modelo");
        int anio = rs.getInt("anio");
        if ("MOTO".equals(rs.getString("tipo_vehiculo"))) {
            return new Moto(idVehiculo, patente, marca, modelo, anio, propietario, rs.getInt("cilindrada"));
        }
        String combustible = rs.getString("combustible");
        TipoCombustible tipo = combustible == null ? null : TipoCombustible.valueOf(combustible);
        return new Auto(idVehiculo, patente, marca, modelo, anio, propietario, rs.getInt("cantidad_puertas"), tipo);
    }

    private void cargarDetalles(Connection cn, OrdenTrabajo orden) throws SQLException {
        try (PreparedStatement ps = cn.prepareStatement(SQL_SELECT_DETALLES)) {
            ps.setInt(1, orden.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Repuesto repuesto = new Repuesto(
                            rs.getInt("r_id"),
                            rs.getString("codigo"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio_unitario"),
                            rs.getInt("stock"));
                    DetalleOrden detalle = new DetalleOrden(
                            rs.getInt("id"),
                            repuesto,
                            rs.getInt("cantidad"),
                            rs.getDouble("precio_aplicado"));
                    orden.agregarDetalle(detalle);
                }
            }
        }
    }
}
