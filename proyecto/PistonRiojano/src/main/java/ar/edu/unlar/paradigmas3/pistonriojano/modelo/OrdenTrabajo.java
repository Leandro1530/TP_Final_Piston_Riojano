/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Leandro
 */
public class OrdenTrabajo {

    private int id;
    private int numero;
    private LocalDate fechaIngreso;
    private String descripcionProblema;
    private EstadoOrden estado;
    private double horasManoObra;
    private Vehiculo vehiculo;
    private Mecanico mecanico;
    // Composición 1 a 0..*: la orden crea y administra sus detalles (Patrón Delegate)
    private List<DetalleOrden> detalles = new ArrayList<>();

    public OrdenTrabajo() {
        this.detalles = new ArrayList<>();
        this.fechaIngreso = LocalDate.now();
        this.estado = EstadoOrden.PENDIENTE;
    }

    public OrdenTrabajo(int numero, String descripcionProblema, Vehiculo vehiculo, Mecanico mecanico) {
        this();
        this.numero = numero;
        this.descripcionProblema = descripcionProblema;
        this.vehiculo = vehiculo;
        this.mecanico = mecanico;
    }

    public OrdenTrabajo(int id, int numero, LocalDate fechaIngreso, String descripcionProblema,
            EstadoOrden estado, double horasManoObra, Vehiculo vehiculo, Mecanico mecanico) {
        this.id = id;
        this.numero = numero;
        this.fechaIngreso = fechaIngreso;
        this.descripcionProblema = descripcionProblema;
        this.estado = estado;
        this.horasManoObra = horasManoObra;
        this.vehiculo = vehiculo;
        this.mecanico = mecanico;
        this.detalles = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public void setDescripcionProblema(String descripcionProblema) {
        this.descripcionProblema = descripcionProblema;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public double getHorasManoObra() {
        return horasManoObra;
    }

    public void setHorasManoObra(double horasManoObra) {
        this.horasManoObra = horasManoObra;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    // Sin setter de la lista: vista de solo lectura para no romper la composición
    public List<DetalleOrden> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }

    /**
     * Delegate: valida y descuenta stock, crea el DetalleOrden internamente
     * y congela el precio vigente del repuesto.
     */
    public void agregarRepuesto(Repuesto r, int cantidad) throws StockInsuficienteException {
        if (!r.hayStockSuficiente(cantidad)) {
            throw new StockInsuficienteException("Stock insuficiente para " + r.getDescripcion()
                    + ": solicitado " + cantidad + ", disponible " + r.getStockDisponible());
        }
        r.descontarStock(cantidad);
        DetalleOrden detalle = new DetalleOrden(r, cantidad, r.getPrecioUnitarioActual());
        this.detalles.add(detalle);
    }

    /**
     * Persistencia (DAO/JDBC): permite reconstruir los detalles leídos
     * desde PostgreSQL (SELECT) sin volver a descontar stock.
     */
    public void agregarDetalle(DetalleOrden detalle) {
        if (detalle != null) {
            this.detalles.add(detalle);
        }
    }

    public void quitarRepuesto(DetalleOrden d) {
        this.detalles.remove(d);
    }

    // Polimorfismo: el coeficiente depende del tipo concreto de Vehiculo (Auto o Moto)
    public double calcularManoObra() {
        return this.horasManoObra * this.mecanico.getValorHora() * this.vehiculo.calcularCoeficiente();
    }

    public double calcularTotalRepuestos() {
        double total = 0;
        for (DetalleOrden d : this.detalles) {
            total += d.calcularSubtotal();
        }
        return total;
    }

    public double calcularTotal() {
        return calcularManoObra() + calcularTotalRepuestos();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Orden de Trabajo N° ").append(numero).append(" ===\n");
        sb.append("Fecha ingreso: ").append(fechaIngreso).append("\n");
        sb.append("Estado: ").append(estado).append("\n");
        sb.append("Problema: ").append(descripcionProblema).append("\n");
        sb.append("Vehículo: ").append(vehiculo).append("\n");
        sb.append("Mecánico: ").append(mecanico).append("\n");
        sb.append("Horas de mano de obra: ").append(horasManoObra).append("\n");
        sb.append("Repuestos:\n");
        if (detalles.isEmpty()) {
            sb.append("  (sin repuestos)\n");
        }
        for (DetalleOrden d : detalles) {
            sb.append("  - ").append(d).append("\n");
        }
        sb.append("Mano de obra: $").append(calcularManoObra()).append("\n");
        sb.append("Total repuestos: $").append(calcularTotalRepuestos()).append("\n");
        sb.append("TOTAL: $").append(calcularTotal());
        return sb.toString();
    }

    // Igualdad lógica por número de orden
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrdenTrabajo otra = (OrdenTrabajo) o;
        return this.numero == otra.numero;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(numero);
    }
}