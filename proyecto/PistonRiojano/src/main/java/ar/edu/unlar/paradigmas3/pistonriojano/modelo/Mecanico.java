/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.modelo;

import java.util.Objects;

/**
 *
 * @author river
 */
public class Mecanico extends Persona {
    private Integer legajo;
    private Especialidad especialidad;
    private double valorHora;

    public Mecanico(Integer legajo, Especialidad especialidad, double valorHora, Integer id, String nombre, String apellido) {
        super(id, nombre, apellido);
        this.legajo = legajo;
        this.especialidad = especialidad;
        this.valorHora = valorHora;
    }

    public Mecanico(Integer legajo, Especialidad especialidad, double valorHora) {
        this.legajo = legajo;
        this.especialidad = especialidad;
        this.valorHora = valorHora;
    }

    public Mecanico() {
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.legajo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mecanico other = (Mecanico) obj;
        return Objects.equals(this.legajo, other.legajo);
    }

    public Integer getLegajo() {
        return legajo;
    }

    public void setLegajo(Integer legajo) {
        this.legajo = legajo;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    @Override
    public String toString() {
        return "Mecanico{" + "legajo=" + legajo + ", especialidad=" + especialidad + ", valorHora=" + valorHora + '}';
    }
    
    

}
