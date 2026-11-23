/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano;

import ar.edu.unlar.paradigmas3.pistonriojano.modelo.*;
import java.util.ArrayList;

/**
 *
 * @author Leandro
 */
public class Main {

    public static void main(String[] args) {
        // 1. Instanciación de Cliente, Mecánicos y Vehículos según constructores del grupo
        Cliente cliente = new Cliente("30123456", "3804-123456", "juan@mail.com", new ArrayList<>(), 1, "Juan", "Pérez");
        Mecanico mecanico1 = new Mecanico(101, Especialidad.MOTOR, 5000.0, 2, "Carlos", "Gómez");
        Mecanico mecanico2 = new Mecanico(102, Especialidad.FRENOS, 4000.0, 3, "Ana", "López");

        Auto auto = new Auto("AB123CD", "Toyota", "Corolla", 2018, 4, TipoCombustible.DIESEL); // coef. 1.20
        Moto moto = new Moto("123ABC", "Honda", "CB125", 2020, 125);                           // coef. 0.80
        cliente.getVehiculos().add(auto);
        cliente.getVehiculos().add(moto);

        // 2. Instanciación de Repuestos
        Repuesto filtro = new Repuesto("R001", "Filtro de aceite", 3500.0, 10);
        Repuesto pastillas = new Repuesto("R002", "Pastillas de freno", 12000.0, 4);
        Repuesto bujia = new Repuesto("R003", "Bujía", 2500.0, 20);

        // 3. Creación de Órdenes de Trabajo
        OrdenTrabajo orden1 = new OrdenTrabajo(1, "Ruido en frenos y cambio de aceite", auto, mecanico1);
        orden1.setHorasManoObra(3);
        orden1.setEstado(EstadoOrden.EN_REPARACION);

        OrdenTrabajo orden2 = new OrdenTrabajo(2, "Service general de moto", moto, mecanico2);
        orden2.setHorasManoObra(2);

        // 4. Agregado de repuestos (Patrón Delegate) y control de StockInsuficienteException
        try {
            orden1.agregarRepuesto(filtro, 2);
            orden1.agregarRepuesto(pastillas, 2);
            orden2.agregarRepuesto(bujia, 1);
            System.out.println("Repuestos agregados correctamente.");
            System.out.println("Stock restante de filtro (era 10): " + filtro.getStockDisponible());
            System.out.println("Stock restante de pastillas (era 4): " + pastillas.getStockDisponible() + "\n");

            // Pide 10 pastillas pero quedan 2: debe lanzar StockInsuficienteException
            orden2.agregarRepuesto(pastillas, 10);
        } catch (StockInsuficienteException e) {
            System.out.println("Excepción capturada correctamente -> " + e.getMessage() + "\n");
        }

        // 5. Cambio de precio posterior: el detalle ya cargado mantiene el precio histórico congelado
        filtro.setPrecioUnitarioActual(9999.0);

        // 6. Quitar un repuesto de la orden 2
        if (!orden2.getDetalles().isEmpty()) {
            orden2.quitarRepuesto(orden2.getDetalles().get(0));
        }

        // 7. Verificación de equals (comparación lógica por número de orden)
        OrdenTrabajo copia = new OrdenTrabajo(1, "Otra descripción", moto, mecanico2);
        System.out.println("orden1.equals(orden2): " + orden1.equals(orden2));
        System.out.println("orden1.equals(copia con mismo número): " + orden1.equals(copia));
        System.out.println("orden1.equals(null): " + orden1.equals(null));
        System.out.println();

        // 8. Salida completa por consola
        System.out.println("Cliente: " + cliente + " | Vehículos asociados: " + cliente.getVehiculos().size());
        System.out.println();
        System.out.println(orden1);
        System.out.println();
        System.out.println(orden2);
    }
}