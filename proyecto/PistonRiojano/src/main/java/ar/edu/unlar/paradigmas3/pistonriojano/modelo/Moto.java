/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.modelo;

/**
 *
 * @author gustavo
 */

public class Moto extends Vehiculo {

    public static final int LIMITE_CILINDRADA = 250;
    public static final double COEFICIENTE_BAJA_CILINDRADA = 0.80;
    public static final double COEFICIENTE_ALTA_CILINDRADA = 1.00;

    private int cilindrada;

    public Moto(String patente, String marca, String modelo, int anio, int cilindrada) {
        super(patente, marca, modelo, anio);
        this.cilindrada = cilindrada;
    }

    public Moto(String patente, String marca, String modelo, int anio, Cliente propietario, int cilindrada) {
        super(patente, marca, modelo, anio, propietario);
        this.cilindrada = cilindrada;
    }

    public Moto(Integer id, String patente, String marca, String modelo, int anio, Cliente propietario, int cilindrada) {
        super(id, patente, marca, modelo, anio, propietario);
        this.cilindrada = cilindrada;
    }

    @Override
    public double calcularCoeficiente() {
        if (this.cilindrada <= LIMITE_CILINDRADA) {
            return COEFICIENTE_BAJA_CILINDRADA;
        }
        return COEFICIENTE_ALTA_CILINDRADA;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    @Override
    public String toString() {
        return super.toString() + " [Moto - " + cilindrada + " cc]";
    }
}