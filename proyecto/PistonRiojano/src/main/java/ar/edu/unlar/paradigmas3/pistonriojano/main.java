/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano;

import ar.edu.unlar.paradigmas3.pistonriojano.modelo.*;
import dominio.ConexionBD;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Leandro
 */
public class main {

    public static void main(String[] args) {
        probarConexionBD();
        probarModelo();
    }

    private static void probarConexionBD() {
        String sql = "SELECT current_database(), current_user, version()";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            DatabaseMetaData meta = cn.getMetaData();
            System.out.println("=== Conexión JDBC OK ===");
            System.out.println("Motor: " + meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion());
            System.out.println("Driver: " + meta.getDriverName() + " " + meta.getDriverVersion());

            if (rs.next()) {
                System.out.println("Base de datos activa: " + rs.getString(1));
                System.out.println("Usuario conectado: " + rs.getString(2));
                System.out.println("Servidor: " + rs.getString(3));
            }
            System.out.println();
        } catch (SQLException e) {
            System.err.println("=== Error en ConexionBD ===");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("ErrorCode: " + e.getErrorCode());
            System.err.println();
        }
    }

    private static void probarModelo() {
        Cliente cliente = new Cliente("30123456", "3804-123456", "juan@mail.com", new ArrayList<>(), 1, "Juan", "Pérez");
        Mecanico mecanico1 = new Mecanico(101, Especialidad.MOTOR, 5000.0, 2, "Carlos", "Gómez");
        Mecanico mecanico2 = new Mecanico(102, Especialidad.FRENOS, 4000.0, 3, "Ana", "López");

        Auto auto = new Auto("AB123CD", "Toyota", "Corolla", 2018, 4, TipoCombustible.DIESEL);
        Moto moto = new Moto("123ABC", "Honda", "CB125", 2020, 125);
        cliente.getVehiculos().add(auto);
        cliente.getVehiculos().add(moto);

        Repuesto filtro = new Repuesto("R001", "Filtro de aceite", 3500.0, 10);
        Repuesto pastillas = new Repuesto("R002", "Pastillas de freno", 12000.0, 4);
        Repuesto bujia = new Repuesto("R003", "Bujía", 2500.0, 20);

        OrdenTrabajo orden1 = new OrdenTrabajo(1, "Ruido en frenos y cambio de aceite", auto, mecanico1);
        orden1.setHorasManoObra(3);
        orden1.setEstado(EstadoOrden.EN_REPARACION);

        OrdenTrabajo orden2 = new OrdenTrabajo(2, "Service general de moto", moto, mecanico2);
        orden2.setHorasManoObra(2);

        try {
            orden1.agregarRepuesto(filtro, 2);
            orden1.agregarRepuesto(pastillas, 2);
            orden2.agregarRepuesto(bujia, 1);
            System.out.println("Repuestos agregados correctamente.");
            System.out.println("Stock restante de filtro (era 10): " + filtro.getStockDisponible());
            System.out.println("Stock restante de pastillas (era 4): " + pastillas.getStockDisponible() + "\n");

            orden2.agregarRepuesto(pastillas, 10);
        } catch (StockInsuficienteException e) {
            System.out.println("Excepción capturada correctamente -> " + e.getMessage() + "\n");
        }

        filtro.setPrecioUnitarioActual(9999.0);

        if (!orden2.getDetalles().isEmpty()) {
            orden2.quitarRepuesto(orden2.getDetalles().get(0));
        }

        System.out.println("Cliente: " + cliente + " | Vehículos asociados: " + cliente.getVehiculos().size());
        System.out.println();
        System.out.println(orden1);
        System.out.println();
        System.out.println(orden2);
    }
}