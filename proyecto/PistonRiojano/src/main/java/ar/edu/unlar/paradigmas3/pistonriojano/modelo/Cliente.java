/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author river
 */
public class Cliente extends Persona {
    
private String dni;
private String telefono;
private String email;
private List <Vehiculo> vehiculos;

    public Cliente(String dni, String telefono, String email, List<Vehiculo> vehiculos, Integer id, String nombre, String apellido) {
        super(id, nombre, apellido);
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.vehiculos = vehiculos;
    }

    public Cliente(String dni, String telefono, String email, List<Vehiculo> vehiculos) {
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.vehiculos = vehiculos;
    }

    public Cliente() {
        
        this.vehiculos = new ArrayList<>();
        
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.dni);
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
        final Cliente other = (Cliente) obj;
        return Objects.equals(this.dni, other.dni);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    @Override
    public String toString() {
        return "Cliente{" + "dni=" + dni + ", telefono=" + telefono + ", email=" + email + '}';
    }



}
