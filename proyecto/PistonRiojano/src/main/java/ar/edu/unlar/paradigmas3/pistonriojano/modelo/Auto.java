/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.modelo;

/**
 *
 * @author gustavo
 */

public class Auto extends Vehiculo {

    public static final double COEFICIENTE_NAFTA_GNC = 1.00;
    public static final double COEFICIENTE_DIESEL = 1.20;

    private int cantidadPuertas;
    private TipoCombustible tipoCombustible;

    public Auto(String patente, String marca, String modelo, int anio, int cantidadPuertas, TipoCombustible tipoCombustible) {
        super(patente, marca, modelo, anio);
        this.cantidadPuertas = cantidadPuertas;
        this.tipoCombustible = tipoCombustible;
    }

    public Auto(String patente, String marca, String modelo, int anio, Cliente propietario, int cantidadPuertas, TipoCombustible tipoCombustible) {
        super(patente, marca, modelo, anio, propietario);
        this.cantidadPuertas = cantidadPuertas;
        this.tipoCombustible = tipoCombustible;
    }

    public Auto(Integer id, String patente, String marca, String modelo, int anio, Cliente propietario, int cantidadPuertas, TipoCombustible tipoCombustible) {
        super(id, patente, marca, modelo, anio, propietario);
        this.cantidadPuertas = cantidadPuertas;
        this.tipoCombustible = tipoCombustible;
    }

    @Override
    public double calcularCoeficiente() {
        if (this.tipoCombustible == TipoCombustible.DIESEL) {
            return COEFICIENTE_DIESEL;
        }
        return COEFICIENTE_NAFTA_GNC;
    }

    public int getCantidadPuertas() {
        return cantidadPuertas;
    }

    public void setCantidadPuertas(int cantidadPuertas) {
        this.cantidadPuertas = cantidadPuertas;
    }

    public TipoCombustible getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(TipoCombustible tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    @Override
    public String toString() {
        return super.toString() + " [Auto - " + cantidadPuertas + " puertas, " + tipoCombustible + "]";
    }
}
