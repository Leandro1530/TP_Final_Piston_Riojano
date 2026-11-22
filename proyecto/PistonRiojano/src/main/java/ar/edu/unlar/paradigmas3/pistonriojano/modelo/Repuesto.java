/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.modelo;

import java.util.Objects;
/**
 *
 * @author gustavo
 */
public class Repuesto {
    
    private String codigo;
    private String descripcion;
    private double precioUnitarioActual;
    private int stockDisponible;
    
    public Repuesto(){
    }
    
    public Repuesto(String codigo, String descripcion, double precioUnitarioActual, int stockDisponible){
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioUnitarioActual = precioUnitarioActual;
        this.stockDisponible = stockDisponible;        
    }
    
    public boolean hayStockSuficiente(int cantidadSolicitada){
        return cantidadSolicitada > 0 && this.stockDisponible >= cantidadSolicitada;
    }
    
    public void descontarStock (int cantidad){
        this.stockDisponible -= cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUnitarioActual() {
        return precioUnitarioActual;
    }

    public void setPrecioUnitarioActual(double precioUnitarioActual) {
        this.precioUnitarioActual = precioUnitarioActual;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }
    
    @Override
    public String toString (){
        return codigo + " - " + descripcion + " ($" + precioUnitarioActual + ")";
    }
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repuesto repuesto = (Repuesto) o;
        return Objects.equals(codigo, repuesto.codigo);
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(codigo);
    }
}
