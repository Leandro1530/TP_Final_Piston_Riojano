/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.modelo;

/**
 *
 * @author Leandro
 */
public class DetalleOrden {

    private int id;
    private Repuesto repuesto;
    private int cantidad;
    private double precioAplicado;

    public DetalleOrden() {
    }

    public DetalleOrden(Repuesto repuesto, int cantidad, double precioAplicado) {
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.precioAplicado = precioAplicado;
    }

    public DetalleOrden(int id, Repuesto repuesto, int cantidad, double precioAplicado) {
        this.id = id;
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.precioAplicado = precioAplicado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Repuesto getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(Repuesto repuesto) {
        this.repuesto = repuesto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioAplicado() {
        return precioAplicado;
    }

    public void setPrecioAplicado(double precioAplicado) {
        this.precioAplicado = precioAplicado;
    }

    // Subtotal de la línea usando el precio congelado (no el precio actual del repuesto)
    public double calcularSubtotal() {
        return this.cantidad * this.precioAplicado;
    }

    @Override
    public String toString() {
        return "DetalleOrden{id=" + id
                + ", repuesto=" + repuesto
                + ", cantidad=" + cantidad
                + ", precioAplicado=" + precioAplicado
                + ", subtotal=" + calcularSubtotal() + "}";
    }
}
