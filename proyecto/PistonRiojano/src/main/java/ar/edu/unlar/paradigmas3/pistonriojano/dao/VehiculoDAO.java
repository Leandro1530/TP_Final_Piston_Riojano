/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.edu.unlar.paradigmas3.pistonriojano.dao;
import ar.edu.unlar.paradigmas3.pistonriojano.modelo.Vehiculo;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author gustavo
 */
public interface VehiculoDAO {

    void insertar(Vehiculo vehiculo) throws SQLException;

    void actualizar(Vehiculo vehiculo) throws SQLException;

    void eliminar(String patente) throws SQLException;

    Vehiculo buscarPorPatente(String patente) throws SQLException;

    List<Vehiculo> listarPorCliente(int idCliente) throws SQLException;

    List<Vehiculo> listarTodos() throws SQLException;

    boolean tieneOrdenesTrabajo(String patente) throws SQLException;

}
