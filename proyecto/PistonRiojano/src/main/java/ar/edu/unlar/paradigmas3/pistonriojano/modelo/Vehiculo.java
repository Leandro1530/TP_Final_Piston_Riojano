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
public abstract class Vehiculo {
    
    private String patente;
    private String marca;
    private String modelo;
    private int anio;
    
    public Vehiculo(){
    }
    
    public Vehiculo(String patente, String marca, String modelo, int anio){
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
    }
    
    /**
     * Método polimórfico para obtener el coeficiente de cálculo de mano de obra
     * sin utilizar instanceof.
     */
    public abstract double calcularCoeficiente();

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public String toString() {
        return patente + " - " + marca + " " + modelo + " (" + anio + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(patente, vehiculo.patente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patente);
    }
    
    
}
